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
 * on Fri Oct 03 14:50:57 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.event.*;
import ictk.boardgame.chess.net.ics.*;
import ictk.util.Log;

import java.util.*;
import java.util.regex.*;
import java.io.IOException;

/**

 */
public class FICSSeekAdReadableParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSSeekAdReadableParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^(" //begin
         + "([\\w]+)"
         + "((?:\\([A-Z*]+\\))*)"
         + "\\s"
         + "\\(\\s*([0-9+-]+[EP]?)\\)"
         + "\\sseeking\\s"
         + "(\\d+)"
         + "\\s"
         + "(\\d+)"
         + "\\s"
         + "(rated|unrated)"
         + "\\s"
         + "(\\S+)"
         + "(?:\\s\\[(\\w+)\\])?"
         + "(?:\\s(m))?"
         + "(?:\\s(f))?"
         + "\\s\\(\"play\\s"
         + "(\\d+)"
         + "\"\\sto\\srespond\\)"

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSSeekAdReadableParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSSeekAdReadableParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSSeekAdEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSSeekAdEvent evt = (ICSSeekAdEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
	    
      evt.setPlayer(m.group(2));
      
	    
      evt.setAccountType(parseICSAccountType(m, 3));
      
	    
      evt.setRating(parseICSRating(m, 4));
      
	    
      try {
         evt.setInitialTime(Integer.parseInt(m.group(5)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse time for: "
            + m.group(5) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setIncrement(Integer.parseInt(m.group(6)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse incr for: "
            + m.group(6) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      evt.setVariant(new ICSVariant(m.group(8)));
      
	    
      try {
         evt.setAdNumber(Integer.parseInt(m.group(12)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse number for: "
            + m.group(12) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      

      evt.setEventType(ICSEvent.SEEK_AD_READABLE_EVENT);

      //rated?
      evt.setRated(m.group(7).equals("rated"));

      //colors
      if (m.group(9) == null)
         evt.setColor(ICSSeekAdEvent.COLOR_UNSPECIFIED);
      else if (m.group(9).equals("white"))
            evt.setColor(ICSSeekAdEvent.COLOR_WHITE);
      else
            evt.setColor(ICSSeekAdEvent.COLOR_BLACK);

      //automatic/manual
      evt.setManual(m.group(10) != null);

      //restricted by formula
      evt.setRestrictedByFormula(m.group(11) != null);
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSSeekAdEvent evt = (ICSSeekAdEvent) event;
      StringBuffer sb = new StringBuffer(80);
      
      sb.append(evt.getPlayer())
        .append(evt.getAccountType())
	.append(" (");

      int rating = evt.getRating().get();

      if (!(evt.getRating().isNotSet()
           || evt.getRating().isNotApplicable())) {
	 if (rating < 1000)
	   sb.append(" ");
	 if (rating < 100)
	   sb.append(" ");
	 if (rating < 10)
	   sb.append(" ");
      }

      sb.append(evt.getRating())
	.append(") seeking ")
	.append(evt.getInitialTime())
	.append(" ")
	.append(evt.getIncrement());

      if (evt.isRated())
         sb.append(" rated ");
      else
         sb.append(" unrated ");

      sb.append(evt.getVariant());

      if (evt.getColor() == ICSSeekAdEvent.COLOR_WHITE)
         sb.append(" [white]");
      else if (evt.getColor() == ICSSeekAdEvent.COLOR_BLACK)
         sb.append(" [black]");

      if (evt.isManual())
         sb.append(" m");

      if (evt.isRestrictedByFormula())
         sb.append(" f");

      sb.append(" (\"play ")
        .append(evt.getAdNumber())
	.append("\" to respond)");
	    

      return sb.toString();
   }
}
