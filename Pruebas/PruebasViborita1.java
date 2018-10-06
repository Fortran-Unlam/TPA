import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Core.Fruta;
import Core.Tablero;
import Core.Viborita;
import Posicion.Posicion;

public class PruebasViborita1 
{
	/* Se verifica que luego de crear una viborita y asignarle 3 posiciones a su cuerpo
	   la longitud de su cuerpo es de 3 unidades.*/
	@Test
	public void tamanoCuerpo()
	{
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		Viborita v1 = new Viborita("verde", cuerpov1, 0);
		Assert.assertEquals(3, v1.getLongitud());
	}
	
	/* Se verifica que luego de crear una viborita, esta se encuentra viva en el instante 0.*/
	@Test
	public void viboritaVivaAlCrear()
	{
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		Viborita v1 = new Viborita("verde", cuerpov1, 0);
		Assert.assertEquals(true, v1.isViva());
	}
	
	/* Se verifica que si estoy avanzando por ejemplo en la direccion del eje X creciente
	   no puedo avanzar en la direccion del eje X decreciente.*/
	@Test
	public void cambioDireccionSentidoContrario()
	{
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		Viborita v1 = new Viborita("verde", cuerpov1, 0);
		//En el eje X.
		v1.cambiarDireccion(2);
		Assert.assertEquals(0, v1.getDireccionActual());
		//En el eje Y.
		v1.cambiarDireccion(1);
		v1.cambiarDireccion(3);
		Assert.assertEquals(1, v1.getDireccionActual());
	}
	
	/*Se verifica tras un desplazamiento planteado previamente, en un tablero
	contenedor de una sola viborita, si esta ultima sigue la trayectoria predefinida.
	no se incluyen frutas, ni el tratamiento de conflictos, solo se evalua el desplazamiento*/
	@Test
	public void desplazamientoViborita1()
	{
		Posicion dimensionTablero = new Posicion(8,8);
		//Decido para una prueba inicial empezar con un cuerpo de 3 unidades en eje X.
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		//Creacion de la viborita.
		Viborita v1 = new Viborita("Verde", cuerpov1, 0);
		
		//Construccion del un tablero y relacionan bidireccional tablero-viborita.
		ArrayList<Viborita> viboritasEnJuego = new ArrayList<Viborita>();
		viboritasEnJuego.add(v1);
		ArrayList<Fruta> frutasEnJuego = new ArrayList<Fruta>();
		Tablero t1 = new Tablero(dimensionTablero, viboritasEnJuego, frutasEnJuego);
		v1.setTablero(t1);
		/* Se observa la trayectoria de la viborita tras 7 unidades de tiempo y
		*  cambios de direcciones simulados que un jugador podria realizar.*/
		//T0
		System.out.println(v1);
		//T1
		v1.cambiarDireccion(0);
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T2
		v1.cambiarDireccion(1); // Cambio de direccion hacia "arriba" direccion eje Y creciente.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T3
		v1.cambiarDireccion(1); // No cambio de direccion.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T4
		v1.cambiarDireccion(1); // No cambio de direccion.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T5
		v1.cambiarDireccion(3); // Intento de hacer un cambio de direccion invalido eje Y decreciente.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T6
		v1.cambiarDireccion(2); // Cambio de direccion hacia la "izquierda" direccion eje X decreciente.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//T7
		v1.cambiarDireccion(3); // Cambio de direccio hacia "abajo" direccion eje Y decreciente.
		v1.desplazar(); //SIMIL t1.desplazarTodasViboritas();
		System.out.println(v1);
		//Al final de todos los desplazamientos anteriores, la cabeza de la vibora debe quedar en (2,3).
		//Se adjunta hoja manuscrita para entender los desplazamientos.
		Assert.assertEquals(new Posicion(2,3), v1.getPosicionesDelCuerpo().get(0));
	}
}
