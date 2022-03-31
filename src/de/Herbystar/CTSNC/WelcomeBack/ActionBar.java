package de.Herbystar.CTSNC.WelcomeBack;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.ReplaceString;
import de.Herbystar.CTSNC.Scroller;
import de.Herbystar.TTA.TTA_Methods;

public class ActionBar {
	
	static int scheduleid;
	
	//ActionBar Animated & Normal
	public static void sendWelcomeBackActionbar(final Player player) {
		
		boolean enabled = Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Enabled");
		boolean enabled_normal = Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Normal.Enabled");
		boolean enabled_animated = Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Animated.Enabled");
		
		String content_normal = ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.WELCOME_BACK.ActionBar.Normal.Content"), player);
		
		
		if(enabled == true) {
			if(enabled_normal == false && enabled_animated == true)  {
				scheduleid  = scheduleid +1;
				int displayduration = Main.instance.getConfig().getInt("CTSNC.WELCOME_BACK.ActionBar.Animated.DisplayDuration");
			    scheduleid = Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.instance, new Runnable() {
				    int width = Main.instance.getConfig().getInt("CTSNC.WELCOME_BACK.ActionBar.Animated.MaxLenght");
				    int spaceBetween = Main.instance.getConfig().getInt("CTSNC.WELCOME_BACK.ActionBar.Animated.SpaceBetweenStartAndEnd");
				    String content_animated = ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.WELCOME_BACK.ActionBar.Animated.Content"), player);
					Scroller ActionBar = new Scroller(content_animated, width, spaceBetween, '§');

					@Override
					public void run() {
						TTA_Methods.sendActionBar(player, ActionBar.next());
					}										
				}
				, 0L, 3L);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.instance, new Runnable() {

					@Override
					public void run() {
						Bukkit.getScheduler().cancelTask(scheduleid);									
					}									
				}
				,displayduration);
			} else {
				if(Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Normal.Enabled") == true && Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Animated.Enabled") == false)  {
					TTA_Methods.sendActionBar(player, content_normal);
				}
				if(Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Animated.Enabled") == true && Main.instance.getConfig().getBoolean("CTSNC.WELCOME_BACK.ActionBar.Normal.Enabled") == true)  {
					Bukkit.getConsoleSender().sendMessage("§c[§eCTSNC§c] §6>> §cYou can't activate the animated and normal mode of the actionbar at the same time!");
				}
			}
		}
	}

}
