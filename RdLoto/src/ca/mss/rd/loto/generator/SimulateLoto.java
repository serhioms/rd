package ca.mss.rd.loto.generator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import ca.mss.rd.job.AbstractJob;
import ca.mss.rd.job.JobPoolParallel;
import ca.mss.rd.util.Logger;
import ca.mss.rd.util.UtilDateTime;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.UtilSort;


public class SimulateLoto {

	private final int maxTicketsPrint;
	private final int maxSimulations;
	private final int maxPoolSet;
	private final int maxPoolSetSize;
	
	private final LetoTicketKombo ticket; 
	private final LetoDrawHistory history;
	
	private AtomicInteger ttlPSeqViolation = new AtomicInteger(0);
	private AtomicInteger ttlSPoolViolation = new AtomicInteger(0);
	private AtomicInteger ttlTSecViolation = new AtomicInteger(0);
	private AtomicInteger ttlDubViolation = new AtomicInteger(0);
	
	private int count;
	private volatile AtomicLong isExecuting = new AtomicLong(0);

	private int max = 0;
	private List<int[]> ticketsMax = new ArrayList<int[]>(); 
	private List<int[]> poolsMax = new ArrayList<int[]>(); 
	private Map<Double, Integer> matchDraw = new HashMap<Double, Integer>();

	public SimulateLoto(int maxSimulations, int maxPoolSet, int maxPoolSetSize, LetoTicketKombo ticket, LetoDrawHistory history, int maxTicketsPrint) {
		this.maxTicketsPrint = maxTicketsPrint;
		this.maxSimulations = maxSimulations;
		this.maxPoolSet = maxPoolSet;
		this.maxPoolSetSize = maxPoolSetSize;
		this.ticket = ticket;
		this.history = history;
	}


	public void simulateAll() throws InterruptedException {
		long start = UtilDateTime.startWatch();

		for(int i=0; i<maxSimulations; i++){
			simulateOne();
		}

		if( Logger.LETO_SIMULATE.isOn ) {
			String oldSorted = null;
			Logger.LETO_SIMULATE.printf("Pools:");
			for(int i=0,maxi=poolsMax.size(); i<maxi; i++){
				String sorted = UtilMisc.toSortedString(UtilMisc.cloneInt(poolsMax.get(i)), "%-2d");
				Logger.LETO_SIMULATE.printf("%3d)\t%s\t%s", i+1, UtilMisc.toString(poolsMax.get(i), "%-2d"),  sorted.equals(oldSorted)?"": sorted);
				oldSorted = sorted;
			}
		}

		if( Logger.LETO_SIMULATE.isOn ) {
			Logger.LETO_SIMULATE.printf("Tickets:");
			int step = Math.max(1, ticketsMax.size()/maxTicketsPrint);
			for(int i=0,c=1,maxi=ticketsMax.size(); i<maxi; i+=step,c++){
				Logger.LETO_SIMULATE.printf("%3d)\t %s\t\t%s", c, UtilMisc.toSortedCloneString(ticketsMax.get(i), "%-2d"), UtilMisc.toString(ticketsMax.get(i), "%-2d"));
			}
		}

		assert( Logger.LETO_SIMULATE.isOn ? Logger.LETO_SIMULATE.printf("Done [simulation=%-5d][poolsMax=%-3d][subpool=%-3d] = Violations [seq=%-5d][subpool=%-6d][tsec=%-6d][tdub=%-3d] in %s", 
				maxSimulations, 
				maxPoolSet, 
				maxPoolSetSize, 
				ttlPSeqViolation.get(), 
				ttlSPoolViolation.get(),
				ttlTSecViolation.get(),
				ttlDubViolation.get(),
				UtilDateTime.stopWatch(start)): true);
	}

	
	public void simulateOne() throws InterruptedException {
		JobPoolParallel<LotoJob> jpool = new JobPoolParallel<LotoJob>();
		
		LotoJob[] jobs = new LotoJob[maxPoolSet];
				
		for(int i=0, maxi=maxPoolSet; i<maxi; i++){
			jobs[i] = new LotoJob(ticket, history, maxPoolSetSize, isExecuting);
			jpool.queueJob(jobs[i]);
		}
		
		jpool.startPool();
		
		while( isExecuting.get() > 0 ){
			Thread.sleep(10L);
		}
		
		jpool.shutdownPool(new LotoJob(AbstractJob.SHUTDOWN));

		// Choose the best set from each job
		for(int k=0; k<jobs.length; k++){
			LotoJob job = jobs[k];
			if( chooseOptimalSet(job.ticketsMax, job.poolsMax) ){
				ttlPSeqViolation.addAndGet(job.pseqViolation);
				ttlSPoolViolation.addAndGet(job.spoolViolation);
			}
		}
	}
	
	private boolean chooseOptimalSet(List<int[]> tickets, List<int[]> pools) {

		if( ++count % 10 == 0 ){
			Logger.LETO_SIMULATE.printf("%5d) Check if set of tickets is historically optimal...", count);
		}
		
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
			poolsMax.addAll(pools);

			Logger.LETO_SIMULATE.printf("New max (p=%d,t=%d)=%3d in history %s[%2d]", poolsMax.size(), ticketsMax.size(), max, UtilSort.sortNumber(matchDraw).toString(), history.getHistory().size());
			
			return true;
		} else {
			return false;
		}
	}

}
