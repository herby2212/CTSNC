package de.Herbystar.CTSNC;

import java.text.DecimalFormat;
import org.bukkit.entity.Player;

public class WorldTime {
	
	public static String getWorldTime(Player player) {		
		Double ticks = (double) player.getWorld().getTime();
		Double t1 = 24000d;
		Double t2 = 1140d;
		Double h1 = 60d;
		Double h2 = 120d;
		Double h3 = 180d;
		Double h4 = 240d;
		Double h5 = 300d;
		Double h6 = 360d;
		Double hour = 60d;
		Double translator = t1 / t2;
		Double t = 0d;
		
		if(ticks < 1000 && ticks >= 0) {
			t = (ticks / translator) + (h6);
		}
		if(ticks < 2000 && ticks >= 1000) {
			t = (ticks / translator) + (h6 + h1);
		}
		if(ticks < 6000 && ticks >= 2000) {
			t = (ticks / translator) + (h6);
		}
		if(ticks < 7000 && ticks >= 6000) {
			t = (ticks / translator) + (h6 + h2);
		}
		if(ticks < 9000 && ticks >= 7000) {
			t = (ticks / translator) + (h6 + h1);
		}
		if(ticks < 13000 && ticks >= 9000) {
			t = (ticks / translator) + (h6 + h2);
		}
		if(ticks <= 16000 && ticks >= 13000) {
			t = (ticks / translator) + (h6 + h3);
		}
		if(ticks <= 22000 && ticks > 16000) {
			t = (ticks / translator) + (h6 + h4);
		}
		if(ticks < 24000 && ticks > 22000) {
			t = (ticks / translator) + (h6 + h5);
		}
		
		Double time = t / hour;
		if(time > 24d) {
			time = time - 24d;
		}
		
		double minutes = time % 1;
		double hours = time - minutes;
		
		minutes = (minutes / 100) * 60;
		
		time = hours + minutes;
		
		String final_time = new DecimalFormat("#00.00").format(time);
		final_time = final_time.replace(",", ":").replace(".", ":");
		
		return final_time;
	}
}
