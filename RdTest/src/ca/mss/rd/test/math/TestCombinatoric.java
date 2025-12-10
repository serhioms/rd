package ca.mss.rd.test.math;

import java.util.Enumeration;
import java.util.List;

import com.objectwave.utility.Combinations;
import com.objectwave.utility.Combinatoric;

public class TestCombinatoric {

	public static void main(String[] args) throws Exception {
		System.out.println(Combinatoric.factorial(10));
		System.out.println(Combinatoric.p(10));
		System.out.println(Combinatoric.p(5, 3));
		
		System.out.println(Combinatoric.c(18, 3));
		Enumeration<?> comb = new Combinations<Integer>(new Integer[]{1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18}, 3);
		for(int count=1; comb.hasMoreElements(); count++ ){
			@SuppressWarnings("unchecked")
			List<Integer> set = (List<Integer>) comb.nextElement();
			System.out.println(count+") "+set);
		}
	}

}
