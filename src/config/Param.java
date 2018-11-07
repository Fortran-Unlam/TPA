package config;

import java.io.Serializable;

public abstract class Param implements Serializable {
	
	private static final long serialVersionUID = 6653691337938229625L;
	public static final int VENTANA_JUEGO_WIDTH = 1200;
	public static final int VENTANA_JUEGO_HEIGHT = 700;
	
	public static final int BOTON_WIDTH = 130;
	public static final int BOTON_HEIGHT = 40;
	
	public static final int VENTANA_CLIENTE_WIDTH = 500;
	public static final int VENTANA_CLIENTE_HEIGHT = 400;
	
	public static final int VENTANA_SALA_WIDTH = 600;
	public static final int VENTANA_SALA_HEIGHT = 450;

	public static final int MAPA_WIDTH = 1000;
	public static final int MAPA_HEIGHT = 700;

	public static final int PIXEL_RESIZE = 5;

	public static final int MAPA_MAX_X = MAPA_WIDTH / PIXEL_RESIZE;
	public static final int MAPA_MAX_Y = MAPA_HEIGHT / PIXEL_RESIZE;

	public static final int PORT_1 = 9001;
	public static final int PORT_2 = 9002;
	public static final int PORT_3 = 9003;	//Back off port
	public static final int PORT_4 = 9004;	//Back off port
	
	public static final int MAXIMAS_CONEXIONES_SIMULTANEAS = 100;
	public static final String HOST = "localhost";
	
	public static final String CREACION_SALA_ADMIN = "admin";
	public static final String UNION_SALA = "unionUsuarioSala";
	
	public static final String REQUEST_LOGUEAR = "loguear";
	public static final String REQUEST_LOGUEO_CORRECTO = "logueoCorrecto";
	public static final String REQUEST_LOGUEO_INCORRECTO = "logueoIncorrecto";
	public static final String REQUEST_LOGUEO_DUPLICADO = "errorYaLogeado";
	public static final String REQUEST_CERRAR_SESION = "cerrarSesion";
	public static final String REQUEST_CERRAR_SESION_OK = "cerrarSesionOk";
	public static final String REQUEST_GET_ALL_SALAS = "getAllSalas";
	public static final String REQUEST_REGISTRAR_USUARIO = "registrar";
	public static final String REQUEST_REGISTRO_CORRECTO = "RegistroCorrecto";
	public static final String REQUEST_REGISTRO_INCORRECTO = "RegistroIncorrecto";
	public static final String REQUEST_REGISTRO_DUPLICADO = "RegistroDuplicado";
	public static final String REQUEST_CREAR_SALA = "CrearSala";
	public static final String REQUEST_SALIR_SALA = "SalirSala";
	public static final String REQUEST_SALA_CREADA = "SalaCreada";
	public static final String REQUEST_INGRESO_SALA = "IngresoSala";
	public static final String REQUEST_ERROR_CREAR_SALA = "ErrorCreacionSala";
	public static final String REQUEST_ACTUALIZAR_SALAS = "ActualizacionSalas";
	public static final String REQUEST_INGRESO_VENTANA_UNIR_SALA = "DameLasSalas";
	
	public static final String REQUEST_EMPEZAR_JUEGO = "EmpezarJuego";
	public static final String REQUEST_JUEGO_EMPEZADO = "JuegoEmpezado";
	public static final String REQUEST_MOSTRAR_MAPA = "MostrarMapa";
	public static final String REQUEST_ENVIAR_TECLA = "enviarTecla";
	public static final int CANTIDAD_FRUTA_MINIMAS = 25;
	public static final String SEPARADOR_EN_MENSAJES =";";
	public static final String DATOS_SALA = "DatosSala";
	
	//Mensaje para cerrar ventana.
	public static final String MENSAJE_CERRAR_VENTANA ="�Esta seguro que desea salir de la Viborita?";
	public static final String TITLE_CERRAR_VENTANA ="�Salir del juego?";
	public static final String TIPO_JUEGO_FRUTA = "tipoJuegoFruta";
	public static final String TIPO_JUEGO_SUPERVIVENCIA = "tipoJuegoSupervivencia";
	public static final String TIPO_JUEGO_TIEMPO = "tipoJuegoTiempo";

	public static final String SONIDO_PATH = "/sonido/";
	
	public static final String MUSICA_FONDO_PATH = SONIDO_PATH + "musica.wav";
	
	public static final String NOTICE_CREACION_SALA = "ServerTeCreeUnaSala";
	public static final String NOTICE_UNION_SALA = "ServerMeUniAUnaSala";
	public static final String NOTICE_ACTUALIZAR_SALAS = "ClienteActualizaTusSalas";
	
}
