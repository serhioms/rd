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
public class FICSPlayerNotificationParser extends ICSEventParser {

   //static/////////////////////////////////////////////////////////////////
   public static FICSPlayerNotificationParser singleton;
   public static final Pattern masterPattern;

   static {
      masterPattern  = Pattern.compile(
         "^:?(" //begin
         + "Notification:\\s"
         + "([\\w]+)"
         + "((?:\\([A-Z*]+\\))*)"
         + "\\shas\\s"
         + "(arrived|departed)"
         + "(\\sand\\sisn't\\son\\syour\\snotify\\slist)?"
         + "\\."

         + ")"  //end
         , Pattern.MULTILINE);

      singleton = new FICSPlayerNotificationParser();
   }

   //instance///////////////////////////////////////////////////////////////
   protected FICSPlayerNotificationParser () {
      super(masterPattern);
   }

   /* getInstance ***********************************************************/
   public static ICSEventParser getInstance () {
       return singleton;
   }

   /* createICSEvent ********************************************************/
   public ICSEvent createICSEvent (Matcher match) {
      ICSEvent evt = new ICSPlayerConnectionEvent();
         assignMatches(match, evt);

	 return evt;
   }

   /* assignMatches *********************************************************/
   public void assignMatches (Matcher m, ICSEvent event) {
      ICSPlayerConnectionEvent evt = (ICSPlayerConnectionEvent) event;

      if (Log.debug && debug)
         Log.debug(DEBUG, "assigning matches", m);

      
      evt.setFake(detectFake(m.group(0)));
      
	    
      evt.setPlayer(m.group(2));
      
	    
      evt.setAccountType(parseICSAccountType(m, 3));
      
      evt.setConnected("arrived".equals(m.group(4)));
      evt.setNotification(true);
      evt.setOnNotifyList(m.group(5) != null);
      evt.setEventType(ICSEvent.PLAYER_NOTIFICATION_EVENT);
	 
   }

   /* toNative ***************************************************************/
   public String toNative (ICSEvent event) {

      if (event.getEventType() == ICSEvent.UNKNOWN_EVENT)
         return event.getMessage();

      ICSPlayerConnectionEvent evt = (ICSPlayerConnectionEvent) event;
      StringBuffer sb = new StringBuffer(40);
      
      if (evt.isFake()) sb.append(":");
      
      sb.append("Notification: ")
        .append(evt.getPlayer())
        .append(evt.getAccountType())
        .append(" has ");

      if (evt.isConnected())
         sb.append("arrived");
      else
         sb.append("departed");

      if (evt.isOnNotifyList())
         sb.append(" and isn't on your notify list");

      sb.append(".");
	    

      return sb.toString();
   }
}
