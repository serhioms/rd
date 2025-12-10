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
 * on Fri Oct 03 14:50:55 EDT 2003
 *--------------------------------------------------------------------------*/

import ictk.boardgame.chess.net.ics.*;
import ictk.boardgame.chess.net.ics.fics.event.*;
import ictk.util.Log;

import java.util.regex.*;
import java.io.IOException;

/**
 * This coorisponds to Channel tells, Shouts, Emote Shouts, T-Shouts, 
 * C-Shouts, S-Shouts, T-Channel tells, etc.                          
 */
public class ICSChannelEvent extends ICSMessageEvent {


   //static initializer/////////////////////////////////////////////////////
   protected static final int CHANNEL_EVENT =  ICSEvent.CHANNEL_EVENT;

   
      /** if the channel event is a shout then the channel number
       ** indicates what type of shout */
   public static final int SHOUT_CHANNEL  = 1,
      /** emotes are typically: -->Handle scratches head */
			   EMOTE_CHANNEL  = 2,
			   CSHOUT_CHANNEL = 3,
			   SSHOUT_CHANNEL = 4,
			   TSHOUT_CHANNEL = 5;
      

   //instance vars//////////////////////////////////////////////////////////
   protected String player;
   protected ICSAccountType acctType;
   protected int channel;


   //constructors///////////////////////////////////////////////////////////
   public ICSChannelEvent () {
      super(CHANNEL_EVENT);
   }

   //assessors/////////////////////////////////////////////////////////////
   public String getPlayer () {
      return player;
   }

   public ICSAccountType getAccountType () {
      return acctType;
   }

   public int getChannel () {
      return channel;
   }

   //mutators//////////////////////////////////////////////////////////////
   public void setPlayer (String player) {
      this.player = player;
   }

   public void setAccountType (ICSAccountType acctType) {
      this.acctType = acctType;
   }

   public void setChannel (int channel) {
      this.channel = channel;
   }


   //readable//////////////////////////////////////////////////////////////
   public String getReadable () {
      
      String str = null;
         switch (getEventType()) {
	    
         case ICSEvent.CHANNEL_EVENT:
            str = FICSChannelParser.getInstance().toNative(this);
	    break;

         case ICSEvent.SHOUT_EVENT:
            str = FICSShoutParser.getInstance().toNative(this);
	    break;

	 }
      return str;
         
   }
}
