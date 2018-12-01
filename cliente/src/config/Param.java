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

	public static final int PIXEL_RESIZE = 12;

	public static final int MAPA_MAX_X = MAPA_WIDTH / PIXEL_RESIZE;
	public static final int MAPA_MAX_Y = MAPA_HEIGHT / PIXEL_RESIZE;

	public static int PORT_1 = 9001;
	public static int PORT_2 = 9002;
	public static int PORT_3 = 9003; // Back off port
	public static int PORT_4 = 9004; // Back off port

	public static final int MAXIMAS_CONEXIONES_SIMULTANEAS = 100;
	public static String IP_SERVER = "localhost";

	public static final String CREACION_SALA_ADMIN = "admin";
	public static final String UNION_SALA = "unionUsuarioSala";

	public static final String REQUEST_LOGUEAR = "loguear";
	public static final String REQUEST_LOGUEO_CORRECTO = "logueoCorrecto";
	public static final String REQUEST_LOGUEO_INCORRECTO = "logueoIncorrecto";
	public static final String REQUEST_LOGUEO_DUPLICADO = "errorYaLogeado";
	public static final String REQUEST_CERRAR_SESION = "cerrarSesion";
	public static final String REQUEST_CERRAR_SESION_OK = "cerrarSesionOk";
	public static final String REQUEST_CERRAR_SESION_FALLIDO = "cerrarSesionFail";
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
	public static final String REQUEST_MOSTRAR_GANADOR = "enviarGanador";
	public static final String REQUEST_GANADOR_ENVIADO = "hayGanador";
	public static final String REQUEST_ESTAN_TODOS_EN_SALA = "estanTodosEnSala";
	public static final String PODES_SALIR = "saliTranqui";

	public static final String REQUEST_EMPEZAR_JUEGO = "EmpezarJuego";
	public static final String REQUEST_JUEGO_EMPEZADO = "JuegoEmpezado";
	public static final String REQUEST_MOSTRAR_MAPA = "MostrarMapa";
	public static final String REQUEST_ENVIAR_TECLA = "enviarTecla";
	public static final String REQUEST_LOGUEO_BACKOFF_CLIENTE = "svAsignameLaConexion";
	public static final int CANTIDAD_FRUTA_MINIMAS = 25;
	public static final String SEPARADOR_EN_MENSAJES = ";";
	public static final String DATOS_SALA = "DatosSala";
	public static final String CANTIDAD_RONDAS = "CantidadRondas";
	public static final String SALA_TERMINADA = "laSalaTerminoXqElAdmSeFue";

	//Mensaje para cerrar ventana.
	public static final String MENSAJE_CERRAR_VENTANA ="¿Esta seguro que desea salir de la Viborita?";
	public static final String TITLE_CERRAR_VENTANA ="¿Salir del juego?";
	
	public static final String TIPO_JUEGO_FRUTA = "tipoJuegoFruta";
	public static final String TIPO_JUEGO_SUPERVIVENCIA = "tipoJuegoSupervivencia";
	public static final String TIPO_JUEGO_TIEMPO = "tipoJuegoTiempo";

	public static final String SONIDO_PATH = "/sonido/";
	public static final String IMAGEN_PATH = "/imagen/";

	public static final String SONIDO_FONDO_PATH = SONIDO_PATH + "musica.wav";
	public static final String SONIDO_GOLPE_PATH = SONIDO_PATH + "golpe.wav";
	public static final String SONIDO_MUERE_PATH = SONIDO_PATH + "muere.wav";
	public static final String SONIDO_FRUTA_PATH = SONIDO_PATH + "fruta.wav";

	public static final String IMG_CABEZA_PATH = IMAGEN_PATH + "cabeza.png";
	public static final String IMG_CUERPO_PATH = IMAGEN_PATH + "cuerpo.png";
	public static final String IMG_CUERPO_BOT_PATH = IMAGEN_PATH + "cuerpo_bot.png";
	public static final String IMG_FRUTA_PATH = IMAGEN_PATH + "fruta.png";
	public static final String IMG_MAPA_UNO_PATH = IMAGEN_PATH + "mapaUno.png";
	public static final String IMG_MAPA_DOS_PATH = IMAGEN_PATH + "mapaDos.png";
	public static final String IMG_MAPA_TRES_PATH = IMAGEN_PATH + "mapaTres.png";
	public static final String IMG_BOMBA_PATH = IMAGEN_PATH + "bomba.png";
	public static final String IMG_BLOQUE_PATH = IMAGEN_PATH + "bloque.png";

	public static final String NOTICE_CREACION_SALA = "ServerTeCreeUnaSala";
	public static final String NOTICE_UNION_SALA = "ServerMeUniAUnaSala";
	public static final String NOTICE_SALIR_SALA = "MeFuiDeLaSala";
	public static final String NOTICE_ENTRAR_A_VER_SALAS = "entreAVerLasSalas";
	public static final String NOTICE_ACTUALIZAR_SALAS_DISPONIBLES = "refrescaLasSalasDisponibles";
	public static final String NOTICE_REFRESCAR_USUARIOS_SALA_PARTICULAR = "refrescaLosUsuariosDeUnaSalaParticular";
	public static final String NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR = "refrescaLosParametrosDeUnaSalaParticular";
	public static final String NOTICE_LOGUEO_BACKOFF_OK = "CheClienteTeLogueeReZarpado";
	public static final String NOTICE_EMPEZAR_JUEGO = "EmpezarJuego";
	public static final String NOTICE_EMPEZA_JUEGO_CLIENTE = "EmpezarJuegoCliente";
	public static final String NOTICE_ADMIN_DAME_PARAM_SALA = "adminDameParamSala";
	public static final String NOTICE_TODOS_EN_SALA = "elEstadoDeTodosEnSalaEs";
	
	public static final int LIMITE_CARACTERES_USUARIO = 20;
	public static final int LIMITE_CARACTERES_CONTRASENA = 10;
	public static final int LIMITE_CARACTERES_NOMBRE_SALA = 20;
	public static final int LIMITE_CARACTERES_USUARIOS_MAX = 2;
	public static final int LIMITE_CARACTERES_CANT_BOTS = 2;
	public static final String REQUEST_SALIR_JUEGO = "SalirDeJuego";
	public static final String CANTIDAD_DE_BOTS = "cantidadDeBots";
	public static final String CANTIDAD_DE_FRUTAS = "cantidadDeFrutas";
	public static final String CANTIDAD_DE_TIEMPO = "cantidadDeTiempo";
	public static final String MAPA_DE_JUEGO = "mapaDeJuego";
	
	
}
