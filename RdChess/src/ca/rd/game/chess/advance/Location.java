package ca.rd.game.chess.advance;

public enum Location {
	a8(0),b8(1),c8(0),d8(1),e8(0),f8(1),g8(0),h8(1),
	a7(1),b7(0),c7(1),d7(0),e7(1),f7(0),g7(1),h7(0),
	a6(0),b6(1),c6(0),d6(1),e6(0),f6(1),g6(0),h6(1),
	a5(1),b5(0),c5(1),d5(0),e5(1),f5(0),g5(1),h5(0),
	a4(0),b4(1),c4(0),d4(1),e4(0),f4(1),g4(0),h4(1),
	a3(1),b3(0),c3(1),d3(0),e3(1),f3(0),g3(1),h3(0),
	a2(0),b2(1),c2(0),d2(1),e2(0),f2(1),g2(0),h2(1),
	a1(1),b1(0),c1(1),d1(0),e1(1),f1(0),g1(1),h1(0);

	final static String[] HORIZONTAL = new String[]{"A","B","C","D","E","F","G","H"};
	final static String[] VERTICAL = new String[]{"8","7","6","5","4","3","2","1"};

	final static int MAX_H = HORIZONTAL.length;
	final static int MAX_V = VERTICAL.length;

	final static int PAWN_WHITE_VERTICAL = 6;
	final static int PAWN_BLACK_VERTICAL = 1;

	final public boolean isBlack;
	final public int h,v;
	
	private Location(int isBlack) {
		this.isBlack = (isBlack == 1);
		this.h = ordinal()%8;
		this.v = ordinal()/8;
	}
	
	final static public int getIndex(int h, int v){
		return h+v*8;
	}

}