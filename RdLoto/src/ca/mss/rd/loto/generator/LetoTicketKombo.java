package ca.mss.rd.loto.generator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import ca.mss.rd.util.UtilKombinatorika;
import ca.mss.rd.util.UtilMisc;
import ca.mss.rd.util.model.SubPool;

public class LetoTicketKombo {

	final public int ticketSize; 
	
	final public int poolSize; 
	final public int subPoolSize; 
	
	final public int binomSize = 2; 
	final public int poolLength; 
	final public int factorialSize; 
	
	public LetoTicketKombo(int ticketSize, int poolSize, int subPoolSize) {
		this.ticketSize = ticketSize;
		this.poolSize = poolSize;
		this.subPoolSize = subPoolSize;
		this.poolLength = poolSize*subPoolSize; 
		this.factorialSize = ticketSize-binomSize*subPoolSize; 
	}

	public List<int[]> generateTickets(String pool) {
		
		// Parse pool numbers
		String[] psplit = pool.split(",");

		if( psplit.length != poolLength ){
			throw new RuntimeException(String.format("Expected size of pool (%d) must be (%d)", psplit.length, poolLength));
		}
		
		int[] ipool = new int[poolLength];
		
		for(int k=0; k<ipool.length; k++){
			ipool[k] = Integer.parseInt(psplit[k]);
		}
		
		return generateTickets(ipool);
	}
	
	public List<int[]> generateTickets(int[] ipool) {

		if( ipool.length != poolLength ){
			throw new RuntimeException(String.format("Unexpected pool length %d. Must be %d.", ipool.length, poolLength));
		}
		
		List<int[]> list = new ArrayList<int[]>();

		SubPool[] pool = new SubPool[poolSize];

		// Create POOL object
		for(int i=0,k=0; i<poolSize; i++){
			pool[i] = new SubPool(subPoolSize, i);
			for(int j=0; j<subPoolSize; j++){
				pool[i].subpool[j] = ipool[k++];
			}
		}

		// Create ticket object
		int[] ticket = new int[ticketSize]; 

		// tmp
		Set<Integer> bmap = new HashSet<Integer>(); 
		
		// Start ticket population from binomial part
		Iterator<ArrayList<SubPool>> biter = UtilKombinatorika.getBinomialIterator(pool, binomSize);
		while( biter.hasNext() ){
			ArrayList<SubPool> binom = biter.next();
			
			if( binom.size() != binomSize ){
				throw new RuntimeException(String.format("Expected size of binomial part of ticket (%d) must be (%d)", binom.size(), binomSize));
			}
			
			int t=0;
			bmap.clear();
			
			// binomial part of ticket
			for(int i=0; i<binomSize; i++){
				for(int j=0; j<subPoolSize; j++){
					ticket[t++] = binom.get(i).subpool[j];
				}
				bmap.add(binom.get(i).index);
			}
			
			// continue ticket population from factorial part
			if( factorialSize > 0 ){
				Iterator<Integer[]> fiter = UtilKombinatorika.getFactorialIterator(factorialSize, subPoolSize);
				for(int startt=t; fiter.hasNext(); t=startt ){
					Integer[] factorial = fiter.next();
					
					if( factorial.length != factorialSize ){
						throw new RuntimeException(String.format("Expected size of factorial part of ticket (%d) must be (%d)", factorial.length, factorialSize));
					}
					
					// factorial part of ticket
					for(int i=0,k=0; i<poolSize; i++){
						if( !bmap.contains(i) ){
							ticket[t++] = pool[i].subpool[factorial[k++]];
						}
					}
					list.add(UtilMisc.cloneInt(ticket));
				}
			} else {
				list.add(UtilMisc.cloneInt(ticket));
			}
			
		}
		
		return list;
	}

}
