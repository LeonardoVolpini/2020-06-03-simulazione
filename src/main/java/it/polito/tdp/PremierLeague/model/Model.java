package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {

	private SimpleDirectedWeightedGraph<Player,DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer,Player> idMap;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.idMap= new HashMap<>();
		
	}
	
	public void creaGrafo(double x) {
		this.grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		this.dao.listAllPlayers(idMap);
		/*List<Player> vertici= new ArrayList<>();
		for (Player p : idMap.values()) {
			dao.setInfoPlayer(p);
			if (p.getMediaGoal()>x) { //credo sia >= ma risultato giusto con solo >
				vertici.add(p);
			}			
		}
		Graphs.addAllVertices(grafo, vertici);*/
		Graphs.addAllVertices(grafo, dao.getVertici(x, idMap));
		for (Adiacenza a : dao.getAdiacenze(idMap)) {
			if(grafo.vertexSet().contains(a.getP1()) && grafo.vertexSet().contains(a.getP2()) ) {
				if (a.getPeso()<0) //arco da p2 a p1
					Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), ((double)-1)*a.getPeso() );
				else //arco da p1 a p2
					Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso() );
			}
		}
		
		System.out.println("Grafo Creato");
		System.out.println("#vertici: "+grafo.vertexSet().size());
		System.out.println("#archi: "+grafo.edgeSet().size());
	}
	
	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	
}
