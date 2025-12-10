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
 * This coorisponds to Player connection notification, either by      
 * "pin=1" or someone on your Notify list.                            
 */
public class ICSPlayerConnectionEvent extends ICSEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int PLAYER_CONNECTION_EVENT =  ICSEvent.PLAYER_CONNECTION_EVENT;

   

   //instance vars//////////////////////////////////////////////////////////
   protected String player;
   protected ICSAccountType acctType;
   protected boolean connected;
   protected boolean notified;
   protected boolean onNotifyList;


   //constructors///////////////////////////////////////////////////////////
   public ICSPlayerConnectionEvent () {
      super(PLAYER_CONNECTION_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public String getPlayer () {
      return player;
   }

   public ICSAccountType getAccountType () {
      return acctType;
   }

   public boolean isConnected () {
      return connected;
   }

   public boolean isNotification () {
      return notified;
   }

   public boolean isOnNotifyList () {
      return onNotifyList;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setPlayer (String player) {
      this.player = player;
   }

   public void setAccountType (ICSAccountType acctType) {
      this.acctType = acctType;
   }

   public void setConnected (boolean connected) {
      this.connected = connected;
   }

   public void setNotification (boolean notified) {
      this.notified = notified;
   }

   public void setOnNotifyList (boolean onNotifyList) {
      this.onNotifyList = onNotifyList;
   }


   //readable//////////////////////////////////////////////////////////////
   public String getReadable () {
      
      String str = null;
         switch (getEventType()) {
	    
         case ICSEvent.PLAYER_CONNECTION_EVENT:
            str = FICSPlayerConnectionParser.getInstance().toNative(this);
	    break;

         case ICSEvent.PLAYER_NOTIFICATION_EVENT:
            str = FICSPlayerNotificationParser.getInstance().toNative(this);
	    break;

	 }
      return str;
         
   }
}
