package de.Herbystar.CTNSC.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.ReplaceString;

public class PlayerMoveEvents implements Listener {	
	//private Modules m = new Modules();
	
	@EventHandler
	public void onPlayerMoveEvent(PlayerMoveEvent e) {
		final Player p = e.getPlayer();
		
		/*
		//Scoreboard
		if(p.getScoreboard() == null) { 
			if(m.ScoreboardModule() == true) {
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {

					@Override
					public void run() {
						if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == true) {
							new Scoreboards(p);
						}
						if(m.NameTagModule() == true) {
							CustomTags ct = new CustomTags();
							ct.setCustomTags(p);
						}
					}			
				}
				, 5L);
			}
		}
		*/
		
		//AFK
		if(Main.instance.afk != null) {
			Bukkit.getScheduler().cancelTask(Main.instance.afk);
		}
		if(Main.instance.AFK.contains(p.getUniqueId())) {
			if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKMode") == true) {
				if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Broadcast") == true) {
					for(Player all : Bukkit.getOnlinePlayers()) {
						all.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Broadcast.Content_Disabled"), p));
					}				
				}
				if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Private") == true) {
					p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Private.Content_Disabled"), p));
				}
				Main.instance.AFK.remove(p.getUniqueId());
			}
		}// else {
//			if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKMode") == true) {
//				Main.instance.afk = Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
//
//					@Override
//					public void run() {
//						if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Broadcast") == true) {
//							Bukkit.broadcastMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Broadcast.Content_Enabled"), p));
//						}
//						if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Private") == true) {
//							p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Private.Content_Enabled"), p));
//						}
//						Main.instance.AFK.add(p.getUniqueId());					
//					}				
//				}
//				, Main.instance.getConfig().getInt("CTSNC.AFK_System.AutoAFKModeSchedule(InTicks)"));
//			}
//		}
	}

}
