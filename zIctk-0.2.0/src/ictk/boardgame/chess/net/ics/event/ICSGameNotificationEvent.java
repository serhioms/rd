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
 * on Fri Oct 03 14:50:56 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.*;
import ictk.boardgame.chess.net.ics.fics.event.*;
import ictk.util.Log;

import java.util.regex.*;
import java.io.IOException;

/**
 * These are game notifications, which occur when you have a player   
 * on your "gnot" list                                                
 */
public class ICSGameNotificationEvent extends ICSEvent
                            implements ICSBoardEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int GAME_NOTIFICATION_EVENT =  ICSEvent.GAME_NOTIFICATION_EVENT;

   

   //instance vars//////////////////////////////////////////////////////////
   protected int boardNumber;
   protected String white;
   protected ICSRating whiteRating;
   protected String black;
   protected ICSRating blackRating;
   protected boolean rated;
   protected ICSVariant variant;
   protected int time;
   protected int incr;


   //constructors///////////////////////////////////////////////////////////
   public ICSGameNotificationEvent () {
      super(GAME_NOTIFICATION_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public String getWhitePlayer () {
      return white;
   }

   public ICSRating getWhiteRating () {
      return whiteRating;
   }

   public String getBlackPlayer () {
      return black;
   }

   public ICSRating getBlackRating () {
      return blackRating;
   }

   public boolean isRated () {
      return rated;
   }

   public ICSVariant getVariant () {
      return variant;
   }

   public int getInitialTime () {
      return time;
   }

   public int getIncrement () {
      return incr;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setWhitePlayer (String white) {
      this.white = white;
   }

   public void setWhiteRating (ICSRating whiteRating) {
      this.whiteRating = whiteRating;
   }

   public void setBlackPlayer (String black) {
      this.black = black;
   }

   public void setBlackRating (ICSRating blackRating) {
      this.blackRating = blackRating;
   }

   public void setRated (boolean rated) {
      this.rated = rated;
   }

   public void setVariant (ICSVariant variant) {
      this.variant = variant;
   }

   public void setInitialTime (int time) {
      this.time = time;
   }

   public void setIncrement (int incr) {
      this.incr = incr;
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
      return FICSGameNotificationParser.getInstance().toNative(this);
   }
}
