package jt;


import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import jt.data.Degree;
import jt.data.HoroData;

/**
 * @author  Dileep Bapat
 * @description 
 * 		Horoscope encapsulates the calculation of planets using date and time and place coords
 * Currently the calculation is constrained for IST only. replacing 5.5 with timezone offset should
 * work for other zones.. but never tested it. 
 * 
 * These functions are originally implemented in c/c++ (1999-2003) then ported directly to java.
 * there are lot of strange implementation especially in this class. Please bear with me..
 */
public class Horoscope {

	private float Pos_bhava[] = new float[12];
	private float Pos_pos[] = new float[10];
	private float Pos_ayanamsha;
	private int   Pos_tithi;
	
	private String[] Hous = new String[]{"Aries",
			"Taurus",
			"Gemini",
			"Cancer",
			"Leo",
			"Virgo",
			"Libra",
			"Scorpio",
			"Sagittarius",
			"Capricorn",
			"Aquarius",
			"Pisces"};
	
	private String[] Pos = new String[]{"Sun",
			"Moon",
			"Mercury",
			"Venus",
			"Mars",
			//"Ceres",
			"Jupiter",
			"Saturn",
			"Uranus",
			"Neptune",
			"Pluto"};
	
	public String toString() {
		String horo = "Horoscope : " + horoData.toString() + "\n\n";
		for (int i=0; i<Pos_pos.length; i++) {
			horo = horo + Pos[i]+" = " + Pos_pos[i] + "\n";
		}
		horo += "\n";
		for (int i=0; i<Pos_bhava.length; i++) {
			horo = horo + Hous[i] + " = " + Pos_bhava[i] + "\n";
		}
		horo = horo + "\n" + "Sun raise time : " + getRaviUdaya().toString();
		horo = horo + "\n" + "Amount of precession : " + new Degree(Pos_ayanamsha);
		try {
		horo = horo + "\n" + "Lunar day: " + Pos_tithi + " ["+jt.lang.Helper.getTithiName(Pos_tithi)+"]";
		}catch (Exception e ) {}
		return horo;
	}
	/**
	 * @param args
	 */
	public Horoscope(HoroData horoData) {
		setHoroData(horoData);
		setAyanamshaType(AyanamshaType.Krishnamoorthi);
	}
	
	
	/**
	 * Current Ayanmsha calculatin method to be used to horoscope
	 * @uml.property  name="ayanamshaType"
	 */
	private int ayanamshaType = AyanamshaType.Krishnamoorthi;
	/**
	 * Getter of the property <tt>ayanamshaType</tt>
	 * @return  Returns the ayanamshaType.
	 * @uml.property  name="ayanamshaType"
	 */
	public int getAyanamshaType() {
		return ayanamshaType;
	}
	/**
	 * Setter of the property <tt>ayanamshaType</tt>
	 * @param ayanamshaType  The ayanamshaType to set.
	 * @uml.property  name="ayanamshaType"
	 */
	public void setAyanamshaType(int ayanamshaType) {
		this.ayanamshaType = ayanamshaType;
	}
	/** 
	 * @uml.property name="horoData"
	 * @uml.associationEnd multiplicity="(1 1)" inverse="horoscope:jt.data.HoroData"
	 * @uml.association name="uses"
	 */
	private HoroData horoData;// = new jt.data.HoroData(new Date());
	/** 
	 * Getter of the property <tt>horoData</tt>
	 * @return  Returns the horoData.
	 * @uml.property  name="horoData"
	 */
	public HoroData getHoroData() {
		return horoData;
	}
	/** 
	 * Setter of the property <tt>horoData</tt>
	 * @param horoData  The horoData to set.
	 * @uml.property  name="horoData"
	 */
	public void setHoroData(HoroData horoData) {
		this.horoData = horoData;
	}
	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		 System.out.println(TimeZone.getDefault());
		 //System.out.println("Calendar = " + Calendar.getInstance());
		 Calendar.getInstance();
		Horoscope horoscope = new Horoscope (new HoroData(new Date()));
		//System.out.println(horoscope.toString());
		horoscope.Calculate();
		//jt.lang.Helper.setLangCode("kan");
		System.out.println(horoscope.toString());
	}

	public void Calculate() {
		System.out.println("Calculating...");
		{
			 Calendar.getInstance();
			{
				//sex=seX?'f':'m';
		
				int dd = horoData.getDate().getDate(); 
				int mm = horoData.getDate().getMonth() + 1;
				int yy = horoData.getDate().getYear();
				int hh = horoData.getDate().getHours();
				int mn = horoData.getDate().getMinutes();
				
				double l1=horoData.getLattitude().getDoubleVal();
				double l2=horoData.getLongitude().getDoubleVal();
				double  h2_[] = new double[12];
				
				
				if(yy<20) yy+=100;
				if(yy>1900) yy-=1900;

				int dbk=dd;
				int mbk=mm;
				int ybk=yy+1900;
				//int hbk=hh;
				//int mnbk=mn;
				
				//tm_ = "" + hh + " : " +mn; //sprintf(tm_,"%02ld:%02ld \n",hh,mn);
				//dt_ = "" + dd + ":" + mm + ":" + (yy>1900?yy:yy+1900); //			sprintf(dt_,"\n%02ld:%02ld:%04ld",dd,mm,yy>1900?yy:yy+1900);

			if(geoGraphic)
			{System.out.println("Lattitude = %f  :  " + l1);
			
			l1=Math.atan(Math.tan(l1*22.0/7.0/180.0)*((6357.0*6357.0)/(6378.0*6378.0)));
			l1=l1*180.0*7.0/22.0;
			System.out.println("Lattitude = %f\n" + l1);
			}




			double a10[]={
			279.697,
			270.434,
			178.179,
			342.767,
			293.747,
			238.049,
			266.564
			};


			double a11[]={
			0.985647,
			13.1763,
			4.09237,
			1.60216,
			0.52407,
			0.08312,
			0.03349
			};

			double a12[]={
			0.3354,
			96.527,
			7.023,
			8.704,
			1.163,
			9.42,
			7.87
			};

			double a20[]={
			281.221,
			334.329,
			75.8997,
			130.164,
			334.218,
			12.7209,
			91.0982};

			double a21[]={
			1.719175,
			4069.03,
			1.55549,
			1.40804,
			1.84076,
			1.60996,
			1.95842};

			double a30[]={
			0,
			259.183,
			47.1459,
			75.7796,
			48.7864,
			99.4434,
			112.7904};

			double a31[]={
			0,
			-1934.142,
			1.1852,
			0.89985,
			0.770992,
			1.01053,
			0.873195};

			double a40[]={
			0,
			5.1454,
			7.00288,
			3.39363,
			1.85033,
			1.30874,
			2.49262};

			double a41[]={
			0,
			0,
			0.00186083,
			0.00100583,
			-0.000675,
			-0.00569611,
			-0.00391889};

			double a50[]={
			0.016751,
			0.0549005,
			0.205614,
			0.00682069,
			0.0933129,
			0.0483347,
			0.0558923};

			double a51[]={
			-0.418,
			0,
			0.2046,
			-0.4774,
			0.92064,
			1.6418,
			-3.455};

			double a6[]={      	1.0,
						0.00256,
						0.387098,
						0.723332,
						1.52369,
						5.20256,
						9.55475};

			/*int b2[]={
			7,
			20,
			6,
			10,
			7,
			18,
			16,
			19,
			17};*/

			//int m1[] ={ 31,28,31,30,31,30,31,31,30,31,30,31};

			int m9=mm;
			int	d9=dd;
			//int h9=hh;
			//int m8=mn;
			int y9=yy;
			float p1=(float)3.1415926;
			float p2=p1/180;
			//int	z9=y9+1900;
			float p3=(float)23.4523;
			float ay=(float)22.3667;
			float h8=(float)hh+(float)mn/60-(float)5.5;
			int i1=(int)(0.55+1/(float)m9);
			int i2=yy-i1;
			int i3=m9+i1*12-3;
			float d2=(float)58.5+(int)((float)365.25*i2)+(int)((float)30.6*i3+0.41)+d9;
			int vara=(int)(d2-(int)(d2/7)*7);
			vara=(vara+1)%7;
			float d3=h8/24;
			float t1=(d2+d3)/36525;

			switch (ayanamshaType) {
				case AyanamshaType.Krishnamoorthi:
					ay=ay+(float)50.24*t1/36; 
				break;
				case AyanamshaType.Raman: 
					ay=(float)((y9+1900-397)*50.5/3600);
				break;
				case AyanamshaType.Lahiri:
					/* Lahiri ayanamsha */ 
					{
						double d2r= 22.0/7/180;
						int juliean_days = mdy2julian(mbk, dbk, ybk);
						double t = ((juliean_days - 2415020) + h8/24 - 0.5)/36525;
						double lnode = fmod(((933060-6962911*t+7.5*t*t)/3600.0) , 360.0);  /* Mean lunar node */
						double Off = (259205536.0*t+2013816.0)/3600.0;             /* Mean Sun        */
						Off = 17.23 * Math.sin(d2r * lnode)+1.27* Math.sin(d2r * Off)-(5025.64+1.11*t)*t;
						Off = (Off- 80861.27)/3600.0;  // 84038.27 = Fagan-Bradley 80861.27 = Lahiri
						ay = (float)-Off;
					} break;
				default: new Exception("Invalid option for ayanamsha");
				}

			float b1,b2,b3,b4,c1,c2,e1,e2,f1,f2,g1,s1=0,s2=0,h1,h2,h3,h4,g2,g3,v1;
			float x1,x2,x3,y1=0,y2=0,y3=0,z1,z2,z3,t3,g4,hx,ag;//,na


			for(int i=0;i<7;i++)
			{
			b1=(float)(a11[i]*d2);b2=(float)(a12[i]*d2*0.000001);b3=(float)(a11[i]*d3);
			b1=fng(b1);
			b4=(float)(a10[i]+b1+b2+b3);
			b1=b4;b1=fng(b4);
			b2=b1*p2;
			c1=(float)(a20[i]+a21[i]*t1);c1=fng(c1);
			c2=c1*p2;
			e1=(float)(a30[i]+a31[i]*t1);e1=fng(e1);
			e2=e1*p2;
			f1=(float)(a40[i]+a41[i]*t1);f2=f1*p2;
			g1=(float)(a50[i]+a51[i]*t1*0.0001);
			h1=b2-c2;
			if(i==1)
			{
			 h2=b2-s1;
			 h3=b2-e2;
			 h4=2*h2;
			 b1=(float)(b1+6.289*Math.sin(h1)-1.274*Math.sin(h1-h4)+0.658*Math.sin(h4));
			 b1=(float)(b1+0.214*Math.sin(2*h1)- 0.186*Math.sin(s2)-0.114*Math.sin(2*h3));
			 b1=(float)(b1-0.0588*Math.sin(2*h1-h4)+0.0533*Math.sin(h1+h4)-0.0573*Math.sin(h1+s2-h4));
			 b1=(float)(b1-0.0459*Math.sin(s2-h4)+0.0411*Math.sin(h1-s2)-0.0346*Math.sin(h2));
			 b1=(float)(b1-0.0305*Math.sin(h1+s2)-0.0153*Math.sin(2*h3-h4)-0.0125*Math.sin(h1+2*h3));
			 b1=(float)(b1+0.011*Math.sin(h1-2*h3)+0.01*Math.sin(3*h1)-0.011*Math.sin(h1-2*h4));
			 b1=(float)(b1-8.500001e-03*Math.sin(2*h1-2*h4)-0.0079*Math.sin(h1-h4-s2));
			 b1=(float)(b1-0.0068*Math.sin(s2+h4));
//			 System.out.println("____= %ld",int(b1/360+5)%12+1);
			 b1=fng(b1);
			 h2_[i]=b1;
//			 e1=fng(e1);
			 h2_[7]=e1;
			 e2=e1+180;
			 e2=fng(e2);
			 h2_[8]=e2;
			}      //end moon ephmeris
			else{
			 g2=g1*g1;
			 g3=g2*g1;
			 v1=(float)(h1+2*g1*Math.sin(h1)+1.25*g2*Math.sin(2*h1));
			 v1+=g3*(1.08333*Math.sin(3*h1)-0.25*Math.sin(h1));
			 h2=v1+c2-e2;
			 h3=(float)(a6[i]*(1-g2)/(1+g1*Math.cos(v1)));
			 x1=(float)(h3*(Math.cos(e2)*Math.cos(h2)-Math.sin(e2)*Math.sin(h2)*Math.cos(f2)));
			 x2=(float)(h3*(Math.sin(e2)*Math.cos(h2)+Math.cos(e2)*Math.sin(h2)*Math.cos(f2)));
			 x3=(float)(h3*Math.sin(h2)*Math.sin(f2));
			 if(i==0)
				 {   //sun
				 s1=b2;
				 s2=h1;
				 y1=x1;
				 y2=x2;
				 y3=x3;
//				 System.out.println("Value= %f",(Math.atan(y2/y1)+p1/2*(y1<0))/p2);
//				 return 0;
				 h2_[i]=(Math.atan((y2)/(y1))+p1*(1-((y1<0)?0:1)))/p2;
				 if(h2_[i]<0) h2_[i]+=360;
				 }
			 else{
				z1=x1+y1;
				z2=x2+y2;
				z3=x3+y3;z3=(1==1?z3:z3); //foolish thing can be deleted (as ignoring)
				h2_[i]=	(Math.atan((z2)/(z1))+p1*(1-((z1<0)?0:1))) / p2;
				if(h2_[i]<0)h2_[i]+=360;
				}
			 }

//			 System.out.println("pos %f\n",fng(h2_[i]-ay+360));
			}

//			d2+=d3;
//			System.out.println("Days %f\n",h8);
////////////////////////////////////			//
			{
			float su_d2 = d2;
			float su_p3 =p3;
			float su_p2=p2;
			float su_p1=p1;
			float su_s=(float)h2_[0];


				su_s=(float)(su_s-h8*(360.0/365.25/24));
				su_s=(float)(fmod(su_s+360,360));
//////////			/
				float l=0,su_s_bk=su_s;
				//Ravi Udaya alternative implementation
				float H;
				for( H=0;H<24;H++)
				{	 l=calLagna(H,su_d2,su_p3,su_p2,su_p1,l1,l2);
					//if(su_s>335 && l<25) l+=360;
					su_s=su_s_bk;
					if(su_s-l>330)l+=360;
					else if(l-su_s>330)su_s+=360;
					if(Math.floor(su_s/25)==Math.floor(l/25))
						break;
				}
				if ( H>=24) new JTException("Check this.....might be error or wrong assumption");

				if (su_s<l)
				{ H--;}
			su_s=su_s_bk;
					
				//	if((int)(su_s/25)!=(int)(l/25))
				//	if((int)(su_s/25)!=(int)(calLagna(H,su_d2,su_p3,su_p2,su_p1+360)/25)) 
				//	{System.out.println("Error : Some thing is wrong in Sooryodaya calc1\n");//__asm int 3 
				//		 MessageBox(0,"Error in Sooryodaya first part so leaving the calculation.","Longitude out of India",MB_OK);
				//	 }
				

////////////			//
				//if(su_s-calLagna(7.5,su_d2,su_p3,su_p2,su_p1)>180) su_s+=360;
				//if(-su_s+calLagna(7.5,su_d2,su_p3,su_p2,su_p1)>180) su_s-=360;
				float h;
				for( h=H;h<H+2.5;h=(h+(float)0.1))
				{l=calLagna(h,su_d2,su_p3,su_p2,su_p1,l1,l2);
				  if(su_s-l>330)l+=360;
				 if(su_s<l && Math.abs((int)su_s-(int)l)<150)break;
				}
				 if(h>H+2.5) {System.out.println("Error : Some thing is wrong in Sooryodaya calc1\n");//__asm int 3 
					 new JTException("2. Suryodaya exids 7:30 so leaving the calculation.");
				 }
				 else
				 {
					h=h-(float)0.1;
					float th;
					for( th=h;th<h+0.15;th+=0.01)
					{   l=calLagna(th,su_d2,su_p3,su_p2,su_p1,l1,l2);
						if(su_s-l>330)l+=360; //circular difference.
						if(su_s<l)break;
					}
				   if(th>h+0.15) {System.out.println("Error : Some thing is wrong in Sooryodaya calc2\n");//__asm int 3 }
					 new JTException("3 Suryodaya exids 7:30 so leaving the calculation.");
					}
					h=th-(float)0.01;
					if(h<0)h+=24;
				 }
			    //	System.out.println("Su  %f   Lagna %f  time %f ",su_s-ay,calLagna(h,su_d2,su_p3,su_p2,su_p1)-ay,h);
				//System.out.println("RaviUdaya = %ld : %ld : %ld \n",int(h),(int)(fmod(h,1)*60),(int)(fmod(h*60,1)*60));
				raviUdaya=h;

//#######################################################  #ifdef IncludeBhava
				{
				double d2r=(22.0)/7/180;
				int juliean_days = mdy2julian(mbk, dbk, ybk);
				double t = ((juliean_days - 2415020) + h8/24 - 0.5)/36525;
				double ra = (fmod(((6.6460656 + 2400.0513 * t + 2.58e-5 * t * t + h8) * 15 + l2 /* - l2 for west ignored*/ ),360)) * d2r; // RAMC
				double ob = (23.452294 - 0.0130125 * t) * d2r; // Obliquity of Ecliptic
				double mc = Math.atan2(Math.tan(ra),Math.cos(ob));
					if(mc < 0.0)mc += 22.0/7;
					if(Math.sin(ra) < 0.0)mc += 22.0/7;
					mc /= d2r;
					mc = mc - ay + 360;
					mc = fmod(mc, 360.0);
					//{ char buf[256]; sSystem.out.println(buf, "MC is %lf", mc); MessageBox(0,buf, "MC", MB_OK);}

				double as = Math.atan2(Math.cos(ra),-Math.sin(ra)*Math.cos(ob)-Math.tan(l1 * d2r)*Math.sin(ob));
					if(as < 0.0)as += 22.0/7;
					if(Math.cos(ra) < 0.0)as += 22.0/7;
					as = fmod(as /d2r , 360.0);
					as = as - ay + 360;
					as = fmod(as, 360.0);

				for (int i=0; i<12; i++) Pos_bhava[i] = 0;

			if (houseDivType  == 0) { // Equal trisect between Lagna and mc (a.k.a Prophyry)
			/*	MC caculation as per Brian Conrad from indiapress.org */
				
				double x = as - mc;
				if(x < 0.0)x += 360.0;
				x /= 3;
				int y = 9;
				for(int i = 0; i < 4; i++){
					Pos_bhava[y] = (float)fmod(mc + x * i + 360.0, 360.0);
					y++;
					if(y >= 12)y = 0; 
				}  		
				x = fmod(mc - fmod(as + 180.0, 360.0), 360.0);
				if (x<0) x+=360.0;
				x /= 3;
				y = 6;
				for(int i = 0; i < 3; i++){
					Pos_bhava[y] = (float)fmod((as + 180 + x * i), 360.0);
					y++;
				}
				for(int i = 1; i < 6; i++){
					Pos_bhava[i] = (float)fmod((Pos_bhava[i+6] + 180), 360.0);
				}

			} else if (houseDivType  == 1) { //Regiomontanus method for house division.
					for (int i=1; i<=12; i++) Pos_bhava[i-1] = (float)GetRegHouse(i, ra,l1, ob,ay);
			} else if (houseDivType  == 2) { //Campanus method for house division.
					for (int i=1; i<=12; i++) Pos_bhava[i-1] = (float)GetCampanusHouse(i, ra,l1, ob,ay);

			}else if (houseDivType  == 3) { //Old time divisioned houses.
					for(int i=0;i<12;i++)
						Pos_bhava[i]=(float)fmod(calLagna((float)(h8+5.5+i*2),su_d2,su_p3,su_p2,su_p1,l1,l2)-ay+360,360);
			}
			}
//############################################			#endif


			}
////////////////////////////////////			/
			g1=(float)(99.691+0.985647*d2);
			g1=fng(g1+3.354e-7*d2+0.000387*t1*t1);
			g1=(float)(g1+h8*15.0411+l2);
//			g1=g1+h8*15.0417827298+l2;
			p3=(float)(p3-0.0130125*t1);
			p3=p3*p2;
			g2=(float)(Math.tan(p3)*Math.tan(l1*p2)*Math.cos(g1*p2));
			g2=(float)(Math.atan(1/g2+Math.tan(g1*p2)));
			if(g2>0)g2=g2-p1;
			g3=g1*p2-g2;
			t3=(float)(Math.tan(g3));
			g4=(float)(g3+Math.atan(t3*(1-Math.cos(p3))/(Math.cos(p3)+t3*t3)));
			hx=g4/p2; //conv to degree
			h2_[9]=fng(hx);
			ag=fng(h2_[1]-h2_[0]+360);
//			System.out.println("AG %f",ag);
//			na=(int)(ag/180);
//			if(na==0) System.out.println("Shukla  paksha\n");
//			else      System.out.println("Krishna paksha\n");
			int tithi=(int)ag/6;
//			tithi%=15;
//			System.out.println("Tithi %ld\n",tithi/2);
			float pos[] = new float[12];
			int ind[] = new int [12];
			for(int i=0;i<10;i++)
			{h3=(float)h2_[i]-ay;
			if(h3<0)h3+=360;
			pos[i]=h3;
			ind[i]=(int)h3/30;
			}
//			System.out.println("Lagna %s\n",a1_[ind[9]]);
//			s1=int(3*pos[1]/40);
//			nakshatra=s1;
//			System.out.println("Nakshatra %s\n",st_[(int)s1]);
//			s2=s1-int((s1)/9)*9;
//			s2=int(s2+0.001);
//			System.out.println("Dasha %s\n",p2_[(int)s2]);
//			for(i=0;i<10;i++)
//			System.out.println("%s\t\t@ %14f  degree, in %s (%ld)\n",b1_[i],pos[i],a1_[ind[i]],ind[i]+1);
//			System.out.println("________________________________________________________________________________\n");
//			getchar();
			/*System.out.println("Date %ld:%ld:%ld  Time   %ld:%ld \n",dd,mo,yy,hh,mn);
			System.out.println("Lattitude %ld:%ld\n",ld,lm);
			System.out.println("Longitude %ld:%ld\n",jd,jm);
			System.out.println("________________________________________________________________________________\n");
			*/
//			printtofile();


			for (int i=0;i<10;i++)
			Pos_pos[i]=pos[i];
			Pos_ayanamsha=ay;
			Pos_tithi = tithi / 2 + 1;
			//return(Pos);
			}
		}
	}
	
	
	private float calLagna(float h,float d2,float p3,float p2,float p1, double l1, double l2)
	{
	float g1,g2,g3,g4,t3,hx;
	h=h-(float)5.5;
	float t1=(d2+h/24)/36525;
	g1=(float)(99.691+0.985647*d2);
	g1=fng(g1+3.354e-7*d2+0.000387*t1*t1);
	p3=(float)(p3-0.0130125*t1);
	p3=p3*p2;
	g1=(float)(g1+h*15.0411+l2); //g1=g1+h8*15.0417827298+l2;
	g2=(float)(Math.tan(p3)*Math.tan(l1*p2)*Math.cos(g1*p2));
	g2=(float)(Math.atan(1/g2+Math.tan(g1*p2)));
	if(g2>0)g2=g2-p1;
	g3=g1*p2-g2;
	t3=(float)(Math.tan(g3));
	g4=(float)(g3+Math.atan(t3*(1-Math.cos(p3))/(Math.cos(p3)+t3*t3)));
	hx=g4/p2; //conv to degree
	hx=fng(hx);
	return hx;
	}


	private float fng(double val) {
		return (float)(val - (int)(val/360)*360);
	}
	
	private // calculate Julian Day from Month, Day and Year
	int mdy2julian(int m,int d,int y)
	{
		double im = 12 * (y + 4800) + m - 3;
		double j = (2 * (im - Math.floor(im/12) * 12) + 7 + 365 * im)/12;
		j = Math.floor(j) + d + Math.floor(im/48) - 32083;
		if(j > 2299171)j += Math.floor(im/4800) - Math.floor(im/1200) + 38;
		return (int)j;	
		
	}
	
	private double d2r(double x) {
		return x/180*22/7;
	}

	private double r2d(double x) {
		return x*180/22*7;
	}
	private double calcAsce(double ra,double ob, double pole, double MDPA) {
		double oa, A, B, Bfactor, Asce;

	    oa = ra + Math.PI / 2.0 - MDPA;	// Oblique ancession
	    A = Math.atan(Math.tan(pole) / Math.cos(oa));
	    if (oa < 90 && oa > Math.PI / 2 * 3 ) {
	        B = ob + A;
		} else {
	        B = A + ob; // dont be fool its same as above... supposed to be -ve but + is giving correct result as cos (oa) becomes -ve;
		}
	    if (B > Math.PI / 2) {
	        Bfactor = Math.sin(B - Math.PI / 2);
	        Asce = (Math.atan(-Math.tan(oa) * Math.cos(A) / Bfactor));
		} else {
	        Bfactor = Math.cos(B);
	        Asce = (Math.atan(Math.tan(oa) * Math.cos(A) / Bfactor) + Math.PI);
	    }
	    //If ((oa - 22# / 7# / 2# >= 22 / 7) And (oa - 22# / 7# / 2# < 22 / 7 * 2)) Then: Asce = Asce + 22 / 7
	    if (oa > Math.PI * 2) oa -= Math.PI * 2;
	    if (oa < 0.0)	 oa += Math.PI * 2;
	    if ((oa < 1.57142 /* PI/2 as floating comparision const is used*/) ||
			(oa >= Math.PI / 2 * 3))  Asce += Math.PI;
	    return  Asce;
	}	
	/* Pls note this works from house 1 -- 12 not 0 -- 11 */
	private double GetRegHouse(int house,double ra, double lt, double ob, double ay) {
		double pole, MDPA, Asce;
		int idx=0;
		double fact[] = new double [4];

		fact[1] = d2r(0);
		fact[2] = d2r(30);
		fact[3] = d2r(60);
	    
	    //ra = d2r(ra);            //RAMC
	    lt = d2r(lt);            //Lattitude
	    //ob = d2r(ob);            //Oblique ecliptic
	if (house < 1 || house > 12)  new JTException ("Invalid House ");
	if (house == 4 || house == 10 ) {
	        Asce = Math.atan(Math.tan(ra) / Math.cos(ob));
	        if (Asce < 0.0)		Asce += 22.0 / 7;
	        if (Math.sin(ra) < 0)	Asce += 22.0 / 7;
	        if (house == 4 ) Asce = Asce += Math.PI;
	}
	else {
		if(house == 2 || house == 12 || house == 6 || house == 8 ) idx = 2;
	    else if (house == 3 || house == 11 || house == 5 || house == 9) idx = 3;
	    else if (house == 1 || house == 7) idx = 1;
	    else new JTException("Error: Please check DF876087SDF house value");
	    
	    
	    pole = Math.atan(Math.tan(lt) * Math.cos(fact[idx]));
	    
	        if (house == 2 || house == 3 || house == 8 || house == 9 ) MDPA = -fact[idx];
	        else	MDPA = fact[idx];
	        
	    
	    Asce = calcAsce(ra, ob, pole, MDPA);
	    
	    if (house >= 5 && house <= 9)  Asce += Math.PI;

	}
	    Asce = r2d(Asce) - ay;
	    Asce = fmod(Asce + 360, 360.0);
	    return (Asce);
	}


	/* Pls note this works from house 1 -- 12 not 0 -- 11 */
	private double GetCampanusHouse(int house,double ra, double lt, double ob, double ay) {
		double pole, MDPA, Asce;
		int idx=0;
		double fact[]=new double[4];

		fact[1] = d2r(90);
		fact[2] = d2r(60);
		fact[3] = d2r(30);
	    
	    //ra = d2r(ra);            //RAMC
	    lt = d2r(lt);            //Lattitude
	    //ob = d2r(ob);            //Oblique ecliptic
	if (house < 1 || house > 12)  new JTException ("Invalid House ");
	if (house == 4 || house == 10 ) {
	        Asce = Math.atan(Math.tan(ra) / Math.cos(ob));
	        if (Asce < 0.0)		Asce += Math.PI;
	        if (Math.sin(ra) < 0)	Asce += Math.PI;
	        if (house == 4 ) Asce = Asce += Math.PI;
	}
	else {
		if(house == 2 || house == 12 || house == 6 || house == 8 ) idx = 2;
	    else if (house == 3 || house == 11 || house == 5 || house == 9) idx = 3;
	    else if (house == 1 || house == 7) idx = 1;
	    else new JTException("Error: Please check DF876087SDF house value");
	    
	    
		pole = Math.asin(Math.sin(lt) * Math.sin(fact[idx]));
	    
		MDPA = Math.atan(1 / Math.cos(lt) / Math.tan(fact[idx]));
	        if (house == 2 || house == 3 || house == 8 || house == 9 ) MDPA = -MDPA;
	        else if (house == 1 || house == 7) MDPA = 0;
	        
	    
	    Asce = calcAsce(ra, ob, pole, MDPA);
	    
	    if (house >= 5 && house <= 9)  Asce += Math.PI;

	}
	    Asce = r2d(Asce) - ay;
	    Asce = fmod(Asce + 360, 360.0);
	    return (Asce);
	}


	/**
	 * @uml.property  name="geoGraphic"
	 */
	private boolean geoGraphic = false;
	/**
	 * Getter of the property <tt>geoGraphic</tt>
	 * @return  Returns the geoGraphic.
	 * @uml.property  name="geoGraphic"
	 */
	public boolean getGeoGraphic() {
		return geoGraphic;
	}
	/**
	 * Setter of the property <tt>geoGraphic</tt>
	 * @param geoGraphic  The geoGraphic to set.
	 * @uml.property  name="geoGraphic"
	 */
	public void setGeoGraphic(boolean geoGraphic) {
		this.geoGraphic = geoGraphic;
	}
	/**
	 * Ravi Udaya (Sun raise time) for the day of horoscope.
	 * @uml.property  name="raviUdaya"
	 */
	private float raviUdaya;
	/**
	 * Getter of the property <tt>raviUdaya</tt>
	 * @return  Returns the raviUdaya.
	 * @uml.property  name="raviUdaya"
	 */
	public Date getRaviUdaya() {
		Date dt = (Date)horoData.getDate().clone();
		dt.setHours((int)raviUdaya);
		double min = (raviUdaya - ((int)raviUdaya)) * 60;
		dt.setMinutes((int)min);
		double sec = (min - ((int)min))*60;
		dt.setSeconds((int)sec);
		return dt;
	}

	/**
	 * @uml.property  name="houseDivType"
	 */
	private int houseDivType;

	/**
	 * Getter of the property <tt>houseDivType</tt>
	 * @return  Returns the houseDivType.
	 * @uml.property  name="houseDivType"
	 */
	public int getHouseDivType() {
		return houseDivType;
	}
	/**
	 * Setter of the property <tt>houseDivType</tt>
	 * @param houseDivType  The houseDivType to set.
	 * @uml.property  name="houseDivType"
	 */
	public void setHouseDivType(int houseDivType) {
		this.houseDivType = houseDivType;
	}
	private double fmod(double val, double div) {
		int mul = (int)(val/div);
		return (val - div*mul);
	}
}