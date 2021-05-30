package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
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
	private boolean grafoCreato;
	
	private List<Player> bestTeam;
	private Integer bestGradoTitolarita;
	
	public Model() {
		this.dao= new PremierLeagueDAO();
		this.idMap= new HashMap<>();
		this.grafoCreato=false;
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
			if(grafo.vertexSet().contains(a.getP1()) && grafo.vertexSet().contains(a.getP2()) && a.getPeso()!=0) {
				if (a.getPeso()<0) //arco da p2 a p1
					Graphs.addEdgeWithVertices(grafo, a.getP2(), a.getP1(), ((double)-1)*a.getPeso() );
				else //arco da p1 a p2
					Graphs.addEdgeWithVertices(grafo, a.getP1(), a.getP2(), a.getPeso() );
			}
		}
		this.grafoCreato=true;
		System.out.println("Grafo Creato");
		System.out.println("#vertici: "+grafo.vertexSet().size());
		System.out.println("#archi: "+grafo.edgeSet().size());
	}
	
	public BestPlayer getTopPlayer() {
		if (grafo==null) {
			this.grafoCreato=false;
			return null;
		}
		Player best=null;
		double max=0;
		List<PlayerBattuto> battuti=new ArrayList<>();
		for (Player p : grafo.vertexSet()) {
			double n=grafo.outDegreeOf(p);
			if (n>max) {
				best=p;
				max=n;
				for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(p)) {
					PlayerBattuto b = new PlayerBattuto(grafo.getEdgeTarget(e),grafo.getEdgeWeight(e));
					battuti.add(b);
				}
			}
		}
		Collections.sort(battuti);
		return new BestPlayer(best,battuti);
	}
	
	public int getNumVertici() {
		return grafo.vertexSet().size();
	}
	
	public int getNumArchi() {
		return grafo.edgeSet().size();
	}
	
	public void dreamTeam(int k) {
		if (grafo==null)
			this.grafoCreato=false;
		this.bestGradoTitolarita=0;
		this.bestTeam= new ArrayList<>();
		List<Player> parziale= new ArrayList<>();
		
		cercaDreamTeam(parziale, new ArrayList<Player>(grafo.vertexSet()), k);
	}
	
	private void cercaDreamTeam(List<Player> parziale, List<Player> giocatori, int k ) {
		if(parziale.size()==k) { //ho raggiunto il numero di giocatori per il DreamTeam
			int degree= this.getDegreeList(parziale);
			if (degree>this.bestGradoTitolarita) {
				this.bestTeam=new ArrayList<>(parziale);
				this.bestGradoTitolarita=degree;
			}
			return;
		}
		for (Player p : giocatori) {
			if (!parziale.contains(p)) {
				parziale.add(p);
				List<Player> rimasti= new ArrayList<>(giocatori);
				rimasti.removeAll(Graphs.successorListOf(grafo, p));
				cercaDreamTeam(parziale,rimasti,k);
				
				//backtracking:
				parziale.remove(p);
				
			}
		}
	}
	
	private int getDegreeList(List<Player> parziale) {
		int tot=0;
		int in;
		int out;
		for (Player p : parziale) {
			in=0;
			out=0;
			for (DefaultWeightedEdge e : grafo.outgoingEdgesOf(p))
				out += grafo.getEdgeWeight(e);
			for (DefaultWeightedEdge e : grafo.incomingEdgesOf(p))
				in += grafo.getEdgeWeight(e);
			tot += (out-in);
		}
		return tot;
	}
	
	public String stampaDreamTeam() {
		String s="Grado di titolarità del team: "+this.bestGradoTitolarita+"\n";
		for (Player p: this.bestTeam) {
			s += p.toString()+"\n";
		}
		return s;
	}
	
	public boolean isGrafoCreato() {
		return this.grafoCreato;
	}
}
