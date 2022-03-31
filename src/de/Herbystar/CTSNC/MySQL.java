package de.Herbystar.CTSNC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import de.Herbystar.CTSNC.Files.Files;

public class MySQL {
	
	private String host;
	private int port;
	private String user;
	private String password;
	private String database;
	
	private Connection con;	
	
	public MySQL() throws Exception {
		this.host = Files.config9.getString("CTSNC.MySQL.host");
		this.port = Files.config9.getInt("CTSNC.MySQL.port");
		this.user = Files.config9.getString("CTSNC.MySQL.user");
		this.password = Files.config9.getString("CTSNC.MySQL.password");
		this.database = Files.config9.getString("CTSNC.MySQL.database");
		this.OpenConnection();
	}
	
	public Connection OpenConnection() throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://" + this.host + ":" + this.port + "/" + this.database, this.user, this.password);
		this.con = con;
		return con;
	}
	
	public Connection getConnection() {
		return this.con;
	}
	
	public boolean hasConnection() {
		try {
			return this.con != null || this.con.isValid(1);
		} catch (SQLException ex) {
			return false;
		}
	}
	
	public void closeRessources(ResultSet rs, PreparedStatement st) {
		if(rs != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
		}
		if(st != null) {
			try {
				rs.close();
			} catch (SQLException ex) {
			}
		}
	}
	
	public void closeConnetion() {
		try {
			this.con.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		this.con = null;
	}
	
	public void queryUpdate(String query) {
		Connection con = this.con;
		PreparedStatement st = null;
		try {
			st = con.prepareStatement(query);
			st.executeUpdate();
		} catch (SQLException ex) {
			System.err.println("Failed to send update '" + query + "'!");
		}
	}

}
