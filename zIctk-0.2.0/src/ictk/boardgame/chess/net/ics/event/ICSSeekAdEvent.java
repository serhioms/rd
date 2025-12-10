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
 * on Fri Oct 03 14:50:57 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.*;
import ictk.boardgame.chess.net.ics.fics.event.*;
import ictk.util.Log;

import java.util.regex.*;
import java.io.IOException;

/**
 * A seek ad is an expressed intrest to play a game.                  
 */
public class ICSSeekAdEvent extends ICSEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int SEEK_AD_EVENT =  ICSEvent.SEEK_AD_EVENT;

   
   public static final int COLOR_UNSPECIFIED = 0,
                           COLOR_WHITE       = 1,
                           COLOR_BLACK       = 2;
      

   //instance vars//////////////////////////////////////////////////////////
   protected int number;
   protected String player;
   protected ICSAccountType acctType;
   protected ICSRating rating;
   protected ICSVariant variant;
   protected boolean isRestrictedByFormula;
   protected boolean meetsFormula;
   protected int color;
   protected boolean rated;
   protected boolean notified;
   protected boolean manual;
   protected int time;
   protected int incr;
   protected int rangeLow;
   protected int rangeHigh;


   //constructors///////////////////////////////////////////////////////////
   public ICSSeekAdEvent () {
      super(SEEK_AD_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public int getAdNumber () {
      return number;
   }

   public String getPlayer () {
      return player;
   }

   public ICSAccountType getAccountType () {
      return acctType;
   }

   public ICSRating getRating () {
      return rating;
   }

   public ICSVariant getVariant () {
      return variant;
   }

   public boolean isRestrictedByFormula () {
      return isRestrictedByFormula;
   }

   public boolean meetsFormula () {
      return meetsFormula;
   }

   public int getColor () {
      return color;
   }

   public boolean isRated () {
      return rated;
   }

   public boolean isNotification () {
      return notified;
   }

   public boolean isManual () {
      return manual;
   }

   public int getInitialTime () {
      return time;
   }

   public int getIncrement () {
      return incr;
   }

   public int getRatingRangeLow () {
      return rangeLow;
   }

   public int getRatingRangeHigh () {
      return rangeHigh;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setAdNumber (int number) {
      this.number = number;
   }

   public void setPlayer (String player) {
      this.player = player;
   }

   public void setAccountType (ICSAccountType acctType) {
      this.acctType = acctType;
   }

   public void setRating (ICSRating rating) {
      this.rating = rating;
   }

   public void setVariant (ICSVariant variant) {
      this.variant = variant;
   }

   public void setRestrictedByFormula (boolean isRestrictedByFormula) {
      this.isRestrictedByFormula = isRestrictedByFormula;
   }

   public void meetsFormula (boolean meetsFormula) {
      this.meetsFormula = meetsFormula;
   }

   public void setColor (int color) {
      this.color = color;
   }

   public void setRated (boolean rated) {
      this.rated = rated;
   }

   public void setNotification (boolean notified) {
      this.notified = notified;
   }

   public void setManual (boolean manual) {
      this.manual = manual;
   }

   public void setInitialTime (int time) {
      this.time = time;
   }

   public void setIncrement (int incr) {
      this.incr = incr;
   }

   public void setRatingRangeLow (int rangeLow) {
      this.rangeLow = rangeLow;
   }

   public void setRatingRangeHigh (int rangeHigh) {
      this.rangeHigh = rangeHigh;
   }


   //readable//////////////////////////////////////////////////////////////
   public String getReadable () {
      
      String str = null;
         switch (getEventType()) {
	    
         case ICSEvent.SEEK_AD_EVENT:
            str = FICSSeekAdParser.getInstance().toNative(this);
	    break;

         case ICSEvent.SEEK_AD_READABLE_EVENT:
            str = FICSSeekAdReadableParser.getInstance().toNative(this);
	    break;

	 }
      return str;
         
   }
}
