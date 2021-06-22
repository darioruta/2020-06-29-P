package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;



public class Model {
	
	private PremierLeagueDAO dao;
	private Map<Integer, Match> idMap;
	private Graph<Match, DefaultWeightedEdge> grafo;
	private boolean esito;
	private List<Match> percorsoMigliore;
	private int pesoMax;
	
	public Model() {
		this.dao = new PremierLeagueDAO();
		this.esito = false;
	
	}
	
	public void creaGrafo(int mese, int minuti) {
		
		this.grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.idMap= new HashMap<>();
		this.esito= true;
		this.dao.getVertici(idMap, mese);
		for (Match m : this.idMap.values()) {
			this.grafo.addVertex(m);
		}
		
		List <Adiacenza> archi = this.dao.getArchi(this.idMap, mese, minuti);
		
		for (Adiacenza a : archi) {
			if (this.grafo.containsVertex(a.getM1()) && this.grafo.containsVertex(a.getM2())) {
					Graphs.addEdgeWithVertices(this.grafo, a.getM1(), a.getM2(), a.getPeso());
				}
		}
		
		/*String res = "#Vertici: "+this.grafo.vertexSet().size()+"\n#Archi: " + this.grafo.edgeSet().size();
		System.out.println("#Vertici " + this.grafo.vertexSet().size());
		System.out.println("#Archi " + this.grafo.edgeSet().size());
		return res;*/
		}
			
	public int getVertexSize() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdgeSize() {
		return this.grafo.edgeSet().size();
	}
	
	
	
	public List<Adiacenza> getConnMax(int min){
		
		List<Adiacenza> res = new LinkedList<Adiacenza>();
		
		for (DefaultWeightedEdge e : this.grafo.edgeSet()) {
			if(this.grafo.getEdgeWeight(e)>= min) {
				Adiacenza aTemp = new Adiacenza (this.grafo.getEdgeSource(e), this.grafo.getEdgeTarget(e), (int) this.grafo.getEdgeWeight(e));
				res.add(aTemp);
				
			}
				
		}
		
		return res;
		
	}
	
	public boolean isGrafoCreato() {
		return esito;
	}

	public Set<Match> getVertexSet() {
		
		return this.grafo.vertexSet();
	}
	

	public List<Match> cercaPercorso(Match partenza, Match arrivo){
		
		this.percorsoMigliore = new ArrayList<Match>();
		this.pesoMax=0;
		List<Match> parziale = new ArrayList<Match>();
		parziale.add(partenza);
		cerca(parziale,partenza ,arrivo);
		
		
		
		return percorsoMigliore;
	}

	private void cerca(List<Match> parziale,Match partenza, Match arrivo) {
	
		if(parziale.get(parziale.size()-1).equals(arrivo)) {
			if(calcolaLunghezza(parziale)> pesoMax) {
				pesoMax = calcolaLunghezza(parziale);
				this.percorsoMigliore = new ArrayList<>(parziale);
			}
		}
		for(Match mm : Graphs.successorListOf(this.grafo, parziale.get(parziale.size()-1))){
			if(!parziale.contains(mm)) {
				parziale.add(mm);
				cerca(parziale, partenza, arrivo);
				parziale.remove(parziale.size()-1);
			}
		}
		
	}

	private int calcolaLunghezza(List<Match> parziale) {
		
		int somma=0;
		
		for(int i=0;i<parziale.size()-1; i++) {
			somma+= this.grafo.getEdgeWeight(this.grafo.getEdge(parziale.get(i), parziale.get(i+1)));
		}
		
		return somma;
	}
	
	public int getPesoMax() {
		return this.pesoMax;
	}
	
	
	
	
	
}
