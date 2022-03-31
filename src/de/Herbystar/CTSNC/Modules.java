package de.Herbystar.CTSNC;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Modules {
	
	private static PluginManager pm = Bukkit.getServer().getPluginManager();
	private Plugin tablist;
	private Plugin scoreboard;
	private Plugin chat;
	private Plugin displayname;
	private Plugin stats;
	private Plugin nametag;
	private Plugin christmas;
	private Plugin acd;
	
	public void sendModules() { //Send an overview of all hooked/using modules
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§c========>[§eCTSNC Modules§c]<========");
		Bukkit.getConsoleSender().sendMessage("");
		
		sendMsg(TablistModule(), "Tablist");
		
		sendMsg(ScoreboardModule(), "Scoreboard");
		
		sendMsg(ChatModule(), "Chat");
		
		sendMsg(NameTagModule(), "NameTag");
		
		sendMsg(DisplayNameModule(), "DisplayName");
		
		sendMsg(StatsModule(), "Stats");
		
		sendMsg(ChristmasModule(), "Christmas");
		
		sendMsg(AntiDirectConnectModule(), "AntiDirectConnect");
		
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§c========>[§eCTSNC Modules§c]<========");
		Bukkit.getConsoleSender().sendMessage("");

	}
	
	private void sendMsg(Boolean module, String moduleName) {
		String output = "§c[§eCTSNC§c] §e";
		Plugin pl = pm.getPlugin("CTSNC_" + moduleName);
		if(module == true) {
			output = output + moduleName + "§e-Module §aactivated! §7- §eVersion: §c" + pl.getDescription().getVersion();
		} else {
			output = output + moduleName + "§e-Module §cdisabled!";
		}
		Bukkit.getServer().getConsoleSender().sendMessage(output);

	}
	
	public static boolean hasPluginVersion(Plugin pl, String version) {
		int goalVersion = Integer.parseInt(version.replace(".", ""));
		String plName = pl.getDescription().getName();
		if(pl.getDescription().getVersion().contains("SNAPSHOT")) {
			return true;
		}
		int plVersion = Integer.parseInt(pl.getDescription().getVersion().replace(".", ""));
		if(plVersion >= goalVersion) {
			return true;
		}
		Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c§l! §e§l" + plName + "§c§l is outdated and will not work for this version of CTSNC!");
		pm.disablePlugin(pl);
		return false;
	}
	
	public boolean TablistModule() { //Check if module is installed
		tablist = pm.getPlugin("CTSNC_Tablist");
		if(tablist != null && hasPluginVersion(tablist, "10")) {
			return true;
		}
		return false;
	}
	
	public boolean NameTagModule() {
		nametag = pm.getPlugin("CTSNC_NameTag");
		if(nametag != null && hasPluginVersion(nametag, "13")) {
			return true;
		}
		return false;
	}
	
	public boolean ScoreboardModule() {
		scoreboard = pm.getPlugin("CTSNC_Scoreboard");
		if(scoreboard != null && hasPluginVersion(scoreboard, "21")) {
			return true;
		}
		return false;
	}
	
	public boolean ChatModule() {
		chat = pm.getPlugin("CTSNC_Chat");
		if(chat != null && hasPluginVersion(chat, "10")) {
			return true;
		}
		return false;
	}
	
	public boolean DisplayNameModule() {
		displayname = pm.getPlugin("CTSNC_DisplayName");
		if(displayname != null && hasPluginVersion(displayname, "11")) {
			return true;
		}
		return false;
	}
	
	public boolean StatsModule() {
		stats = pm.getPlugin("CTSNC_Stats");
		if(stats != null && hasPluginVersion(stats, "12")) {
			return true;
		}
		return false;
	}
	
	public boolean ChristmasModule() {
		christmas = pm.getPlugin("CTSNC_Christmas");
		if(christmas != null && hasPluginVersion(christmas, "11")) {
			return true;
		}
		return false;
	}
	
	public boolean AntiDirectConnectModule() {
		acd = pm.getPlugin("CTSNC_AntiDirectConnect");
		if(acd != null) {
			return true;
		}
		return false;
	}

}
