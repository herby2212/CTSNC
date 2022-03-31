package de.Herbystar.CTNSC.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.Modules;
import de.Herbystar.CTSNC.ReplaceString;
import de.Herbystar.CTSNC_Modules.Christmas.Christmas;

public class PlayerQuitEvents implements Listener {
	
	private Modules m = new Modules();
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if(m.ChristmasModule() == true) {
			Christmas.remove(p);
		}
		if(m.TablistModule() == true) {
			if(Main.Tablists.contains(p)) {
				Main.Tablists.remove(p);
			}
		}
		if(m.ScoreboardModule() == true) {
			if(de.Herbystar.CTSNC_Modules.Scoreboard.Main.boards.containsKey(p)) {
				(de.Herbystar.CTSNC_Modules.Scoreboard.Main.boards.get(p)).remove();
			}
		}
		if(Main.instance.getConfig().getBoolean("CTSNC.ONQUIT.QuitMessage.Enabled") == true) {
			e.setQuitMessage(ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.ONQUIT.QuitMessage.Content"), p));
		}
		if(Main.instance.AFK.contains(p.getUniqueId())) {
			if(Main.instance.getConfig().getBoolean("CTSNC.AFK_System.AutoAFKMode") == true) {
				Main.instance.AFK.remove(p.getUniqueId());
			}
		} else {
			if(Main.instance.afk != null) {
				Bukkit.getScheduler().cancelTask(Main.instance.afk);
			}
		}
	}
}
