package de.Herbystar.CTNSC.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.ReplaceString;

public class PlayerInteractEvents implements Listener {
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		//AFK
		if(Main.instance.AFK.contains(p.getUniqueId())) {
			if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKMode") == true) {
				if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Broadcast") == true) {
					Bukkit.broadcastMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Broadcast.Content_Disabled"), p));
				}
				if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKModeMessage.Private") == true) {
					p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Private.Content_Disabled"), p));
				}
				Main.instance.AFK.remove(p.getUniqueId());
			}
		}
	}

}
