package it.polito.tdp.PremierLeague.model;

import java.util.List;

public class BestPlayer {
	
	Player p;
	List<PlayerBattuto> avversariBattuti;
	
	public BestPlayer(Player p, List<PlayerBattuto> avversariBattuti) {
		this.p = p;
		this.avversariBattuti = avversariBattuti;
	}

	public Player getP() {
		return p;
	}

	public void setP(Player p) {
		this.p = p;
	}

	public List<PlayerBattuto> getAvversariBattuti() {
		return avversariBattuti;
	}

	public void setAvversariBattuti(List<PlayerBattuto> avversariBattuti) {
		this.avversariBattuti = avversariBattuti;
	}

	@Override
	public String toString() {
		return "BestPlayer: "+p.toString()+"\nAVVERSARI BATTUTI:\n"+ avversariBattuti;
	}
	
}
