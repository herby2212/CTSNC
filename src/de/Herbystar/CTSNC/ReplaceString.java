package de.Herbystar.CTSNC;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import me.BukkitPVP.PointsAPI.PointsAPI;
import me.armar.plugins.autorank.Autorank;
import me.baks.rpl.PlayerList;
import me.clip.autosell.AutoSellAPI;
import me.clip.autosell.objects.AutoSellOptions;
import me.clip.ezblocks.EZBlocks;
import me.clip.placeholderapi.PlaceholderAPI;
import me.libraryaddict.disguise.DisguiseAPI;
import me.lucko.luckperms.api.Contexts;
import me.lucko.luckperms.api.LuckPermsApi;
import me.lucko.luckperms.api.caching.MetaData;
import me.lucko.luckperms.api.context.ContextManager;
import me.realized.tm.api.TMAPI;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import me.ryanhamshire.GriefPrevention.PlayerData;
import net.alpenblock.bungeeperms.BungeePerms;
import net.alpenblock.bungeeperms.Group;
import net.alpenblock.bungeeperms.PermissionsManager;
import net.alpenblock.bungeeperms.User;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.query.QueryOptions;
import net.sacredlabyrinth.phaed.simpleclans.Clan;
import net.sacredlabyrinth.phaed.simpleclans.ClanPlayer;

import org.anjocaido.groupmanager.permissions.AnjoPermissionsHandler;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicesManager;
import org.bukkit.scheduler.BukkitTask;

import pl.islandworld.api.IslandWorldApi;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.Ben12345rocks.VotingPlugin.UserManager.UserManager;
import com.gmail.nossr50.api.ChatAPI;
import com.gmail.nossr50.api.ExperienceAPI;
import com.gmail.nossr50.api.PartyAPI;
import com.lenis0012.bukkit.pvp.PvpLevels;
import com.lenis0012.bukkit.pvp.PvpPlayer;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.entity.MPlayer;
import com.palmergames.bukkit.towny.Towny;
import com.palmergames.bukkit.towny.object.Resident;
import com.palmergames.bukkit.towny.object.Town;
import com.wasteofplastic.askyblock.ASkyBlockAPI;

import de.Herbystar.CTSNC.Files.Files;
import de.Herbystar.TTA.TTA_Methods;
import de.NeonnBukkit.CoinsAPI.API.CoinsAPI;

public class ReplaceString {
	
	//Links to other classes
	private static Hooks h = new Hooks();
	
	//HashMaps/Arrays
	public static HashMap<UUID, BukkitTask> stopwatch = new HashMap<UUID, BukkitTask>();
	public static HashMap<UUID, Integer> stopwatch_time = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> stopwatch_time_minutes = new HashMap<UUID, Integer>();
	public static HashMap<UUID, Integer> stopwatch_time_hours = new HashMap<UUID, Integer>();
	
	//External plugins
	private static PointsAPI points = (PointsAPI) Bukkit.getPluginManager().getPlugin("PointsAPI");
	private static Towny town = (Towny) Bukkit.getPluginManager().getPlugin("Towny");
		
	
	public static String replaceString(String string) {
		return replaceString(string, null);
	}
	
	@SuppressWarnings({ "deprecation" })
	public static String replaceString(String string, Player player) {
		loadConfigurations();
		String n = lowerCaseString(string);
		if(player != null) {
									
			double deaths = Files.config12.getInt("CTSNC." + player.getUniqueId() + ".Deaths");
			double kills = Files.config12.getInt("CTSNC." + player.getUniqueId() + ".Kills");
			double kd = kills / deaths;
			double kdr = round_double(kd);
			int mobkills = Files.config12.getInt("CTSNC." + player.getUniqueId() + ".MobKills");	
			int blocksMined = Files.config12.getInt("CTSNC." + player.getUniqueId() + ".BlocksMined");
			
			//If Stopwatch is running
			if(stopwatch.get(player.getUniqueId()) != null) {
				//Stopwatch Data
				int seconds = stopwatch_time.get(player.getUniqueId());
				int minutes = stopwatch_time_minutes.get(player.getUniqueId());
				int hours = stopwatch_time_hours.get(player.getUniqueId());
								
				//Stopwatch Variable
				if(Integer.toString(seconds) != "0") {
					n = n.replace("[ctsnc-stopwatch-seconds]", Integer.toString(seconds));
				} else {
					n = n.replace("[ctsnc-stopwatch-seconds]", "00");
				}
				if(Integer.toString(minutes) != "0") {
					n = n.replace("[ctsnc-stopwatch-minutes]", Integer.toString(minutes));
				} else {
					n = n.replace("[ctsnc-stopwatch-minutes]", "00");
				}
				if(Integer.toString(hours) != "0") {
					n = n.replace("[ctsnc-stopwatch-hours]", Integer.toString(hours));
				} else {
					n = n.replace("[ctsnc-stopwatch-hours]", "00");
				}
			} else {
				n = n.replace("[ctsnc-stopwatch-seconds]", "00");
				n = n.replace("[ctsnc-stopwatch-minutes]", "00");
				n = n.replace("[ctsnc-stopwatch-hours]", "00");
			}
			
			Double ph = round_double(player.getHealth());
			Double plx = round_double(player.getLocation().getX());
			Double ply = round_double(player.getLocation().getY());
			Double plz = round_double(player.getLocation().getZ());
			Float plpitch = round_float(player.getLocation().getPitch());
			Float plyaw = round_float(player.getLocation().getYaw());
			
			String biome = player.getLocation().getBlock().getBiome().toString().toLowerCase();
//			String lightlevel = new DecimalFormat("##.##").format(player.getLocation().getBlock().getLightLevel());
			//TODO: Disabled due to very rare case of a server crash, more details: https://herbystar.eu/threads/scoreboard-module.6/page-2#post-454
			

			//Player World & Position variables
			n = n.replace("&", "§").
			replace("[ctsnc-world-name]", player.getWorld().getName()).
			replace("[ctsnc-world-players-size]", Integer.toString(player.getWorld().getPlayers().size())).
			replace("[ctsnc-world-time]", WorldTime.getWorldTime(player)).
			replace("[ctsnc-player-position-x]", Double.toString(plx)).
			replace("[ctsnc-player-position-y]", Double.toString(ply)).
			replace("[ctsnc-player-position-z]", Double.toString(plz)).
			replace("[ctsnc-player-position-pitch]", Float.toString(plpitch)).
			replace("[ctsnc-player-position-yaw]", Float.toString(plyaw)).
//			replace("[ctsnc-player-position-lightlevel]", lightlevel).
			replace("[ctsnc-player-direction]", player.getLocation().getDirection().toString()).
			replace("[ctsnc-world-biome]", WordUtils.capitalize(biome)).
			replace("[ctsnc-world-sealevel]", Integer.toString(player.getWorld().getSeaLevel())).
			replace("[ctsnc-player-view-direction]", GetPlayerCardinalDirection(player));
			
						
			
			
			//Player variables
			if(player.getName() != null) {
				n = n.replace("[ctsnc-player-name]", player.getName());
			}
			n = n.replace("&", "§").
			replace("[ctsnc-player-kills]", Integer.toString((int) kills)).
			replace("[ctsnc-player-deaths]", Integer.toString((int) deaths)).
			replace("[ctsnc-player-kdr]", Double.toString((double) kdr)).
			replace("[ctsnc-player-exp]", Float.toString(player.getExp())).
			replace("[ctsnc-player-level]", Integer.toString(player.getLevel())).
			replace("[ctsnc-player-ip]", player.getAddress().getAddress().toString()).
			replace("[ctsnc-player-health]", Double.toString(ph)).
			replace("[ctsnc-player-maxhealth]", Double.toString(player.getMaxHealth())).
			replace("[ctsnc-player-displayname]", player.getDisplayName()).
			replace("[ctsnc-player-foodlevel]", Integer.toString(player.getFoodLevel())).
			replace("[ctsnc-player-gamemode]", player.getGameMode().toString()).
			replace("[ctsnc-player-kills-mobs]", Integer.toString(mobkills)).
			replace("[ctsnc-player-blocks-mined]", Integer.toString(blocksMined));

			
			
			//BungeeCord
			if(Main.instance.getConfig().getBoolean("CTSNC.BungeeCord") == true) {
				BungeeCord.getPlayerCount("ALL");
				try {
					n = n.replace("[ctsnc-bungeecord-players]", Integer.toString(Main.playerCounts.get("ALL")));
				} catch(Exception ex) {
					
				}
			}
			
			
			//Armor Variables
			if(player.getInventory().getHelmet() != null && player.getInventory().getHelmet().getData().getItemType() != Material.SKULL_ITEM && player.getInventory().getHelmet().getData().getItemType() != Material.SKULL) {
				n = n.replace("[ctsnc-player-armor-helmet]", player.getInventory().getHelmet().toString());
			}
			if(player.getInventory().getChestplate() != null) {
				n = n.replace("[ctsnc-player-armor-chestplate]", player.getInventory().getChestplate().toString());
			}
			if(player.getInventory().getLeggings() != null) {
				n = n.replace("[ctsnc-player-armor-leggings]", player.getInventory().getLeggings().toString());
			}
			if(player.getInventory().getBoots() != null) {
				n = n.replace("[ctsnc-player-armor-boots]", player.getInventory().getBoots().toString());
			}
			
			
			//Ping
			n = n.replace("[ctsnc-ping]", Integer.toString((int) TTA_Methods.getPing(player)));			
			
			
			//Ticks per Second (TPS)
			if(Files.config11.getBoolean("CTSNC.VARIABLES.TPS") == true) {
				if(player != null && (TTA_Methods.getTPS(100) > 0) && Main.instance.dispatch_command == false) {
					n = n.replace("[ctsnc-tps]", Double.toString(round_double(TTA_Methods.getTPS(100))));
				}
			}
			
			
			//Variable Hook's
			
			
			/*
			 * VotingPlugin
			 */
			
			if(h.hookVotingPlugin() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.VotingPlugin") == true) {
					try {
						com.Ben12345rocks.VotingPlugin.Objects.User vpu = UserManager.getInstance().getVotingPluginUser(player);
						if(vpu != null) {
							n = n.replace("[votingplugin-player-points]", Integer.toString(vpu.getPoints()));
							
							n = n.replace("[votingplugin-best-votestreak-day]", Integer.toString(vpu.getBestDayVoteStreak()));
							n = n.replace("[votingplugin-best-votestreak-week]", Integer.toString(vpu.getBestWeekVoteStreak()));						
							n = n.replace("[votingplugin-best-votestreak-month]", Integer.toString(vpu.getBestMonthVoteStreak()));
							
							n = n.replace("[votingplugin-highest-total-daily]", Integer.toString(vpu.getHighestDailyTotal()));
							n = n.replace("[votingplugin-highest-total-weekly]", Integer.toString(vpu.getHighestWeeklyTotal()));
							n = n.replace("[votingplugin-highest-total-monthly]", Integer.toString(vpu.getHighestMonthlyTotal()));
							
							n = n.replace("[votingplugin-milestone-count]", Integer.toString(vpu.getMilestoneCount()));
							
							n = n.replace("[votingplugin-voteparty-votes]", Integer.toString(vpu.getVotePartyVotes()));
							
							n = n.replace("[votingplugin-day-totalvotes]", Integer.toString(vpu.getDailyTotal()));
							n = n.replace("[votingplugin-day-votestreak]", Integer.toString(vpu.getDayVoteStreak()));
							
							n = n.replace("[votingplugin-week-totalvotes]", Integer.toString(vpu.getWeeklyTotal()));
							n = n.replace("[votingplugin-week-votestreak]", Integer.toString(vpu.getWeekVoteStreak()));

							n = n.replace("[votingplugin-month-totalvotes]", Integer.toString(vpu.getMonthTotal()));
							n = n.replace("[votingplugin-month-votestreak]", Integer.toString(vpu.getMonthVoteStreak()));
						}
					} catch(Exception ex) {
						String pluginName = "VotingPlugin";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			
			/*
			 * GriefPrevention
			 */
			
			if(h.hookGriefPrevention() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.GriefPrevention") == true) {
					Claim claim = null;
					PlayerData pd = null;
					try {
						if(GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId()) != null)  {
							pd = GriefPrevention.instance.dataStore.getPlayerData(player.getUniqueId());
							n = n.replace("[griefprevention-player-claims]", Integer.toString(pd.getAccruedClaimBlocks()));
							n = n.replace("[griefprevention-player-claims-limit]", Integer.toString(pd.getAccruedClaimBlocksLimit()));
							n = n.replace("[griefprevention-player-claims-remaining]", Integer.toString(pd.getRemainingClaimBlocks()));
							n = n.replace("[griefprevention-player-claims-bonus]", Integer.toString(pd.getBonusClaimBlocks()));
						}
						if(GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), false, null) != null) {
							claim = GriefPrevention.instance.dataStore.getClaimAt(player.getLocation(), false, null);
							n = n.replace("[griefprevention-position-claim-id]", Long.toString(claim.getID()));
							n = n.replace("[griefprevention-position-claim-owner]", claim.getOwnerName());
							n = n.replace("[griefprevention-position-claim-area]", Integer.toString(claim.getArea()));
							n = n.replace("[griefprevention-position-claim-height]", Integer.toString(claim.getHeight()));
							n = n.replace("[griefprevention-position-claim-width]", Integer.toString(claim.getWidth()));
						}
					} catch(Exception ex) {
						String pluginName = "GriefPrevention";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			
			/*
			 * Autorank
			 */
			if(h.hookAutorank() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.Autorank") == true) {
					try {
						n = n.replace("[autorank-player-time]", Integer.toString(Autorank.getInstance().getAPI().getTimeOfPlayer(player)));
						n = n.replace("[autorank-playtime-global]", Integer.toString(Autorank.getInstance().getAPI().getGlobalPlayTime(player.getUniqueId())));
						n = n.replace("[autorank-playtime-local]", Integer.toString(Autorank.getInstance().getAPI().getLocalPlayTime(player.getUniqueId())));
					} catch(Exception ex) {
						String pluginName = "Autorank";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			

			/*
			 * LibsDisguises
			 */
			if(h.hookLibsDisguises() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.LibsDisguises") == true) {
					try {
						if(DisguiseAPI.isDisguised(player) == true) {

							n = n.replace("[libsdisguises-player-isdisguised]", "True");
							n = n.replace("[libsdisguises-player-disguise]", DisguiseAPI.getDisguise(player).toString());
						} else {
							n = n.replace("[libsdisguises-player-isdisguised]", "False");
						}
					} catch(Exception ex) {
						String pluginName = "LibsDisguises";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}

			
			/*
			 * PlayerPoints
			 */
			if(h.hookPlayerPoints() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.PlayerPoints") == true) {
					try {
						n = n.replace("[playerpoints-points]", Integer.toString((int) h.pp.getAPI().look(player.getUniqueId())));
					} catch(Exception ex) {
						String pluginName = "PlayerPoints";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * PointsAPI
			 */
			if(h.hookPointsAPI() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.PointsAPI") == true) {
					try {
						int i = points.getPoints(player);
						n = n.replace("[pointsapi-points]", Integer.toString(i));
					} catch(Exception ex) {
						String pluginName = "PointsAPI";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * Vault/Iconomy
			 */
			if(h.hookVault() == true) {
				if(h.setupEconomy() == true) {
					if(Files.config11.getBoolean("CTSNC.VARIABLES.Vault") == true) {
						try {
							n = n.replace("[vault-money]", h.eco.format(h.eco.getBalance(player)) + "");
						} catch(Exception ex) {
							String pluginName = "Vault";
							Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
						}
					}
				}
			}
			
			/*
			 * TokenManager
			 */
			if(h.hookTokenManager() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.TokenManager") == true) {
					try {
						if(TMAPI.getTokens(player) >= 0) {
							n = n.replace("[tokenmanager-tokens]", TMAPI.getTokens(player) + "");
						}
					} catch(Exception ex) {
						String pluginName = "TokenManager";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * GroupManager
			 */
			if(h.hookGroupManager() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.GroupManager") == true) {
					try {
						AnjoPermissionsHandler aph = h.groupmanager.getWorldsHolder().getWorldPermissions(player);
						if(aph != null) {
							String groupName = aph.getGroup(player.getName());
							n = n.replace("[groupmanager-group]", groupName);
							n = n.replace("[groupmanager-group-prefix]", aph.getGroupPrefix(groupName));
							n = n.replace("[groupmanager-group-suffix]", aph.getGroupSuffix(groupName));
							n = n.replace("[groupmanager-group-primary]", aph.getPrimaryGroup(player.getName()));
							n = n.replace("[groupmanager-user-prefix]", aph.getUserPrefix(player.getName()));
							n = n.replace("[groupmanager-user-suffix]", aph.getUserSuffix(player.getName()));
						}
					} catch(Exception ex) {
						String pluginName = "GroupManager";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * PermissionEx
			 */
			if(h.hookPermissionEx() == true) {
				try {
					PermissionUser user = PermissionsEx.getUser(player);
				    List<String> groups = user.getParentIdentifiers(player.getWorld().getName());
					String pp = user.getPrefix();
					String ps = user.getSuffix();
					String pg = groups.toString();
					if(Files.config11.getBoolean("CTSNC.VARIABLES.PermissionEx.Formatting") == true) {
						pp = pp.replace("[", "").replace("]", "");
						ps = ps.replace("[", "").replace("]", "");
						pg = pg.replace("[", "").replace("]", "");
						if(pp.length() > 2) {
							pp = pp.substring(0, pp.length() - 2);
						}
					}
				    if(Files.config11.getBoolean("CTSNC.VARIABLES.PermissionEx.Enabled") == true) {
						n = n.replace("[permissionsex-group]", pg).replace("[permissionsex-group-prefix]", pp).replace("[permissionsex-group-suffix]", ps);
				    }
				} catch(Exception ex) {
					String pluginName = "PermissionEx";
					Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
				}
			}
			
			/*
			 * PlaceholderAPI
			 */
			if(h.hookPlaceholderAPI() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.PlaceholderAPI") == true) {
					try {
						n = PlaceholderAPI.setPlaceholders(player, n);
						n = PlaceholderAPI.setBracketPlaceholders(player, n);
					} catch(Exception ex) {
						String pluginName = "PlaceholderAPI";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * PvPLevels
			 */
			if(h.hookPvpLevels() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.PvpLevels") == true && Main.instance.dispatch_command == false) {
					try {
						PvpLevels pl = PvpLevels.instance;
						PvpPlayer pp = pl.getPlayer(player);
						String pvpdeath = Integer.toString(pp.getDeaths());
						String pvpkills = Integer.toString(pp.getKills());
						String pvpkillstreak = Integer.toString(pp.getKillstreak());
						String pvplevel = Integer.toString(pp.getLevel());
						n = n.replace("[pvplevels-deaths]", pvpdeath);
						n = n.replace("[pvplevels-kills]", pvpkills);
						n = n.replace("[pvplevels-killstreak]", pvpkillstreak);
						n = n.replace("[pvplevels-level]", pvplevel); 															
					} catch(Exception ex) {
						String pluginName = "PvpLevels";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * IslandWorld
			 */
			if(h.hookIslandWorld() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.IslandWorld") == true) {
					try {
						n = n.replace("[islandworld-island-have]", Boolean.toString(IslandWorldApi.haveIsland(player.getName())));
						//n = n.replace("[islandworld-island-coordinates]", IslandWorldApi.getIslandCoords(player.getName(), IslandWorldApi.isHelpingIsland(player.getName())).toString());
						//n = n.replace("[islandworld-island-members]", IslandWorldApi.getMembers(player.getName(), IslandWorldApi.isHelpingIsland(player.getName())).toString());
						n = n.replace("[islandworld-island-canBuild]", Boolean.toString(IslandWorldApi.canBuildOnLocation(player, player.getLocation(), IslandWorldApi.isHelpingIsland(player.getName()))));
						n = n.replace("[islandworld-player-points]", Long.toString(IslandWorldApi.getPoints(player.getName(), IslandWorldApi.isHelpingIsland(player.getName()), true)));
					} catch(Exception ex) {
						String pluginName = "IslandWorld";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * CoinsAPI
			 */
			if(h.hookCoinsAPI() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.CoinsAPI") == true) {
					try {
						int coins = CoinsAPI.getCoins(player.getUniqueId().toString());
						n = n.replace("[coinsapi-player-coins]", Integer.toString(coins));
					} catch(Exception ex) {
						String pluginName = "CoinsAPI";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * LuckPerms
			 * https://github.com/lucko/LuckPerms/wiki/Developer-API:-Usage#checking-if-a-player-is-in-a-group
			 */
			if(h.hookLuckPerms() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.LuckPerms") == true) {
					try {
						ServicesManager manager = Bukkit.getServicesManager();
						/*
						 * First API hook for older versions (tested with 4.4.1) of LuckPerms will be used if it fails a newer version (5.1.26) will be used
						 */
						try {
							if (manager.isProvidedFor(LuckPermsApi.class)) {
							    final LuckPermsApi api = manager.getRegistration(LuckPermsApi.class).getProvider();
							    ContextManager cm = api.getContextManager();
							    me.lucko.luckperms.api.User user = api.getUser(player.getUniqueId());
							    
							    user.refreshPermissions();

							    Contexts contexts = cm.lookupApplicableContexts(user).orElse(cm.getStaticContexts());
							    MetaData metaData = user.getCachedData().getMetaData(contexts);
							    me.lucko.luckperms.api.Group group = api.getGroup(user.getPrimaryGroup());
							    
							    try {
								    n = n.replace("[luckperms-group-displayname]", group.getDisplayName());
								    n = n.replace("[luckperms-group-friendlyname]", group.getFriendlyName());
							    } catch (Exception ex) {
							    	if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    		ex.printStackTrace();
							    	}
							    }
							    try {
								    n = n.replace("[luckperms-player-prefix]", metaData.getPrefix());
								    n = n.replace("[luckperms-player-suffix]", metaData.getSuffix());
							    } catch (Exception ex) {
							    	if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    		ex.printStackTrace();
							    	}
								}
							    n = n.replace("[luckperms-player-primaryGroup]", api.getUser(player.getUniqueId()).getPrimaryGroup());
							    if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    	Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §7Old version of LuckPermsAPI is used.");
							    }
							}
						} catch (Error | Exception e) {
							if(manager.isProvidedFor(LuckPerms.class)) {
								LuckPerms lApi = manager.getRegistration(LuckPerms.class).getProvider();
								net.luckperms.api.model.user.User lUser = lApi.getUserManager().getUser(player.getUniqueId());
								net.luckperms.api.context.ContextManager lCm = lApi.getContextManager();
								
								QueryOptions lQueryOptions = lCm.getQueryOptions(lUser).orElse(lCm.getStaticQueryOptions());
								CachedMetaData lMetaData = lUser.getCachedData().getMetaData(lQueryOptions);
								
								net.luckperms.api.model.group.Group lGroup = lApi.getGroupManager().getGroup(lUser.getPrimaryGroup());
								
							    try {
								    n = n.replace("[luckperms-group-displayname]", lGroup.getDisplayName());
								    n = n.replace("[luckperms-group-friendlyname]", lGroup.getFriendlyName());
							    } catch (Exception ex) {
							    	if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    		ex.printStackTrace();
							    	}
							    }
							    try {
								    n = n.replace("[luckperms-player-prefix]", lMetaData .getPrefix());
								    n = n.replace("[luckperms-player-suffix]", lMetaData .getSuffix());
							    } catch (Exception ex) {
							    	if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    		ex.printStackTrace();
							    	}
								}
							    n = n.replace("[luckperms-player-primaryGroup]", lUser.getPrimaryGroup());
								if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
							    	Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §7New version of LuckPermsAPI is used.");
							    }
							}
						}
					} catch(Exception ex) {
						String pluginName = "LuckPerms";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
						if(Main.instance.getConfig().getBoolean("CTSNC.Debug") == true) {
				    		ex.printStackTrace();
				    	}
					}
				}
			}
			/*
			 * SimpleClans
			 */
			if(h.hookSimpleClans() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.SimpleClans") == true) {
					try {
						ClanPlayer cp = h.sc.getClanManager().getClanPlayer(player.getUniqueId());
						Clan c = null;
						if(cp != null) {
							Float kdr_sc = round_float(cp.getKDR());
							c = cp.getClan();
							n = n.replace("[simpleclans-player-joindate]", cp.getJoinDateString());
							n = n.replace("[simpleclans-player-lastseen]", cp.getLastSeenString());
							n = n.replace("[simpleclans-player-rank]", cp.getRank());
							if(cp.getTag() == null) {
								n = n.replace("[simpleclans-player-tag]", "No clan");
							} else {
								n = n.replace("[simpleclans-player-tag]", cp.getTag());
							}
							n = n.replace("[simpleclans-player-flags]", cp.getFlags());
							n = n.replace("[simpleclans-player-kills-neutral]", Integer.toString(cp.getNeutralKills()));
							n = n.replace("[simpleclans-player-kills-civilian]", Integer.toString(cp.getCivilianKills()));
							n = n.replace("[simpleclans-player-kills-rival]", Integer.toString(cp.getRivalKills()));
							n = n.replace("[simpleclans-player-deaths]", Integer.toString(cp.getDeaths()));
							n = n.replace("[simpleclans-player-kdr]", Float.toString(kdr_sc));
							n = n.replace("[simpleclans-player-inactivedays]", Integer.toString(cp.getInactiveDays()));
						}
						if(c != null) {
							Double b = c.getBalance();
							b = round_double(b);
							Float total_kdr_sc = round_float(c.getTotalKDR());
							n = n.replace("[simpleclans-clan-name]", c.getName());
							n = n.replace("[simpleclans-clan-colortag]", c.getColorTag());
							n = n.replace("[simpleclans-clan-capeurl]", c.getCapeUrl());
							n = n.replace("[simpleclans-clan-flags]", c.getFlags());
							n = n.replace("[simpleclans-clan-founded]", c.getFoundedString());
							n = n.replace("[simpleclans-clan-tag]", c.getTag());
							n = n.replace("[simpleclans-clan-balance]", Double.toString(b));
							n = n.replace("[simpleclans-clan-size]", Integer.toString(c.getSize()));
							n = n.replace("[simpleclans-clan-total-neutral]", Integer.toString(c.getTotalNeutral()));
							n = n.replace("[simpleclans-clan-total-civilian]", Integer.toString(c.getTotalCivilian()));
							n = n.replace("[simpleclans-clan-total-rival]", Integer.toString(c.getTotalRival()));
							n = n.replace("[simpleclans-clan-total-deaths]", Integer.toString(c.getTotalDeaths()));
							n = n.replace("[simpleclans-clan-total-kdr]", Float.toString(total_kdr_sc));
						}
					} catch(Exception ex) {
						String pluginName = "SimpleClans";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}		
				}
			}
			
			/*
			 * BungeePerms
			 */
			if(h.hookBungeePerms() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.BungeePerms") == true) {
					try {
						BungeePerms bp = BungeePerms.getInstance();
						PermissionsManager pm = bp.getPermissionsManager();
						User user = null;
//						if(pm.getUser(player.getUniqueId()) != null) {
//							user = pm.getUser(player.getUniqueId());
//						} else {
//							user = pm.getUser(player.getName());
//						}
						user = pm.getUser(player.getName());
						if(user != null) {
							Group pg = pm.getMainGroup(user);
							String prefix = pg.getPrefix();
							String suffix = pg.getSuffix();
							String groupName = pg.getName();
							int rank = pg.getRank();
							n = n.replace("[bungeeperms-group-prefix]", prefix);
							n = n.replace("[bungeeperms-group-suffix]", suffix);
							n = n.replace("[bungeeperms-group-name]", groupName);
							n = n.replace("[bungeeperms-player-rank]", Integer.toString(rank));
						}
					} catch(Exception ex) {
						String pluginName = "BungeePerms";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * RPGLives
			 */
			if(h.hookRPGLives() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.RPGLives") == true) {
					try {
						net.minequests.gloriousmeme.rpglives.Main rpglives = net.minequests.gloriousmeme.rpglives.Main.get();
						n = n.replace("[rpglives-player-lives]", Integer.toString(rpglives.getLives(player)));
						n = n.replace("[rpglives-player-maxlives]", Integer.toString(rpglives.getMaxLives(player)));
						n = n.replace("[rpglives-player-regentime]", Integer.toString(rpglives.getRegenTime(player)));
					} catch(Exception ex) {
						String pluginName = "RPGLives";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * EZBlocks
			 */
			if(h.hookEZBlocksPlugin() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.EZBlocks") == true) {
					try {
						int blocksBroken = EZBlocks.getEZBlocks().getBlocksBroken(player);
						n = n.replace("[ezblocks-player-blocksbroken]", Integer.toString(blocksBroken));
					} catch(Exception ex) {
						String pluginName = "EZBlocks";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			
			/*
			 * ASkyBlock 
			 */
			if(h.hookASkyBlock() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.ASkyBlock") == true && Main.instance.dispatch_command == false) {
					try {
						ASkyBlockAPI asb = ASkyBlockAPI.getInstance();
						UUID puuid = player.getUniqueId();
						try {
							n = n.replace("[askyblock-player-challenge-status]", asb.getChallengeStatus(puuid).toString());
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-player-challenge-times]", asb.getChallengeTimes(puuid).toString());
						} catch(Exception e) {}
						try {
							if(asb.getHomeLocation(puuid) != null) {
								try {
									n = n.replace("[askyblock-player-home-world]", asb.getHomeLocation(puuid).getWorld().getName());
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-home-x]", Double.toString(round_double(asb.getHomeLocation(puuid).getX())));
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-home-y]", Double.toString(round_double(asb.getHomeLocation(puuid).getY())));
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-home-z]", Double.toString(round_double(asb.getHomeLocation(puuid).getZ())));
								} catch(Exception e) {}
							}
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-player-island-name]", asb.getIslandName(puuid));
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-player-island-level]", Integer.toString(asb.getIslandLevel(puuid)));
						} catch(Exception e) {}
						try {
							if(asb.getIslandLocation(puuid) != null) {
								try {
									n = n.replace("[askyblock-player-island-world]", asb.getIslandLocation(puuid).getWorld().getName());
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-island-x]", Double.toString(round_double(asb.getIslandLocation(puuid).getX())));
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-island-y]", Double.toString(round_double(asb.getIslandLocation(puuid).getY())));
								} catch(Exception e) {}
								try {
									n = n.replace("[askyblock-player-island-z]", Double.toString(round_double(asb.getIslandLocation(puuid).getZ())));
								} catch(Exception e) {}
							}
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-islands-world]", asb.getIslandWorld().getName());
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-islands-count]", Integer.toString(asb.getIslandCount()));
						} catch(Exception e) {}
						try {
							if(player.getLocation() != null) {
								n = n.replace("[askyblock-player-location-warpowner]", asb.getWarpOwner(player.getLocation()));
							}
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-player-resetsleft]", Integer.toString(asb.getResetsLeft(puuid)));
						} catch(Exception e) {}
						try {
							n = n.replace("[askyblock-spawn-world]", asb.getSpawnLocation().getWorld().getName());
						} catch(NullPointerException e) {}
						try {
							n = n.replace("[askyblock-spawn-x]", Double.toString(round_double(asb.getSpawnLocation().getX())));
						} catch(NullPointerException e) {}
						try {
							n = n.replace("[askyblock-spawn-y]", Double.toString(round_double(asb.getSpawnLocation().getY())));
						} catch(NullPointerException e) {}
						try {
							n = n.replace("[askyblock-spawn-z]", Double.toString(round_double(asb.getSpawnLocation().getZ())));
						} catch(NullPointerException e) {}
						try {
							n = n.replace("[askyblock-spawn-range]", Integer.toString(asb.getSpawnRange()));
						} catch(Exception e) {}
					} catch(Exception ex) {
						String pluginName = "ASkyBlock";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * 
			 * Towny
			 * 
			 */
			if(h.hookTownyPlugin() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.Towny") == true && Main.instance.dispatch_command == false) {
					try {
						Towny tw = town;
						Resident r = tw.getTownyUniverse().getResident(player.getName());
						if(r.hasTown()) {
							Town t = r.getTown();
							if(t.isCapital()) {
								n = n.replace("[towny-town-isCapital]", "True");
							} else {
								n = n.replace("[towny-town-isCapital]", "False");
							}
							n = n.replace("[towny-town-bonusBlocks]", Integer.toString(t.getBonusBlocks()));
							n = n.replace("[towny-town-bonusBlocksCost]", Double.toString(t.getBonusBlockCost()));
							n = n.replace("[towny-town-commercialPlotPrice]", Double.toString(t.getCommercialPlotPrice()));
							n = n.replace("[towny-town-commercialPlotTax]", Double.toString(t.getCommercialPlotTax()));
							n = n.replace("[towny-town-embassyPlotPrice]", Double.toString(t.getEmbassyPlotPrice()));
							n = n.replace("[towny-town-embassyPlotTax]", Double.toString(t.getEmbassyPlotTax()));
							n = n.replace("[towny-town-economyName]", t.getEconomyName());
							try {
								n = n.replace("[towny-town-holdingBalance]", Double.toString(t.getHoldingBalance()));
							} catch (Exception e) {
							}
							n = n.replace("[towny-town-maxOutpostSpawn]", Integer.toString(t.getMaxOutpostSpawn()));
							n = n.replace("[towny-town-maxJailSpawn]", Integer.toString(t.getMaxJailSpawn()));
							n = n.replace("[towny-town-name]", t.getName());
							n = n.replace("[towny-town-plotPrice]", Double.toString(t.getPlotPrice()));
							n = n.replace("[towny-town-plotTax]", Double.toString(t.getPlotTax()));
							n = n.replace("[towny-town-tag]", t.getTag());
							n = n.replace("[towny-town-taxes]", Double.toString(t.getTaxes()));
							n = n.replace("[towny-town-totalBlocks]", Integer.toString(t.getTotalBlocks()));

							if(t.hasMayor() == true) {
								n = n.replace("[towny-town-mayor]", t.getMayor().getName());
							}
							if(t.hasNation() == true) {
								n = n.replace("[towny-town-nation]", t.getNation().getName());
							}
						}
					} catch(Exception ex) {
						String pluginName = "Towny";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}

			/*
			 * AutoSell
			 */
			if(h.hookAutoSell() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.AutoSell") == true) {
					try {
						AutoSellOptions aso = AutoSellAPI.getAutoSellOptions();
						
						n = n.replace("[autosell-format-items]", aso.getItemsFormat());
						n = n.replace("[autosell-format-thousands]", aso.getThousandsFormat());
						n = n.replace("[autosell-format-millions]", aso.getMillionsFormat());
						n = n.replace("[autosell-format-billions]", aso.getBillionsFormat());
						n = n.replace("[autosell-format-trillions]", aso.getTrillionsFormat());
						n = n.replace("[autosell-format-quadrillions]", aso.getQuadrillionsFormat());
						
						String fortuneMultiplier = format(aso.getFortuneMultiplier());
						String maxMultiplier = format(aso.getMaxMultiplier());
						
						n = n.replace("[autosell-fortune-drops-max]", Integer.toString(aso.getFortuneMaxDrops()));
						n = n.replace("[autosell-fortune-drops-min]", Integer.toString(aso.getFortuneMinDrops()));
						n = n.replace("[autosell-fortune-modifier]", Integer.toString(aso.getFortuneModifier()));
						n = n.replace("[autosell-fortune-multiplier]", fortuneMultiplier);
						
						n = n.replace("[autosell-interval-sellannouncer]", Integer.toString(aso.getSellAnnouncerInterval()));
						n = n.replace("[autosell-interval-ingottoblock]", Integer.toString(aso.getIngotToBlockInterval()));
						
						if(AutoSellAPI.getMultiplier(player) != null) {
							String multiplier = format(AutoSellAPI.getMultiplier(player).getMultiplier());
							n = n.replace("[autosell-player-multiplier]", multiplier);
							n = n.replace("[autosell-player-multiplier-timeleft]", AutoSellAPI.getMultiplier(player).getTimeLeft());
						}
						n = n.replace("[autosell-multiplier-max]", maxMultiplier);

						
						if(AutoSellAPI.getCurrentShop(player) != null) {
							n = n.replace("[autosell-player-shop-name]", AutoSellAPI.getCurrentShop(player).getName());
							n = n.replace("[autosell-player-shop-priority]", Integer.toString(AutoSellAPI.getCurrentShop(player).getPriority()));
							n = n.replace("[autosell-player-shop-size]", Integer.toString(AutoSellAPI.getCurrentShop(player).getShopSize()));
						}
					} catch(Exception ex) {
						String pluginName = "AutoSell";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}

			
			/*
			 * Factions
			 */
			if(h.hookFactions() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.Factions") == true) {
					try {
						MPlayer mp = MPlayer.get(player);
						Faction ft = mp.getFaction();
						String role = "";
						if(mp != null) {	
							role = mp.getRole().toString().toLowerCase();
							n = n.replace("[factions-player-title]", mp.getTitle());
							n = n.replace("[factions-player-role]", WordUtils.capitalize(role));
							n = n.replace("[factions-player-name]", mp.getName());
							n = n.replace("[factions-player-power]", format(mp.getPowerRounded()));
							n = n.replace("[factions-player-power-perhour]", format(mp.getPowerPerHour()));
							n = n.replace("[factions-player-power-perdeath]", format(mp.getPowerPerDeath()));
							n = n.replace("[factions-player-power-min]", Integer.toString(mp.getPowerMinRounded()));
							n = n.replace("[factions-player-power-max-universal]", Integer.toString(mp.getPowerMaxUniversalRounded()));
							n = n.replace("[factions-player-power-max]", Integer.toString(mp.getPowerMaxRounded()));
							n = n.replace("[factions-player-power-boost]", format(mp.getPowerBoost()));
						}
						
						if(ft != null) {
							if(ft.getColl() != null) {
								n = n.replace("[factions-faction-basename]", ft.getColl().getBasename());
							}
							if(ft.hasDescription()) {
								n = n.replace("[factions-faction-description]", ft.getDescription());
							}
							if(ft.hasMotd()) {
								n = n.replace("[factions-faction-motd]", ft.getMotd());
							}
							n = n.replace("[factions-faction-lands]", Integer.toString(ft.getLandCount()));
							if(ft.getLeader() != null) {
								n = n.replace("[factions-faction-leader]", ft.getLeader().getName());
							}
							n = n.replace("[factions-faction-onlineplayers]", Integer.toString(ft.getOnlinePlayers().size()));
							n = n.replace("[factions-faction-power]", Integer.toString(ft.getPowerRounded()));
							n = n.replace("[factions-faction-power-max]", Integer.toString(ft.getPowerMaxRounded()));
							n = n.replace("[factions-faction-power-boost]", format(ft.getPowerBoost()));
							n = n.replace("[factions-faction-name]", ft.getName());
							try {
								n = n.replace("[factions-faction-claimedworlds]", Integer.toString(ft.getClaimedWorlds().size()));
							} catch(NoSuchMethodError e) {
								
							}
							n = n.replace("[factions-faction-players]", Integer.toString(ft.getMPlayers().size()));
						}
					} catch(Exception ex) {
						String pluginName = "Factions";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * McMMMO
			 */
						
			if(h.hookMcMMO() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.McMMO") == true) {
					try {
						String partyName = "";
						String partyMembers = "";
						
						if(player.isOnline() == true) {
							try {
								n = n.replace("[mcmmo-powerlevel-player]", Integer.toString(ExperienceAPI.getPowerLevel(player)));
							} catch (Exception e) {
							}
						} else {
							try {
								n = n.replace("[mcmmo-powerlevel-player]", Integer.toString(ExperienceAPI.getPowerLevelOffline(player.getUniqueId())));
							} catch(Exception e) {
							}
						}
						n = n.replace("[mcmmo-powerlevel-cap]", Integer.toString(ExperienceAPI.getPowerLevelCap()));
						
						try {
							if(PartyAPI.inParty(player)) {
								partyName = PartyAPI.getPartyName(player);
								n = n.replace("[mcmmo-party-name]", partyName);
								n = n.replace("[mcmmo-party-leader]", PartyAPI.getPartyLeader(partyName));
								if(PartyAPI.hasAlly(partyName)) {
									n = n.replace("[mcmmo-party-ally]", PartyAPI.getAllyName(partyName));
								}
								n = n.replace("[mcmmo-party-members]", Integer.toString(PartyAPI.getMembersMap(player).size()));
								n = n.replace("[mcmmo-party-members-online]", Integer.toString(PartyAPI.getOnlineMembers(player).size()));
								for(Player pm : PartyAPI.getOnlineMembers(player)) {
									partyMembers = partyMembers + pm.getDisplayName() + ", ";
								}
								n = n.replace("[mcmmo-party-members-online-list]", partyMembers);
							}
							
							n = n.replace("[mcmmo-chat-admin-isusing]", Boolean.toString(ChatAPI.isUsingAdminChat(player)));
							n = n.replace("[mcmmo-chat-party-isusing]", Boolean.toString(ChatAPI.isUsingPartyChat(player)));
						} catch (Exception e) {
						}
					} catch(Exception ex) {
						String pluginName = "McMMO";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
			
			/*
			 * KOTH 
			 */
			
			
			/*
			 * RPGPlayerLeveling
			 */
			if(h.hookRPGPlayerLeveling() == true) {
				if(Files.config11.getBoolean("CTSNC.VARIABLES.RPGPlayerLeveling") == true && Main.instance.dispatch_command == false) {
					try {
						PlayerList pl = PlayerList.getByName(player.getName());
						if(pl != null) {
							int level = pl.getPlayerLevel();
							int statspoints = pl.getStatPoints();
							int strenght = pl.getStr();
							int stamina = pl.getSta();
							int dexterity = pl.getDex();
							int intellect = pl.getInt();
							int power = me.baks.rpl.api.API.getPower(player);
							int maxpower = me.baks.rpl.api.API.getMaxPower(player);
							int mana = me.baks.rpl.api.API.getMana(player);
							int maxmana = me.baks.rpl.api.API.getMaxMana(player);
							int exp = me.baks.rpl.api.API.getExp(player);
							int maxexp = me.baks.rpl.api.API.getMaxExp(player);

							if(level != 0) {
								n = n.replace("[rpgplayerleveling-level]", Integer.toString(level));
							} else {
								n = n.replace("[rpgplayerleveling-level]", "0");
							}
							if(statspoints != 0) {
								n = n.replace("[rpgplayerleveling-statspoints]", Integer.toString(statspoints));
							} else {
								n = n.replace("[rpgplayerleveling-statspoints]", "0");
							}
							if(strenght != 0) {
								n = n.replace("[rpgplayerleveling-strength]", Integer.toString(strenght));
							} else {
								n = n.replace("[rpgplayerleveling-strength]", "0");
							}
							if(stamina != 0) {
								n = n.replace("[rpgplayerleveling-stamina]", Integer.toString(stamina));
							} else {
								n = n.replace("[rpgplayerleveling-stamina]", "0");
							}
							if(dexterity != 0) {
								n = n.replace("[rpgplayerleveling-dexterity]", Integer.toString(dexterity));
							} else {
								n = n.replace("[rpgplayerleveling-dexterity]", "0");
							}
							if(intellect != 0) {
								n = n.replace("[rpgplayerleveling-intellect]", Integer.toString(intellect));
							} else {
								n = n.replace("[rpgplayerleveling-intellect]", "0");
							}
							if(power != 0) {
								n = n.replace("[rpgplayerleveling-power]", Integer.toString(power));
							} else {
								n = n.replace("[rpgplayerleveling-power]", "0");
							}
							if(maxpower != 0) {
								n = n.replace("[rpgplayerleveling-power-max]", Integer.toString(maxpower));
							} else {
								n = n.replace("[rpgplayerleveling-power-max]", "0");
							}
							if(mana != 0) {
								n = n.replace("[rpgplayerleveling-mana]", Integer.toString(mana));
							} else {
								n = n.replace("[rpgplayerleveling-mana]", "0");
							}
							if(maxmana != 0) {
								n = n.replace("[rpgplayerleveling-mana-max]", Integer.toString(maxmana));
							} else {
								n = n.replace("[rpgplayerleveling-mana-max]", "0");
							}
							if(exp != 0) {
								n = n.replace("[rpgplayerleveling-exp]", Integer.toString(exp));
							} else {
								n = n.replace("[rpgplayerleveling-exp]", "0");
							}
							if(maxexp != 0) {
								n = n.replace("[rpgplayerleveling-exp-max]", Integer.toString(maxexp));
							} else {
								n = n.replace("[rpgplayerleveling-exp-max]", "0");
							}
						}
					} catch(Exception ex) {
						String pluginName = "RPGPlayerLeveling";
						Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §c" + pluginName + " variable failure!");
					}
				}
			}
		}
		
		
		//TimeFormat
		Date d = Calendar.getInstance().getTime();
		if(Main.instance.getConfig().getInt("CTSNC.GMT+") != 0) {
			d.setHours(d.getHours() + Main.instance.getConfig().getInt("CTSNC.GMT+"));
		}
		DateFormat df = new SimpleDateFormat(Main.instance.getConfig().getString("CTSNC.TimeFormat"));
		String t = df.format(d);		
		
		
		//Server properties
		Runtime r = Runtime.getRuntime();
		Long fram = r.freeMemory() / 1048576L;
		Long mram = r.maxMemory() / 1048576L;
		Long uram = (r.totalMemory() - r.freeMemory()) / 1048576L;
		
		
		//Player list
		String players = "";
		for(Player all : Bukkit.getOnlinePlayers()) {
			players = players + all.getDisplayName() + ", ";
		}
		
		
		//Overall
		n = n.replace('&', '§').
		replace("<0>", "█").
		replace("<1>", "❤").
		replace("<2>", "☀").
		replace("<3>", "✯").
		replace("<4>", "☢").
		replace("<5>", "☎").
		replace("<6>", "♫").
		replace("<7>", "❄").
		replace("<8>", "«").
		replace("<9>", "»").
		replace("<10>", "Ω").
		replace("<11>", "☠").
		replace("<12>", "☣").
		replace("<13>", "➥").
		replace("<14>", "➦").
		replace("<15>", "➼").
		replace("<16>", "✪").
		replace("<17>", "✔").
		replace("<18>", "✖").
		replace("<19>", "☯").
		replace("<20>", "☭").
		replace("<21>", "✸").
		replace("<22>", "✈").
		replace("<23>", "●").
	    replace("<24>", "♦").
	    replace("<25>", "☻").
	    replace("<26>", "►").
	    replace("<27>", "◄").
	    replace("<28>", "❄").
	    replace("<29>", "☃").
		//replace("[ctsnc-server-name]", Bukkit.getServerName()).
	    replace("[ctsnc-server-name]", Bukkit.getServer().getName()).	
	    replace("[ctsnc-server-players-online]", Bukkit.getServer().getOnlinePlayers().size() + "").
		replace("[ctsnc-server-players-max]", Bukkit.getMaxPlayers() + "").
		replace("[ctsnc-server-time]", t).
		replace("[ctsnc-server-ram-free]", Long.toString((long) fram)).
		replace("[ctsnc-server-ram-max]", Long.toString((long) mram)).
		replace("[ctsnc-server-ram-used]", Long.toString((long) uram)).
		replace("[ctsnc-server-players-online-list]", players).
		replace("[ctsnc-server-motd]", Bukkit.getServer().getMotd()).
		replace("[ctsnc-nextline]", "\n");
		
		/*
		 * Blocks
		 */
		n = n.replace("<b0>", "┃");
		n = n.replace("<b1>", "▁");
		n = n.replace("<b2>", "▂");
		n = n.replace("<b3>", "▃");
		n = n.replace("<b4>", "▄");
		n = n.replace("<b5>", "▅");
		n = n.replace("<b6>", "▆");
		n = n.replace("<b7>", "▇");
		n = n.replace("<b8>", "█");
		
		
		//Smiley's
		if(Files.config11.getBoolean("CTSNC.VARIABLES.Emojis") == true) {
			String sm1 = Files.config13.getString("CTSNC._:D_");
			String sm2 = Files.config13.getString("CTSNC._:)_");
			String sm3 = Files.config13.getString("CTSNC._;)_");
			String sm4 = Files.config13.getString("CTSNC._:(_");
			String sm5 = Files.config13.getString("CTSNC._<3_");
			if(sm1 != "") {
				n = n.replace(":D", sm1);
			}
			if(sm2 != "") {
				n = n.replace(":)", sm2);
			}
			if(sm3 != "") {
				n = n.replace(";)", sm3);
			}
			if(sm4 != "") {
				n = n.replace(":(", sm4);
			}
			if(sm5 != "") {
				n = n.replace("<3", sm5);
			}
		}
		return n;
	}
	
    public static String GetPlayerCardinalDirection(Player player) {
        String dir = "";
        float y = player.getLocation().getYaw();
        if( y < 0 ){y += 360;}
        y %= 360;
        int i = (int)((y+8) / 22.5);
        if(i == 0){dir = "west";}
        else if(i == 2){dir = "northwest";}
        else if(i == 4){dir = "north";}
        else if(i == 6){dir = "northeast";}
        else if(i == 8){dir = "east";}
        else if(i == 10){dir = "southeast";}
        else if(i == 12){dir = "south";}
        else if(i == 14){dir = "southwest";}
        else {dir = "west";}
        return dir;
    }
    
    public static String format(double input) {
    	double math = input;
    	String finish = "";
    	finish = new DecimalFormat("##.##").format(math);
    	
    	return finish;
    }
    
    public static Double round_double(double input) {
    	double finish = input;
    	finish = Math.round(finish * 100.D) / 100.0D;
    	
    	return finish;
    	
    }
    
    public static Float round_float(float input) {
    	float finish = input;
		finish = Math.round(finish * 100.F) / 100.0F;
		
		return finish;
    }
    
    private static String lowerCaseString(String n) {
    	if(n.startsWith("[") && n.endsWith("]") && n.contains("-")) {
    		n = n.toLowerCase();
    	}
    	return n;
    }
    
    public static void loadConfigurations() {
    	try {
        	Files.config1 = YamlConfiguration.loadConfiguration(Files.f1);
        	Files.config2 = YamlConfiguration.loadConfiguration(Files.f2);
        	Files.config3 = YamlConfiguration.loadConfiguration(Files.f3);
        	Files.config4 = YamlConfiguration.loadConfiguration(Files.f4);
        	Files.config5 = YamlConfiguration.loadConfiguration(Files.f5);
        	Files.config6 = YamlConfiguration.loadConfiguration(Files.f6);
        	Files.config7 = YamlConfiguration.loadConfiguration(Files.f7);
        	Files.config8 = YamlConfiguration.loadConfiguration(Files.f8);
        	Files.config9 = YamlConfiguration.loadConfiguration(Files.f9);
        	Files.config10 = YamlConfiguration.loadConfiguration(Files.f10);
        	Files.config11 = YamlConfiguration.loadConfiguration(Files.f11);
    		Files.config12 = YamlConfiguration.loadConfiguration(Files.f12);
    		Files.config13 = YamlConfiguration.loadConfiguration(Files.f13);
    	} catch(Exception e) {
    		Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §cError while loading configurations!");
    		Bukkit.getConsoleSender().sendMessage("§cBe sure everything is right formatted! (§bYAML | UTF-8)");
    	}
    }

}
