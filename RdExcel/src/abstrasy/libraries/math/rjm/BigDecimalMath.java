package abstrasy.libraries.math.rjm;
 
 
//import abstrasy.Interpreter;
 
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
 
import java.security.ProviderException;
 
 
/**
 *
 * BigDecimal special functions.
 * <a href="http://arxiv.org/abs/0908.3030">A Java Math.BigDecimal Implementation of Core Mathematical Functions</a>
 * @since 2009-05-22
 * @author Richard J. Mathar
 * @see <a href="http://apfloat.org/">apfloat</a>
 * @see <a href="http://dfp.sourceforge.net/">dfp</a>
 * @see <a href="http://jscience.org/">JScience</a>
 *
 */
 
/*
 * Subpackage : abstrasy.library.math.rjm (ver:0.1.0)
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
 * -------------------------------------------------------------------------------------------
 *
 * This file is a modified version of the original BigDecimalMath class from org.nevec.rjm
 * (Richard J. Mathar). All Modifications has been made to support calculations with an
 * arbitrary (or explicite) degree of precision.
 *
 */
 
public class BigDecimalMath {
 
    /**
     * Optimization for constant values (l.bruninx, 19-mar-2012):
     *   NEG_ONE : -1
     *   TWO     : 2
     */
    private final static BigDecimal NEG_ONE = BigDecimal.ONE.negate();
    private final static BigDecimal TWO = new BigDecimal(2);
   
    /**
     * Internal method:
     * ---------------
     * Create a new MathContext with a precision+2...
     *
     * @param mc
     * @return new MathContect with mc precision +2...
     *
     * @author l.bruninx, 2012-03-22.
     * @since 2012-03-22
     *
     */
    private static final MathContext _mc_plus2_(MathContext mc){
        return new MathContext(mc.getPrecision() + 1, mc.getRoundingMode()); // more precision...
    }
   
    /**
     * Internal method:
     * ---------------
     *
     * Determine the common precision of the two BigDecimal numbers. If numbers have no
     * decimal precision (like 2.5 and 1.6) the method return 0. If they have some precision
     * (like 2.5679 and 2.5683), the method return the common precision (like 2 for 2.56xx).
     *
     * @param x
     * @param y
     * @return int common precision of x and y.
     *
     * @author l.bruninx, 2012-03-27.
     * @since 2012-03-27
     */
    private static final int _common_precision_of_(BigDecimal x, BigDecimal y){
        BigDecimal a_x = x.abs();
        BigDecimal f_x = floor(a_x);
        a_x=a_x.subtract(f_x);
        BigDecimal a_y = y.abs();
        BigDecimal f_y = floor(a_y);
        a_y=a_y.subtract(f_y);
        if(f_x.compareTo(f_y)!=0)return 0;
        BigDecimal res;
        if(a_x.compareTo(a_y)>0){
            res=a_x.subtract(a_y);
        }
        else{
            res=a_y.subtract(a_x);
        }
        int p=0;
        while(res.movePointRight(++p).compareTo(BigDecimal.ONE)<0);
        return p-1;
    }
 
    /***************************************
     *
     * Utilities by l.bruninx, 2012-03-28
     *
     ***************************************/
   
    /**
     * get digits10 of the Decimal.
     *
     * Note: 0.xxxxx give 0 digits10, 1.xxxx give 1 digits10, 23 give 2 digits10, etc...
     *
     * @param x BigDecimal
     * @return int count of digits of the integer part in base 10.
     * @author l.bruninx
     * @since 2012-03-28
     */
    public static int getIntDigitsCnt(BigDecimal x){
        //int digits;
        //BigDecimal value=x.abs();
        //for (digits = 1; value.compareTo(BigDecimal.ONE)>0; digits++) value=value.movePointLeft(1);
        //return digits-1;
        // simply optimized by...
        return x.precision()-x.scale();
    }
   
    final private static BigDecimal _dec_round_(BigDecimal r, MathContext mc){
        int digits=r.precision()-r.scale();
        return r.round(new MathContext(digits+mc.getPrecision(),mc.getRoundingMode()));
    }
   
    final private static MathContext _mc_adjust_(BigDecimal r, MathContext mc){
        int digits=r.precision()-r.scale();
        // Interpreter.Log("BigDecimalMath._mc_adjust_:"+digits);
        return new MathContext(digits+mc.getPrecision(),mc.getRoundingMode());
    }
   
    final private static MathContext _mc_adjust_exp_(BigDecimal r, MathContext mc){
        int digits=r.precision()-r.scale();
        double t1=Math.pow(2.718,Math.abs(digits+2));
        // precision too big???
        if(t1>10000)                              
            digits=10000;
        else
            digits=(int)t1;
        // Interpreter.Log("BigDecimalMath._mc_adjust_exp_:"+digits);
        return new MathContext(digits+mc.getPrecision(),mc.getRoundingMode());
    }
   
    final private static MathContext _mc_adjust_pow_(BigDecimal r, BigDecimal z, MathContext mc){
        int digits=r.precision()-r.scale();
        int digits2=z.precision()-z.scale();
        double t1=Math.pow(Math.abs(digits2+2),Math.abs(digits+2));
        // precision too big???
        if(t1>10000)
            digits=10000;
        else
            digits=(int)t1;
        // Interpreter.Log("BigDecimalMath._mc_adjust_pow_:"+digits);
        return new MathContext(digits+mc.getPrecision(),mc.getRoundingMode());
    }
   
   
 
 
 
    /**
     *
     * Constants are moved to Const class.
     *
     * Modification by l.bruninx.
     *
     */
   
    static private BigDecimal pi_cache = Const.PI; // added by l.bruninx, 2012-03-26.
   
    static public BigDecimal pi(final MathContext mc) {
        //System.out.print("(pi:"+mc.getPrecision()+")");
        /* look it up if possible */
        if (mc.getPrecision() < Const.PI.precision())
            return Const.PI.round(mc);
        else {
            /*
             * cache optimisation...
             */
            synchronized(Const.PI){                                   // added by l.bruninx, 2012-03-26.
               if (mc.getPrecision() < pi_cache.precision())          // added by l.bruninx, 2012-03-26.
                   return pi_cache.round(mc);                         // added by l.bruninx, 2012-03-26.
                else if ((mc.getPrecision()==pi_cache.precision()))   // added by l.bruninx, 2012-03-26.
                   return pi_cache;                                   // added by l.bruninx, 2012-03-26.
            }
            /*
             * Broadhurst <a href="http://arxiv.org/abs/math/9803067">arXiv:math/9803067</a>
             */
            int[] a = { 1,
                0,
                0,
                -1,
                -1,
                -1,
                0,
                0 };
            BigDecimal S = broadhurstBBP(1, 1, a, mc).multiply(new BigDecimal(8),mc);
            /*
             * cache optimisation...
             */
            synchronized(Const.PI){                                   // added by l.bruninx, 2012-03-26.
               if (S.precision() > pi_cache.precision())pi_cache=S;   // added by l.bruninx, 2012-03-26.
            }                                                         // added by l.bruninx, 2012-03-26.
            return S;
        }
    } /* BigDecimalMath.pi */
 
    /** Euler-Mascheroni constant.
     * @param mc The required precision of the result.
     * @return 0.577...
     * @since 2009-08-13
     *
     * Modified by l.bruninx, 2012-03-26 for to use an autodaptative algorithm.
     *
     * So, some bugs are found when compute greater precision:
     *
     * (Math:euler 1490) -> External error (java.lang.IllegalArgumentException: Digits < 0)
     *
     * This is due to the zeta method.
     *
     */
    static public BigDecimal gamma(MathContext mc) {
        /* look it up if possible */
        if (mc.getPrecision() < Const.GAMMA.precision())
            return Const.GAMMA.round(mc);
        else {
            //double eps = prec2err(0.577, mc.getPrecision()); // removed by l.bruninx, 2012-03-26
 
            /*
             * Euler-Stieltjes as shown in Dilcher, Aequat Math 48 (1) (1994) 55-85
             */
            MathContext mcloc = new MathContext(2 + mc.getPrecision());
            BigDecimal resul = BigDecimal.ONE;
            resul = resul.add(log(new BigDecimal(2), mcloc));
            resul = resul.subtract(log(new BigDecimal(3), mcloc));
 
            /* how many terms: zeta-1 falls as 1/2^(2n+1), so the
             * terms drop faster than 1/2^(4n+2). Set 1/2^(4kmax+2) < eps.
             * Leading term zeta(3)/(4^1*3) is 0.017. Leading zeta(3) is 1.2. Log(2) is 0.7
             */
            //int kmax = (int) ((Math.log(eps / 0.7) - 2.) / 4.);       // removed by l.bruninx, 2012-03-26
            //mcloc = new MathContext(1 + err2prec(1.2, eps / kmax));   // removed by l.bruninx, 2012-03-26
            int n = Const.GAMMA.precision();//1; // modified by l.bruninx, 2012-03-26
            /**
             * n : precision ???
             */
            BigDecimal old_resul=Const.GAMMA;
            Bernoulli bern_cache=new Bernoulli();
            Factorial fact_cache=new Factorial();
            //bern_cache.at(mc.getPrecision()*2);
            //fact_cache.at(mc.getPrecision()*2);
            // Interpreter.Log("Start gamma:");
            do{        // modified by l.bruninx, 2012-03-26
                old_resul=resul.round(mc);
                /*
                 * zeta is close to 1. Division of zeta-1 through
                 * 4^n*(2n+1) means divion through roughly 2^(2n+1)
                 */
                //System.out.print("A("+(2 * n + 1)+"):");
                BigDecimal c = zeta(2 * n + 1, mcloc,bern_cache,fact_cache).subtract(BigDecimal.ONE,mcloc);
                //System.out.print("B");
                BigInteger fourn = new BigInteger("" + (2 * n + 1));
                fourn = fourn.shiftLeft(2 * n);
                c = c.divide(new BigDecimal(fourn),mcloc);
                resul = resul.subtract(c,mcloc);
                //if (c.doubleValue() < 0.1 * eps)                      // removed by l.bruninx, 2012-03-26
                //    break;                                            // removed by l.bruninx, 2012-03-26
               
 
                //if(Interpreter.isDebugMode()){
                    // Interpreter.Log("gamma Step:"+resul.round(new MathContext(_common_precision_of_(old_resul,resul))).toPlainString());
                //}
               
                n++;
            }while(resul.round(mc).compareTo(old_resul)!=0); // modified by l.bruninx, 2012-03-26
            return resul.round(mc);
        }
 
    } /* BigDecimalMath.gamma */
 
 
    /** The square root.
     * @param x the non-negative argument.
     * @param mc Arbitrary MathContext (added/modified by l.bruninx 19-mar-2012).
     * @return the square root of the BigDecimal.
     * @since 2008-10-27
     */
    static public BigDecimal sqrt(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of square root");
        if (x.abs().subtract(new BigDecimal(Math.pow(10., -mc.getPrecision()))).compareTo(BigDecimal.ZERO) < 0)
            return BigDecimalMath.scalePrec(BigDecimal.ZERO, mc);
        /* start the computation from a double precision estimate */
        BigDecimal s = new BigDecimal(Math.sqrt(x.doubleValue()), mc);
        final BigDecimal half = new BigDecimal("2");
 
        /* increase the local accuracy by 2 digits */
        MathContext locmc = new MathContext(mc.getPrecision() + 2, mc.getRoundingMode());
 
        /*
         * relative accuracy requested is 10^(-precision)
         */
        //final double eps = Math.pow(10.0, -mc.getPrecision()); // removed by l.bruninx, 2012-03-23
        BigDecimal olds;
        do {
            olds=_dec_round_(s,mc);
            /* s = s -(s/2-x/2s); test correction s-x/s for being
             * smaller than the precision requested. The relative correction is 1-x/s^2,
             * (actually half of this, which we use for a little bit of additional protection).
             */
            //if (Math.abs(BigDecimal.ONE.subtract(x.divide(s.pow(2, locmc), locmc)).doubleValue()) < eps)
            //    break;                                         // removed by l.bruninx, 2012-03-23
            s = s.add(x.divide(s, locmc)).divide(half, locmc);
            /* debugging
             * System.out.println("itr "+x.round(locmc).toString() + " " + s.round(locmc).toString()) ;
             */
        }while(_dec_round_(s,mc).compareTo(olds)!=0); // added by l.bruninx, 2012-03-23
        return _dec_round_(s,mc); // modified by l.bruninx, 2012-03-19
    } /* BigDecimalMath.sqrt */
 
    /** The square root.
     * @param x the non-negative argument.
     * @return the square root of the BigDecimal rounded to the precision implied by x.
     * @since 2009-06-25
     */
    static public BigDecimal sqrt(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of square root");
 
        return root(2, x);
    } /* BigDecimalMath.sqrt */
 
    /** The cube root.
     * @param x The argument.
     * @return The cubic root of the BigDecimal rounded to the precision implied by x.
     * The sign of the result is the sign of the argument.
     * @since 2009-08-16
     */
    static public BigDecimal cbrt(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return root(3, x.negate()).negate();
        else
            return root(3, x);
    } /* BigDecimalMath.cbrt */
 
    /** The integer root.
     * @param n the positive argument.
     * @param x the non-negative argument.
     * @return The n-th root of the BigDecimal rounded to the precision implied by x, x^(1/n).
     * @since 2009-07-30
     */
    static public BigDecimal root(final int n, final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("negative argument " + x.toString() + " of root");
        if (n <= 0)
            throw new ArithmeticException("negative power " + n + " of root");
 
        if (n == 1)
            return x;
 
        /* start the computation from a double precision estimate */
        BigDecimal s = new BigDecimal(Math.pow(x.doubleValue(), 1.0 / n));
 
        /*
         * this creates nth with nominal precision of 1 digit
         */
        final BigDecimal nth = new BigDecimal(n);
 
        /* Specify an internal accuracy within the loop which is
         * slightly larger than what is demanded by 'eps' below.
         */
        final BigDecimal xhighpr = scalePrec(x, 2);
        MathContext mc = new MathContext(2 + x.precision());
 
        /*
         * Relative accuracy of the result is eps.
         */
        final double eps = x.ulp().doubleValue() / (2 * n * x.doubleValue());
        for (; ; ) {
            /* s = s -(s/n-x/n/s^(n-1)) = s-(s-x/s^(n-1))/n; test correction s/n-x/s for being
             * smaller than the precision requested. The relative correction is (1-x/s^n)/n,
             */
            BigDecimal c = xhighpr.divide(s.pow(n - 1), mc);
            c = s.subtract(c);
            MathContext locmc = new MathContext(c.precision());
            c = c.divide(nth, locmc);
            s = s.subtract(c);
            if (Math.abs(c.doubleValue() / s.doubleValue()) < eps)
                break;
        }
        return s.round(new MathContext(err2prec(eps)));
    } /* BigDecimalMath.root */
 
    /** The hypotenuse.
     * @param x the first argument.
     * @param y the second argument.
     * @return the square root of the sum of the squares of the two arguments, sqrt(x^2+y^2).
     * @since 2009-06-25
     */
    static public BigDecimal hypot(final BigDecimal x, final BigDecimal y) {
        /*
         * compute x^2+y^2
         */
        BigDecimal z = x.pow(2).add(y.pow(2));
 
        /* truncate to the precision set by x and y. Absolute error = 2*x*xerr+2*y*yerr,
         * where the two errors are 1/2 of the ulp's.  Two intermediate protectio digits.
         */
        BigDecimal zerr = x.abs().multiply(x.ulp()).add(y.abs().multiply(y.ulp()));
        MathContext mc = new MathContext(2 + err2prec(z, zerr));
 
        /* Pull square root */
        z = sqrt(z.round(mc));
 
        /*
         * Final rounding. Absolute error in the square root is (y*yerr+x*xerr)/z, where zerr holds 2*(x*xerr+y*yerr).
         */
        mc = new MathContext(err2prec(z.doubleValue(), 0.5 * zerr.doubleValue() / z.doubleValue()));
        return z.round(mc);
    } /* BigDecimalMath.hypot */
 
    /** The hypotenuse.
     * @param n the first argument.
     * @param x the second argument.
     * @return the square root of the sum of the squares of the two arguments, sqrt(n^2+x^2).
     * @since 2009-08-05
     */
    static public BigDecimal hypot(final int n, final BigDecimal x) {
        /*
         * compute n^2+x^2 in infinite precision
         */
        BigDecimal z = (new BigDecimal(n)).pow(2).add(x.pow(2));
 
        /* Truncate to the precision set by x. Absolute error = in z (square of the result) is |2*x*xerr|,
         * where the error is 1/2 of the ulp. Two intermediate protection digits.
         * zerr is a signed value, but used only in conjunction with err2prec(), so this feature does not harm.
         */
        double zerr = x.doubleValue() * x.ulp().doubleValue();
        MathContext mc = new MathContext(2 + err2prec(z.doubleValue(), zerr));
 
        /* Pull square root */
        z = sqrt(z.round(mc));
 
        /* Final rounding. Absolute error in the square root is x*xerr/z, where zerr holds 2*x*xerr.
         */
        mc = new MathContext(err2prec(z.doubleValue(), 0.5 * zerr / z.doubleValue()));
        return z.round(mc);
    } /* BigDecimalMath.hypot */
 
 
   
 
    /** The exponential function.
     * @param x the argument.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return exp(x).
     *
     * The precision of the result is explicitly defined by the MathContext argument.
     *
     * @since 2012-03-21
     * @author l.bruninx
     *
     * Based on Richard J. Mathar exp(BigDecimal) method.
     */
    static public BigDecimal exp(BigDecimal x, MathContext mc) {
        /*
         * To calculate the value if x is negative, use exp(-x) = 1/exp(x)
         */
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            final BigDecimal invx = exp(x.negate());
            /*
             * Relative error in inverse of invx is the same as the relative errror in invx.
             * This is used to define the precision of the result.
             */
            //MathContext mc = new MathContext(invx.precision());
            return BigDecimal.ONE.divide(invx, mc);
        }
        else if (x.compareTo(BigDecimal.ZERO) == 0) {
            /* recover the valid number of digits from x.ulp(), if x hits the
             * zero. The x.precision() is 1 then, and does not provide this information.
             */
            return new BigDecimal(1, mc); // (BigDecimal.ONE, -(int) (Math.log10(x.ulp().doubleValue())));
        }
        else {
            /*
             * Use exp(x) = 1 + x/1! + x2/2! + x3/3! + ... until max precision (no more modifications of the rounding value).
             *
             */
 
            BigDecimal e = BigDecimal.ONE;
            BigDecimal t = BigDecimal.ONE;
            BigDecimal olde = e;
            MathContext nmc = _mc_adjust_exp_(x,mc); // more precision (exponent case)...
            int i = 1;
            do {
                olde = _dec_round_(e,mc);
                t = t.multiply(x.divide(new BigDecimal(i++),nmc),nmc);
                e = e.add(t);
                if(i==Integer.MAX_VALUE) break;  // max of the calculation
            }
            while (_dec_round_(e,mc).compareTo(olde) != 0);
            return _dec_round_(e,mc);
        }
    } /* BigDecimalMath.exp */
   
 
    /** A suggestion for the maximum numter of terms in the Taylor expansion of the exponential.
     */
    static private int TAYLOR_NTERM = 8;
    /** The exponential function.
     * @param x the argument.
     * @return exp(x).
     * The precision of the result is implicitly defined by the precision in the argument.
     * In particular this means that "Invalid Operation" errors are thrown if catastrophic
     * cancellation of digits causes the result to have no valid digits left.
     * @since 2009-05-29
     * @author Richard J. Mathar
     */
    static public BigDecimal exp(BigDecimal x) {
        /*
         * To calculate the value if x is negative, use exp(-x) = 1/exp(x)
         */
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            final BigDecimal invx = exp(x.negate());
            /*
             * Relative error in inverse of invx is the same as the relative errror in invx.
             * This is used to define the precision of the result.
             */
            MathContext mc = new MathContext(invx.precision());
            return BigDecimal.ONE.divide(invx, mc);
        }
        else if (x.compareTo(BigDecimal.ZERO) == 0) {
            /* recover the valid number of digits from x.ulp(), if x hits the
             * zero. The x.precision() is 1 then, and does not provide this information.
             */
            return scalePrec(BigDecimal.ONE, -(int) (Math.log10(x.ulp().doubleValue())));
        }
        else {
            /*
             * Push the number in the Taylor expansion down to a small
             * value where TAYLOR_NTERM terms will do. If x<1, the n-th term is of the order
             * x^n/n!, and equal to both the absolute and relative error of the result
             * since the result is close to 1. The x.ulp() sets the relative and absolute error
             * of the result, as estimated from the first Taylor term.
             * We want x^TAYLOR_NTERM/TAYLOR_NTERM! < x.ulp, which is guaranteed if
             * x^TAYLOR_NTERM < TAYLOR_NTERM*(TAYLOR_NTERM-1)*...*x.ulp.
             *
             */
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue();
            if (Math.pow(xDbl, TAYLOR_NTERM) < TAYLOR_NTERM * (TAYLOR_NTERM - 1.0) * (TAYLOR_NTERM - 2.0) * xUlpDbl) {
                /*
                 * Add TAYLOR_NTERM terms of the Taylor expansion (Euler's sum formula)
                 */
                BigDecimal resul = BigDecimal.ONE;
 
                /* x^i */
                BigDecimal xpowi = BigDecimal.ONE;
 
                /* i factorial */
                BigInteger ifac = BigInteger.ONE;
 
                /*
                 * TAYLOR_NTERM terms to be added means we move x.ulp() to the right
                 * for each power of 10 in TAYLOR_NTERM, so the addition won't add noise beyond
                 * what's already in x.
                 */
                MathContext mcTay = new MathContext(err2prec(1., xUlpDbl / TAYLOR_NTERM));
                for (int i = 1; i <= TAYLOR_NTERM; i++) {
                    ifac = ifac.multiply(new BigInteger("" + i));
                    xpowi = xpowi.multiply(x);
                    final BigDecimal c = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(c);
                    if (Math.abs(xpowi.doubleValue()) < i && Math.abs(c.doubleValue()) < 0.5 * xUlpDbl)
                        break;
                }
                /*
                 * exp(x+deltax) = exp(x)(1+deltax) if deltax is <<1. So the relative error
                 * in the result equals the absolute error in the argument.
                 */
                MathContext mc = new MathContext(err2prec(xUlpDbl / 2.));
                return resul.round(mc);
            }
            else {
                /*
                 * Compute exp(x) = (exp(0.1*x))^10. Division by 10 does not lead
                 * to loss of accuracy.
                 */
                int exSc = (int) (1.0 - Math.log10(TAYLOR_NTERM * (TAYLOR_NTERM - 1.0) * (TAYLOR_NTERM - 2.0) * xUlpDbl / Math.pow(xDbl, TAYLOR_NTERM)) / (TAYLOR_NTERM - 1.0));
                BigDecimal xby10 = x.scaleByPowerOfTen(-exSc);
                BigDecimal expxby10 = exp(xby10);
 
                /*
                 * Final powering by 10 means that the relative error of the result
                 * is 10 times the relative error of the base (First order binomial expansion).
                 * This looses one digit.
                 */
                MathContext mc = new MathContext(expxby10.precision() - exSc);
                /* Rescaling the powers of 10 is done in chunks of a maximum of 8 to avoid an invalid operation
                 * response by the BigDecimal.pow library or integer overflow.
                 */
                while (exSc > 0) {
                    int exsub = Math.min(8, exSc);
                    exSc -= exsub;
                    MathContext mctmp = new MathContext(expxby10.precision() - exsub + 2);
                    int pex = 1;
                    while (exsub-- > 0)
                        pex *= 10;
                    expxby10 = expxby10.pow(pex, mctmp);
                }
                return expxby10.round(mc);
            }
        }
    } /* BigDecimalMath.exp */
 
    /** The base of the natural logarithm.
     * @param mc the required precision of the result
     * @return exp(1) = 2.71828....
     * @since 2009-05-29
     */
    static public BigDecimal exp(final MathContext mc) {
        /* look it up if possible */
        if (mc.getPrecision() < Const.E.precision())
            return Const.E.round(mc);
        else {
            /* Instantiate a 1.0 with the requested pseudo-accuracy
             * and delegate the computation to the public method above.
             */
            BigDecimal uni = scalePrec(BigDecimal.ONE, mc.getPrecision());
            return exp(uni,mc); // modified by l.bruninx, 2012-03-22.
        }
    } /* BigDecimalMath.exp */
 
    /** The natural logarithm.
     * @param x the argument.
     * @return ln(x).
     * The precision of the result is implicitly defined by the precision in the argument.
     * @since 2009-05-29
     * @author Richard J. Mathar
     */
    static public BigDecimal log(BigDecimal x) {
        /*
         * the value is undefined if x is negative.
         */
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot take log of negative " + x.toString());
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            /* log 1. = 0. */
            return scalePrec(BigDecimal.ZERO, x.precision() - 1);
        }
        else if (Math.abs(x.doubleValue() - 1.0) <= 0.3) {
            /* The standard Taylor series around x=1, z=0, z=x-1. Abramowitz-Stegun 4.124.
             * The absolute error is err(z)/(1+z) = err(x)/x.
             */
            BigDecimal z = scalePrec(x.subtract(BigDecimal.ONE), 2);
            BigDecimal zpown = z;
            double eps = 0.5 * x.ulp().doubleValue() / Math.abs(x.doubleValue());
            BigDecimal resul = z;
            for (int k = 2; ; k++) {
                zpown = multiplyRound(zpown, z);
                BigDecimal c = divideRound(zpown, k);
                if (k % 2 == 0)
                    resul = resul.subtract(c);
                else
                    resul = resul.add(c);
                if (Math.abs(c.doubleValue()) < eps)
                    break;
            }
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        }
        else {
            final double xDbl = x.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue();
 
            /* Map log(x) = log root[r](x)^r = r*log( root[r](x)) with the aim
             * to move roor[r](x) near to 1.2 (that is, below the 0.3 appearing above), where log(1.2) is roughly 0.2.
             */
            int r = (int) (Math.log(xDbl) / 0.2);
 
            /* Since the actual requirement is a function of the value 0.3 appearing above,
             * we avoid the hypothetical case of endless recurrence by ensuring that r >= 2.
             */
            r = Math.max(2, r);
 
            /*
             * Compute r-th root with 2 additional digits of precision
             */
            BigDecimal xhighpr = scalePrec(x, 2);
            BigDecimal resul = root(r, xhighpr);
            resul = log(resul).multiply(new BigDecimal(r));
 
            /* error propagation: log(x+errx) = log(x)+errx/x, so the absolute error
             * in the result equals the relative error in the input, xUlpDbl/xDbl .
             */
            MathContext mc = new MathContext(err2prec(resul.doubleValue(), xUlpDbl / xDbl));
            return resul.round(mc);
        }
    } /* BigDecimalMath.log */
 
    /** The natural logarithm.
     * @param x the argument.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return ln(x).
     * The precision of the result is explicitly defined by the arbitrary MathContext.
     * @since 2012-03-19
     * @author l.bruninx
     *
     * Based on log(BigDecimal) from Richard J. Mathar.
     *
     */
    static public BigDecimal log(BigDecimal x, MathContext mc) {
        /*
         *
         * the value is undefined if x is negative.
         */
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot take log of negative " + x.toString());
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            /* log 1. = 0. */
            return scalePrec(BigDecimal.ZERO, x.precision() - 1);
        }
        else if ((x.compareTo(TWO) == 0) && mc.getPrecision()<Const.LOG2.precision()){
            return Const.LOG2.round(mc);
        }
        else if ((x.compareTo(BigDecimal.TEN) == 0) && mc.getPrecision()<Const.LOG10.precision()){
            return Const.LOG10.round(mc);
        }
        else if (Math.abs(x.doubleValue() - 1.0) <= 0.3) {
            /*
             * The standard Taylor series around x=1, z=0, z=x-1. Abramowitz-Stegun 4.124.
             * The absolute error is err(z)/(1+z) = err(x)/x.
             */
            BigDecimal z = x.subtract(BigDecimal.ONE);
            MathContext nmc = _mc_adjust_(z,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
            BigDecimal zpown = z;
            BigDecimal resul = z;
            BigDecimal oldr = resul;
           
            int k = 2;
            do {
                oldr = _dec_round_(resul,mc);
                for(int j=2;j>0;j--){
                    zpown = zpown.multiply(z);
                    BigDecimal c = zpown.divide(new BigDecimal(k), nmc);
 
                    if (k++ % 2 == 0)
                        resul = resul.subtract(c);
                    else
                        resul = resul.add(c);
 
                    //System.out.println(k+" sqrt :"+resul.toPlainString());
                    //k++;
                }
            }
            while (_dec_round_(resul,mc).compareTo(oldr) != 0); // auto-adaptative algo for maximum precision
 
            return _dec_round_(resul,mc);
        }
        else {
            /*
             * Recursively log(x) = 2 * log(sqrt(x)) = log(sqrt(x)) + log(sqrt(x))
             */
            //System.out.print("-");
            MathContext nmc = _mc_adjust_(x,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
            BigDecimal r2x = sqrt(x, nmc);
            //System.out.println("sqrt:"+r2x.toPlainString());
            BigDecimal log_r2x = log(r2x, nmc);
            //System.out.print("=");
            return _dec_round_(log_r2x.add(log_r2x),mc);
 
            //final double xDbl = x.doubleValue();
 
            /* Map log(x) = log root[r](x)^r = r*log( root[r](x)) with the aim
             * to move roor[r](x) near to 1.2 (that is, below the 0.3 appearing above), where log(1.2) is roughly 0.2.
             */
            //int r = (int) (Math.log(xDbl) / 0.2);
 
            /* Since the actual requirement is a function of the value 0.3 appearing above,
             * we avoid the hypothetical case of endless recurrence by ensuring that r >= 2.
             */
            //r = Math.max(2, r);
 
            /*
             * Compute r-th root with 2 additional digits of precision
             */
            //BigDecimal resul = pow(x,BigDecimal.ONE.divide(new BigDecimal(r),mc),mc);
            //return log(resul,mc).multiply(new BigDecimal(r),mc);
 
        }
    } /* BigDecimalMath.log */
 
    /**
     * The base 10 logarithm.
     * @param x the argument.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return ln(x).
     * The precision of the result is explicitly defined by the arbitrary MathContext.
     * @since 2012-03-19
     * @author l.bruninx
     *
     */
    public static BigDecimal log10(BigDecimal x, MathContext mc) {
        /*
         * simply use log10=ln(x) / ln(10)..
         */
        MathContext nmc = _mc_adjust_(x,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
        // use Const.LOG10 for optimization.
        BigDecimal ln10 = nmc.getPrecision()<Const.LOG10.precision() ? Const.LOG10.round(nmc) : log(BigDecimal.TEN, nmc);
        BigDecimal lnx = log(x, nmc);
        return lnx.divide(ln10, mc);
    }
 
 
    /** The natural logarithm.
     * @param n The main argument, a strictly positive integer.
     * @param mc The requirements on the precision.
     * @return ln(n).
     * @since 2009-08-08
     * @author Richard J. Mathar
     */
    static public BigDecimal log(int n, final MathContext mc) {
        /*
         * the value is undefined if x is negative.
         */
        if (n <= 0)
            throw new ArithmeticException("Cannot take log of negative " + n);
        else if (n == 1)
            return BigDecimal.ZERO;
        else if (n == 2) {
            if (mc.getPrecision() < Const.LOG2.precision())
                return Const.LOG2.round(mc);
            else {
                /* Broadhurst <a href="http://arxiv.org/abs/math/9803067">arXiv:math/9803067</a>
                 * Error propagation: the error in log(2) is twice the error in S(2,-5,...).
                 */
                int[] a = { 2,
                    -5,
                    -2,
                    -7,
                    -2,
                    -5,
                    2,
                    -3 };
                BigDecimal S = broadhurstBBP(2, 1, a, new MathContext(1 + mc.getPrecision()));
                S = S.multiply(new BigDecimal(8));
                S = sqrt(divideRound(S, 3));
                return _dec_round_(S,mc);
            }
        }
        else if (n == 3) {
            /* summation of a series roughly proportional to (7/500)^k. Estimate count
             * of terms to estimate the precision (drop the favorable additional
             * 1/k here): 0.013^k <= 10^(-precision), so k*log10(0.013) <= -precision
             * so k>= precision/1.87.
             */
            int kmax = (int) (mc.getPrecision() / 1.87);
            MathContext mcloc = new MathContext(mc.getPrecision() + 1 + (int) (Math.log10(kmax * 0.693 / 1.098)));
            BigDecimal log3 = multiplyRound(log(new BigDecimal(2), mcloc), 19);
 
            /* log3 is roughly 1, so absolute and relative error are the same. The
             * result will be divided by 12, so a conservative error is the one
             * already found in mc
             */
            double eps = prec2err(1.098, mc.getPrecision()) / kmax;
            Rational r = new Rational(7153, 524288);
            Rational pk = new Rational(7153, 524288);
            for (int k = 1; ; k++) {
                Rational tmp = pk.divide(k);
                if (tmp.doubleValue() < eps)
                    break;
 
                /*
                 * how many digits of tmp do we need in the sum?
                 */
                mcloc = new MathContext(err2prec(tmp.doubleValue(), eps));
                BigDecimal c = pk.divide(k).BigDecimalValue(mcloc);
                if (k % 2 != 0)
                    log3 = log3.add(c);
                else
                    log3 = log3.subtract(c);
                pk = pk.multiply(r);
            }
            log3 = divideRound(log3, 12);
            return _dec_round_(log3,mc);
        }
        else if (n == 5) {
            /* summation of a series roughly proportional to (7/160)^k. Estimate count
             * of terms to estimate the precision (drop the favorable additional
             * 1/k here): 0.046^k <= 10^(-precision), so k*log10(0.046) <= -precision
             * so k>= precision/1.33.
             */
            int kmax = (int) (mc.getPrecision() / 1.33);
            MathContext mcloc = new MathContext(mc.getPrecision() + 1 + (int) (Math.log10(kmax * 0.693 / 1.609)));
            BigDecimal log5 = multiplyRound(log(new BigDecimal(2), mcloc), 14);
 
            /* log5 is roughly 1.6, so absolute and relative error are the same. The
             * result will be divided by 6, so a conservative error is the one
             * already found in mc
             */
            double eps = prec2err(1.6, mc.getPrecision()) / kmax;
            Rational r = new Rational(759, 16384);
            Rational pk = new Rational(759, 16384);
            for (int k = 1; ; k++) {
                Rational tmp = pk.divide(k);
                if (tmp.doubleValue() < eps)
                    break;
 
                /*
                 * how many digits of tmp do we need in the sum?
                 */
                mcloc = new MathContext(err2prec(tmp.doubleValue(), eps));
                BigDecimal c = pk.divide(k).BigDecimalValue(mcloc);
                log5 = log5.subtract(c);
                pk = pk.multiply(r);
            }
            log5 = divideRound(log5, 6);
            return _dec_round_(log5,mc);
        }
        else if (n == 7) {
            /* summation of a series roughly proportional to (1/8)^k. Estimate count
             * of terms to estimate the precision (drop the favorable additional
             * 1/k here): 0.125^k <= 10^(-precision), so k*log10(0.125) <= -precision
             * so k>= precision/0.903.
             */
            int kmax = (int) (mc.getPrecision() / 0.903);
            MathContext mcloc = new MathContext(mc.getPrecision() + 1 + (int) (Math.log10(kmax * 3 * 0.693 / 1.098)));
            BigDecimal log7 = multiplyRound(log(new BigDecimal(2), mcloc), 3);
 
            /*
             * log7 is roughly 1.9, so absolute and relative error are the same.
             */
            double eps = prec2err(1.9, mc.getPrecision()) / kmax;
            Rational r = new Rational(1, 8);
            Rational pk = new Rational(1, 8);
            for (int k = 1; ; k++) {
                Rational tmp = pk.divide(k);
                if (tmp.doubleValue() < eps)
                    break;
 
                /*
                 * how many digits of tmp do we need in the sum?
                 */
                mcloc = new MathContext(err2prec(tmp.doubleValue(), eps));
                BigDecimal c = pk.divide(k).BigDecimalValue(mcloc);
                log7 = log7.subtract(c);
                pk = pk.multiply(r);
            }
            return _dec_round_(log7,mc);
 
        }
 
        else if ((n == 10) && (mc.getPrecision() < Const.LOG10.precision()))       // added by l.bruninx, 2012-03-29
                return Const.LOG10.round(mc);                                      // added by l.bruninx, 2012-03-29
       
        else {
            /* At this point one could either forward to the log(BigDecimal) signature (implemented)
             * or decompose n into Ifactors and use an implemenation of all the prime bases.
             * Estimate of the result; convert the mc argument to an  absolute error eps
             * log(n+errn) = log(n)+errn/n = log(n)+eps
             */
            double res = Math.log((double) n);
            double eps = prec2err(res, mc.getPrecision());
            /*
             * errn = eps*n, convert absolute error in result to requirement on absolute error in input
             */
            eps *= n;
            /*
             * Convert this absolute requirement of error in n to a relative error in n
             */
            final MathContext mcloc = new MathContext(1 + err2prec((double) n, eps));
            /* Padd n with a number of zeros to trigger the required accuracy in
             * the standard signature method
             */
            BigDecimal nb = scalePrec(new BigDecimal(n), mcloc);
            return _dec_round_(log(nb),mc);
        }
    } /* log */
 
    /** The natural logarithm.
     * @param r The main argument, a strictly positive value.
     * @param mc The requirements on the precision.
     * @return ln(r).
     * @since 2009-08-09
     * @author Richard J. Mathar
     */
    static public BigDecimal log(final Rational r, final MathContext mc) {
        /*
         * the value is undefined if x is negative.
         */
        if (r.compareTo(Rational.ZERO) <= 0)
            throw new ArithmeticException("Cannot take log of negative " + r.toString());
        else if (r.compareTo(Rational.ONE) == 0)
            return BigDecimal.ZERO;
        else {
 
            /* log(r+epsr) = log(r)+epsr/r. Convert the precision to an absolute error in the result.
             * eps contains the required absolute error of the result, epsr/r.
             */
            double eps = prec2err(Math.log(r.doubleValue()), mc.getPrecision());
 
            /* Convert this further into a requirement of the relative precision in r, given that
             * epsr/r is also the relative precision of r. Add one safety digit.
             */
            MathContext mcloc = new MathContext(1 + err2prec(eps));
 
            final BigDecimal resul = log(r.BigDecimalValue(mcloc));
 
            return resul.round(mc);
        }
    } /* log */
 
    /** Power function.
     * @param x Base of the power.
     * @param y Exponent of the power.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return x^y.
     *  The estimation of the relative error in the result is |log(x)*err(y)|+|y*err(x)/x|
     * @since 2009-06-01
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     *
     */
    static public BigDecimal pow(final BigDecimal x, final BigDecimal y, MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot power negative " + x.toString());
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            /*
             * return x^y = exp(y*log(x)) ;
             */
           
            MathContext nmc=_mc_adjust_pow_(x,y,mc);
            BigDecimal logx = log(x, nmc);
            BigDecimal ylogx = y.multiply(logx);
            ylogx=exp(ylogx,nmc);
            return _dec_round_(ylogx,mc);
        }
    } /* BigDecimalMath.pow */
 
    /** Raise to an integer power and round.
     * @param x The base.
     * @param n The exponent.
     * @return x^n.
     * @since 2009-08-13
     * @since 2010-05-26 handle also n<0 cases.
     */
    static public BigDecimal powRound(final BigDecimal x, final int n) {
        /** Special cases: x^1=x and x^0 = 1
                */
        if (n == 1)
            return x;
        else if (n == 0)
            return BigDecimal.ONE;
        else {
            /* The relative error in the result is n times the relative error in the input.
             * The estimation is slightly optimistic due to the integer rounding of the logarithm.
             * Since the standard BigDecimal.pow can only handle positive n, we split the algorithm.
             */
            MathContext mc = new MathContext(x.precision() - (int) Math.log10((double) (Math.abs(n))));
            if (n > 0)
                return x.pow(n, mc);
            else
                return BigDecimal.ONE.divide(x.pow(-n), mc);
        }
    } /* BigDecimalMath.powRound */
 
    /** Raise to an integer power and round.
     * @param x The base.
     * @param n The exponent.
     *   The current implementation allows n only in the interval of the standard int values.
     * @return x^n.
     * @since 2010-05-26
     */
    static public BigDecimal powRound(final BigDecimal x, final BigInteger n) {
        /** For now, the implementation forwards to the cases where n
         * is in the range of the standard integers. This might, however, be
         * implemented to decompose larger powers into cascaded calls to smaller ones.
         */
        if (n.compareTo(Rational.MAX_INT) > 0 || n.compareTo(Rational.MIN_INT) < 0)
            throw new ProviderException("Not implemented: big power " + n.toString());
        else
            return powRound(x, n.intValue());
    } /* BigDecimalMath.powRound */
 
    /** Raise to a fractional power and round.
     * @param x The base.
     *     Generally enforced to be positive, with the exception of integer exponents where
     *     the sign is carried over according to the parity of the exponent.
     * @param q The exponent.
     * @return x^q.
     * @since 2010-05-26
     */
    static public BigDecimal powRound(final BigDecimal x, final Rational q) {
        /**
         * Special cases: x^1=x and x^0 = 1
         */
        if (q.compareTo(BigInteger.ONE) == 0)
            return x;
        else if (q.signum() == 0)
            return BigDecimal.ONE;
        else if (q.isInteger()) {
            /* We are sure that the denominator is positive here, because normalize() has been
             * called during constrution etc.
             */
            return powRound(x, q.a);
        }
        /*
         * Refuse to operate on the general negative basis. The integer q have already been handled above.
         */
        else if (x.compareTo(BigDecimal.ZERO) < 0)
            throw new ArithmeticException("Cannot power negative " + x.toString());
        else {
            if (q.isIntegerFrac()) {
                /*
                 * Newton method with first estimate in double precision.
                 * The disadvantage of this first line here is that the result must fit in the
                 * standard range of double precision numbers exponents.
                 */
                double estim = Math.pow(x.doubleValue(), q.doubleValue());
                BigDecimal res = new BigDecimal(estim);
 
                /* The error in x^q is q*x^(q-1)*Delta(x).
                 * The relative error is q*Delta(x)/x, q times the relative error of x.
                 */
                BigDecimal reserr = new BigDecimal(0.5 * q.abs().doubleValue() * x.ulp().divide(x.abs(), MathContext.DECIMAL64).doubleValue());
 
                /* The main point in branching the cases above is that this conversion
                 * will succeed for numerator and denominator of q.
                 */
                int qa = q.a.intValue();
                int qb = q.b.intValue();
 
                /* Newton iterations. */
                BigDecimal xpowa = powRound(x, qa);
                for (; ; ) {
                    /* numerator and denominator of the Newton term.  The major
                     * disadvantage of this implementation is that the updates of the powers
                     * of the new estimate are done in full precision calling BigDecimal.pow(),
                     * which becomes slow if the denominator of q is large.
                     */
                    BigDecimal nu = res.pow(qb).subtract(xpowa);
                    BigDecimal de = multiplyRound(res.pow(qb - 1), q.b);
 
                    /* estimated correction */
                    BigDecimal eps = nu.divide(de, MathContext.DECIMAL64);
 
                    BigDecimal err = res.multiply(reserr, MathContext.DECIMAL64);
                    int precDiv = 2 + err2prec(eps, err);
                    if (precDiv <= 0) {
                        /*
                         * The case when the precision is already reached and any precision
                         * will do.
                         */
                        eps = nu.divide(de, MathContext.DECIMAL32);
                    }
                    else {
                        MathContext mc = new MathContext(precDiv);
                        eps = nu.divide(de, mc);
                    }
 
                    res = subtractRound(res, eps);
                    /* reached final precision if the relative error fell below reserr,
                     * |eps/res| < reserr
                     */
                    if (eps.divide(res, MathContext.DECIMAL64).abs().compareTo(reserr) < 0) {
                        /* delete the bits of extra precision kept in this
                         * working copy.
                         */
                        MathContext mc = new MathContext(err2prec(reserr.doubleValue()));
                        return res.round(mc);
                    }
                }
            }
            else {
                /* The error in x^q is q*x^(q-1)*Delta(x) + Delta(q)*x^q*log(x).
                 * The relative error is q/x*Delta(x) + Delta(q)*log(x). Convert q to a floating point
                 * number such that its relative error becomes negligible: Delta(q)/q << Delta(x)/x/log(x) .
                 */
                int precq = 3 + err2prec((x.ulp().divide(x, MathContext.DECIMAL64)).doubleValue() / Math.log(x.doubleValue()));
                MathContext mc = new MathContext(precq);
 
                /*
                 * Perform the actual calculation as exponentiation of two floating point numbers.
                 */
                return pow(x, q.BigDecimalValue(mc), mc);
            }
 
 
        }
    } /* BigDecimalMath.powRound */
 
    /** Trigonometric sine.
     * @param x The argument in radians.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return sin(x) in the range -1 to 1.
     * @since 2009-06-01
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (+fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     */
    static public BigDecimal sin(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return sin(x.negate(), mc).negate();
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            /*
             * reduce modulo 2pi
             */
            BigDecimal res = mod2pi(x);
 
            /*
             * Simple Taylor expansion, sum_{i=1..infinity} (-1)^(..)res^(2i+1)/(2i+1)!
             *
             * All removed & replaced by l.bruninx, 2012-03-19 to support an arbitrary Mathcontext and to fix
             * the java.lang.IllegalArgumentException: Digits < 0 Errors encountered with the original code.
             *
             */
 
            BigDecimal resx = BigDecimal.ZERO;
            BigDecimal oldx = resx;
            MathContext nmc = _mc_adjust_(res,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
            int k = 0;
            do {
                oldx = _dec_round_(resx,mc);                   // auto-adaptative algo...
                for (int z = 2; z > 0; z--) {
                    int k2p1 = 2 * k + 1;
                    BigDecimal a = res.pow(k2p1);
                    BigDecimal b = new BigDecimal(k2p1);
                    for (int i = (k2p1 - 1); i > 1; i--)
                        b = b.multiply(new BigDecimal(i));
                    BigDecimal t = a.divide(b, nmc);
 
                    if (0 == k % 2)
                        resx = resx.add(t);
                    else
                        resx = resx.subtract(t);
 
                    k++;
                }
            }
            while (_dec_round_(resx,mc).compareTo(oldx) != 0); // auto-adaptative algo...
            return _dec_round_(resx,mc);
        }
    } /* sin */
 
    /** Trigonometric cosine.
     * @param x The argument in radians.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return cos(x) in the range -1 to 1.
     * @since 2009-06-01
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     */
    static public BigDecimal cos(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return cos(x.negate(), mc);
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ONE;
        else {
            /*
             * reduce modulo 2pi
             */
            BigDecimal res = mod2pi(x);
            MathContext nmc = _mc_adjust_(res,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
            BigDecimal p = pi(nmc);
 
            /*
             * Simple Taylor expansion, sum_{i=1..infinity} (-1)^(..)res^(2i+1)/(2i+1)!
             *
             * All removed & replaced by l.bruninx, 2012-03-19 to support an arbitrary Mathcontext and to fix
             * the java.lang.IllegalArgumentException: Digits < 0 Errors encountered with the original code.
             */
 
           
            BigDecimal r = sin(p.divide(new BigDecimal(2)).add(res), nmc);
            return _dec_round_(r,mc);
        }
    } /* BigDecimalMath.cos */
 
    /** The trigonometric tangent.
     * @param x the argument in radians.
     * @return the tan(x)
     */
    static public BigDecimal tan(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else if (x.compareTo(BigDecimal.ZERO) < 0) {
            return tan(x.negate(), mc).negate();
        }
        else {
            /*
             * All removed & replaced by l.bruninx, 2012-03-19 to support an arbitrary Mathcontext and to fix
             * the java.lang.IllegalArgumentException: Digits < 0 Errors encountered with the original code.
             *
             * Use tan{x} = sin{x} / cos{x}
             *
             */
            MathContext nmc = _mc_adjust_(x,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
            BigDecimal r=sin(x, nmc).divide(cos(x, nmc),nmc);
            return _dec_round_(r,mc);
 
        }
    } /* BigDecimalMath.tan */
 
    /** The trigonometric co-tangent.
     * @param x the argument in radians.
     * @return the cot(x)
     * @since 2009-07-31
     */
    static public BigDecimal cot(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) == 0) {
            throw new ArithmeticException("Cannot take cot of zero " + x.toString());
        }
        else if (x.compareTo(BigDecimal.ZERO) < 0) {
            return cot(x.negate()).negate();
        }
        else {
            /*
             * reduce modulo pi
             */
            BigDecimal res = modpi(x);
 
            /*
             * absolute error in the result is err(x)/sin^2(x) to lowest order
             */
            final double xDbl = res.doubleValue();
            final double xUlpDbl = x.ulp().doubleValue() / 2.;
            final double eps = xUlpDbl / 2. / Math.pow(Math.sin(xDbl), 2.);
 
            final BigDecimal xhighpr = scalePrec(res, 2);
            final BigDecimal xhighprSq = multiplyRound(xhighpr, xhighpr);
 
            MathContext mc = new MathContext(err2prec(xhighpr.doubleValue(), eps));
            BigDecimal resul = BigDecimal.ONE.divide(xhighpr, mc);
 
            /* x^(2i-1) */
            BigDecimal xpowi = xhighpr;
 
            Bernoulli b = new Bernoulli();
 
            /* 2^(2i) */
            BigInteger fourn = new BigInteger("4");
            /* (2i)! */
            BigInteger fac = BigInteger.ONE;
 
            for (int i = 1; ; i++) {
                Rational f = b.at(2 * i);
                fac = fac.multiply(new BigInteger("" + (2 * i))).multiply(new BigInteger("" + (2 * i - 1)));
                f = f.multiply(fourn).divide(fac);
                BigDecimal c = multiplyRound(xpowi, f);
                if (i % 2 == 0)
                    resul = resul.add(c);
                else
                    resul = resul.subtract(c);
                if (Math.abs(c.doubleValue()) < 0.1 * eps)
                    break;
 
                fourn = fourn.shiftLeft(2);
                xpowi = multiplyRound(xpowi, xhighprSq);
            }
            mc = new MathContext(err2prec(resul.doubleValue(), eps));
            return resul.round(mc);
        }
    } /* BigDecimalMath.cot */
 
    /** The inverse trigonometric sine.
     * @param x the argument.
     * @param mc MathContext (added by l.bruninx 2012-03-19).
     * @return the arcsin(x) in radians.
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     */
    static public BigDecimal asin(final BigDecimal x, MathContext mc) {
        if (x.compareTo(BigDecimal.ONE) > 0 || x.compareTo(BigDecimal.ONE.negate()) < 0) {
            throw new ArithmeticException("Out of range argument " + x.toString() + " of asin");
        }
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            /*
             * arcsin(1) = pi/2
             */
            //double errpi = Math.sqrt(x.ulp().doubleValue()); // removed by l.bruninx, 2012-03-19
            //MathContext mc = new MathContext(err2prec(3.14159, errpi)); // removed by l.bruninx, 2012-03-19
            return pi(mc).divide(TWO, mc); // modified by l.bruninx, 2012-03-19
        }
        else if (x.compareTo(BigDecimal.ZERO) < 0) {
            return asin(x.negate(), mc).negate();
        }
        else {
            /*
             * Use asin{x} = atan{ x / sqrt{1 - (x * x) } }
             *
             * All removed & replaced by l.bruninx, 2012-03-19 to support an arbitrary Mathcontext and to fix
             * the java.lang.IllegalArgumentException: Digits < 0 Errors encountered with the original code.
             */
            BigDecimal x2 = x.multiply(x);
            BigDecimal d = sqrt(BigDecimal.ONE.subtract(x2), mc);
            return atan(x.divide(d, mc), mc);
        }
    } /* BigDecimalMath.asin */
 
    /** The inverse trigonometric cosine.
     * @param x the argument.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return the arccos(x) in radians.
     * @since 2009-09-29
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     *
     */
    static public BigDecimal acos(final BigDecimal x, MathContext mc) {
 
        /**
         * Check range and results for 0, 1 and -1 arguments (l.bruninx, 19-mar-2012).
         **/
        if (x.compareTo(BigDecimal.ONE) > 0 || x.compareTo(NEG_ONE) < 0) {
            throw new ArithmeticException("Out of range argument " + x.toString() + " of acos");
        }
        else if (x.compareTo(BigDecimal.ONE) == 0) {
            // (acos 1) -> 0
            return BigDecimal.ZERO;
        }
        else if (x.compareTo(BigDecimal.ZERO) == 0) {
            // (acos 0) -> pi/2
            return pi(mc).divide(TWO, mc); // l.bruninx, 19-mar-2012.
        }
        else if (x.compareTo(NEG_ONE) == 0) {
            // (acos -1) -> pi
            return pi(mc);
        }
        /**
         * end of check range and results for 0, 1 and -1 values (l.bruninx, 19-mar-2012).
         */
 
        /*
         * Essentially forwarded to pi/2 - asin(x)
         */
        final BigDecimal xhighpr = scalePrec(x, 2);
        BigDecimal resul = asin(xhighpr, mc); // modified by l.bruninx, 2012-03-19
        // double eps = resul.ulp().doubleValue() / 2.;             // removed by l.bruninx, 2012-03-19
 
        //MathContext mc = new MathContext(err2prec(3.14159, eps)); // removed by l.bruninx, 2012-03-19
        BigDecimal pihalf = pi(mc).divide(TWO, mc); // modified by l.bruninx, 2012-03-19
        return pihalf.subtract(resul, mc);
 
        /*
         * Orignial section to check absolute error in the result is err(x)/sqrt(1-x^2) to lowest order
         * removed by l.bruninx, 2012-03-19.
         *
         * All removed & replaced by l.bruninx, 2012-03-19 to support an arbitrary Mathcontext and to fix
         * the java.lang.IllegalArgumentException: Digits < 0 Errors encountered with the original code.
         */
 
    } /* BigDecimalMath.acos */
 
 
    /** The inverse trigonometric tangent.
     * @param x the argument.
     * @param mc Arbitrary MathContext (added by l.bruninx 19-mar-2012).
     * @return the principal value of arctan(x) in radians in the range -pi/2 to +pi/2.
     * @since 2009-08-03
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     */
    static public BigDecimal atan(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0) {
            return atan(x.negate(), mc).negate();
        }
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
 
            /*
             *  For arbitrary precision:
             *
             *  x2 = x * x
             *
             *  a = 1 / ( sqrt{ 1 + x2} )
             *
             *  b = 1
             *
             *  Iterations for maximal precision:
             *
             *     a = (a + b) / 2
             *     b = sqrt{a * b}
             *
             *  Result:
             *
             *     r = x / (sqrt{ 1 + x2 } * a)
             *
             *  -----------------------------------------------------------
             *
             *  All modified by l.bruninx, 2012-03-19.
             *
             *  -----------------------------------------------------------
             */
            BigDecimal x2 = x.multiply(x);
            BigDecimal a = BigDecimal.ONE.divide(sqrt(BigDecimal.ONE.add(x2), mc), mc);
            BigDecimal b = BigDecimal.ONE;
            BigDecimal olda;
            do {
                olda = a.round(mc);
                a = a.add(b).divide(new BigDecimal(2), mc);
                b = sqrt(a.multiply(b), mc);
            }
            while (a.round(mc).compareTo(olda) != 0);
            BigDecimal r = x.divide(sqrt(BigDecimal.ONE.add(x2), mc).multiply(a), mc);
            return r;
        }
 
    } /* BigDecimalMath.atan */
 
    /** The hyperbolic cosine.
     * @param x The argument.
     * @return The cosh(x) = (exp(x)+exp(-x))/2 .
     * @author Richard J. Mathar
     * @since 2009-08-19
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     *
     */
    static public BigDecimal cosh(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return cos(x.negate(), mc);
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ONE;
        else {
            if (x.doubleValue() > 1.5) {
                /* cosh^2(x) = 1+ sinh^2(x). */
                return hypot(1, sinh(x, mc));
            }
            else {
                BigDecimal xhighpr = scalePrec(x, 2);
                /*
                 * Simple Taylor expansion, sum_{0=1..infinity} x^(2i)/(2i)!
                 */
                BigDecimal resul = BigDecimal.ONE;
 
                /* x^i */
                BigDecimal xpowi = BigDecimal.ONE;
 
                /* 2i factorial */
                BigInteger ifac = BigInteger.ONE;
 
                /*
                 * The absolute error in the result is the error in x^2/2 which is x times the error in x.
                 */
                double xUlpDbl = 0.5 * x.ulp().doubleValue() * x.doubleValue();
 
                /* The error in the result is set by the error in x^2/2 itself, xUlpDbl.
                 * We need at most k terms to push x^(2k)/(2k)! below this value.
                 * x^(2k) < xUlpDbl; (2k)*log(x) < log(xUlpDbl);
                 */
                int k = (int) (Math.log(xUlpDbl) / Math.log(x.doubleValue())) / 2;
 
                /* The individual terms are all smaller than 1, so an estimate of 1.0 for
                 * the absolute value will give a safe relative error estimate for the indivdual terms
                 */
                MathContext mcTay = new MathContext(err2prec(1., xUlpDbl / k));
                for (int i = 1; ; i++) {
                    /*
                     * TBD: at which precision will 2*i-1 or 2*i overflow?
                     */
                    ifac = ifac.multiply(new BigInteger("" + (2 * i - 1)));
                    ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                    xpowi = xpowi.multiply(xhighpr).multiply(xhighpr);
                    BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(corr);
                    if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                        break;
                }
                /*
                 * The error in the result is governed by the error in x itself.
                 */
                // MathContext mc = new MathContext(err2prec(resul.doubleValue(), xUlpDbl)); // removed by l.bruninx, 2012-03-19
                return resul.round(mc);
            }
        }
    } /* BigDecimalMath.cosh */
 
    /** The hyperbolic sine.
     * @param x the argument.
     * @return the sinh(x) = (exp(x)-exp(-x))/2 .
     * @author Richard J. Mathar
     * @since 2009-08-19
     *
     * Arbitrary MathContext added by l.bruninx, 2012-03-19 (fix java.lang.IllegalArgumentException: Digits < 0 Errors).
     *
     */
    static public BigDecimal sinh(final BigDecimal x, final MathContext mc) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return sinh(x.negate(), mc).negate(); // modified by l.bruninx, 2012-03-19
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            if (x.doubleValue() > 2.4) {
                /*
                 * Move closer to zero with sinh(2x)= 2*sinh(x)*cosh(x).
                 */
               
                BigDecimal two = new BigDecimal(2);
                BigDecimal xhalf = x.divide(two);
                BigDecimal resul = sinh(xhalf, mc).multiply(cosh(xhalf, mc)).multiply(two); // modified by l.bruninx, 2012-03-19
                /* The error in the result is set by the error in x itself.
                 * The first derivative of sinh(x) is cosh(x), so the absolute error
                 * in the result is cosh(x)*errx, and the relative error is coth(x)*errx = errx/tanh(x)
                 */
                //double eps = Math.tanh(x.doubleValue());                                       // removed by l.bruninx, 2012-03-19
                //MathContext mc = new MathContext(err2prec(0.5 * x.ulp().doubleValue() / eps)); // removed by l.bruninx, 2012-03-19
                return resul.round(mc);
            }
            else {
                BigDecimal xhighpr = scalePrec(x, 2);
                /* Simple Taylor expansion, sum_{i=0..infinity} x^(2i+1)/(2i+1)! */
                BigDecimal resul = xhighpr;
 
                /* x^i */
                BigDecimal xpowi = xhighpr;
 
                /* 2i+1 factorial */
                BigInteger ifac = BigInteger.ONE;
 
                /*
                 * The error in the result is set by the error in x itself.
                 */
                double xUlpDbl = x.ulp().doubleValue();
 
                /* The error in the result is set by the error in x itself.
                 * We need at most k terms to squeeze x^(2k+1)/(2k+1)! below this value.
                 * x^(2k+1) < x.ulp; (2k+1)*log10(x) < -x.precision; 2k*log10(x)< -x.precision;
                 * 2k*(-log10(x)) > x.precision; 2k*log10(1/x) > x.precision
                 */
                int k = (int) (x.precision() / Math.log10(1.0 / xhighpr.doubleValue())) / 2;
                MathContext mcTay = new MathContext(err2prec(x.doubleValue(), xUlpDbl / k));
                for (int i = 1; ; i++) {
                    /* TBD: at which precision will 2*i or 2*i+1 overflow?
                                        */
                    ifac = ifac.multiply(new BigInteger("" + (2 * i)));
                    ifac = ifac.multiply(new BigInteger("" + (2 * i + 1)));
                    xpowi = xpowi.multiply(xhighpr).multiply(xhighpr);
                    BigDecimal corr = xpowi.divide(new BigDecimal(ifac), mcTay);
                    resul = resul.add(corr);
                    if (corr.abs().doubleValue() < 0.5 * xUlpDbl)
                        break;
                }
                /*
                 * The error in the result is set by the error in x itself.
                 */
                // MathContext mc = new MathContext(x.precision());     // removed by l.bruninx, 2012-03-19
                return resul.round(mc);
            }
        }
    } /* BigDecimalMath.sinh */
 
    /** The hyperbolic tangent.
     * @param x The argument.
     * @return The tanh(x) = sinh(x)/cosh(x).
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal tanh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return tanh(x.negate()).negate();
        else if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);
 
            /*
             * tanh(x) = (1-e^(-2x))/(1+e^(-2x)) .
             */
            BigDecimal exp2x = exp(xhighpr.multiply(new BigDecimal(-2)));
 
            /*
             * The error in tanh x is err(x)/cosh^2(x).
             */
            double eps = 0.5 * x.ulp().doubleValue() / Math.pow(Math.cosh(x.doubleValue()), 2.0);
            MathContext mc = new MathContext(err2prec(Math.tanh(x.doubleValue()), eps));
            return BigDecimal.ONE.subtract(exp2x).divide(BigDecimal.ONE.add(exp2x), mc);
        }
    } /* BigDecimalMath.tanh */
 
    /** The inverse hyperbolic sine.
     * @param x The argument.
     * @return The arcsinh(x) .
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal asinh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);
 
            /*
             * arcsinh(x) = log(x+hypot(1,x))
             */
            BigDecimal logx = log(hypot(1, xhighpr).add(xhighpr));
 
            /*
             * The absolute error in arcsinh x is err(x)/sqrt(1+x^2)
             */
            double xDbl = x.doubleValue();
            double eps = 0.5 * x.ulp().doubleValue() / Math.hypot(1., xDbl);
            MathContext mc = new MathContext(err2prec(logx.doubleValue(), eps));
            return logx.round(mc);
        }
    } /* BigDecimalMath.asinh */
 
    /** The inverse hyperbolic cosine.
     * @param x The argument.
     * @return The arccosh(x) .
     * @author Richard J. Mathar
     * @since 2009-08-20
     */
    static public BigDecimal acosh(final BigDecimal x) {
        if (x.compareTo(BigDecimal.ONE) < 0)
            throw new ArithmeticException("Out of range argument cosh " + x.toString());
        else if (x.compareTo(BigDecimal.ONE) == 0)
            return BigDecimal.ZERO;
        else {
            BigDecimal xhighpr = scalePrec(x, 2);
 
            /*
             * arccosh(x) = log(x+sqrt(x^2-1))
             */
            BigDecimal logx = log(sqrt(xhighpr.pow(2).subtract(BigDecimal.ONE)).add(xhighpr));
 
            /*
             * The absolute error in arcsinh x is err(x)/sqrt(x^2-1)
             */
            double xDbl = x.doubleValue();
            double eps = 0.5 * x.ulp().doubleValue() / Math.sqrt(xDbl * xDbl - 1.);
            MathContext mc = new MathContext(err2prec(logx.doubleValue(), eps));
            return logx.round(mc);
        }
    } /* BigDecimalMath.acosh */
 
    /** The Gamma function.
     * @param x The argument.
     * @return Gamma(x).
     * @since 2009-08-06
     */
    static public BigDecimal Gamma(final BigDecimal x) {
        /*
         * reduce to interval near 1.0 with the functional relation, Abramowitz-Stegun 6.1.33
         */
        if (x.compareTo(BigDecimal.ZERO) < 0)
            return divideRound(Gamma(x.add(BigDecimal.ONE)), x);
        else if (x.doubleValue() > 1.5) {
            /*
             * Gamma(x) = Gamma(xmin+n) = Gamma(xmin)*Pochhammer(xmin,n).
             */
            int n = (int) (x.doubleValue() - 0.5);
            BigDecimal xmin1 = x.subtract(new BigDecimal(n));
            return multiplyRound(Gamma(xmin1), pochhammer(xmin1, n));
        }
        else {
            /*
             * apply Abramowitz-Stegun 6.1.33
             */
            BigDecimal z = x.subtract(BigDecimal.ONE);
 
            /*
             * add intermediately 2 digits to the partial sum accumulation
             */
            z = scalePrec(z, 2);
            MathContext mcloc = new MathContext(z.precision());
 
            /*
             * measure of the absolute error is the relative error in the first, logarithmic term
             */
            double eps = x.ulp().doubleValue() / x.doubleValue();
 
            BigDecimal resul = log(scalePrec(x, 2)).negate();
 
            if (x.compareTo(BigDecimal.ONE) != 0) {
 
                BigDecimal gammCompl = BigDecimal.ONE.subtract(gamma(mcloc));
                resul = resul.add(multiplyRound(z, gammCompl));
                Bernoulli bern_cache=new Bernoulli();
                Factorial fact_cache=new Factorial();
                for (int n = 2; ; n++) {
                    /* multiplying z^n/n by zeta(n-1) means that the two relative errors add.
                     * so the requirement in the relative error of zeta(n)-1 is that this is somewhat
                     * smaller than the relative error in z^n/n (the absolute error of thelatter  is the
                     * absolute error in z)
                     */
                    BigDecimal c = divideRound(z.pow(n, mcloc), n);
                    MathContext m = new MathContext(err2prec(n * z.ulp().doubleValue() / 2. / z.doubleValue()));
                    c = c.round(m);
 
                    /* At larger n, zeta(n)-1 is roughly 1/2^n. The product is c/2^n.
                     * The relative error in c is c.ulp/2/c . The error in the product should be small versus eps/10.
                     * Error from 1/2^n is c*err(sigma-1).
                     * We need a relative error of zeta-1 of the order of c.ulp/50/c. This is an absolute
                     * error in zeta-1 of c.ulp/50/c/2^n, and also the absolute error in zeta, because zeta is
                     * of the order of 1.
                     */
                    if (eps / 100. / c.doubleValue() < 0.01)
                        m = new MathContext(err2prec(eps / 100. / c.doubleValue()));
                    else
                        m = new MathContext(2);
                    /* zeta(n) -1 */
                    BigDecimal zetm1 = zeta(n, m,bern_cache,fact_cache).subtract(BigDecimal.ONE);
                    c = multiplyRound(c, zetm1);
 
                    if (n % 2 == 0)
                        resul = resul.add(c);
                    else
                        resul = resul.subtract(c);
 
                    /* alternating sum, so truncating as eps is reached suffices
                                        */
                    if (Math.abs(c.doubleValue()) < eps)
                        break;
                }
            }
 
            /* The relative error in the result is the absolute error in the
             * input variable times the digamma (psi) value at that point.
             */
            double zdbl = z.doubleValue();
            eps = psi(zdbl) * x.ulp().doubleValue() / 2.;
            mcloc = new MathContext(err2prec(eps));
            return exp(resul).round(mcloc);
        }
    } /* BigDecimalMath.gamma */
 
    /** The Gamma function.
     * @param q The argument.
     * @param mc The required accuracy in the result.
     * @return Gamma(x).
     * @since 2010-05-26
     */
    static public BigDecimal Gamma(final Rational q, final MathContext mc) {
        if (q.isBigInteger()) {
            if (q.compareTo(Rational.ZERO) <= 0)
                throw new ArithmeticException("Gamma at " + q.toString());
            else {
                /* Gamma(n) = (n-1)! */
                Factorial f = new Factorial();
                BigInteger g = f.at(q.trunc().intValue() - 1);
                return scalePrec(new BigDecimal(g), mc);
            }
        }
        else if (q.b.intValue() == 2) {
            /*
             * half integer cases which are related to sqrt(pi)
             */
            BigDecimal p = sqrt(pi(mc));
            if (q.compareTo(Rational.ZERO) >= 0) {
                Rational pro = Rational.ONE;
                Rational f = q.subtract(1);
                while (f.compareTo(Rational.ZERO) > 0) {
                    pro = pro.multiply(f);
                    f = f.subtract(1);
                }
                return multiplyRound(p, pro);
            }
            else {
                Rational pro = Rational.ONE;
                Rational f = q;
                while (f.compareTo(Rational.ZERO) < 0) {
                    pro = pro.divide(f);
                    f = f.add(1);
                }
                return multiplyRound(p, pro);
            }
        }
        else {
            /* The relative error of the result is psi(x)*Delta(x). Tune Delta(x) such
             * that this is equivalent to mc: Delta(x) = precision/psi(x).
             */
            double qdbl = q.doubleValue();
            double deltx = 5. * Math.pow(10., -mc.getPrecision()) / psi(qdbl);
 
            MathContext mcx = new MathContext(err2prec(qdbl, deltx));
            BigDecimal x = q.BigDecimalValue(mcx);
 
            /* forward calculation to the general floating point case */
            return Gamma(x);
        }
    } /* BigDecimalMath.Gamma */
 
    /** Pochhammer's  function.
     * @param x The main argument.
     * @param n The non-negative index.
     * @return (x)_n = x(x+1)(x+2)*...*(x+n-1).
     * @since 2009-08-19
     */
    static public BigDecimal pochhammer(final BigDecimal x, final int n) {
        /*
         * reduce to interval near 1.0 with the functional relation, Abramowitz-Stegun 6.1.33
         */
        if (n < 0)
            throw new ProviderException("Not implemented: pochhammer with negative index " + n);
        else if (n == 0)
            return BigDecimal.ONE;
        else {
            /*
             * internally two safety digits
             */
            BigDecimal xhighpr = scalePrec(x, 2);
            BigDecimal resul = xhighpr;
 
            double xUlpDbl = x.ulp().doubleValue();
            double xDbl = x.doubleValue();
            /*
             * relative error of the result is the sum of the relative errors of the factors
             */
            double eps = 0.5 * xUlpDbl / Math.abs(xDbl);
            for (int i = 1; i < n; i++) {
                eps += 0.5 * xUlpDbl / Math.abs(xDbl + i);
                resul = resul.multiply(xhighpr.add(new BigDecimal(i)));
                final MathContext mcloc = new MathContext(4 + err2prec(eps));
                resul = resul.round(mcloc);
            }
            return resul.round(new MathContext(err2prec(eps)));
        }
    } /* BigDecimalMath.pochhammer */
 
    /** Reduce value to the interval [0,2*Pi].
     * @param x the original value
     * @return the value modulo 2*pi in the interval from 0 to 2*pi.
     * @since 2009-06-01
     */
    static public BigDecimal mod2pi(BigDecimal x) {
        /* write x= 2*pi*k+r with the precision in r defined by the precision of x and not
         * compromised by the precision of 2*pi, so the ulp of 2*pi*k should match the ulp of x.
         * First get a guess of k to figure out how many digits of 2*pi are needed.
         */
        int k = (int) (0.5 * x.doubleValue() / Math.PI);
 
        /*
         * want to have err(2*pi*k)< err(x)=0.5*x.ulp, so err(pi) = err(x)/(4k) with two safety digits
         */
        double err2pi;
        if (k != 0)
            err2pi = 0.25 * Math.abs(x.ulp().doubleValue() / k);
        else
            err2pi = 0.5 * Math.abs(x.ulp().doubleValue());
        MathContext mc = new MathContext(2 + err2prec(6.283, err2pi));
        BigDecimal twopi = pi(mc).multiply(new BigDecimal(2));
 
        /* Delegate the actual operation to the BigDecimal class, which may return
         * a negative value of x was negative .
         */
        BigDecimal res = x.remainder(twopi);
        if (res.compareTo(BigDecimal.ZERO) < 0)
            res = res.add(twopi);
 
        /*
         * The actual precision is set by the input value, its absolute value of x.ulp()/2.
         */
        mc = new MathContext(err2prec(res.doubleValue(), x.ulp().doubleValue() / 2.));
        return res.round(mc);
    } /* mod2pi */
 
    /** Reduce value to the interval [-Pi/2,Pi/2].
     * @param x The original value
     * @return The value modulo pi, shifted to the interval from -Pi/2 to Pi/2.
     * @since 2009-07-31
     */
    static public BigDecimal modpi(BigDecimal x) {
        /* write x= pi*k+r with the precision in r defined by the precision of x and not
         * compromised by the precision of pi, so the ulp of pi*k should match the ulp of x.
         * First get a guess of k to figure out how many digits of pi are needed.
         */
        int k = (int) (x.doubleValue() / Math.PI);
 
        /* want to have err(pi*k)< err(x)=x.ulp/2, so err(pi) = err(x)/(2k) with two safety digits
                */
        double errpi;
        if (k != 0)
            errpi = 0.5 * Math.abs(x.ulp().doubleValue() / k);
        else
            errpi = 0.5 * Math.abs(x.ulp().doubleValue());
        MathContext mc = new MathContext(2 + err2prec(3.1416, errpi));
        BigDecimal onepi = pi(mc);
        BigDecimal pihalf = onepi.divide(new BigDecimal(2));
 
        /* Delegate the actual operation to the BigDecimal class, which may return
         * a negative value of x was negative .
         */
        BigDecimal res = x.remainder(onepi);
        if (res.compareTo(pihalf) > 0)
            res = res.subtract(onepi);
        else if (res.compareTo(pihalf.negate()) < 0)
            res = res.add(onepi);
 
        /*
         * The actual precision is set by the input value, its absolute value of x.ulp()/2.
         */
        mc = new MathContext(err2prec(res.doubleValue(), x.ulp().doubleValue() / 2.));
        return res.round(mc);
    } /* modpi */
 
    /** Riemann zeta function.
     * @param n The positive integer argument.
     * @param mc Specification of the accuracy of the result.
     * @return zeta(n).
     * @since 2009-08-05
     *
     * l.bruninx modifications (2012-03-26);
     * Corrections needed for arbitrary precision: The original code raise many exceptions...
     * Optimizations needed... too long!!!... (memoization???)
     *
     */
    static public BigDecimal zeta(final int n, final MathContext mc, Bernoulli bern_cache, Factorial fact_cache) {
       
        MathContext nmc=new MathContext(3+mc.getPrecision(),mc.getRoundingMode());
       
        if (n <= 0)
            throw new ProviderException("Not implemented: zeta at negative argument " + n);
        if (n == 1)
            throw new ArithmeticException("Pole at zeta(1) ");
 
        if (n % 2 == 0) {
            /*
             * Even indices. Abramowitz-Stegun 23.2.16. Start with 2^(n-1)*B(n)/n!
             */
            //System.out.print("'");
            Rational b = (bern_cache!=null ? bern_cache : new Bernoulli()).at(n).abs();
            b = b.divide((fact_cache!=null ? fact_cache : new Factorial()).at(n));
            b = b.multiply(BigInteger.ONE.shiftLeft(n - 1));
            //System.out.print("'");
 
            /* to be multiplied by pi^n. Absolute error in the result of pi^n is n times
             * error in pi times pi^(n-1). Relative error is n*error(pi)/pi, requested by mc.
             * Need one more digit in pi if n=10, two digits if n=100 etc, and add one extra digit.
             */
            MathContext mcpi = new MathContext(mc.getPrecision() + (int) (Math.log10(10.0 * n)));
            BigDecimal piton = pi(mcpi).pow(n, mc);
            return piton.multiply(b.BigDecimalValue(mc),mc);
        }
        else if (n == 3) {
            /* Broadhurst BBP <a href="http://arxiv.org/abs/math/9803067">arXiv:math/9803067</a>
             * Error propagation: S31 is roughly 0.087, S33 roughly 0.131
             */
            int[] a31 = { 1,
                -7,
                -1,
                10,
                -1,
                -7,
                1,
                0 };
            int[] a33 = { 1,
                1,
                -1,
                -2,
                -1,
                1,
                1,
                0 };
            BigDecimal S31 = broadhurstBBP(3, 1, a31, mc);
            BigDecimal S33 = broadhurstBBP(3, 3, a33, mc);
            S31 = S31.multiply(new BigDecimal(48),nmc);
            S33 = S33.multiply(new BigDecimal(32),nmc);
            return S31.add(S33).divide(new BigDecimal(7), mc);
        }
        else if (n == 5) {
            /* Broadhurst BBP <a href=http://arxiv.org/abs/math/9803067">arXiv:math/9803067</a>
             * Error propagation: S51 is roughly -11.15, S53 roughly 22.165, S55 is roughly 0.031
             * 9*2048*S51/6265 = -3.28. 7*2038*S53/61651= 5.07. 738*2048*S55/61651= 0.747.
             * The result is of the order 1.03, so we add 2 digits to S51 and S52 and one digit to S55.
             */
            int[] a51 = { 31,
                -1614,
                -31,
                -6212,
                -31,
                -1614,
                31,
                74552 };
            int[] a53 = { 173,
                284,
                -173,
                -457,
                -173,
                284,
                173,
                -111 };
            int[] a55 = { 1,
                0,
                -1,
                -1,
                -1,
                0,
                1,
                1 };
            BigDecimal S51 = broadhurstBBP(5, 1, a51, new MathContext(2 + mc.getPrecision()));
            BigDecimal S53 = broadhurstBBP(5, 3, a53, new MathContext(2 + mc.getPrecision()));
            BigDecimal S55 = broadhurstBBP(5, 5, a55, new MathContext(1 + mc.getPrecision()));
            S51 = S51.multiply(new BigDecimal(18432),nmc);
            S53 = S53.multiply(new BigDecimal(14336),nmc);
            S55 = S55.multiply(new BigDecimal(1511424),nmc);
            return S51.add(S53).subtract(S55).divide(new BigDecimal(62651), mc);
        }
        else {
            /* Cohen et al Exp Math 1 (1) (1992) 25
                        */
            //System.out.print("'");
            Rational betsum = new Rational();
            Bernoulli bern = bern_cache!=null ? bern_cache : new Bernoulli();
            Factorial fact = fact_cache!=null ? fact_cache : new Factorial();
            for (int npr = 0; npr <= (n + 1) / 2; npr++) {
                Rational b = bern.at(2 * npr).multiply(bern.at(n + 1 - 2 * npr));
                b = b.divide(fact.at(2 * npr)).divide(fact.at(n + 1 - 2 * npr));
                b = b.multiply(1 - 2 * npr);
                if (npr % 2 == 0)
                    betsum = betsum.add(b);
                else
                    betsum = betsum.subtract(b);
            }
            //System.out.print("'");
            betsum = betsum.divide(n - 1);
            /* The first term, including the facor (2pi)^n, is essentially most
             * of the result, near one. The second term below is roughly in the range 0.003 to 0.009.
             * So the precision here is matching the precisionn requested by mc, and the precision
             * requested for 2*pi is in absolute terms adjusted.
             */
            MathContext mcloc = new MathContext(2 + mc.getPrecision() + (int) (Math.log10((double) (n))));
            BigDecimal ftrm = pi(mcloc).multiply(TWO,mcloc);
            ftrm = ftrm.pow(n,mcloc);
            ftrm = ftrm.multiply(betsum.BigDecimalValue(mcloc),mcloc);
            BigDecimal exps = new BigDecimal(0);
 
            /* the basic accuracy of the accumulated terms before multiplication with 2
                        */
            //double eps = Math.pow(10., -mc.getPrecision());
 
            if (n % 4 == 3) {
                //System.out.print("q");
                /* since the argument n is at least 7 here, the drop
                 * of the terms is at rather constant pace at least 10^-3, for example
                 * 0.0018, 0.2e-7, 0.29e-11, 0.74e-15 etc for npr=1,2,3.... We want 2 times these terms
                 * fall below eps/10.
                 */
                int kmax = mc.getPrecision() / 3;
                //eps /= kmax;
                /* need an error of eps for 2/(exp(2pi)-1) = 0.0037
                 * The absolute error is 4*exp(2pi)*err(pi)/(exp(2pi)-1)^2=0.0075*err(pi)
                 */
                BigDecimal exp2p = pi(mcloc);//new MathContext(3 + err2prec(3.14, eps / 0.0075)));
                exp2p = exp(exp2p.multiply(TWO),mcloc);
                BigDecimal c = exp2p.subtract(BigDecimal.ONE,mcloc);
                exps = BigDecimal.ONE.divide(c,mcloc);
 
                for (int npr = 2; npr <= kmax; npr++) {
                    /* the error estimate above for npr=1 is the worst case of
                     * the absolute error created by an error in 2pi. So we can
                     * safely re-use the exp2p value computed above without
                     * reassessment of its error.
                     */
                    //System.out.print("Z<"+npr+":"+kmax);
                    c = exp2p.pow(npr,mcloc).subtract(BigDecimal.ONE,mcloc);
                    c = c.multiply(new BigDecimal( (new BigInteger("" + npr)).pow(n) ) ,mcloc);
                    c = BigDecimal.ONE.divide(c,mcloc);
                    exps = exps.add(c,mcloc);
                    //System.out.println(">");
                }
           
            }
            else {
                //System.out.print("p");
                /* since the argument n is at least 9 here, the drop
                 * of the terms is at rather constant pace at least 10^-3, for example
                 * 0.0096, 0.5e-7, 0.3e-11, 0.6e-15 etc. We want these terms
                 * fall below eps/10.
                 */
                int kmax = (1 + mc.getPrecision()) / 3;
                //eps /= kmax;
                /* need an error of eps for 2/(exp(2pi)-1)*(1+4*Pi/8/(1-exp(-2pi)) = 0.0096
                 * at k=7 or = 0.00766 at k=13 for example.
                 * The absolute error is 0.017*err(pi) at k=9, 0.013*err(pi) at k=13, 0.012 at k=17
                 */
                BigDecimal twop = pi(mcloc);//new MathContext(3 + err2prec(3.14, eps / 0.017)));
                twop = twop.multiply(TWO,mcloc);
                BigDecimal exp2p = exp(twop,mcloc);
                BigDecimal c = exp2p.subtract(BigDecimal.ONE,mcloc);
                exps = BigDecimal.ONE.divide(c,mcloc);
                c = BigDecimal.ONE.subtract(BigDecimal.ONE.divide(exp2p,mcloc),mcloc);
                c = twop.divide(c,nmc).multiply(TWO,mcloc);
                BigDecimal nm1=new BigDecimal(n - 1);
                c = c.divide(nm1,mcloc).add(BigDecimal.ONE,mcloc);
                exps = exps.multiply(c,mcloc);
 
               
                for (int npr = 2; npr <= kmax; npr++) {
                    //System.out.print("z<"+npr+":"+kmax);
                    c = exp2p.pow(npr,mcloc).subtract(BigDecimal.ONE,mcloc);
                    c = c.multiply(new BigDecimal( (new BigInteger("" + npr)).pow(n) ),mcloc);
 
                    BigDecimal d = BigDecimal.ONE.divide(exp2p.pow(npr,mcloc),mcloc);
                    d = BigDecimal.ONE.subtract(d,mcloc);
                    d = twop.divide(d,mcloc).multiply(new BigDecimal(2 * npr),mcloc);
                    d = d.divide(nm1,mcloc).add(BigDecimal.ONE,mcloc);
 
                    d = d.divide(c,mcloc);
 
                    exps = exps.add(d,nmc);
                    //System.out.println(">");
                }
 
            }
            exps = exps.multiply(TWO,mcloc);
            return ftrm.subtract(exps, mc);
        }
    } /* zeta */
 
    /** Riemann zeta function.
     * @param n The positive integer argument.
     * @return zeta(n)-1.
     * @since 2009-08-20
     */
    static public double zeta1(final int n) {
        /* precomputed static table in double precision
                */
        final double[] zmin1 = { 0.,
            0.,
            6.449340668482264364724151666e-01,
            2.020569031595942853997381615e-01,
            8.232323371113819151600369654e-02,
            3.692775514336992633136548646e-02,
            1.734306198444913971451792979e-02,
            8.349277381922826839797549850e-03,
            4.077356197944339378685238509e-03,
            2.008392826082214417852769232e-03,
            9.945751278180853371459589003e-04,
            4.941886041194645587022825265e-04,
            2.460865533080482986379980477e-04,
            1.227133475784891467518365264e-04,
            6.124813505870482925854510514e-05,
            3.058823630702049355172851064e-05,
            1.528225940865187173257148764e-05,
            7.637197637899762273600293563e-06,
            3.817293264999839856461644622e-06,
            1.908212716553938925656957795e-06,
            9.539620338727961131520386834e-07,
            4.769329867878064631167196044e-07,
            2.384505027277329900036481868e-07,
            1.192199259653110730677887189e-07,
            5.960818905125947961244020794e-08,
            2.980350351465228018606370507e-08,
            1.490155482836504123465850663e-08,
            7.450711789835429491981004171e-09,
            3.725334024788457054819204018e-09,
            1.862659723513049006403909945e-09,
            9.313274324196681828717647350e-10,
            4.656629065033784072989233251e-10,
            2.328311833676505492001455976e-10,
            1.164155017270051977592973835e-10,
            5.820772087902700889243685989e-11,
            2.910385044497099686929425228e-11,
            1.455192189104198423592963225e-11,
            7.275959835057481014520869012e-12,
            3.637979547378651190237236356e-12,
            1.818989650307065947584832101e-12,
            9.094947840263889282533118387e-13,
            4.547473783042154026799112029e-13,
            2.273736845824652515226821578e-13,
            1.136868407680227849349104838e-13,
            5.684341987627585609277182968e-14,
            2.842170976889301855455073705e-14,
            1.421085482803160676983430714e-14,
            7.105427395210852712877354480e-15,
            3.552713691337113673298469534e-15,
            1.776356843579120327473349014e-15,
            8.881784210930815903096091386e-16,
            4.440892103143813364197770940e-16,
            2.220446050798041983999320094e-16,
            1.110223025141066133720544570e-16,
            5.551115124845481243723736590e-17,
            2.775557562136124172581632454e-17,
            1.387778780972523276283909491e-17,
            6.938893904544153697446085326e-18,
            3.469446952165922624744271496e-18,
            1.734723476047576572048972970e-18,
            8.673617380119933728342055067e-19,
            4.336808690020650487497023566e-19,
            2.168404344997219785013910168e-19,
            1.084202172494241406301271117e-19,
            5.421010862456645410918700404e-20,
            2.710505431223468831954621312e-20,
            1.355252715610116458148523400e-20,
            6.776263578045189097995298742e-21,
            3.388131789020796818085703100e-21,
            1.694065894509799165406492747e-21,
            8.470329472546998348246992609e-22,
            4.235164736272833347862270483e-22,
            2.117582368136194731844209440e-22,
            1.058791184068023385226500154e-22,
            5.293955920339870323813912303e-23,
            2.646977960169852961134116684e-23,
            1.323488980084899080309451025e-23,
            6.617444900424404067355245332e-24,
            3.308722450212171588946956384e-24,
            1.654361225106075646229923677e-24,
            8.271806125530344403671105617e-25,
            4.135903062765160926009382456e-25,
            2.067951531382576704395967919e-25,
            1.033975765691287099328409559e-25,
            5.169878828456431320410133217e-26,
            2.584939414228214268127761771e-26,
            1.292469707114106670038112612e-26,
            6.462348535570531803438002161e-27,
            3.231174267785265386134814118e-27,
            1.615587133892632521206011406e-27,
            8.077935669463162033158738186e-28,
            4.038967834731580825622262813e-28,
            2.019483917365790349158762647e-28,
            1.009741958682895153361925070e-28,
            5.048709793414475696084771173e-29,
            2.524354896707237824467434194e-29,
            1.262177448353618904375399966e-29,
            6.310887241768094495682609390e-30,
            3.155443620884047239109841220e-30,
            1.577721810442023616644432780e-30,
            7.888609052210118073520537800e-31 };
        if (n <= 0)
            throw new ProviderException("Not implemented: zeta at negative argument " + n);
        if (n == 1)
            throw new ArithmeticException("Pole at zeta(1) ");
 
        if (n < zmin1.length)
            /* look it up if available */
            return zmin1[n];
        else {
            /* Result is roughly 2^(-n), desired accuracy 18 digits. If zeta(n) is computed, the equivalent accuracy
             * in relative units is higher, because zeta is around 1.
             */
            double eps = 1.e-18 * Math.pow(2., (double) (-n));
            MathContext mc = new MathContext(err2prec(eps));
            Bernoulli bern_cache=new Bernoulli();
            Factorial fact_cache=new Factorial();
            return zeta(n, mc,bern_cache,fact_cache).subtract(BigDecimal.ONE).doubleValue();
        }
    } /* zeta */
 
 
    /** trigonometric cot.
     * @param x The argument.
     * @return cot(x) = 1/tan(x).
     */
    static public double cot(final double x) {
        return 1. / Math.tan(x);
    }
 
    /** Digamma function.
     * @param x The main argument.
     * @return psi(x).
     *  The error is sometimes up to 10 ulp, where AS 6.3.15 suffers from cancellation of digits and psi=0
     * @since 2009-08-26
     */
    static public double psi(final double x) {
        /*
         * the single positive zero of psi(x)
         */
        final double psi0 = 1.46163214496836234126265954232572132846819;
        if (x > 2.0) {
            /*
             * Reduce to a value near x=1 with the standard recurrence formula.
             * Abramowitz-Stegun 6.3.5
             */
            int m = (int) (x - 0.5);
            double xmin1 = x - m;
            double resul = 0.;
            for (int i = 1; i <= m; i++)
                resul += 1. / (x - i);
            return resul + psi(xmin1);
        }
        else if (Math.abs(x - psi0) < 0.55) {
            /* Taylor approximation around the local zero
                        */
            final double[] psiT0 = { 9.67672245447621170427e-01,
                -4.42763168983592106093e-01,
                2.58499760955651010624e-01,
                -1.63942705442406527504e-01,
                1.07824050691262365757e-01,
                -7.21995612564547109261e-02,
                4.88042881641431072251e-02,
                -3.31611264748473592923e-02,
                2.25976482322181046596e-02,
                -1.54247659049489591388e-02,
                1.05387916166121753881e-02,
                -7.20453438635686824097e-03,
                4.92678139572985344635e-03,
                -3.36980165543932808279e-03,
                2.30512632673492783694e-03,
                -1.57693677143019725927e-03,
                1.07882520191629658069e-03,
                -7.38070938996005129566e-04,
                5.04953265834602035177e-04,
                -3.45468025106307699556e-04,
                2.36356015640270527924e-04,
                -1.61706220919748034494e-04,
                1.10633727687474109041e-04,
                -7.56917958219506591924e-05,
                5.17857579522208086899e-05,
                -3.54300709476596063157e-05,
                2.42400661186013176527e-05,
                -1.65842422718541333752e-05,
                1.13463845846638498067e-05,
                -7.76281766846209442527e-06,
                5.31106092088986338732e-06,
                -3.63365078980104566837e-06,
                2.48602273312953794890e-06,
                -1.70085388543326065825e-06,
                1.16366753635488427029e-06,
                -7.96142543124197040035e-07,
                5.44694193066944527850e-07,
                -3.72661612834382295890e-07,
                2.54962655202155425666e-07,
                -1.74436951177277452181e-07,
                1.19343948298302427790e-07,
                -8.16511518948840884084e-08,
                5.58629968353217144428e-08,
                -3.82196006191749421243e-08,
                2.61485769519618662795e-08,
                -1.78899848649114926515e-08,
                1.22397314032336619391e-08,
                -8.37401629767179054290e-09,
                5.72922285984999377160e-09 };
            final double xdiff = x - psi0;
            double resul = 0.;
            for (int i = psiT0.length - 1; i >= 0; i--)
                resul = resul * xdiff + psiT0[i];
            return resul * xdiff;
        }
        else if (x < 0.) {
            /* Reflection formula */
            double xmin = 1. - x;
            return psi(xmin) + Math.PI / Math.tan(Math.PI * xmin);
        }
        else {
            double xmin1 = x - 1;
            double resul = 0.;
            for (int k = 26; k >= 1; k--) {
                resul -= zeta1(2 * k + 1);
                resul *= xmin1 * xmin1;
            }
            /* 0.422... = 1 -gamma */
            return resul + 0.422784335098467139393487909917597568 + 0.5 / xmin1 - 1. / (1 - xmin1 * xmin1) - Math.PI / (2. * Math.tan(Math.PI * xmin1));
        }
    } /* psi */
 
 
    /** Broadhurst ladder sequence.
     * @param a The vector of 8 integer arguments
     * @param mc Specification of the accuracy of the result
     * @return S_(n,p)(a)
     * @since 2009-08-09
     * @see <a href="http://arxiv.org/abs/math/9803067">arXiv:math/9803067</a>
     */
    static protected BigDecimal broadhurstBBP(final int n, final int p, final int[] a, MathContext mc) {
        /*
         * Explore the actual magnitude of the result first with a quick estimate.
         */
        double x = 0.0;
        for (int k = 1; k < 10; k++)
            x += a[(k - 1) % 8] / Math.pow(2., p * (k + 1) / 2) / Math.pow((double) k, n);
 
        /*
         * Convert the relative precision and estimate of the result into an absolute precision.
         */
        //double eps = prec2err(x, mc.getPrecision()); // removed by l.bruninx, 2012-03-22
 
        /* Divide this through the number of terms in the sum to account for error accumulation
         * The divisor 2^(p(k+1)/2) means that on the average each 8th term in k has shrunk by
         * relative to the 8th predecessor by 1/2^(4p).  1/2^(4pc) = 10^(-precision) with c the 8term
         * cycles yields c=log_2( 10^precision)/4p = 3.3*precision/4p  with k=8c
         */
        //int kmax = (int) (6.6 * mc.getPrecision() / p); // removed by l.bruninx, 2012-03-22
 
        /* Now eps is the absolute error in each term */
        //eps /= kmax; // removed by l.bruninx, 2012-03-22
        BigDecimal res = BigDecimal.ZERO;
        MathContext nmc=_mc_plus2_(mc);
        BigDecimal oldres=res;     // modoiied by l.bruninx, 2012-03-22
        int c = 0;                 // modoiied by l.bruninx, 2012-03-22
        do{                        // modoiied by l.bruninx, 2012-03-22
            oldres=res.round(mc);  // modoiied by l.bruninx, 2012-03-22
            Rational r = new Rational();
            for (int k = 0; k < 8; k++) {
                Rational tmp = new Rational(new BigInteger("" + a[k]), (new BigInteger("" + (1 + 8 * c + k))).pow(n));
                /* floor( (pk+p)/2)
                                */
                int pk1h = p * (2 + 8 * c + k) / 2;
                tmp = tmp.divide(BigInteger.ONE.shiftLeft(pk1h));
                r = r.add(tmp);
            }
 
            //if (Math.abs(r.doubleValue()) < eps)// removed by l.bruninx, 2012-03-22
            //    break;                          // removed by l.bruninx, 2012-03-22
            //MathContext mcloc = new MathContext(1 + err2prec(r.doubleValue(), eps)); // removed by l.bruninx, 2012-03-22
            res = res.add(r.BigDecimalValue(nmc),nmc);
            c++;                                    // modoiied by l.bruninx, 2012-03-22
        }while(res.round(mc).compareTo(oldres)!=0); // modoiied by l.bruninx, 2012-03-22
        return res.round(mc);
    } /* broadhurstBBP */
 
 
    /** Add a BigDecimal and a BigInteger.
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @since 2012-03-02
     */
    static public BigDecimal add(final BigDecimal x, final BigInteger y) {
        return x.add(new BigDecimal(y));
    } /* add */
 
 
    /** Add and round according to the larger of the two ulp's.
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @since 2009-07-30
     */
    static public BigDecimal addRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.add(y);
        /*
         * The estimation of the absolute error in the result is |err(y)|+|err(x)|
         */
        double errR = Math.abs(y.ulp().doubleValue() / 2.) + Math.abs(x.ulp().doubleValue() / 2.);
        MathContext mc = new MathContext(err2prec(resul.doubleValue(), errR));
        return resul.round(mc);
    } /* addRound */
 
    /** Add and round according to the larger of the two ulp's.
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @since 2010-07-19
     */
    static public BigComplex addRound(final BigComplex x, final BigDecimal y) {
        final BigDecimal R = addRound(x.re, y);
        return new BigComplex(R, x.im);
    } /* addRound */
 
    /** Add and round according to the larger of the two ulp's.
     * @param x The left summand
     * @param y The right summand
     * @return The sum x+y.
     * @since 2010-07-19
     */
    static public BigComplex addRound(final BigComplex x, final BigComplex y) {
        final BigDecimal R = addRound(x.re, y.re);
        final BigDecimal I = addRound(x.im, y.im);
        return new BigComplex(R, I);
    } /* addRound */
 
    /** Subtract and round according to the larger of the two ulp's.
     * @param x The left term.
     * @param y The right term.
     * @return The difference x-y.
     * @since 2009-07-30
     */
    static public BigDecimal subtractRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.subtract(y);
        /*
         * The estimation of the absolute error in the result is |err(y)|+|err(x)|
         */
        double errR = Math.abs(y.ulp().doubleValue() / 2.) + Math.abs(x.ulp().doubleValue() / 2.);
        MathContext mc = new MathContext(err2prec(resul.doubleValue(), errR));
        return resul.round(mc);
    } /* subtractRound */
 
    /** Subtract and round according to the larger of the two ulp's.
     * @param x The left summand
     * @param y The right summand
     * @return The difference x-y.
     * @since 2010-07-19
     */
    static public BigComplex subtractRound(final BigComplex x, final BigComplex y) {
        final BigDecimal R = subtractRound(x.re, y.re);
        final BigDecimal I = subtractRound(x.im, y.im);
        return new BigComplex(R, I);
    } /* subtractRound */
 
    /** Multiply and round.
     * @param x The left factor.
     * @param y The right factor.
     * @return The product x*y.
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final BigDecimal y) {
        BigDecimal resul = x.multiply(y);
        /* The estimation of the relative error in the result is the sum of the relative
         * errors |err(y)/y|+|err(x)/x|
         */
        MathContext mc = new MathContext(Math.min(x.precision(), y.precision()));
        return resul.round(mc);
    } /* multiplyRound */
 
    /** Multiply and round.
     * @param x The left factor.
     * @param y The right factor.
     * @return The product x*y.
     * @since 2010-07-19
     */
    static public BigComplex multiplyRound(final BigComplex x, final BigDecimal y) {
        BigDecimal R = multiplyRound(x.re, y);
        BigDecimal I = multiplyRound(x.im, y);
        return new BigComplex(R, I);
    } /* multiplyRound */
 
    /** Multiply and round.
     * @param x The left factor.
     * @param y The right factor.
     * @return The product x*y.
     * @since 2010-07-19
     */
    static public BigComplex multiplyRound(final BigComplex x, final BigComplex y) {
        BigDecimal R = subtractRound(multiplyRound(x.re, y.re), multiplyRound(x.im, y.im));
        BigDecimal I = addRound(multiplyRound(x.re, y.im), multiplyRound(x.im, y.re));
        return new BigComplex(R, I);
    } /* multiplyRound */
 
    /** Multiply and round.
     * @param x The left factor.
     * @param f The right factor.
     * @return The product x*f.
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final Rational f) {
        if (f.compareTo(BigInteger.ZERO) == 0)
            return BigDecimal.ZERO;
        else {
            /*
             * Convert the rational value with two digits of extra precision
             */
            MathContext mc = new MathContext(2 + x.precision());
            BigDecimal fbd = f.BigDecimalValue(mc);
 
            /*
             * and the precision of the product is then dominated by the precision in x
             */
            return multiplyRound(x, fbd);
        }
    }
 
    /** Multiply and round.
     * @param x The left factor.
     * @param n The right factor.
     * @return The product x*n.
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final int n) {
        BigDecimal resul = x.multiply(new BigDecimal(n));
        /*
         * The estimation of the absolute error in the result is |n*err(x)|
         */
        MathContext mc = new MathContext(n != 0 ? x.precision(): 0);
        return resul.round(mc);
    }
 
    /** Multiply and round.
     * @param x The left factor.
     * @param n The right factor.
     * @return the product x*n
     * @since 2009-07-30
     */
    static public BigDecimal multiplyRound(final BigDecimal x, final BigInteger n) {
        BigDecimal resul = x.multiply(new BigDecimal(n));
        /*
         * The estimation of the absolute error in the result is |n*err(x)|
         */
        MathContext mc = new MathContext(n.compareTo(BigInteger.ZERO) != 0 ? x.precision(): 0);
        return resul.round(mc);
    }
 
    /** Divide and round.
     * @param x The numerator
     * @param y The denominator
     * @return the divided x/y
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final BigDecimal y) {
        /*
         * The estimation of the relative error in the result is |err(y)/y|+|err(x)/x|
         */
        MathContext mc = new MathContext(Math.min(x.precision(), y.precision()));
        BigDecimal resul = x.divide(y, mc);
        /* If x and y are precise integer values that may have common factors,
         * the method above will truncate trailing zeros, which may result in
         * a smaller apparent accuracy than starte... add missing trailing zeros now.
         */
        return scalePrec(resul, mc);
    }
 
    /** Build the inverse and maintain the approximate accuracy.
     * @param z The denominator
     * @return The divided 1/z = [Re(z)-i*Im(z)]/ [Re^2 z + Im^2 z]
     * @since 2010-07-19
     */
    static public BigComplex invertRound(final BigComplex z) {
        if (z.im.compareTo(BigDecimal.ZERO) == 0) {
            /*
             * In this case with vanishing Im(x), the result is  simply 1/Re z.
             */
            final MathContext mc = new MathContext(z.re.precision());
            return new BigComplex(BigDecimal.ONE.divide(z.re, mc));
        }
        else if (z.re.compareTo(BigDecimal.ZERO) == 0) {
            /*
             * In this case with vanishing Re(z), the result is  simply -i/Im z
             */
            final MathContext mc = new MathContext(z.im.precision());
            return new BigComplex(BigDecimal.ZERO, BigDecimal.ONE.divide(z.im, mc).negate());
        }
        else {
            /*
             * 1/(x.re+I*x.im) = 1/(x.re+x.im^2/x.re) - I /(x.im +x.re^2/x.im)
             */
            BigDecimal R = addRound(z.re, divideRound(multiplyRound(z.im, z.im), z.re));
            BigDecimal I = addRound(z.im, divideRound(multiplyRound(z.re, z.re), z.im));
            MathContext mc = new MathContext(1 + R.precision());
            R = BigDecimal.ONE.divide(R, mc);
            mc = new MathContext(1 + I.precision());
            I = BigDecimal.ONE.divide(I, mc);
            return new BigComplex(R, I.negate());
        }
    }
 
    /** Divide and round.
     * @param x The numerator
     * @param y The denominator
     * @return the divided x/y
     * @since 2010-07-19
     */
    static public BigComplex divideRound(final BigComplex x, final BigComplex y) {
        return multiplyRound(x, invertRound(y));
    }
 
    /** Divide and round.
     * @param x The numerator
     * @param n The denominator
     * @return the divided x/n
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final int n) {
        /*
         * The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return x.divide(new BigDecimal(n), mc);
    }
 
    /** Divide and round.
     * @param x The numerator
     * @param n The denominator
     * @return the divided x/n
     * @since 2009-07-30
     */
    static public BigDecimal divideRound(final BigDecimal x, final BigInteger n) {
        /*
         * The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return x.divide(new BigDecimal(n), mc);
    } /* divideRound */
 
    /** Divide and round.
     * @param n The numerator
     * @param x The denominator
     * @return the divided n/x
     * @since 2009-08-05
     */
    static public BigDecimal divideRound(final BigInteger n, final BigDecimal x) {
        /*
         * The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return new BigDecimal(n).divide(x, mc);
    } /* divideRound */
 
    /** Divide and round.
     * @param n The numerator
     * @param x The denominator
     * @return the divided n/x
     * @since 2012-03-01
     */
    static public BigComplex divideRound(final BigInteger n, final BigComplex x) {
        /*
         * catch case of real-valued denominator first
         */
        if (x.im.compareTo(BigDecimal.ZERO) == 0)
            return new BigComplex(divideRound(n, x.re), BigDecimal.ZERO);
        else if (x.re.compareTo(BigDecimal.ZERO) == 0)
            return new BigComplex(BigDecimal.ZERO, divideRound(n, x.im).negate());
 
        BigComplex z = invertRound(x);
        /*
         * n/(x+iy) = nx/(x^2+y^2) -nyi/(x^2+y^2)
         */
        BigDecimal repart = multiplyRound(z.re, n);
        BigDecimal impart = multiplyRound(z.im, n);
        return new BigComplex(repart, impart);
    } /* divideRound */
 
    /** Divide and round.
     * @param n The numerator.
     * @param x The denominator.
     * @return the divided n/x.
     * @since 2009-08-05
     */
    static public BigDecimal divideRound(final int n, final BigDecimal x) {
        /*
         * The estimation of the relative error in the result is |err(x)/x|
         */
        MathContext mc = new MathContext(x.precision());
        return new BigDecimal(n).divide(x, mc);
    }
 
    /** Append decimal zeros to the value. This returns a value which appears to have
     * a higher precision than the input.
     * @param x The input value
     * @param d The (positive) value of zeros to be added as least significant digits.
     * @return The same value as the input but with increased (pseudo) precision.
     */
    static public BigDecimal scalePrec(final BigDecimal x, int d) {
        return x.setScale(d + x.scale());
    }
 
    /** Append decimal zeros to the value. This returns a value which appears to have
     * a higher precision than the input.
     * @param x The input value
     * @param d The (positive) value of zeros to be added as least significant digits.
     * @return The same value as the input but with increased (pseudo) precision.
     */
    static public BigComplex scalePrec(final BigComplex x, int d) {
        return new BigComplex(scalePrec(x.re, d), scalePrec(x.im, d));
    }
 
    /** Boost the precision by appending decimal zeros to the value. This returns a value which appears to have
     * a higher precision than the input.
     * @param x The input value
     * @param mc The requirement on the minimum precision on return.
     * @return The same value as the input but with increased (pseudo) precision.
     */
    static public BigDecimal scalePrec(final BigDecimal x, final MathContext mc) {
        final int diffPr = mc.getPrecision() - x.precision();
        if (diffPr > 0)
            return scalePrec(x, diffPr);
        else
            return x;
    } /* BigDecimalMath.scalePrec */
 
    /** Convert an absolute error to a precision.
     * @param x The value of the variable
     * @param xerr The absolute error in the variable
     * @return The number of valid digits in x.
     *    The value is rounded down, and on the pessimistic side for that reason.
     * @since 2009-06-25
     */
    static public int err2prec(BigDecimal x, BigDecimal xerr) {
        return err2prec(xerr.divide(x, MathContext.DECIMAL64).doubleValue());
    }
 
    /** Convert an absolute error to a precision.
     * @param x The value of the variable
     *    The value returned depends only on the absolute value, not on the sign.
     * @param xerr The absolute error in the variable
     *    The value returned depends only on the absolute value, not on the sign.
     * @return The number of valid digits in x.
     *    Derived from the representation x+- xerr, as if the error was represented
     *    in a "half width" (half of the error bar) form.
     *    The value is rounded down, and on the pessimistic side for that reason.
     * @since 2009-05-30
     */
    static public int err2prec(double x, double xerr) {
        /* Example: an error of xerr=+-0.5 at x=100 represents 100+-0.5 with
         * a precision = 3 (digits).
         */
        return 1 + (int) (Math.log10(Math.abs(0.5 * x / xerr)));
    }
 
    /** Convert a relative error to a precision.
     * @param xerr The relative error in the variable.
     *    The value returned depends only on the absolute value, not on the sign.
     * @return The number of valid digits in x.
     *    The value is rounded down, and on the pessimistic side for that reason.
     * @since 2009-08-05
     */
    static public int err2prec(double xerr) {
        /* Example: an error of xerr=+-0.5 a precision of 1 (digit), an error of
         * +-0.05 a precision of 2 (digits)
         */
        return 1 + (int) (Math.log10(Math.abs(0.5 / xerr)));
    }
 
    /** Convert a precision (relative error) to an absolute error.
     *    The is the inverse functionality of err2prec().
     * @param x The value of the variable
     *    The value returned depends only on the absolute value, not on the sign.
     * @param prec The number of valid digits of the variable.
     * @return the absolute error in x.
     *    Derived from the an accuracy of one half of the ulp.
     * @since 2009-08-09
     */
    static public double prec2err(final double x, final int prec) {
        return 5. * Math.abs(x) * Math.pow(10., -prec);
    }
 
 
    /* ----------------------------------------------------------------------------------
     *
     * Additional methods inspired from the java.lang.Math class model...
     *
     */
 
 
    /**
     * Returns the largest (closest to positive infinity) BigDecimal value that
     * is less than or equal to the argument and is equal to a mathematical integer.
     *
     * This method is similar to Math.floor(double) but it applies to a BigDecimal
     * value.
     *
     * @param x the BigDecimal value
     * @return the largest (closest to positive infinity) BigDecimal value that less
     *    than or equal to the argument and is equal to a mathematical integer.
     * @since 2012-03-15
     * @author l.bruninx
     */
    static public BigDecimal floor(BigDecimal x) {
        return x.setScale(0, BigDecimal.ROUND_FLOOR);
    }
 
    /**
     * Returns the smallest (closest to negative infinity) BigDecimal value that is
     * greater than or equal to the argument and is equal to a mathematical integer.
     *
     * This method is similar to Math.ceil(double) but it applies to a BigDecimal
     * value.
     *
     * @param x the BigDecimal value
     * @return the smallest (closest to negative infinity) floating-point value that
     *    is greater than or equal to the argument and is equal to a mathematical integer.
     * @since 2012-03-15
     * @author l.bruninx
     */
    static public BigDecimal ceil(BigDecimal x) {
        return x.setScale(0, BigDecimal.ROUND_CEILING);
    }
 
    /**
     * Returns the closest integer value to the argument. The result is rounded to
     * an integer value by adding 1/2, taking the floor of the result. In other words,
     * the result is equal to the value of the expression:
     *
     *     BigDecimal.floor(a.add(new BigDecimal(0.5d)))
     *
     * This method is similar to Math.ceil(double) but it applies to a BigDecimal
     * value.
     *
     * @param x the BigDecimal value
     * @return the smallest (closest to negative infinity) floating-point value that
     *    is greater than or equal to the argument and is equal to a mathematical integer.
     * @since 2012-03-15
     * @author l.bruninx
     */
    static public BigDecimal round(BigDecimal x) {
        return x.setScale(0, BigDecimal.ROUND_HALF_DOWN);
    }
 
    /**
     * Returns the converted value from radians to degrees.
     *
     * @param x the BigDecimal value in radians
     * @param mc Arbitrary MathContext
     * @return the BigDecimal value in degrees
     * @since 2012-03-22
     * @author l.bruninx
     */
    static public BigDecimal toDegrees(BigDecimal x, MathContext mc) {
        MathContext nmc = _mc_adjust_(x,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
        BigDecimal c=new BigDecimal(180,nmc);
        c= x.multiply(c).divide(pi(nmc), nmc);
        return _dec_round_(c,mc);                        // mc precision (the integer part include by _dec_round_)...
    }
   
    /**
     * Returns the converted value from detgrees to radians.
     *
     * @param x the BigDecimal value in degrees
     * @param mc Arbitrary MathContext
     * @return the BigDecimal value in radians
     * @since 2012-03-22
     * @author l.bruninx
     */
    static public BigDecimal toRadians(BigDecimal x, MathContext mc) {
        MathContext nmc = _mc_adjust_(x,_mc_plus2_(mc)); // more precision (the integer part include by _mc_adjust_)...
        BigDecimal c=new BigDecimal(180,nmc);
        c=x.divide(c,nmc).multiply(pi(nmc));
        return _dec_round_(c,mc);                        // mc precision (the integer part include by _dec_round_)...
    }
   
} /* BigDecimalMath */