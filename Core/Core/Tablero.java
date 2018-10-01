package Core;

import java.util.ArrayList;

import Posicion.Posicion;
/* Cada ronda se va a desarrollar en un tablero.
 * El tablero es una entidad que contiene las viboritas y las frutas
 * Su responsabilidad es la de conocer en todo momento las posiciones
 * de sus entidades contenidas tanto viboritas como frutas.
 * 
 */
public class Tablero 
{
	//Si el tablero va a ser cuadrado.
	private Posicion dimension;
	//Si el tablero va a ser rectangular.
	/* private Posicion dimension1;
	   private Posicion dimension2;*/ 
	private ArrayList<Viborita> viboritas = new ArrayList<Viborita>();
	private ArrayList<Fruta> frutas = new ArrayList<Fruta>();
	
	//El creador de tablero.
	public Tablero(Posicion dimension, ArrayList<Viborita> viboritas, ArrayList<Fruta> frutas)
	{
		this.dimension = dimension;
		this.viboritas = viboritas;
		this.frutas = frutas;
	}
	
	//Este metodo devuelve las posiciones ocupadas por cuerpos de viboritas.
	protected ArrayList<Posicion> getEspacioOcupadoPorViboritas()
	{
		ArrayList<Posicion> posicionesOcupadas = new ArrayList<Posicion>();
		/* Aca se podria tomar varias decisiones
		* 1) Las viboritas muertas son eliminadas del tablero (no me gusta mucho).
		* 2) Las viboritas muertas se quedan en el tablero estorbando si otra
		* viborita viva choca contra el cuerpo de una muerta muere tambien (tampoco me gusta).
		* 3) La viborita muerta queda en el tablero pero su cuerpo muerto no interfiere (voy por esta).
		*/
		for(Viborita v : this.viboritas)
				if(v.isViva())
					posicionesOcupadas.addAll(v.getPosicionesDelCuerpo());
		return posicionesOcupadas;
	}
	
	//Este metodo devuelve las posiciones ocupadas por frutas del tablero.
	protected ArrayList<Posicion> getEspacioOcupadoPorFrutas()
	{
		ArrayList<Posicion> posicionesFrutas = new ArrayList<Posicion>();
		for(Fruta f : this.frutas)
			posicionesFrutas.add(f.getPos());
		return posicionesFrutas;
	}
	
	/* Este método devuelve las posiciones libres del tablero.
	* quizá no sea necesario, se puede implementar como resta de los ArrayList anteriores.
	*/
	protected ArrayList<Posicion> getEspacioLibre()
	{
		//...
		return null;
	}
	
	/*El tablero al conocer sus limites, tras pasarle una viborita por parametro
	* chequea si la misma se excedió de sus limites.
	*/
	protected boolean getFueraLimite(Viborita v)
	{
		//Considerando cuadrado las dimensiones del tablero.
		if(v.getPosicionesDelCuerpo().get(0).getX() > dimension.getX() || v.getPosicionesDelCuerpo().get(0).getY() > dimension.getY())
			return true;
		return false;
	}
	
	/* Este método es llamado desde viborita indicando que consumió una fruta del tablero
	* y es necesario eliminarla.
	*/
	protected void eliminarFruta(Fruta f)
	{
		this.frutas.remove(f); // Sobreescribir el comparador por defecto de fruta.
	}
}
