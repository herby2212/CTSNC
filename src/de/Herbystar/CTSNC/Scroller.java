package de.Herbystar.CTSNC;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Scroller {
	
	Main plugin;
	public Scroller(Main main) {
		plugin = main;
	}
	
	public char Colour_char = '§';
	public int position;
	public List<String> l;
	public ChatColor cl = ChatColor.RESET;
	
	public Scroller(String message, int width, int spaceBetween, char colourChar) {
		l = new ArrayList<String>();
		
		if(message.length() < width) {
			StringBuilder sb = new StringBuilder(message);
			while(sb.length() < width) {
				sb.append(" ");
			}
			message.toString();
		}
		
		width -= 2;
		if(width < 1) {
			width = 1;
		}
		
		if(spaceBetween < 0) {
			spaceBetween = 0;
		}
		
		if(colourChar != '§') {
			message = ChatColor.translateAlternateColorCodes(colourChar, message);
		}
		
		for(int i = 0; i < message.length() - width; i++) {
			l.add(message.substring(i, i + width));
		}
		
		StringBuilder sp = new StringBuilder();
		for(int i = 0; i < spaceBetween; ++i) {
			l.add(message.substring(message.length() - width + (i > width ? width : i), message.length()) + sp);
			if(sp.length() < width) {
				sp.append(" ");
			}
			
		}
		
		for(int i = 0; i < width - spaceBetween; i++) {
			if(i > sp.length()) {
				break;
			}
			l.add(sp.substring(0, sp.length() - i) + message.substring(0, width - (spaceBetween > width ? width : spaceBetween) + i));
		}
	}
	
	public String next() {
		StringBuilder sb = getNext();
		if(sb.charAt(sb.length() - 1) == Colour_char) {
			sb.setCharAt(sb.length() - 1, ' ');
		}
		if(sb.charAt(0) == Colour_char) {
			ChatColor c = ChatColor.getByChar(sb.charAt(1));
			if(c != null) {
				cl = c;
				sb = getNext();
				if(sb.charAt(0) != ' ') {
					sb.setCharAt(0, ' ');
				}
			}
		}
		return cl + sb.toString();
	}
	
	public StringBuilder getNext() {
		return new StringBuilder(l.get(position++ % l.size()).substring(0));
	}

}
