package de.Herbystar.CTNSC.Events;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.Modules;
import de.Herbystar.CTSNC.ReplaceString;
import de.Herbystar.CTSNC.Files.Files;
import de.Herbystar.CTSNC.Join.ActionBar;
import de.Herbystar.CTSNC.Join.Titles;
import de.Herbystar.CTSNC_Modules.Christmas.Christmas;
import de.Herbystar.CTSNC_Modules.DisplayName.DisplayName;
import de.Herbystar.CTSNC_Modules.NameTag.CustomTags;
import de.Herbystar.CTSNC_Modules.Scoreboard.Scoreboards;
import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class PlayerJoinEvents implements Listener {
	
	private Modules m = new Modules();
			
	@EventHandler
	public void onPlayerJoinEvent(PlayerJoinEvent e) {		
		final Player p = e.getPlayer();		
			
		if(m.ChristmasModule() == true) {
			if(Files.cm.getBoolean("SnowFall") == true) {
				Christmas.add(p);
			}
			if(Files.cm.getBoolean("SantaHead") == true && Files.cm.getBoolean("ChristmasTreeBanner") == false) {
				Christmas.setSantaHead(p);
			}
			if(Files.cm.getBoolean("ChristmasTreeBanner") == true && Files.cm.getBoolean("SantaHead") == false) {
				Christmas.setChristmasBanner(p);
			}
		}
		
		if(p.isOp()) {
			if(Main.instance.getConfig().getBoolean("CTSNC.ServerVersion") == true) {
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_7_R4.")) {
					p.sendMessage("ProtocolHack");
				}
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R1.")) {
					p.sendMessage("1.8");
				}
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R2.")) {
					p.sendMessage("1.8.3");
				}
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R3.")) {
					p.sendMessage("1.8.4+");
				}
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_9_R1.")) {
					p.sendMessage("1.9");
				}
				if(Main.instance.getServerVersion().equalsIgnoreCase("v1_9_R2.")) {
					p.sendMessage("1.9.4");
				}
				if(TTA_BukkitVersion.isVersion("1.10", 2)) {
					p.sendMessage("1.10");
				}
				if(TTA_BukkitVersion.isVersion("1.11", 2)) {
					p.sendMessage("1.11");
				}
				if(TTA_BukkitVersion.isVersion("1.12", 2)) {
					p.sendMessage("1.12");
				}
				if(TTA_BukkitVersion.isVersion("1.13", 2)) {
					p.sendMessage("1.13");
				}
				if(TTA_BukkitVersion.isVersion("1.14", 2)) {
					p.sendMessage("1.14");
				}
			}
			/*
			if(Main.instance.getConfig().getBoolean("CTSNC.AutoUpdater") == true) {
				if(Main.instance.UpdateAviable == true) {
					p.sendMessage("§c[§eCTSNC§c] §a-=> Update is available! <=-");
				}
			}
			*/
		}		
		if(m.TablistModule() == true) {
			if(!Main.Tablists.contains(p)) {
				Main.Tablists.add(p);
			}
		}
		
		if(m.DisplayNameModule() == true) {
			Main.instance.dispatch_command = true;
			DisplayName dn = new DisplayName();
			dn.setDisplayName();
			Main.instance.dispatch_command = false;
		}			
		if(Main.instance.getConfig().getBoolean("CTSNC.ONJOIN.Command.Player.Enabled") == true) {
			for(String cmd : Main.instance.getConfig().getStringList("CTSNC.ONJOIN.Command.Player.Cmd")) {
				p.performCommand(ReplaceString.replaceString(cmd, p));
			}
		}
		if(Main.instance.getConfig().getBoolean("CTSNC.ONJOIN.Command.Console.Enabled") == true) {
			Main.instance.dispatch_command = true;
			for(String cmd : Main.instance.getConfig().getStringList("CTSNC.ONJOIN.Command.Console.Cmd")) {
				Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), ReplaceString.replaceString(cmd, p));
			}
			Main.instance.dispatch_command = false;
		}
		
		if(Main.instance.getConfig().getBoolean("CTSNC.ONJOIN.JoinMessage.Enabled") == true) {
			Main.instance.dispatch_command = true;
			e.setJoinMessage(ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.ONJOIN.JoinMessage.Content"), p));
			Main.instance.dispatch_command = false;
		}
		
		if(Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ServerRestartOverride") == false) {
			if(!Main.instance.wb.contains(p.getUniqueId())) {
				Main.instance.wb.add(p.getUniqueId());
				Main.instance.dispatch_command = true;
				ActionBar.sendOnJoinActionbar(p);
				Titles.sendOnJoinTitles(p);
				Main.instance.dispatch_command = false;
			} else {
				Main.instance.dispatch_command = true;
				de.Herbystar.CTSNC.WelcomeBack.ActionBar.sendWelcomeBackActionbar(p);
				de.Herbystar.CTSNC.WelcomeBack.Titles.sendWelcomeBackTitles(p);
				Main.instance.dispatch_command = false;
			}
		} else {
			List<String> wb = Main.instance.getConfig().getStringList("CTSNC.WB");
			if(!wb.contains(p.getUniqueId().toString())) {
				wb.add(p.getUniqueId().toString());
				Main.instance.getConfig().set("CTSNC.WB", wb);
				Main.instance.saveConfig();
				Main.instance.dispatch_command = true;
				ActionBar.sendOnJoinActionbar(p);
				Titles.sendOnJoinTitles(p);
				Main.instance.dispatch_command = false;
			} else {
				Main.instance.dispatch_command = true;
				de.Herbystar.CTSNC.WelcomeBack.ActionBar.sendWelcomeBackActionbar(p);
				de.Herbystar.CTSNC.WelcomeBack.Titles.sendWelcomeBackTitles(p);
				Main.instance.dispatch_command = false;
			}
		}
	
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if(m.ScoreboardModule() == true) {
					if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == true) {
						new Scoreboards(p);
					}
				}
				if(m.NameTagModule() == true) {
					if(Files.config2.getBoolean("CTSNC.CUSTOM_TAGS.Enabled") == true && Files.config2.getBoolean("CTSNC.CUSTOM_TAGS.TAG_CLEAR!") == false) {
						CustomTags ct = new CustomTags();
						ct.setCustomTags(p);
					}
				}	
			}
		}.run();
		
	}

}
