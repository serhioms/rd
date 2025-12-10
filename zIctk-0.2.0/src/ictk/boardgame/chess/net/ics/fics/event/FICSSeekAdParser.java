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
public class FICSSeekAdParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSSeekAdParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^(" //begin
         + "<s(n)?>"
         + "\\s"
         + "(\\d+)"
         + "\\s"
         + "w=([\\w]+)"
         + "\\s"
         + "ti=(\\d+)"
         + "\\s"
         + "rt=(\\d+[\\sPE])"
         + "\\s"
         + "t=(\\d+)"
         + "\\s"
         + "i=(\\d+)"
         + "\\s"
         + "r=([ur])"
         + "\\s"
         + "tp=([\\S]+)"
         + "\\s"
         + "c=([BW\\?])"
         + "\\s"
         + "rr=(\\d+)"
         + "-(\\d+)"
         + "\\s"
         + "a=([tf])"
         + "\\s"
         + "f=([tf])"

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSSeekAdParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSSeekAdParser () {
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

      
	    
      try {
         evt.setAdNumber(Integer.parseInt(m.group(3)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse number for: "
            + m.group(3) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      evt.setPlayer(m.group(4));
      
	    
      evt.setRating(parseICSRating(m, 6));
      
	    
      try {
         evt.setInitialTime(Integer.parseInt(m.group(7)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse time for: "
            + m.group(7) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setIncrement(Integer.parseInt(m.group(8)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse incr for: "
            + m.group(8) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      evt.setVariant(new ICSVariant(m.group(10)));
      
	    
      try {
         evt.setRatingRangeLow(Integer.parseInt(m.group(12)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse rangeLow for: "
            + m.group(12) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      try {
         evt.setRatingRangeHigh(Integer.parseInt(m.group(13)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse rangeHigh for: "
            + m.group(13) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
      //meets our formula?
      if (m.group(2) == null)
         evt.meetsFormula(true);
      else if (m.group(2).charAt(0) == 'n')
            evt.meetsFormula(false);
         else
            Log.error(Log.PROG_WARNING,
               "Received unknown character in <s[n]?> area: "
               + m.group(2) + " of " + m.group(0));

      //rated?
      evt.setRated(m.group(9).charAt(0) == 'r');

      //colors
      switch (m.group(11).charAt(0)) {
         case '?':
            evt.setColor(ICSSeekAdEvent.COLOR_UNSPECIFIED); break;
         case 'W':
            evt.setColor(ICSSeekAdEvent.COLOR_WHITE); break;
         case 'B':
            evt.setColor(ICSSeekAdEvent.COLOR_BLACK); break;
         default:
            Log.error(Log.PROG_WARNING,
               "Received unknown character in c=[WB\\?] area: "
               + m.group(11) + " of " + m.group(0));
      }

      //automatic/manual
      evt.setManual(m.group(14).charAt(0) == 'f');

      //restricted by formula
      evt.setRestrictedByFormula(m.group(15).charAt(0) == 't');

     //numbers
      int acct = 0;
      try {
         acct = Integer.parseInt(m.group(5),16);
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parser number "
            + m.group(5) + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
         return;
      }

      ICSAccountType accttype = new ICSAccountType();
      //FIXME: needs to parse the mask in "acct"
      evt.setAccountType(accttype);
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSSeekAdEvent evt = (ICSSeekAdEvent) event;
      StringBuffer sb = new StringBuffer(80);
      
      sb.append("<s");
      if (!evt.meetsFormula())
         sb.append("n");
      sb.append("> ")
        .append(evt.getAdNumber())
        .append(" w=")
        .append(evt.getPlayer())
        .append(" ti=")
        //FIXME: account types need to be converted into title mask
        .append("00")
        .append(" rt=")
        .append(evt.getRating())
        .append(" t=")
        .append(evt.getInitialTime())
        .append(" i=")
        .append(evt.getIncrement())
        .append(" r=")
        .append(((evt.isRated()) ? 'r' : 'u'))
        .append(" tp=")
        .append(evt.getVariant())
        .append(" c=");
      switch(evt.getColor()) {
         case ICSSeekAdEvent.COLOR_UNSPECIFIED:
            sb.append("?"); break;
         case ICSSeekAdEvent.COLOR_WHITE:
            sb.append("W"); break;
         case ICSSeekAdEvent.COLOR_BLACK:
            sb.append("B"); break;
      }
      sb.append(" rr=")
        .append(evt.getRatingRangeLow())
        .append("-")
        .append(evt.getRatingRangeHigh())
        .append(" a=")
        .append(((evt.isManual()) ? 'f' : 't'))
        .append(" f=")
        .append(((evt.isRestrictedByFormula()) ? 't' : 'f'));
	    

      return sb.toString();
   }
}
