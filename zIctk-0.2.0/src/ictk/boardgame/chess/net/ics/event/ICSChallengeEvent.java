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
 * on Fri Oct 03 14:50:54 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.*;
import ictk.boardgame.chess.net.ics.fics.event.*;
import ictk.util.Log;

import java.util.regex.*;
import java.io.IOException;

/**
 * The is notification that someone has challenged you to a game.     
 * Usually this event is the result of someome issuing the "match"    
 * command against you.                                               
 */
public class ICSChallengeEvent extends ICSEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int CHALLENGE_EVENT =  ICSEvent.CHALLENGE_EVENT;

   

   //instance vars//////////////////////////////////////////////////////////
   protected String challenger;
   protected ICSRating challengerRating;
   protected boolean isColorSpecified;
   protected boolean isWhite;
   protected String challenged;
   protected ICSRating challengedRating;
   protected boolean rated;
   protected ICSVariant variant;
   protected int time;
   protected int incr;
   protected boolean computer;
   protected boolean abuser;


   //constructors///////////////////////////////////////////////////////////
   public ICSChallengeEvent () {
      super(CHALLENGE_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public String getChallenger () {
      return challenger;
   }

   public ICSRating getChallengerRating () {
      return challengerRating;
   }

   public boolean isColorSpecified () {
      return isColorSpecified;
   }

   public boolean isWhite () {
      return isWhite;
   }

   public String getChallenged () {
      return challenged;
   }

   public ICSRating getChallengedRating () {
      return challengedRating;
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

   public int getIncrementTime () {
      return incr;
   }

   public boolean isComputer () {
      return computer;
   }

   public boolean isAbuser () {
      return abuser;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setChallenger (String challenger) {
      this.challenger = challenger;
   }

   public void setChallengerRating (ICSRating challengerRating) {
      this.challengerRating = challengerRating;
   }

   public void setColorSpecified (boolean isColorSpecified) {
      this.isColorSpecified = isColorSpecified;
   }

   public void setWhite (boolean isWhite) {
      this.isWhite = isWhite;
   }

   public void setChallenged (String challenged) {
      this.challenged = challenged;
   }

   public void setChallengedRating (ICSRating challengedRating) {
      this.challengedRating = challengedRating;
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

   public void setIncrementTime (int incr) {
      this.incr = incr;
   }

   public void setComputer (boolean computer) {
      this.computer = computer;
   }

   public void setAbuser (boolean abuser) {
      this.abuser = abuser;
   }


   //readable//////////////////////////////////////////////////////////////
   public String getReadable () {
      return FICSChallengeParser.getInstance().toNative(this);
   }
}
