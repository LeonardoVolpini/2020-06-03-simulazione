package it.polito.tdp.PremierLeague.model;

public class PlayerBattuto implements Comparable<PlayerBattuto>{

	Player player;
	Double peso;
	public PlayerBattuto(Player player, Double peso) {
		super();
		this.player = player;
		this.peso = peso;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Double getPeso() {
		return peso;
	}
	public void setPeso(Double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "PlayerBattuto: "+player.toString()+" | "+peso+"\n";
	}
	@Override
	public int compareTo(PlayerBattuto o) {
		return o.getPeso().compareTo(this.getPeso());
	}
	
	
}
