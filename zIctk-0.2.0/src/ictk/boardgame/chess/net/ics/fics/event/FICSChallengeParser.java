/*
 *  ICTK - Internet Chess ToolKit
 *  More information is available at http://ictk.sourceforge.net
 *  Copyright (C) 2002 J. Varsoke <jvarsoke@ghostmanonfirst.com>
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

package ictk.boardgame.chess.net.ics.fics.event;

/*--------------------------------------------------------------------------*
 * This file was auto-generated 
 * by $Id: parser.xsl,v 1.11 2003/08/25 01:29:00 jvarsoke Exp $
 * on Fri Oct 03 14:50:55 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.event.*;
import ictk.boardgame.chess.net.ics.*;
import ictk.util.Log;

import java.util.*;
import java.util.regex.*;
import java.io.IOException;

/**

 */
public class FICSChallengeParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSChallengeParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^:?(" //begin
         + "Challenge:\\s"
         + "([\\w]+)"
         + "\\s"
         + "\\(\\s*([0-9+-]+[EP]?)\\)"
         + "\\s(?:\\[(white|black)\\])?"
         + "\\s?"
         + "([\\w]+)"
         + "\\s"
         + "\\(\\s*([0-9+-]+[EP]?)\\)"
         + "\\s"
         + "(rated|unrated)"
         + "\\s"
         + "(\\S+)"
         + "\\s"
         + "(\\d+)"
         + "\\s"
         + "(\\d+)"
         + "\\.\\n"
         + "(?:--\\*\\*\\s"
         + "([\\w]+)"
         + "\\sis\\san?\\s(computer|abuser)\\s\\*\\*--\\n)?"
         + "You\\scan\\s\"accept\"\\sor\\s\"decline\",\\sor\\spropose\\sdifferent\\sparameters\\."

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSChallengeParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSChallengeParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSChallengeEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSChallengeEvent evt = (ICSChallengeEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
      evt.setFake(detectFake(m.group(0)));
      
	    
      evt.setChallenger(m.group(2));
      
	    
      evt.setChallengerRating(parseICSRating(m, 3));
      
	    
      evt.setChallenged(m.group(5));
      
	    
      evt.setChallengedRating(parseICSRating(m, 6));
      
	    
      evt.setVariant(new ICSVariant(m.group(8)));
      
	    
      try {
         evt.setInitialTime(Integer.parseInt(m.group(9)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse time for: "
            + m.group(9) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setIncrementTime(Integer.parseInt(m.group(10)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse incr for: "
            + m.group(10) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
      if (m.group(4) != null) {
         evt.setColorSpecified(true);
	 if (m.group(4).equals("white"))
	    evt.setWhite(true);
      }
	 
      evt.setRated("rated".equals(m.group(7)));

      //probably more than just "computer" here, but need to see it.
      if (m.group(12) != null) {
         if ("computer".equals(m.group(12)))
            evt.setComputer(true);
	 else if ("abuser".equals(m.group(12)))
	    evt.setAbuser(true);
	 else 
	    Log.error(Log.PROG_WARNING,
	       "unknown Challenge event alert: " + m.group(12));
      }
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSChallengeEvent evt = (ICSChallengeEvent) event;
      StringBuffer sb = new StringBuffer(154);
      
      if (evt.isFake()) sb.append(":");
      
      sb.append("Challenge: ")
        .append(evt.getChallenger())
	.append(" (")
	.append(evt.getChallengerRating())
	.append(") ");

      if (evt.isColorSpecified()) {
         if (evt.isWhite())
            sb.append("[white] ");
	 else
            sb.append("[black] ");
      }

      sb.append(evt.getChallenged())
	.append(" (")
	.append(evt.getChallengedRating())
	.append(") ");


      if (evt.isRated())
         sb.append("rated ");
      else
         sb.append("unrated ");

      sb.append(evt.getVariant())
        .append(" ")
	.append(evt.getInitialTime())
	.append(" ")
	.append(evt.getIncrementTime())
	.append(".\n");

      if (evt.isComputer()) {
         sb.append("--** ")
	   .append(evt.getChallenger())
	   .append(" is a computer **--\n");
      }

      if (evt.isAbuser()) {
         sb.append("--** ")
	   .append(evt.getChallenger())
	   .append(" is an abuser **--\n");
      }
      
      sb.append(
         "You can \"accept\" or \"decline\", or propose different parameters.");
	    

      return sb.toString();
   }
}
