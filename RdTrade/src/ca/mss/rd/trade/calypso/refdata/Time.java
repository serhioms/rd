/**
 * 
 */
package ca.mss.rd.trade.calypso.refdata;


/**
 * @author moskovsk
 *
 */
public enum Time {

	H1(Time24.H1),
	H2(Time24.H2),
	H3(Time24.H3),
	H4(Time24.H4),
	H5(Time24.H5),
	H6(Time24.H6),
	H7(Time24.H7),
	H8(Time24.H8),
	H9(Time24.H9),
	H10(Time24.H10),
	H11(Time24.H11),
	H12(Time24.H12),
	H13(Time24.H13),
	H14(Time24.H14),
	H15(Time24.H15),
	H16(Time24.H16),
	H17(Time24.H17),
	H18(Time24.H18),
	H19(Time24.H19),
	H20(Time24.H10),
	H21(Time24.H21),
	H22(Time24.H22),
	H23(Time24.H23),
	H24(Time24.H24),
	
	AM1(Time24.H1),
	AM2(Time24.H2),
	AM3(Time24.H3),
	AMS(Time24.H4),
	AM5(Time24.H5),
	AM6(Time24.H6),
	AM7(Time24.H7),
	AM8(Time24.H8),
	AM9(Time24.H9),
	AM10(Time24.H10),
	AM11(Time24.H11),
	AM12(Time24.H12),
	PM1(Time24.H13),
	PM2(Time24.H14),
	PM3(Time24.H15),
	PM4(Time24.H16),
	PM5(Time24.H17),
	PM6(Time24.H18),
	PM7(Time24.H19),
	PM8(Time24.H20),
	PM9(Time24.H21),
	PM10(Time24.H22),
	PM11(Time24.H23),
	PM12(Time24.H24);
	
	public Time24 time;

	/**
	 * @param time
	 */
	private Time(Time24 time) {
		this.time = time;
	}

	final static public int minutes(int mm){
		return Math.max(Math.min(mm, 60), 0);
	}
	
	final static public int hour(int hh){
		return Math.max(hh, 0);
	}
	
	public enum Time24 {
		H1,
		H2,
		H3,
		H4,
		H5,
		H6,
		H7,
		H8,
		H9,
		H10,
		H11,
		H12,
		H13,
		H14,
		H15,
		H16,
		H17,
		H18,
		H19,
		H20,
		H21,
		H22,
		H23,
		H24;
	}
}


