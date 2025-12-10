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
public class FICSGameResultParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSGameResultParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^:?(" //begin
         + "\\{Game\\s"
         + "(\\d+)"
         + "\\s\\("
         + "([\\w]+)"
         + "\\svs\\.\\s"
         + "([\\w]+)"
         + "\\)\\s"
         + "([^}]+)"
         + "\\}\\s"
         + "(\\S+)"

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSGameResultParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSGameResultParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSGameResultEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSGameResultEvent evt = (ICSGameResultEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
      evt.setFake(detectFake(m.group(0)));
      
	    
      try {
         evt.setBoardNumber(Integer.parseInt(m.group(2)));
      }
      catch (NumberFormatException e) {
         Log.error(Log.PROG_WARNING,
            "Can't parse boardNumber for: "
            + m.group(2) 
            + " of " + m.group(0));
         evt.setEventType(ICSEvent.UNKNOWN_EVENT);
         evt.setMessage(m.group(0));
	 if (Log.debug)
	    Log.debug(ICSEventParser.DEBUG, "regex", m);
         return;
      }
      
	    
      evt.setWhitePlayer(m.group(3));
      
	    
      evt.setBlackPlayer(m.group(4));
      
	    
      evt.setResult(new ICSResult(m.group(6)));
      
      evt.getResult().setDescription(m.group(5));
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSGameResultEvent evt = (ICSGameResultEvent) event;
      StringBuffer sb = new StringBuffer(40);
      
      if (evt.isFake()) sb.append(":");
      
      sb.append("{Game ")
        .append(evt.getBoardNumber())
        .append(" (")
        .append(evt.getWhitePlayer())
        .append(" vs. ")
        .append(evt.getBlackPlayer())
        .append(") ")
        .append(evt.getResult().getDescription())
        .append("} ")
        .append(evt.getResult());
	    

      return sb.toString();
   }
}
