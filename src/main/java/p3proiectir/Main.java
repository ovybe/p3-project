package p3proiectir;

import java.awt.Color;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import com.mysql.cj.jdbc.MysqlDataSource;

/**
 * Adds tables to db, starts app
 */
public class Main {
	/**
	 * Tries to add tables to the db, if they don't exist and starts the app
	 */
public static void main(String[] args){
	GUI app = new GUI();
	String f;
	if((f=Actions.SQLOnLaunch())!="0") {
		JOptionPane.showMessageDialog(app, f);
		System.exit(1);
	}
	app.setVisible(true);
	app.setLocationRelativeTo(null);
	Color backgroundcolor=new Color(189, 190, 197);
	app.getContentPane().setBackground(backgroundcolor);
	URL iconURL = null;
	iconURL= Main.class.getResource("atm.png");
	ImageIcon icon = new ImageIcon(iconURL);
	app.setIconImage(icon.getImage());
	app.setTitle("ATM Login");
}		
}
