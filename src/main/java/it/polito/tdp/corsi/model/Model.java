package it.polito.tdp.corsi.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.corsi.db.CorsoDAO;

public class Model {
	private CorsoDAO corsoDAO;
	
	public Model() {
		corsoDAO = new CorsoDAO();
	}
	
	public List<Corso> getCorsiByPeriodo(Integer pd){
		return corsoDAO.getCorsiByPeriodo(pd);
	}
	
	public Map<Corso, Integer> getIscrittiByPeriodo(Integer pd){
		return corsoDAO.getIscrittiByPeriodo(pd);
	}
	
	public List<Studente> getStudentiByCorso(String codice){
		return corsoDAO.getStudentiByCorso(new Corso(codice, null, null, null));
	}
	
	public Map<String, Integer> getDivisioneCDS (String codice) {
		Map<String, Integer> divisione = new HashMap<String, Integer>();
		List<Studente> studenti = this.getStudentiByCorso(codice);
		for(Studente s : studenti) {
			if(divisione.get(s.getCDS()) == null) {
				divisione.put(s.getCDS(), 1);
			}else {
				divisione.put(s.getCDS(), divisione.get(s.getCDS()) + 1);
			}
		}
		return divisione;
	}

	public boolean esisteCorso(String codice) {
		// TODO Auto-generated method stub
		return corsoDAO.esisteCorso(new Corso(codice, null, null, null));
	}
}
