package Core;

import java.util.ArrayList;

import Posicion.Posicion;

public class Viborita 
{
	private String color;
	private boolean viva;
	private int direccionActual; // Direccion de la cabeza de la viborita.
	private ArrayList<Posicion> cuerpo = new ArrayList<>();
	private Tablero tablero; // Tablero al que pertenece la viborita.
	
	//Para hacer tests rapidos.
	public Viborita()
	{
		
	}
	
	public Viborita(String color, ArrayList<Posicion> defecto, int direcciondefecto)
	{
		this.color = color;
		this.setViva(true);
		this.cuerpo = defecto;
		this.direccionActual = direcciondefecto;
	}
	
	public int getLongitud()
	{
		return this.cuerpo.size();
	}
	
	/* Este metodo es utilizado por tablero para saber las posiciones del tablero
	 * valga la redundancia que esta ocupando la viborita.
	 * 
	 */
	protected ArrayList<Posicion> getPosicionesDelCuerpo()
	{
		return cuerpo;
	}
	
	/* Este método se ejecutaría automáticamente cada un x intervalo de tiempo (ej 1seg)
	* cada intervalo de tiempo se debería recalcular las posiciones de la viborita aunque
	* el usuario no haya cambiado de direccion, Ejemplo sí la cabeza de mi viborita
	* esta apuntando para el este, empieza con 3 de longitud ocupando las posiciones
	* 0,0 0,1 0,2, al cabo de un segundo ahora ocuparia los puestos 0,1 0,2 0,3
	*/
	public void desplazar()
	{
		//Logica del desplazamiento del cuerpo de la viborita.
		//...
		//Una vez desplazado al menos la cabeza de la viborita se chequea contra que toco la cabeza.
		//Si choco contra otro espacio ocupado por otra viborita.
		if(this.tablero.getEspacioOcupadoPorViboritas().contains(this.cuerpo.get(0)))
			this.setViva(false);
		//Se separa para que no este todo junto es lo mismo ponerlo en el mismo if con un OR.
		else if(this.tablero.getFueraLimite(this))
			this.setViva(false);
		//Si la cabeza de la viborita se topo con una fruta.
		else if(this.tablero.getEspacioOcupadoPorFrutas().contains(this.cuerpo.get(0)))
		{
			//this.cuerpo.add(new Posicion());
			//El segmento extra de la viborita se agrega atras en su cola o en su cabeza ?
			this.tablero.eliminarFruta(new Fruta(this.cuerpo.get(0)));
		}
		//Si no paso nada de lo anterior no pasa nada, ya se desplazó la viborita para este entonces.
			
	}
	
	/* Este método es el que ejecuta el jugador, el jugador decide cual será el sentido
	* de la cabeza de la viborita, y la cabeza de la viborita guía al resto de su cuerpo.
	* El jugador NO decide o no es responsabilidad suya realizar el desplazamiento de la viborita.
	* El jugador solo decide para donde se desplazará y un timer (intervalo) ejecutará
	* la acción desplazar cada un x intervalo de tiempo.
	* 
	*/
	public void cambiarPosicion(int posicion)
	{
		this.direccionActual = posicion;
	}

	
	public boolean isViva() 
	{
		return viva;
	}

	public void setViva(boolean viva) 
	{
		this.viva = viva;
	}
	
	public void setTablero(Tablero t)
	{
		this.tablero = t;
	}
}
