package abstrasy.libraries.math.rjm;
 
 
//import abstrasy.Interpreter;
 
import java.math.BigInteger;
 
import java.util.Vector;
 
 
/** Bernoulli numbers.
 * @since 2006-06-25
 * @author Richard J. Mathar
 */
 
/*
 * Subpackage : abstrasy.library.math.rjm
 *
 * Based on org.nevec.rjm (Java library for multi-precision evaluation of basic functions)
 *
 * Copyright (c) Richard J. Mathar <mathar@strw.leidenuniv.nl>, licensed under the LGPL v3.0.
 *
 * Restrictions on combined libraries as of section 5 of the LGPL, lifted/removed by the author.
 *
 * This library is free software; you can redistribute it and/or modify it under the terms of
 * the GNU Lesser General Public License as published by the Free Software Foundation; either
 * version 3 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * library; if not, write to the
 *                                      Free Software Foundation, Inc.
 *                                      51 Franklin St, Fifth Floor
 *                                      Boston, MA 02110-1301 USA
 *
 *
 *----------------------------------------------------------------------------------------------
 * This class has been optimized by l.bruninx, 2012-03-27 (reuse cached data + memoization).
 * However, when n>500, the computing time is still very important.
 *
 */
 
public class Bernoulli {
    /*
        * The list of all Bernoulli numbers as a vector, n=0,2,4,....
        */
    static Vector<Rational> a = new Vector<Rational>();
 
    public Bernoulli() {
        if (a.size() == 0) {
            a.add(Rational.ONE);
            a.add(new Rational(1, 6));
        }
    }
 
    /** Set a coefficient in the internal table.
     * @param n the zero-based index of the coefficient. n=0 for the constant term.
     * @param value the new value of the coefficient.
     */
    protected void set(final int n, final Rational value) {
        //Interpreter.Log("Bernoulli.set:" + n );//+":"+value.toString());
        final int nindx = n / 2;
        if (nindx < a.size())
            a.set(nindx, value);
        else {
            while (a.size() < nindx)
                a.add(Rational.ZERO);
            a.add(value);
        }
    }
 
 
    /** The Bernoulli number at the index provided.
     * @param n the index, non-negative.
     * @return the B_0=1 for n=0, B_1=-1/2 for n=1, B_2=1/6 for n=2 etc
     */
    public Rational at(int n) {
        if (n == 1)
            return (new Rational(-1, 2));
        else if (n % 2 != 0)
            return Rational.ZERO;
        else {
            int nindx = n / 2;
            if (a.size() <= nindx) {
                BigInteger[] cacheInt = new BigInteger[n + 2]; // optimization (cache) by l.bruninx, 2012-03-27
                TPow tpow=new TPow(n+2);
                for (int i = a.size() * 2; i <= n; i += 2)
                    set(i, doubleSum(i, cacheInt, tpow));
            }
            return a.elementAt(nindx);
        }
    }
 
    /**
     *
     * @param i
     * @param cache
     * @return
     */
    private final static BigInteger _getBigInt_(int i, BigInteger[] cache) {
        if (cache[i] != null)
            return cache[i];
        cache[i] = new BigInteger("" + i);
        return cache[i];
    }
   
    /**
     * Private internal class to cache precedent results to spare time when compute pow operation.
     */
    private static class TPow{
        private BigInteger[] integer;
        private int[] power;
       
        TPow(int size){
            integer=new BigInteger[size];
            power=new int[size];
        }
    }
   
    /**
     * This internal method is used to compute pow only if necessary. It's more efficient to reuse
     * the last result ( @ pow-2 ) -> x.pow(n-2).mul(x).mul(x) = x.pow(n)...
     *
     * So x.pow(n) is the result of x.mul(x).mul(x) ... n times...
     *
     * @param i
     * @param npow
     * @param tpow
     * @param cache
     * @return
     */
    private final static BigInteger _getBigPow_(int i,int npow, TPow tpow, BigInteger[] cache){
        if(tpow.integer[i]!=null){
            if(tpow.power[i]==npow-2){
                BigInteger bi=_getBigInt_(i,cache);
                tpow.integer[i]=tpow.integer[i].multiply(bi).multiply(bi);
                tpow.power[i]=npow;
                return tpow.integer[i];
            }
        }
 
        BigInteger bi=_getBigInt_(i,cache);
        tpow.integer[i]=bi.pow(npow);
        tpow.power[i]=npow;
        return tpow.integer[i];
    }
   
    /*
        * Generate a new B_n by a standard double sum.
        * @param n The index of the Bernoulli number.
        * @return The Bernoulli number at n.
        */
 
    private Rational doubleSum(int n, BigInteger[] cacheInt, TPow tpow) {
 
 
        /* optimization (memoization) by l.bruninx, 2012-03-27 */
        int start_at_k = 0;
        Rational start_at = Rational.ZERO;
        if (a.size() > 0 && n > a.size() * 2) {
            start_at_k = a.size() * 2;
            start_at = a.elementAt(a.size() - 1);
        }
        /* optimization (memoization) */
 
        Rational resul = start_at; // modified by l.bruninx, 2012-03-27.
        for (int k = start_at_k; k <= n; k++) //modified by l.bruninx, 2012-03-27.
        {
            Rational jsum = Rational.ZERO;
            BigInteger bin = BigInteger.ONE;
 
            for (int j = 0; j <= k; j++) {
                BigInteger jpown = _getBigPow_(j,n,tpow,cacheInt);//(_getBigInt_(j, cacheInt)).pow(n); // optimization by l.bruninx, 2012-03-27
                if (j % 2 == 0)
                    jsum = jsum.add(bin.multiply(jpown));
                else
                    jsum = jsum.subtract(bin.multiply(jpown));
 
                /*
                 * update binomial(k,j) recursively
                 */
                bin = bin.multiply(_getBigInt_(k - j, cacheInt)).divide(_getBigInt_(j + 1, cacheInt));
            }
            resul = resul.add(jsum.divide(_getBigInt_(k + 1, cacheInt)));
        }
        return resul;
    }
 
 
} /* Bernoulli */