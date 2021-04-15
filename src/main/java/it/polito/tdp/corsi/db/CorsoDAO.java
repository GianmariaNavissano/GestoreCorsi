package it.polito.tdp.corsi.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.model.Corso;
import it.polito.tdp.corsi.model.Studente;

public class CorsoDAO {

	public List<Corso> getCorsiByPeriodo(Integer periodo){
		//Definisco la stringa con la Query
		String sql = "select * "
				+ "from corso "
				+ "where pd = ?";
		//Creo la struttura per contenere il risultato
		List<Corso> result = new ArrayList<Corso>();
		
		//Blocco try catch per agire sul database
		try {
			//Prendo la connection chiedendola a DBConnect
			Connection conn = DBConnect.getConnection();
			//Creo un PreparedStatement utilizzando la query
			PreparedStatement st = conn.prepareStatement(sql);
			//Imposto il parametro ? con la variabile periodo ricevuta dal model
			st.setInt(1, periodo);
			//Ora eseguo la query
			ResultSet rs = st.executeQuery();
			
			//Ora vado a leggere il ResultSet
			while(rs.next()) {
				Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
				result.add(c);
			}
			rs.close();
			st.close();
			conn.close();
			return result;
		}catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		//Definisco la stringa con la Query
				String sql = "select c.codins, c.nome, c.crediti, c.pd, count(*) as tot "
						+ "from corso c, iscrizione i "
						+ "where c.codins = i.codins and c.pd = ? "
						+ "group by c.codins, c.nome, c.crediti, c.pd";
				//Creo la struttura per contenere il risultato
				Map<Corso, Integer> result = new HashMap<Corso, Integer>();
				
				//Blocco try catch per agire sul database
				try {
					//Prendo la connection chiedendola a DBConnect
					Connection conn = DBConnect.getConnection();
					//Creo un PreparedStatement utilizzando la query
					PreparedStatement st = conn.prepareStatement(sql);
					//Imposto il parametro ? con la variabile periodo ricevuta dal model
					st.setInt(1, pd);
					//Ora eseguo la query
					ResultSet rs = st.executeQuery();
					
					//Ora vado a leggere il ResultSet
					while(rs.next()) {
						Corso c = new Corso(rs.getString("codins"), rs.getInt("crediti"), rs.getString("nome"), rs.getInt("pd"));
						Integer n = rs.getInt("tot");
						result.put(c, n);
					}
					rs.close();
					st.close();
					conn.close();
					return result;
				}catch (SQLException e) {
					throw new RuntimeException(e);
				}
	}
	
	public List<Studente> getStudentiByCorso(Corso corso){
		String sql = "SELECT s.matricola, s.cognome, s.nome, s.CDS"
				+ " FROM studente s, iscrizione i"
				+ " WHERE s.matricola = i.matricola AND i.codins= ?";
		
		List<Studente> studenti = new LinkedList<Studente>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1,  corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			while(rs.next()) {
				Studente s = new Studente(rs.getInt("matricola"), rs.getString("cognome"), rs.getString("nome"), rs.getString("CDS"));
				studenti.add(s);
			}
			
			rs.close();
			st.close();
			conn.close();
			
			return studenti;
			
		}catch(SQLException e) {
			throw new RuntimeException ("Error db", e);
		}
	}

	public boolean esisteCorso(Corso corso) {
		// TODO Auto-generated method stub
		String sql = "SELECT * FROM corso WHERE codins = ?";
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			
			st.setString(1, corso.getCodins());
			ResultSet rs = st.executeQuery();
			
			if(rs.next()) {
				rs.close();
				st.close();
				conn.close();
				return true;
			}
			else {
				rs.close();
				st.close();
				conn.close();
				return false;
			}
		}catch(SQLException e) {
			throw new RuntimeException ("Error db", e);
		}
	}
	
	
	
}
