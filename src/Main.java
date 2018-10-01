import java.util.ArrayList;
import java.util.Random;

import Core.Fruta;
import Core.Tablero;
import Core.Viborita;
import Posicion.Posicion;

public class Main {

	/*Para esta version 30/09 el main esta cumpliendo la funcion de otra entidad(objeto)
	quizá sea partida que esta creando las viboritas antes de empezar la ronda,
	creando el tablero y realizando sus asociaciones.
	Para proxima version, la entidad que representa el main ya estaría modelada
	y así sucesivamente*/
	public static void main(String[] args) 
	{
		//Parametros que le llegarian a la entidad que representa el main entre otros.
		//Cantidad de viboritas, colores y atributos de cada uno quiza el jugador la tuneo digamos.
		//Dimensiones del tablero, alguien lo eligio o viene por defecto.
		int cantidadViboritas = 3;
		Posicion dimensionTablero = new Posicion(8,8);
		//Yo elegi el orden de creacion, pero podria ser diferente.
		//Primero creo las viboritas.
		Viborita v1 = new Viborita();
		Viborita v2 = new Viborita();
		Viborita v3 = new Viborita();
		//Creo las frutas.
		Fruta f1 = new Fruta(new Posicion(new Random().nextInt(dimensionTablero.getX()), new Random().nextInt(dimensionTablero.getY())));
		Fruta f2 = new Fruta(new Posicion(new Random().nextInt(dimensionTablero.getX()), new Random().nextInt(dimensionTablero.getY())));
		Fruta f3 = new Fruta(new Posicion(new Random().nextInt(dimensionTablero.getX()), new Random().nextInt(dimensionTablero.getY())));
		//Despues creo el tablero.
		ArrayList<Viborita> viboritasEnJuego = new ArrayList<Viborita>();
		ArrayList<Fruta> frutasEnJuego = new ArrayList<Fruta>();
		//Agrego en los arraylist las viboritas y las frutas ...
		Tablero t1 = new Tablero(dimensionTablero, viboritasEnJuego, frutasEnJuego);
		//A cada viborita le indico cual es su tablero.
		//Asi va a pedir informacion del tablero a su respectivo tablero.
		//200 lineas y solo veo tableros jaja.
		v1.setTablero(t1);
		v2.setTablero(t1);
		v3.setTablero(t1);
	}

}
