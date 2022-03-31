package de.Herbystar.CTSNC.Join;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import de.Herbystar.CTSNC.Main;
import de.Herbystar.CTSNC.ReplaceString;
import de.Herbystar.TTA.TTA_Methods;

public class Titles {
	
	//Titles
	public static void sendOnJoinTitles(final Player player) {
		
		final int fadeint = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.FadeIn");
		final int stayt = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.Stay");
		final int fadeoutt = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Title.Times.FadeOut");
		
		final int fadeinst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.FadeIn");
		final int stayst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.Stay");
		final int fadeoutst = Main.instance.getConfig().getInt("CTSNC.ONJOIN.Titles.Subtitle.Times.FadeOut");
		
		final String title_content = ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.ONJOIN.Titles.Title.Content"), player);
		final String subtitle_content = ReplaceString.replaceString(Main.instance.getConfig().getString("CTSNC.ONJOIN.Titles.Subtitle.Content"), player);
		
		boolean enabled = Main.instance.getConfig().getBoolean("CTSNC.ONJOIN.Titles.Enabled");
		
		if(enabled == true) {
			new BukkitRunnable() {
				
				@Override
				public void run() {
					TTA_Methods.sendTitle(player, title_content, fadeint, stayt, fadeoutt, 
							subtitle_content, fadeinst, stayst, fadeoutst);					
				}
			}.runTaskLaterAsynchronously(Main.instance, 5);

		}
	}

}
