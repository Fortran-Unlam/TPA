package Core;

import Posicion.Posicion;

public class Fruta 
{
	private Posicion pos;
	
	//La fruta se va a crear teniendo una posicion.
	public Fruta(Posicion p)
	{
		this.pos = p;
	}

	public Posicion getPos() 
	{
		return pos;
	}

	public void setPos(Posicion pos) 
	{
		this.pos = pos;
	}
	
}
