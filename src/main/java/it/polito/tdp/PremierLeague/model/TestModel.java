package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {
		
		Model m = new Model();
		m.creaGrafo(5, 10);
		List<Adiacenza>aTemp = m.getConnMax(10);
		
		for(Adiacenza a : aTemp) {
			System.out.println(a.toString());
		}
		
	}

}
