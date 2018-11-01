package core;

import java.util.LinkedList;

import core.entidad.Obstaculo;

public class Muro {
	
	private LinkedList<Obstaculo> piedras = new LinkedList<>();

	public Muro(LinkedList<Obstaculo> piedras) {
		this.piedras = piedras;
	}

	public LinkedList<Obstaculo> getPiedras() {
		return piedras;
	}
	
	

}
