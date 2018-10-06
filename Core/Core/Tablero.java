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
	
	//Este metodo devuelve las posiciones ocupadas por cuerpos de viboritas
	protected ArrayList<Posicion> getEspacioOcupadoPorViboritas(Viborita r)
	{
		ArrayList<Posicion> posicionesOcupadas = new ArrayList<Posicion>();
		/* Aca se podria tomar varias decisiones
		* 1) Las viboritas muertas son eliminadas del tablero (no me gusta mucho).
		* 2) Las viboritas muertas se quedan en el tablero estorbando si otra
		* viborita viva choca contra el cuerpo de una muerta muere tambien (tampoco me gusta).
		* 3) La viborita muerta queda en el tablero pero su cuerpo muerto no interfiere (voy por esta).
		*/
		for(Viborita v : this.viboritas)
		{
			//Si estoy revisando el espacio ocupado por viboritas vivas y distinta de la pasada por parametro.
			if(v.isViva() && !v.equals(r))
				posicionesOcupadas.addAll(v.getPosicionesDelCuerpo());
			/* Si estoy revisando el espacio ocupado por la misma viborita que la pasada por parametro
			   tengo que tener en cuenta que debo ignorar la posicion de su propia cabeza ya que
			   esto seria como viborita preguntar, hay alguien en la posicion donde esta mi cabeza,
			   exceptuando mi propia cabeza jaja.
			*/
			else if(v.isViva() && v.equals(r))
			{
				ArrayList<Posicion> aux = new ArrayList<>(v.getPosicionesDelCuerpo());
				aux.remove(0);
				posicionesOcupadas.addAll(aux);
			}
		}		
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
	   quizá no sea necesario, se puede implementar como resta de los ArrayList anteriores.*/
	protected ArrayList<Posicion> getEspacioLibre()
	{
		//...
		return null;
	}
	
	/*El tablero al conocer sus limites, tras pasarle una viborita por parametro
	  chequea si la misma se excedió de sus limites.*/
	protected boolean getFueraLimite(Viborita v)
	{
		//Considerando cuadrado las dimensiones del tablero.
		if(v.getPosicionesDelCuerpo().get(0).x > dimension.x || v.getPosicionesDelCuerpo().get(0).y > dimension.y)
			return true;
		return false;
	}
	
	/* Este método es llamado desde viborita indicando que consumió una fruta del tablero
	   y es necesario eliminarla.*/
	protected void eliminarFruta(Fruta f)
	{
		this.frutas.remove(f); // Sobreescribir el comparador por defecto de fruta.
	}
	
	//Este metodo evalua los conflictos posibles de todas las viboritas pertenecientes al tablero.
	public void evaluarConflictosTotales()
	{
		/* MUY IMPORTANTE, primero tengo que evaluar las viboritas que van a morir al finalizar
		 * el "turno" o lapso de tiempo, y despues "matarlas", porque si voy matando las viboritas
		 * a medida que voy evaluando sus conflictos, la muerte "simultanea" es decir dos viboritas
		 * enfrentadas por ejemplo nunca podria ocurrir, ya que la v1 mataria a la v2 un instante antes
		 * que v2 mate a v1, y v2 al estar muerta ya no podria matar a v1.
		 * 
		 */
		int[] conflictos = new int[this.viboritas.size()];
		int i = -1;
		for(Viborita v : this.viboritas)
			conflictos[++i] = v.evaluarConflictoParticular();
		i = -1;
		for(Viborita v : this.viboritas)
		{
			//El tablero "mata" o pasa de estado de vivo a muerto a la viborita.
			if(conflictos[++i] == 0)
				v.setViva(false);
			//El tablero elimina la fruta de la posicion y hace crecer a la viborita.
			else if(conflictos[i] == 1)
			{
				this.eliminarFruta(new Fruta(v.getPosicionesDelCuerpo().get(0)));
				v.hacerCrecer();
			}
		}
	}
	
	//Este metodo realiza el desplazamiento de todas las viboritas pertenecientes al tablero.
	public void desplazarTodasViboritas()
	{
		for(Viborita v : this.viboritas)
			v.desplazar();
	}
}
