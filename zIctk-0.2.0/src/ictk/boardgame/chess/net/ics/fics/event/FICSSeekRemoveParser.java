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
public class FICSSeekRemoveParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSSeekRemoveParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^(" //begin
         + "<sr>"
         + "((?:\\s\\d+)+)"

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSSeekRemoveParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSSeekRemoveParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSSeekRemoveEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSSeekRemoveEvent evt = (ICSSeekRemoveEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
      StringTokenizer st = new StringTokenizer(m.group(2));

      int[] ads = new int[st.countTokens()];
      int i = 0;
      try {
         while (st.hasMoreTokens()) {
	    ads[i] = Integer.parseInt(st.nextToken());
	    i++;
	 }
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse ads[" + i + "] "
            + "of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
         if (Log.debug)
            Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      evt.setAds(ads);
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSSeekRemoveEvent evt = (ICSSeekRemoveEvent) event;
      StringBuffer sb = new StringBuffer(5);
      
      int[] ads = evt.getAds();
      sb.append("<sr>");
      for (int i=0; i < ads.length; i++)
         sb.append(" ")
	   .append(ads[i]);
	    

      return sb.toString();
   }
}
