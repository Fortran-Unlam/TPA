import java.util.ArrayList;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import Core.Fruta;
import Core.Tablero;
import Core.Viborita;
import Posicion.Posicion;

public class PruebasViborita2 
{
	/*Se verifica tras un desplazamiento planteado previamente, en un tablero
	contenedor de una sola viborita, si esta ultima tras chocarse contra un limite del tablero
	su estado pasa a muerta.*/
	//@Test
	public void muerteViboritaFueraLimites()
	{
		//Se elige una dimension de tablero chica, para evaluar mas rapido la condicion buscada.
		Posicion dimensionTablero = new Posicion(4,4);
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
		//T0
		System.out.println(v1);
		Assert.assertEquals(true, v1.isViva());
		//T1
		v1.cambiarDireccion(0);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		Assert.assertEquals(true, v1.isViva());
		//T2
		v1.cambiarDireccion(1); // Cambio de direccion hacia "arriba" direccion eje Y creciente.
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		Assert.assertEquals(true, v1.isViva());
		//T3
		v1.cambiarDireccion(0);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		Assert.assertEquals(true, v1.isViva());
		//T4
		v1.cambiarDireccion(0);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		Assert.assertEquals(false, v1.isViva());
		//T5
		v1.cambiarDireccion(0);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		Assert.assertEquals(false, v1.isViva());
	}
	/* Se verifica dos viboritas que van a impactar de frente cabeza con cabeza,
	 * si tras el impacto ambas se mueren.
	*/
	//@Test
	public void muerteSimultaneaDosViboritas()
	{
		Posicion dimensionTablero = new Posicion(8,8);
		//Cuerpo viborita 1.
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		
		//Cuerpo viborita 2.
		ArrayList<Posicion> cuerpov2 = new ArrayList<Posicion>();
		cuerpov2.add(0, new Posicion(8,0));
		cuerpov2.add(0, new Posicion(7,0));
		cuerpov2.add(0, new Posicion(6,0));
		
		//Creacion de las viboritas con direcciones opuestas.
		Viborita v1 = new Viborita("Verde", cuerpov1, 0);
		Viborita v2 = new Viborita("Rojo", cuerpov2, 2);
		//Construccion del un tablero y relacionan bidireccional tablero-viborita.
		ArrayList<Viborita> viboritasEnJuego = new ArrayList<Viborita>();
		viboritasEnJuego.add(v1);
		viboritasEnJuego.add(v2);
		ArrayList<Fruta> frutasEnJuego = new ArrayList<Fruta>();
		Tablero t1 = new Tablero(dimensionTablero, viboritasEnJuego, frutasEnJuego);
		v1.setTablero(t1);
		v2.setTablero(t1);
		//T0
		System.out.println(v1);
		System.out.println(v2);
		Assert.assertEquals(true, v1.isViva());
		Assert.assertEquals(true, v2.isViva());
		//T1
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		System.out.println(v2);
		Assert.assertEquals(true, v1.isViva());
		Assert.assertEquals(true, v2.isViva());
		//T2
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		System.out.println(v2);
		Assert.assertEquals(false, v1.isViva());
		Assert.assertEquals(false, v2.isViva());
	}
	
	/* Se verifica el crecimiento de una viborita tras ingerir 3 frutas, participando
	 * ella sola en el tablero.
	*/
	@Test
	public void crecimientoVibora()
	{
		//Se elige una dimension de tablero chica, para evaluar mas rapido la condicion buscada.
		Posicion dimensionTablero = new Posicion(3,3);
		//Cuerpo viborita 1.
		ArrayList<Posicion> cuerpov1 = new ArrayList<Posicion>();
		cuerpov1.add(0, new Posicion(0,0));
		cuerpov1.add(0, new Posicion(1,0));
		cuerpov1.add(0, new Posicion(2,0));
		
		//Creacion de las viboritas con direcciones opuestas.
		Viborita v1 = new Viborita("Verde", cuerpov1, 0);
		
		//Construccion del un tablero y relacionan bidireccional tablero-viborita.
		ArrayList<Viborita> viboritasEnJuego = new ArrayList<Viborita>();
		viboritasEnJuego.add(v1);
		ArrayList<Fruta> frutasEnJuego = new ArrayList<Fruta>();
		frutasEnJuego.add(new Fruta(new Posicion(2, 1)));
		frutasEnJuego.add(new Fruta(new Posicion(2, 3)));
		frutasEnJuego.add(new Fruta(new Posicion(0, 2)));
		Tablero t1 = new Tablero(dimensionTablero, viboritasEnJuego, frutasEnJuego);
		v1.setTablero(t1);
		//T0
		System.out.println(v1);
		//T1
		v1.cambiarDireccion(1);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		//T2
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		//T3
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		//T4
		v1.cambiarDireccion(2);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		//T5
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
		//T6
		v1.cambiarDireccion(3);
		t1.desplazarTodasViboritas();
		t1.evaluarConflictosTotales();
		System.out.println(v1);
	}
}
