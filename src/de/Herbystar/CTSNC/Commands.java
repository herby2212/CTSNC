package de.Herbystar.CTSNC;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import de.Herbystar.CTSNC.Files.Files;
import de.Herbystar.CTSNC_Modules.NameTag.CustomTags;
import de.Herbystar.CTSNC_Modules.Scoreboard.Scoreboards;
import de.Herbystar.TTA.TTA_Methods;

public class Commands implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd,String label, String[] args) {		
		int fadeint = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.FadeIn");
		int stayt = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.Stay");
		int fadeoutt = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.FadeOut");
				
		int fadeinst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.FadeIn");
		int stayst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.Stay");
		int fadeoutst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.FadeOut");
		
		String moduleMissing = Files.config6.getString("CTSNC.MODULES.Missing");
		
		Modules m = new Modules();
		
		Player p = null;
		if(sender instanceof Player) {
			p = (Player) sender;
			
			//Help / Default command
			if(cmd.getName().equalsIgnoreCase("CTS")) {
				if(args.length == 0) {
					if(p.hasPermission("CTSNC.CMD.Default")) {
						p.sendMessage("§c[]=================[§e§lCTSNC§c]=================[]");
						p.sendMessage("");
						p.sendMessage("§a§l<--- Player Commands --->");
						p.sendMessage("");
						p.sendMessage("§e /CTS reload §8-> §cReload the config!");
						p.sendMessage("§e /CTS actionbar §c<message> §8-> §cSend all players on your server this message via the actionbar!  §lUse §e§l'_' §c§lfor §e§l' '§c§l!");
						p.sendMessage("§e /CC §cme/all §8-> §cClear your chat or from all players!");
						p.sendMessage("§e /CTS time §8-> §cShow the time of the server system!");
						p.sendMessage("§e /CTS ping §8-> §cShow your current ping to the server!");
						p.sendMessage("§e /CTS AFK §8-> §cToggle your AFK mode!");
						p.sendMessage("§e /CTS setspawn §8-> §cSet the default spawnpoint!");
						p.sendMessage("§e /CTS spawn §8-> §cTeleport you to the spawnpoint!");
						p.sendMessage("§e /CTS stats §8-> §cShow your current stats!");
						p.sendMessage("§e /CTS staffchat §c<message> §8-> §cChat only with Staff members!");
						p.sendMessage("§e /CTS stopwatch §cstart§e/§cstop §8-> §cStart/Stop your personal stopwatch!");
						p.sendMessage("§e /CTS me §cscoreboard §8-> §cToggle your scoreboard!");
						p.sendMessage("§e /CTS me §ctablist §8-> §cToggle your tablist!");
						p.sendMessage("");
						p.sendMessage("§a§l<--- Console/Player Commands --->");
						p.sendMessage("");
						p.sendMessage("§e /CTS tps §8-> §cShow the current ticks per seconds!");
						p.sendMessage("§e /CTS hooks §8-> §cShow a list of all enabled & disabled hooks");
						p.sendMessage("§e /CTS scoreboard §8-> §cToggle the scoreboard ingame! (Need a server restart)");
						p.sendMessage("§e /CTS tablist §8-> §cToggle the tablist ingame! (Need a server restart)");
						p.sendMessage("§e /CTS chat §8-> §cEnable/disable the chat! ");
						p.sendMessage("§e /CTS cc §cme/all §8-> §cClear your chat or from all players on the server!");
						p.sendMessage("§e /CTS customtag <taglist(1-20)> set <PlayerName> §8-> §cSet the tag for a player!");
						p.sendMessage("§e /CTS title §a<player/global> §c<title> <subtitle> §8-> §cSend a title to all or a single player! §lUse §e§l'_' §c§lfor §e§l' '§c§l!");
						p.sendMessage("§c[]=================[§e§lCTSNC§c]=================[]");
						return true;
					} else {
						if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
							p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
							return true;
						}
					}
				}
				//Stats
				if(args.length == 1) {
					if(args[0].equalsIgnoreCase("Chat")) {
						if(p.hasPermission("CTSNC.CMD.Chat")) {
							if(Files.config4.getBoolean("CTSNC.CHAT.BlockChat") == false) {
								Files.config4 = YamlConfiguration.loadConfiguration(Files.f4);
								Files.config4.set("CTSNC.CHAT.BlockChat", true);
								p.sendMessage(Main.instance.prefix + "§eThe chat is now §aenabled§e!");
								return true;
							} else {
								Files.config4 = YamlConfiguration.loadConfiguration(Files.f4);
								Files.config4.set("CTSNC.CHAT.BlockChat", false);
								p.sendMessage(Main.instance.prefix + "§eThe chat is now §cdisabled§e!");
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					if(args[0].equalsIgnoreCase("Stats")) {
						if(p.hasPermission("CTSNC.CMD.Stats")) {
							if(m.StatsModule() == true) {
								int deaths = Files.config5.getInt("CTSNC." + p.getUniqueId() + ".Deaths");
								int kills = Files.config5.getInt("CTSNC." + p.getUniqueId() + ".Kills");
								double kd = (double)kills / (double)deaths;
								double kdr = Math.round(kd * 100.D) / 100.0D;
								
								p.sendMessage("§c[]======[§e§lCTSNC§c]======[]");
								p.sendMessage("§eKills: §c" + kills);
								p.sendMessage("§eDeaths: §c" + deaths);
								p.sendMessage("§eKD: §c" + kdr);
								p.sendMessage("§c[]======[§e§lCTSNC§c]======[]");
								return true;
							} else {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Plugin reload
					if(args[0].equalsIgnoreCase("reload")) {
						if(p.hasPermission("CTSNC.CMD.Reload")) {
							Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§eReloading configuration...");
							Main.instance.reloadConfig();
							ReplaceString.loadConfigurations();
							Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§aConfiguration reloaded!");
							Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§eRe-starting Processes...");
							if(m.ScoreboardModule() == true) {
								if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == true) {
									new Scoreboards(p);
								}
							}
							Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§aProcesses started!");
							p.sendMessage(Main.instance.prefix + "§aConfiguration reloaded!");
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Set Spawn
					if(args[0].equalsIgnoreCase("setspawn")) {
						if(p.hasPermission("CTSNC.CMD.Spawn")) {
							Files.config8.set("CTSNC.Spawnpoint.World", p.getWorld().getName());
							Files.config8.set("CTSNC.Spawnpoint.X", Double.valueOf(p.getLocation().getX()));
							Files.config8.set("CTSNC.Spawnpoint.Y", Double.valueOf(p.getLocation().getY()));
							Files.config8.set("CTSNC.Spawnpoint.Z", Double.valueOf(p.getLocation().getZ()));
							Files.config8.set("CTSNC.Spawnpoint.Yaw", Double.valueOf(p.getLocation().getYaw()));
							Files.config8.set("CTSNC.Spawnpoint.Pitch", Double.valueOf(p.getLocation().getPitch()));
							try {
								Files.config8.save(Files.f8);
							} catch (IOException e) {
								e.printStackTrace();
							}
							p.sendMessage(Main.instance.prefix + "§aThe §cSpawnpoint §awas successfully set!");
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Teleport to Spawn
					if(args[0].equalsIgnoreCase("spawn")) {
						if(p.hasPermission("CTSNC.CMD.Spawn")) {							
							if(Files.f8.exists()) {
								World w = Bukkit.getWorld(Files.config8.getString("CTSNC.Spawnpoint.World"));
								double x = Files.config8.getDouble("CTSNC.Spawnpoint.X");
								double y = Files.config8.getDouble("CTSNC.Spawnpoint.Y");
								double z = Files.config8.getDouble("CTSNC.Spawnpoint.Z");
								double yaw = Files.config8.getDouble("CTSNC.Spawnpoint.Yaw");
								double pitch = Files.config8.getDouble("CTSNC.Spawnpoint.Pitch");
								p.teleport(new Location(w, x, y, z, (float) yaw, (float) pitch));
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.Spawn.Enabled") == true) {
									p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.Spawn.Content"), p));
								}
								return true;
							} else {
								if(p.isOp()) {
									p.sendMessage(Main.instance.prefix + "§c§lYou need to set the Spawn location!");
									return true;
								}
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//AFK Toggle
					if(args[0].equalsIgnoreCase("AFK")) {
						if(p.hasPermission("CTSNC.CMD.AFK")) {
							if(!Main.instance.AFK.contains(p.getUniqueId())) {
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.AFK.Broadcast.Enabled") == true) {
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Broadcast.Content_Enabled"), p));
									}
								}
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.AFK.Private.Enabled") == true) {
									p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Private.Content_Enabled"), p));
								}
								Main.instance.AFK.add(p.getUniqueId());
								return true;
							} else {
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.AFK.Broadcast.Enabled") == true) {
									for(Player all : Bukkit.getOnlinePlayers()) {
										all.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Broadcast.Content_Disabled"), p));
									}
								}
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.AFK.Private.Enabled") == true) {
									p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.AFK.Private.Content_Disabled"), p));
								}
								Main.instance.AFK.remove(p.getUniqueId());
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Ping
					if(args[0].equalsIgnoreCase("ping")) {
						if(p.hasPermission("CTSNC.CMD.Ping")) {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.Ping.Broadcast.Enabled") == true) {
								for(Player all : Bukkit.getOnlinePlayers()) {
									all.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.Ping.Broadcast.Content"), p));
								}
							}
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.Ping.Private.Enabled") == true) {
								p.sendMessage(ReplaceString.replaceString(Main.instance.prefix + Main.instance.getConfig().getString("CTSNC.MESSAGES.Ping.Private.Content"), p));
							}
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//TPS
					if(args[0].equalsIgnoreCase("tps")) {
						if(p.hasPermission("CTSNC.CMD.TPS")) {
							String tps = new DecimalFormat("##.##").format(TTA_Methods.getTPS(100));
							p.sendMessage(Main.instance.prefix + "§aYour current TPS: §4" + tps + "§a!");
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Hooks
					if(args[0].equalsIgnoreCase("hooks")) {
						if(p.hasPermission("CTSNC.CMD.Hooks")) {
							new Hooks().SendHooks(p);
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//System time
					if(args[0].equalsIgnoreCase("time")) {
						if(p.hasPermission("CTSNC.CMD.Time")) {
							Date d = Calendar.getInstance().getTime();
							DateFormat df = new SimpleDateFormat(Main.instance.getConfig().getString("CTSNC.TimeFormat"));
							String t = df.format(d);
							p.sendMessage(Main.instance.prefix + "§aThe current time is: §4" + t + "§a!");
							return true;
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Scoreboard toggle
					if(args[0].equalsIgnoreCase("scoreboard")) {
						if(p.hasPermission("CTSNC.CMD.Scoreboard")) {
							if(m.ScoreboardModule() == true) {
								if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == false) {
									Files.config1.set("CTSNC.SCOREBOARD.Enabled", true);
									try {
										Files.config1.save(Files.f1);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(Main.instance.prefix + "§eYou §aenabled §ethe scoreboard!");
									return true;
								} else {
									Files.config1.set("CTSNC.SCOREBOARD.Enabled", false);
									try {
										Files.config1.save(Files.f1);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(Main.instance.prefix + "§eYou §cdisabled §ethe scoreboard!");
									return true;
								}
							} else {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//tablist toggle
					if(args[0].equalsIgnoreCase("tablist")) {
						if(p.hasPermission("CTSNC.CMD.Tablist")) {
							if(m.TablistModule() == true) {
								if(Files.config3.getBoolean("CTSNC.TABLIST.Enabled") == false) {
									Files.config3.set("CTSNC.TABLIST.Enabled", true);
									try {
										Files.config3.save(Files.f3);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(Main.instance.prefix + "§eYou §aenabled §ethe tablist!");
									return true;
								} else {
									Files.config3.set("CTSNC.TABLIST.Enabled", false);
									try {
										Files.config3.save(Files.f3);
									} catch (IOException e) {
										e.printStackTrace();
									}
									p.sendMessage(Main.instance.prefix + "§eYou §cdisabled §ethe tablist!");
									return true;
								}
							} else {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
				}
				//Stafchat
				if(args.length >= 2) {
					if(args[0].equalsIgnoreCase("staffchat")) {
						if(p.hasPermission("CTSNC.CHAT.Staff")) {
							if(m.ChatModule() == true) {
								String StaffChatFormat = ReplaceString.replaceString(Files.config4.getString("CTSNC.CHAT.StaffChatFormat"), p);
								if(args[1] != null) {
									String msg = "";
							        for(int i = 1; i < args.length; i++) {
							            String arg = (args[i] + " ");
							            msg = (msg + arg);
							        }
									for(Player all : Bukkit.getOnlinePlayers()) {
										if(all.hasPermission("CTSNC.CHAT.Staff")) {
											all.sendMessage(StaffChatFormat + ReplaceString.replaceString(msg, p));										
										}
									}
									return true;
								} else {
									p.sendMessage(Main.instance.prefix + "§cYou need to set a message!");
									return true;
								}
							} else {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
				}
				if(args.length == 2) {
					//Chatclear
					if(args[0].equalsIgnoreCase("cc")) {
						if(p.hasPermission("CTSNC.CMD.ChatClear")) {
							if(m.ChatModule() == true) {
								int chat_clear_size = Files.config4.getInt("CTSNC.CHAT.ChatClear.Size");
								if(args[1].equalsIgnoreCase("me")) {
									for(int i = 0; i <= chat_clear_size; i++) {
										p.sendMessage("");
									}
									return true;
								}
								if(args[1].equalsIgnoreCase("all")) {
									for(int i = 0; i <= chat_clear_size; i++) {
										for(Player players : Bukkit.getOnlinePlayers()) {
											players.sendMessage("");
										}
									}
									if(Files.config4.getBoolean("CTSNC.CHAT.ChatClearMessage.Enabled") == true) {
										Bukkit.getServer().broadcastMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config4.getString("CTSNC.CHAT.ChatClearMessage.Content"), p));
									}
									return true;
								}
							} else {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Stopwatch
					final Player p1 = p;
					if(args[0].equalsIgnoreCase("stopwatch")) {
						if(p.hasPermission("CTSNC.CMD.Stopwatch")) {
							if(args[1].equalsIgnoreCase("start")) {
								if(ReplaceString.stopwatch.get(p1.getUniqueId()) == null) {
									BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Main.instance, new Runnable() {

										@Override
										public void run() {
											if(ReplaceString.stopwatch_time.get(p1.getUniqueId()) == null && ReplaceString.stopwatch_time_minutes.get(p1.getUniqueId()) == null && ReplaceString.stopwatch_time_hours.get(p1.getUniqueId()) == null) {
												ReplaceString.stopwatch_time.put(p1.getUniqueId(), 1);
												ReplaceString.stopwatch_time_minutes.put(p1.getUniqueId(), 0);
												ReplaceString.stopwatch_time_hours.put(p1.getUniqueId(), 0);
											} else {
												int time = ReplaceString.stopwatch_time.get(p1.getUniqueId());
												int minutes = ReplaceString.stopwatch_time_minutes.get(p1.getUniqueId());
												int hours = ReplaceString.stopwatch_time_hours.get(p1.getUniqueId());
												if(time < 60) {
													time += 1;
												} else {
													time = 1;
													if(minutes < 60) {
														minutes += 1;
													} else {
														minutes = 1;
														if(hours < 60) {
															hours += 1;
														} else {
															time = 1;
															minutes = 0;
															hours = 0;
														}
													}
												}
												ReplaceString.stopwatch_time.remove(p1.getUniqueId());
												ReplaceString.stopwatch_time_minutes.remove(p1.getUniqueId());
												ReplaceString.stopwatch_time_hours.remove(p1.getUniqueId());
												ReplaceString.stopwatch_time.put(p1.getUniqueId(), time);
												ReplaceString.stopwatch_time_minutes.put(p1.getUniqueId(), minutes);
												ReplaceString.stopwatch_time_hours.put(p1.getUniqueId(), hours);
											}
										}									
									}
									, 20L, 20L);
									ReplaceString.stopwatch.put(p1.getUniqueId(), task);
									return true;
								} else {
									p1.sendMessage("§c[§eCTSNC§c] §c§lYou can start the stopwatch only one time!");
									return true;
								}
							}
							if(args[1].equalsIgnoreCase("stop")) {
								if(ReplaceString.stopwatch.get(p1.getUniqueId()) != null) {
									ReplaceString.stopwatch.get(p1.getUniqueId()).cancel();
									ReplaceString.stopwatch.remove(p1.getUniqueId());
									ReplaceString.stopwatch_time.remove(p1.getUniqueId());
									ReplaceString.stopwatch_time_minutes.remove(p1.getUniqueId());
									ReplaceString.stopwatch_time_hours.remove(p1.getUniqueId());
									return true;
								} else {
									p1.sendMessage("§c[§eCTSNC§c] §c§lNo stopwatch to stop!");
									return true;
								}
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					
					//ActionBar broadcast
					if(args[0].equalsIgnoreCase("actionbar")) {
						if(p.hasPermission("CTSNC.CMD.ActionBar")) {
							for(Player players : Bukkit.getOnlinePlayers()) {
								TTA_Methods.sendActionBar(players, ReplaceString.replaceString(args[1], p).replace("_", " "));
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					
					//Me commands
					if(args[0].equalsIgnoreCase("me")) {
						if(args[1].equalsIgnoreCase("scoreboard")) {
							if(p.hasPermission("CTSNC.CMD.Me.Scoreboard")) {
								if(m.ScoreboardModule() == true) {
									if(de.Herbystar.CTSNC_Modules.Scoreboard.Main.boards.containsKey(p)) {
										(de.Herbystar.CTSNC_Modules.Scoreboard.Main.boards.get(p)).remove();
										p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config6.getString("CTSNC.COMMANDS.Cts_Me_Scoreboard"), p));
										return true;
									} else {
										new Scoreboards(p);
										if(m.NameTagModule() == true) {
											CustomTags ct = new CustomTags();
											ct.setCustomTags(p);
										}										
										p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config6.getString("CTSNC.COMMANDS.Cts_Me_Scoreboard"), p));
										return true;
									}
								} else {
									p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
									return true;
								}
							} else {
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
									p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
									return true;
								}
							}
						}
						
						if(args[1].equalsIgnoreCase("tablist")) {
							if(p.hasPermission("CTSNC.CMD.Me.Tablist")) {
								if(m.TablistModule() == true) {
									if(Main.Tablists.contains(p)) {
										Main.Tablists.remove(p);
										p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config6.getString("CTSNC.COMMANDS.Cts_Me_Tablist"), p));
										return true;
									} else {
										Main.Tablists.add(p);
										p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config6.getString("CTSNC.COMMANDS.Cts_Me_Tablist"), p));
										return true;
									}
								} else {
									p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
									return true;
								}
							} else {
								if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
									p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
									return true;
								}
							}
						}
						
					}
				}
				
				if(args.length == 3) {
					//Title broadcast§
					if(args[0].equalsIgnoreCase("title")) {
						if(p.hasPermission("CTSNC.CMD.Title")) {
							if(args[1].equalsIgnoreCase("global")) {
								for(Player players : Bukkit.getOnlinePlayers()) {
									TTA_Methods.sendTitle(players, ReplaceString.replaceString(args[2], p).replace("_", " "), fadeint, stayt, fadeoutt, null, fadeinst, stayst, fadeoutst);
								}
								return true;
							} else {
								Player receiver = Bukkit.getPlayer(args[1]);
								if(receiver.isOnline()) {
									TTA_Methods.sendTitle(receiver, ReplaceString.replaceString(args[2], p).replace("_", " "), fadeint, stayt, fadeoutt, null, fadeinst, stayst, fadeoutst);
								} else {
									p.sendMessage(Main.instance.prefix + "§c§lRequested player not found!");
								}
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
				}
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("customtag") | args[0].equalsIgnoreCase("ct")) {
						if(p.hasPermission("CTSNC.CMD.CustomTags")) {
							if(args[2].equalsIgnoreCase("set")) {
								Files.config7 = YamlConfiguration.loadConfiguration(Files.f7);
								Player p1 = (Player) Bukkit.getOfflinePlayer(args[3]);
								UUID uuid = p1.getUniqueId();
								int tag = Files.config7.getInt("CTSNC.CustomTag." + uuid.toString());
								if(tag == Integer.parseInt(args[1])) {
									p.sendMessage(Main.instance.prefix + "§cThe player contains already to this list!");
									return true;
								}
								Files.config7.set("CTSNC.CustomTag." + uuid.toString(), Integer.parseInt(args[1]));
								Files.config7.set("CTSNC.CustomTag." + args[3], Integer.parseInt(args[1]));
								try {
									Files.config7.save(Files.f7);
								} catch (IOException e) {
									e.printStackTrace();
								}
								p.sendMessage(Main.instance.prefix + "§aYou set the CustomTag for the Player §c" + args[3] +  "§a!");
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
					//Title broadcast
					if(args[0].equalsIgnoreCase("title")) {
						if(p.hasPermission("CTSNC.CMD.Title")) {
							if(args[1].equalsIgnoreCase("global")) {
								for(Player players : Bukkit.getOnlinePlayers()) {
									TTA_Methods.sendTitle(players, ReplaceString.replaceString(args[2], p).replace("_", " "), fadeint, stayt, fadeoutt, ReplaceString.replaceString(args[3], p).replace("_", " "), fadeinst, stayst, fadeoutst);
								}
								return true;
							} else {
								Player receiver = Bukkit.getPlayer(args[1]);
								if(receiver.isOnline()) {
									TTA_Methods.sendTitle(receiver, ReplaceString.replaceString(args[2], p).replace("_", " "), fadeint, stayt, fadeoutt, ReplaceString.replaceString(args[3], p).replace("_", " "), fadeinst, stayst, fadeoutst);
								} else {
									p.sendMessage(Main.instance.prefix + "§c§lRequested player not found!");
								}
								return true;
							}
						} else {
							if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
								p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
								return true;
							}
						}
					}
				}

			}
			if(Main.instance.getConfig().getBoolean("CTSNC.ShortChatClearCmd") == true) {
				if(cmd.getName().equalsIgnoreCase("cc")) {
					if(p.hasPermission("CTSNC.CMD.ChatClear")) {
						if(m.ChatModule() == true) {
							if(args.length == 0) {
								p.sendMessage("§e /CC §cme/all §8-> §cClear your chat or from all players!");
								return true;
							}
							if(args.length == 1) {
								int chat_clear_size = Files.config4.getInt("CTSNC.CHAT.ChatClear.Size");
								if(args[0].equalsIgnoreCase("me")) {
									for(int i = 0; i <= chat_clear_size; i++) {
										p.sendMessage("");
									}
									return true;
								}
								if(args[0].equalsIgnoreCase("all")) {
									for(int i = 0; i <= chat_clear_size; i++) {
										for(Player players : Bukkit.getOnlinePlayers()) {
											players.sendMessage("");
										}
									}
									if(Files.config4.getBoolean("CTSNC.CHAT.ChatClearMessage.Enabled") == true) {
										Bukkit.getServer().broadcastMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config4.getString("CTSNC.CHAT.ChatClearMessage.Content"), p));
									}
									return true;
								}
							}
						} else {
							p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, p));
							return true;
						}
					} else {
						if(Main.instance.getConfig().getBoolean("CTSNC.MESSAGES.NoPermission.Enabled") == true) {
							p.sendMessage(Main.instance.prefix + ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.MESSAGES.NoPermission.Content"), p));
							return true;
						}
					}
				}
			}
		} else {
			if(cmd.getName().equalsIgnoreCase("CTS")) {
				if(args.length == 0) {
					sender.sendMessage("§c[]=================[§e§lCTSNC§c]=================[]");
					sender.sendMessage("");
					sender.sendMessage("§a§l<--- Player Commands --->");
					sender.sendMessage("");
					sender.sendMessage("§e /CTS reload §8-> §cReload the config!");
					sender.sendMessage("§e /CTS actionbar §c<message> §8-> §cSend all players on your server this message via the actionbar!  §lUse §e§l'_' §c§lfor §e§l' '§c§l!");
					sender.sendMessage("§e /CC §cme/all §8-> §cClear your chat or from all players!");
					sender.sendMessage("§e /CTS time §8-> §cShow the time of the server system!");
					sender.sendMessage("§e /CTS ping §8-> §cShow your current ping to the server!");
					sender.sendMessage("§e /CTS AFK §8-> §cToggle your AFK mode!");
					sender.sendMessage("§e /CTS setspawn §8-> §cSet the default spawnpoint!");
					sender.sendMessage("§e /CTS spawn §8-> §cTeleport you to the spawnpoint!");
					sender.sendMessage("§e /CTS stats §8-> §cShow your current stats!");
					sender.sendMessage("§e /CTS staffchat §c<message> §8-> §cChat only with Staff members!");
					sender.sendMessage("§e /CTS stopwatch §cstart§e/§cstop §8-> §cStart/Stop your personal stopwatch!");
					sender.sendMessage("§e /CTS me §cscoreboard §8-> §cToggle your scoreboard!");
					sender.sendMessage("§e /CTS me §ctablist §8-> §cToggle your tablist!");
					sender.sendMessage("");
					sender.sendMessage("§a§l<--- Console/Player Commands --->");
					sender.sendMessage("");
					sender.sendMessage("§e /CTS tps §8-> §cShow the current ticks per seconds!");
					sender.sendMessage("§e /CTS hooks §8-> §cShow a list of all enabled & disabled hooks");
					sender.sendMessage("§e /CTS scoreboard §8-> §cToggle the scoreboard ingame! (Need a server restart)");
					sender.sendMessage("§e /CTS tablist §8-> §cToggle the tablist ingame! (Need a server restart)");
					sender.sendMessage("§e /CTS chat §8-> §cEnable/disable the chat! ");
					sender.sendMessage("§e /CTS cc §cme/all §8-> §cClear your chat or from all players on the server!");
					sender.sendMessage("§e /CTS customtag <taglist(1-20)> set <PlayerName> §8-> §cSet the tag for a player!");
					sender.sendMessage("§e /CTS title §a<player/global> §c<title> <subtitle> §8-> §cSend a title to all or a single player! §lUse §e§l'_' §c§lfor §e§l' '§c§l!");
					sender.sendMessage("§c[]=================[§e§lCTSNC§c]=================[]");
					return true;
				}
				if(args.length == 1) {
					//TPS
					if(args[0].equalsIgnoreCase("tps")) {
						String tps = new DecimalFormat("##.##").format(TTA_Methods.getTPS(100));
						sender.sendMessage(Main.instance.prefix + "§aYour current TPS: §4" + tps + "§a!");
						return true;
					}
					//Hooks
					if(args[0].equalsIgnoreCase("hooks")) {
						new Hooks().SendHooks(null);
						return true;
					}
					//Scoreboard
					if(args[0].equalsIgnoreCase("scoreboard")) {
						if(m.ScoreboardModule() == true) {
							if(Files.config1.getBoolean("CTSNC.SCOREBOARD.Enabled") == false) {
								Files.config1.set("CTSNC.SCOREBOARD.Enabled", true);
								try {
									Files.config1.save(Files.f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
								sender.sendMessage(Main.instance.prefix + "§eYou §aenabled §ethe scoreboard!");
								return true;
							} else {
								Files.config1.set("CTSNC.SCOREBOARD.Enabled", false);
								try {
									Files.config1.save(Files.f1);
								} catch (IOException e) {
									e.printStackTrace();
								}
								sender.sendMessage(Main.instance.prefix + "§eYou §cdisabled §ethe scoreboard!");
								return true;
							}
						} else {
							sender.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, null));
							return true;
						}
					}
					//Tablist
					if(args[0].equalsIgnoreCase("tablist")) {
						if(m.TablistModule() == true) {
							if(Files.config3.getBoolean("CTSNC.TABLIST.Enabled") == false) {
								Files.config3.set("CTSNC.TABLIST.Enabled", true);
								try {
									Files.config3.save(Files.f3);
								} catch (IOException e) {
									e.printStackTrace();
								}
								sender.sendMessage(Main.instance.prefix + "§eYou §aenabled §ethe tablist!");
								return true;
							} else {
								Files.config3.set("CTSNC.TABLIST.Enabled", false);
								try {
									Files.config3.save(Files.f3);
								} catch (IOException e) {
									e.printStackTrace();
								}
								sender.sendMessage(Main.instance.prefix + "§eYou §cdisabled §ethe tablist!");
								return true;
							}
						} else {
							sender.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, null));
							return true;
						}
					}
					//Chat
					if(args[0].equalsIgnoreCase("Chat")) {
						if(Files.config4.getBoolean("CTSNC.CHAT.BlockChat") == false) {
							Files.config4 = YamlConfiguration.loadConfiguration(Files.f4);
							Files.config4.set("CTSNC.CHAT.BlockChat", true);
							sender.sendMessage(Main.instance.prefix + "§eThe chat is now §cdisabled§e!");
							return true;
						} else {
							Files.config4 = YamlConfiguration.loadConfiguration(Files.f4);
							Files.config4.set("CTSNC.CHAT.BlockChat", false);
							sender.sendMessage(Main.instance.prefix + "§eThe chat is now §aenabled§e!");
							return true;
						}
					}
				}
				//ChatClear
				if(args.length == 2) {
					int chat_clear_size = Files.config4.getInt("CTSNC.CHAT.ChatClear.Size");
					if(args[0].equalsIgnoreCase("cc")) {
						if(m.ChatModule() == true) {
							if(args[1].equalsIgnoreCase("me")) {
								sender.sendMessage(Main.instance.prefix + ReplaceString.replaceString("§cYou cant execute this command as console! \n Use instead: /cts cc all", null));
								return true;
							}
							if(args[1].equalsIgnoreCase("all")) {
								for(int i = 0; i <= chat_clear_size; i++) {
									for(Player players : Bukkit.getOnlinePlayers()) {
										players.sendMessage("");
									}
								}
								if(Files.config4.getBoolean("CTSNC.CHAT.ChatClearMessage.Enabled") == true) {
									Bukkit.getServer().broadcastMessage(Main.instance.prefix + ReplaceString.replaceString(Files.config4.getString("CTSNC.CHAT.ChatClearMessage.Content"), null));
								}
								return true;
							}
						} else {
							sender.sendMessage(Main.instance.prefix + ReplaceString.replaceString(moduleMissing, null));
							return true;
						}
					}
				}
				if(args.length == 3) {
					//Title broadcast
					if(args[0].equalsIgnoreCase("title")) {
						if(args[1].equalsIgnoreCase("global")) {
							for(Player players : Bukkit.getOnlinePlayers()) {
								TTA_Methods.sendTitle(players, ReplaceString.replaceString(args[2], players).replace("_", " "), fadeint, stayt, fadeoutt, null, fadeinst, stayst, fadeoutst);
							}
							return true;
						} else {
							Player receiver = Bukkit.getPlayer(args[1]);
							if(receiver.isOnline()) {
								TTA_Methods.sendTitle(receiver, ReplaceString.replaceString(args[2], receiver).replace("_", " "), fadeint, stayt, fadeoutt, null, fadeinst, stayst, fadeoutst);
							} else {
								Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§c§lRequested player not found!");
							}
							return true;
						}
					}
				}
				//Set CustomTags & Title Broadcast
				if(args.length == 4) {
					if(args[0].equalsIgnoreCase("customtag") | args[0].equalsIgnoreCase("ct")) {
						if(args[2].equalsIgnoreCase("set")) {
							Files.config7 = YamlConfiguration.loadConfiguration(Files.f7);
							Player p1 = (Player) Bukkit.getOfflinePlayer(args[3]);
							UUID uuid = p1.getUniqueId();
							int tag = Files.config7.getInt("CTSNC.CustomTag." + uuid.toString());
							if(tag == Integer.parseInt(args[1])) {
								sender.sendMessage(Main.instance.prefix + "§c§lThe player contains already to this list!");
								return true;
							}
							Files.config7.set("CTSNC.CustomTag." + uuid.toString(), Integer.parseInt(args[1]));
							Files.config7.set("CTSNC.CustomTag." + args[3], Integer.parseInt(args[1]));
							try {
								Files.config7.save(Files.f7);
							} catch (IOException e) {
								e.printStackTrace();
							}
							sender.sendMessage(Main.instance.prefix + "§aYou set the CustomTag for the Player §6" + args[3] +  "§a!");
							return true;
						}
					}
					if(args[0].equalsIgnoreCase("title")) {
						if(args[1].equalsIgnoreCase("global")) {
							for(Player players : Bukkit.getOnlinePlayers()) {
								TTA_Methods.sendTitle(players, ReplaceString.replaceString(args[2], players).replace("_", " "), fadeint, stayt, fadeoutt, ReplaceString.replaceString(args[3], players).replace("_", " "), fadeinst, stayst, fadeoutst);
							}
							return true;
						} else {
							Player receiver = Bukkit.getPlayer(args[1]);
							if(receiver.isOnline()) {
								TTA_Methods.sendTitle(receiver, ReplaceString.replaceString(args[2], receiver).replace("_", " "), fadeint, stayt, fadeoutt, ReplaceString.replaceString(args[3], receiver).replace("_", " "), fadeinst, stayst, fadeoutst);
							} else {
								Bukkit.getConsoleSender().sendMessage(Main.instance.prefix + "§c§lRequested player not found!");
							}
							return true;
						}
					}
				}
			}
//			Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] You need to be a player!");
//			return true;
		}
		return false;
	}

}
