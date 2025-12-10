package ca.mss.rd.loto.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

import ca.mss.rd.job.AbstractJob;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilSort;

public class LotoJob extends AbstractJob {

	public static final int MAX_POOL_TRY = 100;
	public static final int MAX_POOL_RND = 10000;
	public static final int MAX_TICKET_TRY = 10;

	
	public int pseqViolation, spoolViolation, spoolRight, tseqViolation, tdubViolation;
	
	private int maxPoolSetSize;
	private int[][] pool;
	private AtomicLong isExecuting;
	private LetoTicketKombo ticket;
	private LetoDrawHistory history;
	
	private Set<String> check = new HashSet<String>();
	private List<int[]> tickets = new ArrayList<int[]>(); 

	private int max = 0;
	public List<int[]> ticketsMax = new ArrayList<int[]>(); 
	public List<int[]> poolsMax = new ArrayList<int[]>();
	private Map<Double, Integer> matchDraw = new HashMap<Double, Integer>();
	public boolean isSuccessful;
	
	public LotoJob(LetoTicketKombo ticket, LetoDrawHistory history, int maxPoolSetSize, AtomicLong isExecuting) {
		super(isExecuting.incrementAndGet());
		this.history = history;
		this.ticket = ticket;
		this.isExecuting = isExecuting;
		this.maxPoolSetSize = maxPoolSetSize;
		this.pool = new int[maxPoolSetSize][];
	}
	
	public LotoJob(boolean shutdown) {
		super(shutdown);
	}
	
	@Override
	public void executeJob() {
		long start = UtilDateTime.startWatch();

		int tcount = 0;
		while( tcount++ < MAX_TICKET_TRY ){
			pseqViolation = spoolViolation = spoolRight = tseqViolation = tdubViolation = 0;
			
			// Main logic
			if( !generatePools() ){
				if(Logger.WARNING.isOn ) Logger.WARNING.printf("Simula(%d) failed (%d/%d) to generate pools (%d) in %s", getId(), pseqViolation, spoolViolation, maxPoolSetSize, UtilDateTime.stopWatch(start));
				break;
			}
			
			assert(Logger.LETO_VERBOSE.isOn ? Logger.LETO_VERBOSE.printf("Simula(%d) succeed (%d/%d+%d) to generate pools (%d) in %s", getId(), pseqViolation, spoolViolation, spoolRight, maxPoolSetSize, UtilDateTime.stopWatch(start)): true);

			generateTickets();
			chooseOptimalSet();
		}

		if( tcount < MAX_TICKET_TRY ){
			assert(Logger.LETO_VERBOSE.isOn ? Logger.LETO_VERBOSE.printf("Simula(%d) succeed (%d/%d) to generate tickets (%d) in %s", getId(), pseqViolation, spoolViolation, maxPoolSetSize, UtilDateTime.stopWatch(start)): true);
		} else {
			assert(Logger.LETO_VERBOSE.isOn ? Logger.LETO_VERBOSE.printf("Simula(%d) failed (%d/%d+%d) to generate tickets (%d) in %s", getId(), pseqViolation, spoolViolation, spoolRight, maxPoolSetSize, UtilDateTime.stopWatch(start)): true);
		}

		isSuccessful = ticketsMax.size() > 0;
		
		isExecuting.decrementAndGet();
	}
	
	private void chooseOptimalSet() {

		matchDraw.clear();
		
		for(int[] t: tickets){
			history.matchHistory(matchDraw, t);
		}
		
		int optimal = history.optimalMatch(matchDraw);
		if( optimal > max ){
			max = optimal;
			
			ticketsMax.clear();
			ticketsMax.addAll(tickets);
			
			poolsMax.clear();
			poolsMax.addAll(UtilMisc.toListInt(pool));
		}
	}

	private void generateTickets() {
		check.clear();
		tickets.clear();
		
		tdubViolation = 0;
		tseqViolation = 0;

		for(int i=0; i<pool.length; i++){
			List<int[]> tmp = ticket.generateTickets(pool[i]);
		
			for(int k=0; k<tmp.size(); k++){
				int[] t = UtilSort.sortArray(UtilMisc.cloneSort(tmp.get(k)));

				// Check for sequential violation
				int sum = 0;
				for(int l=0,m=1; m<t.length; l++,m++){
					if( (t[m]-t[l]) == 1 ){
						sum++;
					}
				}
				if( sum > 1 ){
					tseqViolation++;
				} else {
					// Check for doubling
					String ts = UtilMisc.toString(t);
					boolean isViolated = check.contains(ts);
					check.add(ts);

					if( isViolated ){
						tdubViolation++;
					} else {
						tickets.add(tmp.get(k)); // memorize un-sorted tickets
					}
				}
			}
		}
	}
	
	private boolean generatePools() {
		LetoPool49 lp = new LetoPool49();
		
		int pcount = 0;
		do {
			pool[0] = scramblePool(lp);
			pcount++;
		} while( isPoolBad(0) && (pcount < MAX_POOL_TRY) );

		LetoPool49 tmp = new LetoPool49(pool[0]);
		
		for(int i=1; i<maxPoolSetSize; i++){
			pcount = 0;
			do {
				pool[i] = scramblePool(tmp);
				pcount++;
			} while( isPoolBad(i) && (pcount < MAX_POOL_TRY) );
		}

		// print generated pools
		if( Logger.LETO_VERBOSE.isOn ) {
			String str = String.format("Simula(%d) pool[%d]", getId(), pool[0].length);
			for(int k=0; k<pool.length; k++){
				str += String.format("\n[%d]{%s}", k, UtilMisc.toString(pool[k]));
			}
			Logger.LETO_VERBOSE.printf(str);
		}
		
		return pcount < MAX_POOL_TRY;
	}

	// Generate pool logic
	private int[] scramblePool(LetoPool49 lp) {
		lp.randomize(MAX_POOL_RND);
		return UtilMisc.cloneInt(lp.pool, ticket.poolLength);
	}

	// Check pool logic
	private boolean isPoolBad(int n) {
		if( pool[n] != null ){
			
			// Check numbers in subpool are not sequential more then once
			for(int k=0, max=ticket.poolLength-ticket.subPoolSize; k<max; k+=ticket.subPoolSize){
				int[] subpool = UtilSort.sortArray(UtilMisc.cloneInt(pool[n], k, k + ticket.subPoolSize));
				int sum = 0;
				for(int l=0,m=1; m<subpool.length; l++,m++){
					if( (subpool[m]-subpool[l]) == 1 ){
						sum++;
					}
				}
				if( sum > 1 ){
					pseqViolation++;
					assert( Logger.LETO_VERBOSE.isOn ? (pseqViolation % (maxPoolSetSize/100+1) == 0 ? Logger.LETO_VERBOSE.printf("Not valid due to sequential condition (%d)", sum): true): true);
					return true;
				}
			}
			
			for(int j=n; j>0; j-- ){
				// Check for non of pools has same subpool
				for(int k=0; k<ticket.poolLength; k+=ticket.subPoolSize){
					Set<Integer> spoolk = UtilMisc.toSet(pool[j-1], k, k+ticket.subPoolSize);
					int spoolksize = spoolk.size();
					
					for(int m=0; m<ticket.poolLength; m+=ticket.subPoolSize){
						int[] spoolm = UtilMisc.cloneInt(pool[j], m, m+ticket.subPoolSize);
						
						int sum = 0;
						for(int l=0; l<spoolm.length; l++){
							if( spoolk.contains(spoolm[l]) ){
								sum++;
							}
						}
						if( sum == spoolksize ){
							spoolViolation++;
							assert( Logger.LETO_VERBOSE.isOn ?  (spoolViolation % (maxPoolSetSize/100+1) == 0 ? Logger.LETO_VERBOSE.printf("Not valid due to subpool condition (%d)", sum): true): true);
							return true;
						}
					}
				}
			}
			spoolRight++;
		}
		return false;
	}
	
}