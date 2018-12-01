package cliente.ventana;

import java.io.IOException;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import cliente.Cliente;
import config.Param;

public class Sincronismo extends Thread {

	private static VentanaUnirSala ventanaUnirSala;
	private static VentanaSala ventanaSala;
	private static JsonArray datosDeSalasDisponibles;

	public Sincronismo() {
		this.start();
	}

	public void run() {
		boolean conectado = true;

		while (conectado) {
			try {
				String stringEntrada = (String) Cliente.getconexionServidorBackOff().getEntradaDatos().readUTF();

				JsonReader jsonReader = Json.createReader(new StringReader(stringEntrada));
				JsonObject entradaJson = jsonReader.readObject();
				jsonReader.close();

				String tipoMensaje = entradaJson.getString("type");

				switch (tipoMensaje) {

				case Param.NOTICE_LOGUEO_BACKOFF_OK:
					Cliente.LOGGER.info("Logueo backoff OK");
					break;

				case Param.NOTICE_ACTUALIZAR_SALAS_DISPONIBLES:
					datosDeSalasDisponibles = entradaJson.getJsonArray("datosDeSalas");

					if (ventanaUnirSala != null) {
						ventanaUnirSala.refrescarListaDeSalas(datosDeSalasDisponibles);
					}
					break;
				// Hasta aca todo OK 26/11 cuando el juego empezo el servidor me avisa.
				// Hay que ver como arrancar la ventana de juego tambien.
				case Param.NOTICE_EMPEZA_JUEGO_CLIENTE:
					Sincronismo.ventanaSala.prepararParaArranqueJuego();
					break;
				case Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR:
				case Param.NOTICE_REFRESCAR_USUARIOS_SALA_PARTICULAR:
					// Reflejo 26/11 sucede cuando yo ya empece el juego.
					// Recibo mensajes de mapa, los interpreta bien, pero tambien piensa que son de
					// tipo refrescarSala.
					try {
						Sincronismo.ventanaSala.refrescarSala(entradaJson);
					} catch (Exception e) {
						Cliente.LOGGER.error("No se pudo refresacar salas");
					}
					break;
				case Param.NOTICE_ADMIN_DAME_PARAM_SALA:
					Sincronismo.ventanaSala.prepararYEnviarCambiosDeSala();
					break;
					
				case Param.SALA_TERMINADA:
					Sincronismo.ventanaSala.cerrarSalaPorqueSalioAdmin();
					ventanaUnirSala.setVisible(true);
					break;
				}
				

			} catch (IOException e) {
				Cliente.LOGGER.error("Hubo un error al sincronizar" + e.getMessage());
			}
		}
	}

	public static void setVentanaSala(VentanaSala ventanaSala) {
		Sincronismo.ventanaSala = ventanaSala;
	}

	public static void setVentanaUnirSala(VentanaUnirSala ventanaUnirSala) {
		Sincronismo.ventanaUnirSala = ventanaUnirSala;
		if (datosDeSalasDisponibles != null) {
			ventanaUnirSala.refrescarListaDeSalas(datosDeSalasDisponibles);
		}
	}

}
