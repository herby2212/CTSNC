package de.Herbystar.CTSNC.Files;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;

import de.Herbystar.CTSNC.Modules;

public class ConfigGenerator {
	
	private Modules m = new Modules();
	
	public void CreateConfigs() {
		
		//Tablist
		File f1 = new File("plugins/CTSNC", "Tablist.yml");
		YamlConfiguration config1 = YamlConfiguration.loadConfiguration(f1);
		
		config1.addDefault("CTSNC.TABLIST.Enabled", true);
		config1.addDefault("CTSNC.TABLIST.Update_Interval", 20);
		config1.addDefault("CTSNC.TABLIST.Normal.Enabled", true);
		config1.addDefault("CTSNC.TABLIST.Normal.Header", "&c[&e&lCTSNC&c] &8| &a&lWorks fine&e! [ctsnc-nextline] &6Also with multiple lines!");
		config1.addDefault("CTSNC.TABLIST.Normal.Footer", "&2&lExample &e&lNetwork &8&l| &c&oJoin Now! [ctsnc-nextline] &6IP: &8<9> &aYourNetwork.com ");
		config1.options().copyDefaults(true);
		if(m.TablistModule() == true) { //Generates only the Tablist configuration if the using Module is installed
			saveConfig(config1, f1);
		}
		
		//AntiDirectConnect
		File f14 = new File("plugins/CTSNC", "AntiDirectConnect.yml");
		YamlConfiguration config14 = YamlConfiguration.loadConfiguration(f14);
		
		List<String> ips = new ArrayList<String>();
		ips.add("127.0.0.1");
		ips.add("0.0.0.0");
		
		config14.addDefault("CTSNC.ACD.DisallowMSG", "&c[&eCTSNC&c] &6<9> 6cYou can't direct connect to this ip!");
		config14.addDefault("CTSNC.ACD.Whitelist", ips);
		config14.options().copyDefaults(true);
		if(m.AntiDirectConnectModule() == true) {
			saveConfig(config14, f14);
		}
		
		//Chat
		File f2 = new File("plugins/CTSNC", "Chat.yml");
		YamlConfiguration config2 = YamlConfiguration.loadConfiguration(f2);
		
		config2.addDefault("CTSNC.CHAT.BlockChat", false);
		config2.addDefault("CTSNC.CHAT.Anti-Cap.Enabled", true);
		config2.addDefault("CTSNC.CHAT.Anti-Cap.Caps-Percentage", 25);
		config2.addDefault("CTSNC.CHAT.Anti-Cap.Minimum-Mgs-Lenght", 4);
		config2.addDefault("CTSNC.CHAT.Anti-Cap.Uppercase-Characters", "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
		config2.addDefault("CTSNC.CHAT.ChatClearMessage.Enabled", true);
		config2.addDefault("CTSNC.CHAT.ChatClear.Size", 60);
		config2.addDefault("CTSNC.CHAT.ChatClearMessage.Content", "&cThe chat was cleared by &6[ctsnc-player-displayname]!");
		config2.addDefault("CTSNC.CHAT.ChatReplaceVariable", true);
		config2.addDefault("CTSNC.CHAT.ChatEmojis", true);
		config2.addDefault("CTSNC.CHAT.CustomChatFormat", true);
		config2.addDefault("CTSNC.CHAT.CustomChatName", true);
		config2.addDefault("CTSNC.CHAT.ChatFormat", "%1$s &7<9> &f%2$s");
		config2.addDefault("CTSNC.CHAT.StaffChatFormat", "[ctsnc-player-displayname] &8<9> &f");
		config2.options().copyDefaults(true);
		if(m.ChatModule() == true) {
			saveConfig(config2, f2);
		}
		
		
		//Scoreboard
		File f3 = new File("plugins/CTSNC", "Scoreboard.yml");
		YamlConfiguration config3 = YamlConfiguration.loadConfiguration(f3);
		
		List<String> li = new ArrayList<String>();
		li.add("&1");
		li.add("&8<9> &a&lWelcome:");
		li.add("&8<9> &e[ctsnc-player-name]");
		li.add("&2");
		li.add("&8<9> &a&lOnline:");
		li.add("&8<9> &e[ctsnc-server-players-online]&c/&e[ctsnc-server-players-max]");
		li.add("&3");
		li.add("&8<9> &a&lServer:");
		li.add("&8<9> &e[ctsnc-server-name]");
		li.add("&4");
		li.add("&8<9> &a&lServer Time:");
		li.add("&8<9> &e[ctsnc-server-time]");
		li.add("&5");
		li.add("&8<9> &a&lCurrent Gamemode:");
		li.add("&8<9> &e[ctsnc-player-gamemode]");
		
		List<String> li1 = new ArrayList<String>();
		li1.add("&4<1> &c[&e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&e&lCTSNC &4<1>");
		li1.add("&4<1> &c[&e&lCTSN &4<1>");
		li1.add("&4<1> &c[&e&lCTS &4<1>");
		li1.add("&4<1> &c[&e&lCT &4<1>");
		li1.add("&4<1> &c[&e&lC &4<1>");
		li1.add("&4<1> &c[ &4<1>");
		li1.add("&4<1> &4<1>");
		li1.add("&4<1> &4<1>");
		li1.add("&4<1> &c[ &4<1>");
		li1.add("&4<1> &c[&e&lC &4<1>");
		li1.add("&4<1> &c[&e&lCT &4<1>");
		li1.add("&4<1> &c[&e&lCTS &4<1>");
		li1.add("&4<1> &c[&e&lCTSN &4<1>");
		li1.add("&4<1> &c[&e&lCTSNC &4<1>");
		li1.add("&4<1> &c[&e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&6&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&a&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&b&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&c&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&d&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&f&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &e&lTSNC&c] &4<1>");
		li1.add("&4<1> &e&lSNC&c] &4<1>");
		li1.add("&4<1> &e&lNC&c] &4<1>");
		li1.add("&4<1> &e&lC&c] &4<1>");
		li1.add("&4<1> &c] &4<1>");
		li1.add("&4<1> &4<1>");
		li1.add("&4<1> &4<1>");
		li1.add("&4<1> &c] &4<1>");
		li1.add("&4<1> &e&lC&c] &4<1>");
		li1.add("&4<1> &e&lNC&c] &4<1>");
		li1.add("&4<1> &e&lSNC&c] &4<1>");
		li1.add("&4<1> &e&lTSNC&c] &4<1>");
		li1.add("&4<1> &e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&e&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&6&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&a&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&b&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&c&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&d&lCTSNC&c] &4<1>");
		li1.add("&4<1> &c[&f&lCTSNC&c] &4<1>");
		
					
		config3.addDefault("CTSNC.SCOREBOARD.Enabled", true);
		config3.addDefault("CTSNC.SCOREBOARD.DEBUG", false);
		config3.addDefault("CTSNC.SCOREBOARD.Update_Content", true);
		config3.addDefault("CTSNC.SCOREBOARD.Scoreboard_Content_Update", 20);
		config3.addDefault("CTSNC.SCOREBOARD.Scoreboard_Title_Update", 2);
		config3.addDefault("CTSNC.SCOREBOARD.Header", li1);
		config3.addDefault("CTSNC.SCOREBOARD.Content", li);
		config3.options().copyDefaults(true);
		if(m.ScoreboardModule() == true) {
			saveConfig(config3, f3);
		}
		
		
		
		//CustomTags
		File f4 = new File("plugins/CTSNC", "CustomTags.yml");
		YamlConfiguration config4 = YamlConfiguration.loadConfiguration(f4);
		
		config4.addDefault("CTSNC.CUSTOM_TAGS.Enabled", true);
		config4.addDefault("CTSNC.CUSTOM_TAGS.allowCollisions", true);
		config4.addDefault("CTSNC.CUSTOM_TAGS.PrefixShortener", false);
		config4.addDefault("CTSNC.CUSTOM_TAGS.Update", 20);
		config4.addDefault("CTSNC.CUSTOM_TAGS.PermissionBased", true);
		config4.addDefault("CTSNC.CUSTOM_TAGS.DefaultOP", true);
		config4.addDefault("CTSNC.CUSTOM_TAGS.OP.Name", "&e<3> &4OP: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag1.Permission", "YourName.Admin");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag1.Name", "&4Admin: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag2.Permission", "YourName.Mod");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag2.Name", "&cModerator: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag3.Permission", "YourName.Dev");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag3.Name", "&3Developer: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag4.Permission", "YourName.Youtuber");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag4.Name", "&5YouTuber: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag5.Permission", "YourName.Builder");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag5.Name", "&eBuilder: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag6.Permission", "YourName.Legend");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag6.Name", "&a&lLegend: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag7.Permission", "YourName.Premium");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag7.Name", "&6Premium: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag8.Permission", "YourName.Default");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag8.Name", "&2Default: ");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag9.Permission", "YourName.unused2");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag9.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag10.Permission", "YourName.unused3");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag10.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag11.Permission", "YourName.unused4");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag11.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag12.Permission", "YourName.unused5");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag12.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag13.Permission", "YourName.unused6");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag13.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag14.Permission", "YourName.unused7");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag14.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag15.Permission", "YourName.unused8");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag15.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag16.Permission", "YourName.unused9");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag16.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag17.Permission", "YourName.unused10");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag17.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag18.Permission", "YourName.unused11");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag18.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag19.Permission", "YourName.unused12");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag19.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag20.Permission", "YourName.unused13");
		config4.addDefault("CTSNC.CUSTOM_TAGS.Tag20.Name", "/");
		config4.addDefault("CTSNC.CUSTOM_TAGS.TAG_CLEAR!", false);
		config4.options().copyDefaults(true);
		if(m.ScoreboardModule() == true | m.DisplayNameModule() == true) {
			saveConfig(config4, f4);
		}
		
		
		
		//Variables
		File f5 = new File("plugins/CTSNC", "Variables.yml");
		YamlConfiguration config5 = YamlConfiguration.loadConfiguration(f5);
		
		config5.addDefault("CTSNC.VARIABLES.PlayerPoints", true);
		config5.addDefault("CTSNC.VARIABLES.PointsAPI", true);
		config5.addDefault("CTSNC.VARIABLES.PermissionEx.Enabled", true);
		config5.addDefault("CTSNC.VARIABLES.PermissionEx.Formatting", false);
		config5.addDefault("CTSNC.VARIABLES.GroupManager", true);
		config5.addDefault("CTSNC.VARIABLES.PlaceholderAPI", true);
		config5.addDefault("CTSNC.VARIABLES.TokenManager", true);
		config5.addDefault("CTSNC.VARIABLES.RPGPlayerLeveling", true);
		config5.addDefault("CTSNC.VARIABLES.ASkyBlock", true);
		config5.addDefault("CTSNC.VARIABLES.Vault", true);
		config5.addDefault("CTSNC.VARIABLES.TPS", true);
		config5.addDefault("CTSNC.VARIABLES.Emojis", true);
		config5.addDefault("CTSNC.VARIABLES.Autorank", true);
		config5.addDefault("CTSNC.VARIABLES.PvpLevels", true);
		config5.addDefault("CTSNC.VARIABLES.IslandWorld", true);
		config5.addDefault("CTSNC.VARIABLES.LuckPerms", true);
		config5.addDefault("CTSNC.VARIABLES.BungeePerms", true);
		config5.addDefault("CTSNC.VARIABLES.SimpleClans", true);
		config5.addDefault("CTSNC.VARIABLES.RPGLives", true);
		config5.addDefault("CTSNC.VARIABLES.Factions", true);
		config5.addDefault("CTSNC.VARIABLES.AutoSell", true);
		config5.addDefault("CTSNC.VARIABLES.LibsDisguises", true);
		config5.addDefault("CTSNC.VARIABLES.McMMO", true);
		config5.addDefault("CTSNC.VARIABLES.GriefPrevention", true);
		config5.addDefault("CTSNC.VARIABLES.VotingPlugin", true);
		config5.addDefault("CTSNC.VARIABLES.Towny", true);
		config5.addDefault("CTSNC.VARIABLES.EZBlocks", true);
		config5.addDefault("CTSNC.VARIABLES.CoinsAPI", true);
		config5.options().copyDefaults(true);
		saveConfig(config5, f5);
		
		//MySQL
		File f6 = new File("plugins/CTSNC", "MySQL.yml");
		YamlConfiguration config6 = YamlConfiguration.loadConfiguration(f6);
		
		config6.addDefault("CTSNC.MySQL.Enabled", false);
		config6.addDefault("CTSNC.MySQL.host", "localhost");
		config6.addDefault("CTSNC.MySQL.port", 3306);
		config6.addDefault("CTSNC.MySQL.user", "user");
		config6.addDefault("CTSNC.MySQL.password", "password");
		config6.addDefault("CTSNC.MySQL.database", "database");	
		config6.options().copyDefaults(true);
		saveConfig(config6, f6);
		
		//Metrics
		File f7 = new File("plugins/CTSNC", "Metrics.yml");
		YamlConfiguration config7 = YamlConfiguration.loadConfiguration(f7);
		
		config7.addDefault("CTSNC.Metrics.Enabled", true);
		config7.options().copyDefaults(true);
		saveConfig(config7, f7);
		
		//Messages
		File f8 = new File("plugins/CTSNC", "Messages.yml");
		YamlConfiguration config8 = YamlConfiguration.loadConfiguration(f8);
		
		config8.addDefault("CTSNC.COMMANDS.Cts_Me_Scoreboard", "&a&lYou toggled your &escoreboard &a&lsuccessful!");
		config8.addDefault("CTSNC.COMMANDS.Cts_Me_Tablist", "&a&lYou toggled your &etablist &a&lsuccessful!");
		config8.addDefault("CTSNC.MODULES.Missing", "&c&lYou dont have the right module installed!");
		config8.options().copyDefaults(true);
		saveConfig(config8, f8);
		
		
		//Emoji Chooser
		File f9 = new File("plugins/CTSNC", "Emoji_Chooser.yml");
		YamlConfiguration config9 = YamlConfiguration.loadConfiguration(f9);
		
		config9.addDefault("CTSNC._:D_", ":D");
		config9.addDefault("CTSNC._:)_", ":)");
		config9.addDefault("CTSNC._;)_", ";)");
		config9.addDefault("CTSNC._:(_", ":(");
		config9.addDefault("CTSNC._<3_", "❤");
		config9.options().copyDefaults(true);
		saveConfig(config9, f9);
		
		//WordBlacklist
		File f10 = new File("plugins/CTSNC", "WordBlacklist.yml");
		YamlConfiguration config10 = YamlConfiguration.loadConfiguration(f10);
		
		config10.addDefault("WordBlacklistSupport", true);
		config10.addDefault("WordNotAllowedMessage_1", "&c&l<11> This word is not allowed! <11>");
		List<String> l = new ArrayList<String>();
		l.add("fuck");
		l.add("h a x");
		l.add("hacks");
		l.add("eZ");
		l.add("hax");
		l.add("bitch");
		l.add("easy");
		l.add("biatch");
		l.add("hacker");
		l.add("get wrecked");
		l.add("L2P");
		config10.addDefault("WordBlacklist_1", l);
		
		config10.addDefault("WordNotAllowedMessage_2", "&c&l<11> Message for wordblacklist 2! <11>");
		List<String> l2 = new ArrayList<String>();
		l2.add("testword");
		config10.addDefault("WordBlacklist_2", l2);
		
		config10.addDefault("WordNotAllowedMessage_3", "&c&l<11> Message for wordblacklist 3! <11>");
		List<String> l3 = new ArrayList<String>();
		l3.add("testword3");
		config10.addDefault("WordBlacklist_3", l3);

		config10.addDefault("WordNotAllowedMessage_4", "&c&l<11> Message for wordblacklist 4! <11>");
		List<String> l4 = new ArrayList<String>();
		l4.add("testword4");
		config10.addDefault("WordBlacklist_4", l4);

		config10.addDefault("WordNotAllowedMessage_5", "&c&l<11> Message for wordblacklist 5! <11>");
		List<String> l5 = new ArrayList<String>();
		l5.add("testword5");
		config10.addDefault("WordBlacklist_5", l5);
		config10.options().copyDefaults(true);
		if(m.ChatModule() == true) {
			saveConfig(config10, f10);
		}
		
		File christmas = new File("plugins/CTSNC", "Christmas.yml");
		YamlConfiguration cm = YamlConfiguration.loadConfiguration(christmas);
		
		cm.addDefault("SnowFall", true);
		cm.addDefault("SantaHead", false);
		cm.addDefault("ChristmasTreeBanner", true);
		cm.addDefault("WaterToSnow", false);	
		cm.options().copyDefaults(true);
		saveConfig(cm, christmas);
	}
	
	public void saveConfig(YamlConfiguration config, File file) {
		try {
			config.save(file);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
