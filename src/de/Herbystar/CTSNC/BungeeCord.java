package de.Herbystar.CTSNC;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeCord implements PluginMessageListener {
	
	public Main plugin;
	
	public BungeeCord(Main plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
        if (!channel.equals("BungeeCord")) {
        	return;         
        }
        
        try {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
                String command = in.readUTF();                 
                if (command.equals("PlayerCount")) {
                        String server = in.readUTF();
                        int playerCount = in.readInt(); 
                        Main.playerCounts.put(server, playerCount);                          
                }
        } catch (Exception e) {
                e.printStackTrace();
        }
	}
	
	public static void getPlayerCount(String server) {
		try {
			ByteArrayOutputStream b = new ByteArrayOutputStream();
			DataOutputStream out = new DataOutputStream(b);
			out.writeUTF("PlayerCount");
			out.writeUTF(server);
			Bukkit.getServer().sendPluginMessage(Main.instance, "BungeeCord", b.toByteArray());
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
