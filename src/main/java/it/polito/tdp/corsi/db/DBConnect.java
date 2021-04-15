package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Questa classe serve solo a fornire la Connection per il DAO ed è l'unica a sapere la stringa jdbcURL
 * @author gianmarianavissano
 *
 */
public class DBConnect {
	
	public static Connection getConnection() throws SQLException {
		String jdbcURL = "jdbc:mysql://localhost/iscritticorsi?user=root&password=s257883s";
		return DriverManager.getConnection(jdbcURL);
	}

}
