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
 * on Fri Oct 03 14:50:56 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.event.*;
import ictk.boardgame.chess.net.ics.*;
import ictk.util.Log;

import java.util.*;
import java.util.regex.*;
import java.io.IOException;

/**

 */
public class FICSGameNotificationParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSGameNotificationParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^:?(" //begin
         + "Game\\snotification:\\s"
         + "([\\w]+)"
         + "\\s"
         + "\\(\\s*([0-9+-]+[EP]?)\\)"
         + "\\svs\\.\\s"
         + "([\\w]+)"
         + "\\s"
         + "\\(\\s*([0-9+-]+[EP]?)\\)"
         + "\\s"
         + "(\\w+)"
         + "\\s"
         + "(\\S+)"
         + "\\s"
         + "(\\d+)"
         + "\\s"
         + "(\\d+)"
         + ":\\sGame\\s"
         + "(\\d+)"

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSGameNotificationParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSGameNotificationParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSGameNotificationEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSGameNotificationEvent evt = (ICSGameNotificationEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
      evt.setFake(detectFake(m.group(0)));
      
	    
      evt.setWhitePlayer(m.group(2));
      
	    
      evt.setWhiteRating(parseICSRating(m, 3));
      
	    
      evt.setBlackPlayer(m.group(4));
      
	    
      evt.setBlackRating(parseICSRating(m, 5));
      
	    
      evt.setVariant(new ICSVariant(m.group(7)));
      
	    
      try {
         evt.setInitialTime(Integer.parseInt(m.group(8)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse time for: "
            + m.group(8) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setIncrement(Integer.parseInt(m.group(9)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse incr for: "
            + m.group(9) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setBoardNumber(Integer.parseInt(m.group(10)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse boardNumber for: "
            + m.group(10) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
      evt.setRated("rated".equals(m.group(6)));
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSGameNotificationEvent evt = (ICSGameNotificationEvent) event;
      StringBuffer sb = new StringBuffer(50);
      
      if (evt.isFake()) sb.append(":");
      
      sb.append("Game notification: ")
        .append(evt.getWhitePlayer())
        .append(" (")
        .append(evt.getWhiteRating())
        .append(") vs. ")
        .append(evt.getBlackPlayer())
        .append(" (")
        .append(evt.getBlackRating())
        .append(") ");

      if (!evt.isRated())
         sb.append("un");

      sb.append("rated ")
        .append(evt.getVariant())
        .append(" ")
        .append(evt.getInitialTime())
        .append(" ")
        .append(evt.getIncrement())
        .append(": Game ")
        .append(evt.getBoardNumber());
	    

      return sb.toString();
   }
}
