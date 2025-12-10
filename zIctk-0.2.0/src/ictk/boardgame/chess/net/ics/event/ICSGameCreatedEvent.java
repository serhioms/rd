/*
 *  ICTK - Internet Chess ToolKit
 *  More information is available at http://ictk.sourceforge.net
 *  Copyright (C) 2003 J. Varsoke <jvarsoke@ghostmanonfirst.com>
 *  All rights reserved.
 *
 *  This file is part of ICTK.
 *
 *  ICTK is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2 of the License, or
 *  (at your option) any later version.
 *
 *  ICTK is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with ICTK; if not, write to the Free Software
 *  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package ictk.boardgame.chess.net.ics.event;

/*--------------------------------------------------------------------------*
 * This file was auto-generated 
 * by $Id: event.xsl,v 1.9 2003/08/24 07:46:52 jvarsoke Exp $
 * on Fri Oct 03 14:50:55 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.*;
import ictk.boardgame.chess.net.ics.fics.event.*;
import ictk.util.Log;

import java.util.regex.*;
import java.io.IOException;

/**
 * These are game result notifications. Typically they are seen as    
 * part of setting gin 1 on the server.                               
 */
public class ICSGameCreatedEvent extends ICSEvent
                            implements ICSBoardEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int GAME_CREATED_EVENT =  ICSEvent.GAME_CREATED_EVENT;

   

   //instance vars//////////////////////////////////////////////////////////
   protected int boardNumber;
   protected String white;
   protected String black;
   protected boolean continued;
   protected boolean rated;
   protected ICSVariant variant;


   //constructors///////////////////////////////////////////////////////////
   public ICSGameCreatedEvent () {
      super(GAME_CREATED_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public String getWhitePlayer () {
      return white;
   }

   public String getBlackPlayer () {
      return black;
   }

   public boolean isContinued () {
      return continued;
   }

   public boolean isRated () {
      return rated;
   }

   public ICSVariant getVariant () {
      return variant;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setWhitePlayer (String white) {
      this.white = white;
   }

   public void setBlackPlayer (String black) {
      this.black = black;
   }

   public void setContinued (boolean continued) {
      this.continued = continued;
   }

   public void setRated (boolean rated) {
      this.rated = rated;
   }

   public void setVariant (ICSVariant variant) {
      this.variant = variant;
   }

   //ICSBoardEvent////////////////////////////////////////////////////
   public int getBoardNumber () { 
      return boardNumber; 
   }
	 
   public void setBoardNumber (int board) { 
      this.boardNumber = board; 
   }
	 
   //readable//////////////////////////////////////////////////////////////
   public String getReadable () {
      return FICSGameCreatedParser.getInstance().toNative(this);
   }
}
