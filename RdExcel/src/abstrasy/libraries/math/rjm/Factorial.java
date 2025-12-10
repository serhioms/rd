package abstrasy.libraries.math.rjm;
 
 
//import abstrasy.Interpreter;
 
import java.math.BigInteger;
 
import java.util.Vector;
 
 
/** Factorials.
 * @since 2006-06-25
 * @since 2012-02-15 Storage of the values based on Ifactor, not BigInteger.
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
 */
 
public class Factorial
{
        /** The list of all factorials as a vector.
        */
        static Vector<Ifactor> a = new Vector<Ifactor>() ;
 
        /** ctor().
        * Initialize the vector of the factorials with 0!=1 and 1!=1.
        */
        public Factorial()
        {
                if ( a.size() == 0 )
                {
                        a.add(Ifactor.ONE) ;
                        a.add(Ifactor.ONE) ;
                }
        } /* ctor */
 
        /** Compute the factorial of the non-negative integer.
        * @param n the argument to the factorial, non-negative.
        * @return the factorial of n.
        */
        public BigInteger at(int n)
        {
                /* extend the internal list if needed.
                */
                growto(n) ;
                return a.elementAt(n).n ;
        } /* at */
 
        /** Compute the factorial of the non-negative integer.
        * @param n the argument to the factorial, non-negative.
        * @return the factorial of n.
        */
        public Ifactor toIfactor(int n)
        {
                /* extend the internal list if needed.
                */
                growto(n) ;
                return a.elementAt(n) ;
        } /* at */
 
        /** Extend the internal table to cover up to n!
        * @param n The maximum factorial to be supported.
        * @since 2012-02-15
        */
        private void growto(int n)
        {
                /*
                 * extend the internal list if needed. Size to be 2 for n<=1, 3 for n<=2 etc.
                 */
                // Interpreter.Log("Factorial.growto:" + n );
                while ( a.size() <=n )
                {
                        final int lastn = a.size()-1 ;
                        final Ifactor nextn = new Ifactor(lastn+1) ;
                        a.add(a.elementAt(lastn).multiply(nextn) ) ;
                }
        } /* growto */
 
} /* Factorial */