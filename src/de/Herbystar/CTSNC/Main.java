package de.Herbystar.CTSNC;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import de.Herbystar.CTNSC.Events.PlayerChangeWorldEvents;
import de.Herbystar.CTNSC.Events.PlayerInteractEvents;
import de.Herbystar.CTNSC.Events.PlayerJoinEvents;
import de.Herbystar.CTNSC.Events.PlayerMoveEvents;
import de.Herbystar.CTNSC.Events.PlayerQuitEvents;
import de.Herbystar.CTSNC.Files.ConfigGenerator;
import de.Herbystar.CTSNC.Files.Files;
public class Main extends JavaPlugin {
	
	public String prefix = getConfig().getString("CTSNC.CustomPrefix").replace('&', '§').
			replace("<1>", "❤").replace("<2>", "☀").replace("<3>", "✯").replace("<4>", "☢").replace("<5>", "☎").replace("<6>", "♫").replace("<7>", "❄").replace("<8>", "«").
			replace("<9>", "»").replace("<10>", "Ω").replace("<11>", "☠").replace("<12>", "☣").replace("[ctsnc-server-name]", Bukkit.getServer().getName()).
			replace("[ctsnc-server-players-online]", Bukkit.getServer().getOnlinePlayers().size() + "").replace("[ctsnc-server-players-max]", Bukkit.getMaxPlayers() + "");
	
	public ArrayList<UUID> AFK = new ArrayList<UUID>();
	public Integer afk;
	public boolean UpdateAviable;
	public boolean sc;
	public boolean novpp;
	public String version;
	public Pattern Version_Pattern = Pattern.compile("(v|)[0-9][_.][0-9][_.][R0-9]*");
	//wb = welcome back
	public ArrayList<UUID> wb = new ArrayList<UUID>();
	public static HashMap<String, Integer> playerCounts = new HashMap<String, Integer>();
	public static ArrayList<Player> Tablists = new ArrayList<Player>();

	public static Main instance;
	
	private MySQL sql;
	private Hooks h = new Hooks();
	private Modules m = new Modules();
	private ConfigGenerator cg = new ConfigGenerator();
	public boolean dispatch_command;
	private BungeeCord bc = new BungeeCord(this);
	
	public void onEnable() {
		if(h.CheckDepends() == false) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] You need to download §eTTA§c!");
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] CTSNC will now shut down!");
			Bukkit.getServer().getPluginManager().disablePlugin(this);
			return;
		}
		sendTerms();
		instance = this;
		getCommands();
		loadConfig();
		registerEvents();
		
		if(Main.instance.getConfig().getBoolean("CTSNC.BungeeCord") == true) {
			Bukkit.getMessenger().registerOutgoingPluginChannel(instance, "BungeeCord");
			Bukkit.getServer().getMessenger().registerIncomingPluginChannel(instance, "BungeeCord", bc);	
		}
		
		
//		Christmas = new Christmas(instance);
		
		cg.CreateConfigs();
		Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eConfig Generation §asuccessful!");
		
		h.SendHooks(null);
		m.sendModules();
		StartMetrics();
		StartMySQL();
		h.ServerVersionHook();
		h.WorldSupportHook();
		this.dispatch_command = false;
		this.FirstSetup();
		
		Server server = Bukkit.getServer();
	    ConsoleCommandSender console = server.getConsoleSender();
	    console.sendMessage("§c[§eCTSNC§c] " + ChatColor.AQUA + "Version: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + ChatColor.GREEN + " enabled!");
	    
	    
	}
	
	public void onDisable() {
		for(Player p : Bukkit.getOnlinePlayers()) {
			p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
		}
		
		Server server = Bukkit.getServer();
	    ConsoleCommandSender console = server.getConsoleSender();
	    console.sendMessage("§c[§eCTSNC§c] " + ChatColor.AQUA + "Version: " + getDescription().getVersion() + " §aby " + "§c" + getDescription().getAuthors() + ChatColor.RED + " disabled!");
	}
	
	private void sendTerms() {
		Bukkit.getConsoleSender().sendMessage("");
		Bukkit.getConsoleSender().sendMessage("§c=========>[§eCTSNC Terms§c]<=========");		
		Bukkit.getConsoleSender().sendMessage("§c-> You are not permitted to claim this plugin as your own!");
		Bukkit.getConsoleSender().sendMessage("§c-> You are not permitted to decompiling the plugin's sourcecode!");
		Bukkit.getConsoleSender().sendMessage("§c-> You are not permitted to modify the code or the plugin and call it your own!");
		Bukkit.getConsoleSender().sendMessage("§c-> You are not permitted to redistributing this plugin as your own!");
		Bukkit.getConsoleSender().sendMessage("§c=======>[§aTerms Accepted!§c]<=======");
		Bukkit.getConsoleSender().sendMessage("");

	}
	
	public void stopScoreboards() {
		if(m.NameTagModule() == true) {
			de.Herbystar.CTSNC_Modules.NameTag.Main.customtag.cancel();
		}
		if(m.DisplayNameModule() == true && m.NameTagModule() == false) {
			de.Herbystar.CTSNC_Modules.DisplayName.Main.displayname.cancel();
		}
		if(m.ScoreboardModule() == true) {
			de.Herbystar.CTSNC_Modules.Scoreboard.Main.scoreboardcontent.cancel();
			de.Herbystar.CTSNC_Modules.Scoreboard.Main.scoreboardtitle.cancel();
		}
		if(m.TablistModule() == true) {
			Bukkit.getScheduler().cancelTask(de.Herbystar.CTSNC_Modules.Tablist.Main.tablistcontent);
		}
	}

	public String getServerVersion() {
		if(version != null) {
			return version;
		}
		String pkg = Bukkit.getServer().getClass().getPackage().getName();
		String version1 = pkg.substring(pkg.lastIndexOf(".") +1);
		if(!Version_Pattern.matcher(version1).matches()) {
			version1 = "";
		}
		String version = version1;
		return version = !version.isEmpty() ? version + "." : ""; 
	}	
		
	private void registerEvents() {
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerJoinEvents(), this);		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerQuitEvents(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerMoveEvents(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerInteractEvents(), this);
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerChangeWorldEvents(), this);
	}	

	private void getCommands() {
		getCommand("CTS").setExecutor(new Commands());
		getCommand("CC").setExecutor(new Commands());
	}
	
	private void loadConfig() {
		this.getConfig().options().copyDefaults(true);
		saveConfig();	
	}
	
	private void StartMetrics() {
		if(Files.config10.getBoolean("CTSNC.Metrics.Enabled") == true) {
			try {
				Metrics m = new Metrics(this);
				m.start();
				Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §aStarted §eMetrics §asuccessful!");
			} catch (IOException e) {
				e.printStackTrace();
				Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] Failed to start the §eMetrics§c!");
			}
		} else {
			Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMetrics §cdisabled!");
		}
	}
	
	private void StartMySQL() {
		if(Files.config9.getBoolean("CTSNC.MySQL.Enabled") == true) {
			try {
				this.sql = new MySQL();
				Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §aStarted §eMySQL-Service §asuccessful!");
			} catch (Exception e) {
				Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] Failed to start §eMySQL-Service§c (" + e.getMessage() + ")§c!");
			}
		} else {
			Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMySQL-Service §cdisabled!");
		}
	}
	

		
	public MySQL getMySQL() {
		return this.sql;
	}
	
	public void FirstSetup() {
		if(this.getConfig().getBoolean("CTSNC.FirstSetup") == true) {
			Bukkit.getConsoleSender().sendMessage("");
			  Bukkit.getConsoleSender().sendMessage("§c##########[§eCTSNC§c]##########");
		      Bukkit.getConsoleSender().sendMessage("§c#                         #");
		      Bukkit.getConsoleSender().sendMessage("§c#       §aFirst Setup       §c#");
		      Bukkit.getConsoleSender().sendMessage("§c#    §aRestarting Server    §c#");
		      Bukkit.getConsoleSender().sendMessage("§c#                         #");
			  Bukkit.getConsoleSender().sendMessage("§c##########[§eCTSNC§c]##########");
		      Bukkit.getConsoleSender().sendMessage("");
			  
		      this.getConfig().set("CTSNC.FirstSetup", false);
		      this.saveConfig();
		      Bukkit.getServer().shutdown();
		} 
	}
}
