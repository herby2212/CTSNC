package de.Herbystar.CTSNC;

import me.BukkitPVP.PointsAPI.PointsAPI;
import me.armar.plugins.autorank.Autorank;
import net.alpenblock.bungeeperms.BungeePerms;
import net.milkbowl.vault.Vault;
import net.milkbowl.vault.economy.Economy;
import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import org.anjocaido.groupmanager.GroupManager;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;

import pl.islandworld.api.IslandWorldApi;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.gamingmesh.jobs.Jobs;
import com.lenis0012.bukkit.pvp.PvpLevels;
import com.massivecraft.factions.Factions;

import de.Herbystar.TTA.Utils.TTA_BukkitVersion;

public class Hooks {

	public Economy eco = null;
	public PlayerPoints pp = null;
	public GroupManager groupmanager;
	public SimpleClans sc;
	
	public void SendHooks(Player player) {
		sendMsg(hookVault(), "Vault-Connection", player);

		sendMsg(setupEconomy(), "Vault-Economy-Connection", player);
		
		sendMsg(hookPlayerPoints(), "PlayerPoints-Connection", player);

		sendMsg(hookPointsAPI(), "PointsAPI-Connection", player);

		sendMsg(hookPlaceholderAPI(), "PlaceholderAPI-Connection", player);

		sendMsg(hookPermissionEx(), "PermissionEx-Connection", player);

		sendMsg(hookTokenManager(), "TokenManager-Connection", player);

		sendMsg(hookGroupManager(), "GroupManager-Connection", player);

		sendMsg(hookRPGPlayerLeveling(), "RPGPlayerLeveling-Connection", player);

		sendMsg(hookAutorank(), "Autorank-Connection", player);

		sendMsg(hookPvpLevels(), "PvpLevels-Connection", player);

		sendMsg(hookIslandWorld(), "IslandWorld-Connection", player);

		sendMsg(hookLuckPerms(), "LuckPerms-Connection", player);

		sendMsg(hookBungeePerms(), "BungeePerms-Connection", player);

		sendMsg(hookSimpleClans(), "SimpleClans-Connection", player);

		sendMsg(hookRPGLives(), "eRPGLives-Connection", player);

		sendMsg(hookFactions(), "Factions-Connection", player);

		sendMsg(hookASkyBlock(), "ASkyBlock-Connection", player);

		sendMsg(hookAutoSell(), "AutoSell-Connection", player);

		sendMsg(hookLibsDisguises(), "LibsDisguises-Connection", player);

		sendMsg(hookMcMMO(), "McMMO-Connection", player);

		sendMsg(hookGriefPrevention(), "GriefPrevention-Connection", player);

		sendMsg(hookVotingPlugin(), "VotingPlugin-Connection", player);
		
		sendMsg(hookTownyPlugin(), "Towny-Connection", player);
		
		sendMsg(hookEZBlocksPlugin(), "EZBlocks-Connection", player);
		
		sendMsg(hookCoinsAPI(), "CoinsAPI-Connection", player);
		
	}	
	
	private void sendMsg(Boolean hookPlugin, String message, Player player) {
		String output = "§c[§eCTSNC§c] §e";
		if(hookPlugin == true) {
			output = output + message + " §asuccessful!";
		} else {
			output = output + message + " §cfailed!";
		}
		
		if(player == null) {
			Bukkit.getServer().getConsoleSender().sendMessage(output);
		} else {
			player.sendMessage(ReplaceString.replaceString(output, player));
		}
	}
	
	public boolean hookMcMMO() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("mcMMO");
		if(plugin != null && plugin.isEnabled()) {
			return true;
		}
		return false;		
	}
	
	public boolean hookAutoSell() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("AutoSell");
		if(plugin != null && (hookVault() == true  && setupEconomy() == true)) {
			return true;
		}
		return false;
	}
	
	public boolean hookASkyBlock() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("ASkyBlock");
		if(plugin != null && (hookVault() == true && setupEconomy() == true)) {
			return true;
		}
		return false;
	}
	
	public boolean hookSimpleClans() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("SimpleClans");
		if(plugin != null) {
			sc = (SimpleClans) plugin;
			return true;
		}
		return false;
	}

	public boolean hookLuckPerms() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("LuckPerms");
		if(plugin != null) {
			return true;
		}
		return false;
	}

	public boolean hookIslandWorld() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("IslandWorld");
		if(plugin != null && IslandWorldApi.isInitialized()) {
			return true;
		}
		return false;
	}

	//Depends Hook (If false CTSNC not work!)
	public boolean CheckDepends() {
		if(Bukkit.getServer().getPluginManager().getPlugin("TTA") != null) {
			if(Bukkit.getServer().getPluginManager().getPlugin("TTA").isEnabled() == true) {
				return true;
			}
		}
		return false;		
	}	
	
	//Prepare Hook's
	public void WorldSupportHook() {
		if(Main.instance.getConfig().getBoolean("CTSNC.WorldSupport") == true) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eWorldSupport §aenabled!");
		} else {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eWorldSupport §cdisabled!");
		}
	}
	
	public void ServerVersionHook() {
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_7_R4.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eProtocol Hack Support §aenabled!");
		}
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R1.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.8 Support §aenabled!");
		}
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R2.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.8.3 Support §aenabled!");
		}
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_8_R3.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.8.4-1.8.9 Support §aenabled!");
		}
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_9_R1.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.9 Support §aenabled!");
		}
		if(Main.instance.getServerVersion().equalsIgnoreCase("v1_9_R2.")) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.9.4 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.10", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.10 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.11", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.11 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.12", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.12 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.13", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.13 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.14", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.14 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.15", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.15 Support §aenabled!");
		}
		if(TTA_BukkitVersion.isVersion("1.16", 2)) {
			Bukkit.getServer().getConsoleSender().sendMessage("§c[§eCTSNC§c] §eMinecraft 1.16 Support §aenabled!");
		}
	}
	
	public boolean hookPlayerPoints() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");

	    if(plugin == null || !(plugin instanceof PlayerPoints)) {
	        return false; 
	    }
		pp = PlayerPoints.class.cast(Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints"));
		return(pp != null);
		
	}
	
	public boolean hookLibsDisguises() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("LibsDisguises");
		if(plugin != null && Bukkit.getServer().getPluginManager().getPlugin("ProtocolLib") != null) {
			return true;
		}
		return false;
	}
	
	public boolean hookJobsReborn() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Jobs");

	    if(plugin == null || !(plugin instanceof Jobs)) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookPermissionEx() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PermissionsEx");

	    if(plugin == null || !(plugin instanceof PermissionsEx)) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookPointsAPI() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PointsAPI");

	    if(plugin == null || !(plugin instanceof PointsAPI)) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookPlaceholderAPI() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PlaceholderAPI");

	    if(plugin == null) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookTokenManager() {
		try {
			if(Bukkit.getServer().getPluginManager().getPlugin("TokenManager") != null) {
				return true;
			}
		} catch(Exception e) {
			return false;
		}
		return false;
	}
	
	public boolean hookGroupManager() {
		PluginManager pm = Bukkit.getServer().getPluginManager();
		Plugin GMplugin = pm.getPlugin("GroupManager");
		if(GMplugin != null && GMplugin.isEnabled()) {
			groupmanager = (GroupManager) GMplugin;
			return true;
		}
		return false;
	}
	
	public boolean hookMVdWPlaceholderAPI() {
		if(Bukkit.getServer().getPluginManager().getPlugin("MVdWPlaceholderAPI") == null) {
			return false;
		}
		return true;
	}
	
	public boolean hookVault() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Vault");

	    if(plugin == null || !(plugin instanceof Vault)) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookRPGPlayerLeveling() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("RPGPlayerLeveling");

	    if(plugin == null) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean hookAutorank() {
	    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Autorank");
	    Plugin plLib = Bukkit.getServer().getPluginManager().getPlugin("PluginLibrary");
	    if((plugin != null && plugin instanceof Autorank && plugin.isEnabled() == true) && plLib != null) {
	        return true; 
	    }
	    return false;

	}
	
	public boolean hookPvpLevels() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("PvpLevels");
		
	    if(plugin == null || !(plugin instanceof PvpLevels)) {
	        return false; 
	    }
	    return true;
	}
	
	public boolean setupEconomy() {		
		if(hookVault() == false) {
			return false;
		}
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		if(rsp == null) {
			return false;
		}
		eco = rsp.getProvider();
		return(eco != null);
	}

	public boolean hookBungeePerms() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("BungeePerms");
		
		if(plugin != null || plugin instanceof BungeePerms) {
			return true;
		}
		return false;
	}

	public boolean hookRPGLives() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("RPGLives");
		if(plugin != null) {
			return true;
		}
		return false;
	}

	public boolean hookFactions() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Factions");
		Plugin massivecore = Bukkit.getServer().getPluginManager().getPlugin("MassiveCore");
		if((plugin != null && plugin instanceof Factions) && massivecore != null) {
			return true;
		}
		return false;
	}

	public boolean hookGriefPrevention() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("GriefPrevention");
		if(plugin != null) {
			return true;
		}
		return false;
	}
	
	public boolean hookVotingPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("VotingPlugin");
		if(plugin != null && plugin.isEnabled()) {
			return true;
		}
		return false;
	}	
	
	public boolean hookTownyPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("Towny");
		if(plugin != null) {
			return true;
		}
		return false;
	}
	
	public boolean hookEZBlocksPlugin() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("EZBlocks");
		if(plugin != null) {
			return true;
		}
		return false;
	}
	
	public boolean hookCoinsAPI() {
		Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("CoinsAPINB");
		if(plugin != null) {
			return true;
		}
		return false;
	}
}
