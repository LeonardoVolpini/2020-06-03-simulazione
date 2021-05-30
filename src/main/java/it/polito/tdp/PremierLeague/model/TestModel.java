package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class TestModel {

	public static void main(String[] args) {

		Model m= new Model();
		m.creaGrafo(0.5);
		System.out.println(m.getTopPlayer());
		//m.creaGrafo(0.3);
		//System.out.println(m.getTopPlayer());
		m.dreamTeam(3);
		System.out.print(m.stampaDreamTeam());
	}

}
