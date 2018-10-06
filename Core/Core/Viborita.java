package Core;

import java.util.ArrayList;

import Posicion.Posicion;

public class Viborita 
{
	private String color;
	private boolean viva;
	private int direccionActual; // Direccion de la cabeza de la viborita.
	/* 0 representa la direccion creciente del eje X.
	 * 1 representa la direccion creciente del eje Y.
	 * 2 representa la direccion decreciente del eje X.
	 * 3 representa la direccion decreciente del eje Y.
	 */
	private ArrayList<Posicion> cuerpo = new ArrayList<>();
	private Tablero tablero; // Tablero al que pertenece la viborita.
	/* Se guarda la posicion de la cola antes de realizar el desplazamiento.
	* ya que si luego de realizar el desplazamiento la vibora ingiere una fruta.
	* crece de tamano, es decir su nuevaCola seria su viejaCola.
	*/
	private Posicion viejaCola;
	
	//Constructor por defecto de Viborita.
	public Viborita(String color, ArrayList<Posicion> defecto, int direcciondefecto)
	{
		this.color = color;
		this.setViva(true);
		this.cuerpo = defecto;
		this.direccionActual = direcciondefecto;
		this.viejaCola = defecto.get(defecto.size()-1);
	}
	
	//Este metodo desplaza la viborita teniendo en cuenta la direccionActual de la misma.
	public void desplazar()
	{
		//Si la viborita esta muerta, no se realiza nada.
		if(this.viva == false)
			return;
		Posicion antiguaCabeza = this.cuerpo.get(0);
		Posicion nuevaCabeza = null;
		switch(this.direccionActual)
		{
			case 0:
				nuevaCabeza = new Posicion(antiguaCabeza.x+1, antiguaCabeza.y);
				break;
			case 1:
				nuevaCabeza = new Posicion(antiguaCabeza.x, antiguaCabeza.y+1);
				break;
			case 2:
				nuevaCabeza = new Posicion(antiguaCabeza.x-1, antiguaCabeza.y);
				break;
			case 3:
				nuevaCabeza = new Posicion(antiguaCabeza.x, antiguaCabeza.y-1);
				break;
		}
		//Agrego la nueva cabeza, una posicion adelante de la vieja cabeza.
		this.cuerpo.add(0, nuevaCabeza);
		//Agrego su vieja cola, por si la llegase a necesitar.
		this.viejaCola = this.cuerpo.get(this.cuerpo.size()-1);
		//Remuevo la vieja cola, la nueva cola seria el anteultimo elemento.
		this.cuerpo.remove(this.cuerpo.size()-1);
	}
	
	/* Este metodo evalua los posibles conflictos de esta viborita en particular.
	 * Retornos:
	 * 0 la vibora murio / estaba muerta.
	 * 1 la vibora crecio, por ende comio una fruta.
	 * 2 la vibora se desplazo sin problemas.
	*/
	public int evaluarConflictoParticular()
	{
		//Si la viborita esta muerta, no se realiza nada.
		if(this.viva == false)
			return 0;
		//Si choco contra otro espacio ocupado por otra viborita.
		if(this.tablero.getEspacioOcupadoPorViboritas(this).contains(this.cuerpo.get(0)))
			return 0;
		//Se separa para que no este todo junto es lo mismo ponerlo en el mismo if con un OR.
		else if(this.tablero.getFueraLimite(this))
			return 0;
		//Si la cabeza de la viborita se topo con una fruta.
		else if(this.tablero.getEspacioOcupadoPorFrutas().contains(this.cuerpo.get(0)))
			return 1;
		return 2;	
	}
	
	//Este metodo cambia la direccionActual de la viborita antes de su proximo desplazamiento.
	public void cambiarDireccion(int direccion)
	{
		//Si la viborita esta muerta no se realiza nada.
		if(this.viva == false)
			return;
		//Si estoy avanzando en una direccion, y eligo la misma direccion, no pasa nada.
		if(direccion == this.direccionActual)
			return;
		//No puedo avanzar en una direccion contraria a 180 grados.
		if(Math.abs(direccion-this.direccionActual) == 2)
			return;
		this.direccionActual = direccion;
	}

	//SETTERS NECESARIOS.
	
	public void setViva(boolean viva) 
	{
		this.viva = viva;
	}
	
	public void setTablero(Tablero t)
	{
		this.tablero = t;
	}
	
	/*Este metodo hacer crecer a la viborita un segmento, y se lo agrega donde estaba su vieja cola
	en el instante de tiempo anterior a comer la fruta.*/
	public void hacerCrecer()
	{
		//Es size aca no size-1.
		this.cuerpo.add(this.cuerpo.size(), viejaCola);
	}

	//GETTERS NECESARIOS.
	
	public int getDireccionActual() 
	{
		return direccionActual;
	}
	
	public int getLongitud()
	{
		return this.cuerpo.size();
	}
	
	public boolean isViva() 
	{
		return viva;
	}
	
	/*Este metodo es utilizado por tablero para saber las posiciones que
	  ocupa la viborita dentro del tablero.*/
	public ArrayList<Posicion> getPosicionesDelCuerpo()
	{
		return this.cuerpo;
	}
	
	/* Este metodo es para observar facilmente por consola las posiciones del cuerpo de la viborita.*/
	public String toString()
	{
		if(this.viva == false)
			return "Muerta";
		String retorno = "";
		for(Posicion p : this.cuerpo)
			retorno +=p.x+" "+p.y+"\n";
		retorno+=((this.viva)?"Viva":"Muerta")+"\n";
		return retorno;
	}
}
