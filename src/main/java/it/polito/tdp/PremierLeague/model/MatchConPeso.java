package it.polito.tdp.PremierLeague.model;

public class MatchConPeso {
	
	Match m;
	Integer peso;
	public MatchConPeso(Match m, Integer peso) {
		super();
		this.m = m;
		this.peso = peso;
	}
	public Match getM() {
		return m;
	}
	public void setM(Match m) {
		this.m = m;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	
	

}
