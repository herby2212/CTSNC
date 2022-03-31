package de.Herbystar.CTNSC.Events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.scoreboard.DisplaySlot;

import de.Herbystar.CTSNC.Modules;
import de.Herbystar.CTSNC.Files.Files;
import de.Herbystar.CTSNC_Modules.NameTag.CustomTags;
import de.Herbystar.CTSNC_Modules.Scoreboard.Main;
import de.Herbystar.CTSNC_Modules.Scoreboard.Scoreboards;

public class PlayerChangeWorldEvents implements Listener {
	
	Modules m = new Modules();
	
	@EventHandler
	public void onPlayerChangeWorldEvent(PlayerChangedWorldEvent e) {
		de.Herbystar.CTSNC.Main.instance.dispatch_command = true;
		Player p = e.getPlayer();
		
		if(e.getFrom() == null | (m.ScoreboardModule() == false && m.NameTagModule() == false)) {
			return;
		}
		
		if(m.ScoreboardModule() == true) {
		    e.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		    Bukkit.getScoreboardManager().getNewScoreboard();
		}

	    if (de.Herbystar.CTSNC.Main.instance.getConfig().getBoolean("CTSNC.WorldSupport") == false) {
	    	refreshScoreboard(p);
	    } else {
	    	if(de.Herbystar.CTSNC.Main.instance.getConfig().getStringList("CTSNC.Worlds").contains(e.getFrom().getName())) {
	    		refreshScoreboard(p);
	    	} else {
	    		if(m.ScoreboardModule() == true) {
	    			if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == true) {
		    		    e.getPlayer().getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		    		    Bukkit.getScoreboardManager().getNewScoreboard();
	    			}
	    		}
	    	}
	    }
	    de.Herbystar.CTSNC.Main.instance.dispatch_command = false;
	}
	
	public void refreshScoreboard(Player player) {
		if(m.ScoreboardModule() == true) {
			
		    player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
		    Bukkit.getScoreboardManager().getNewScoreboard();
		    
			if(Main.boards.containsKey(player)) {
				Main.boards.remove(player);
				new Scoreboards(player);
			} else {
				if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == true) {
					new Scoreboards(player);
				}
			}
		}
		if(m.NameTagModule() == true) {
			if(Files.config2.getBoolean("CTSNC.CUSTOM_TAGS.Enabled") == true && Files.config2.getBoolean("CTSNC.CUSTOM_TAGS.TAG_CLEAR!") == false) {
				CustomTags ct = new CustomTags();
				ct.setCustomTags(player);
			}
		}
	}
}