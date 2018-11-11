package cliente.ventana;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.Sonido;
import config.Param;
import javax.swing.JComboBox;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = 1740234947461105665L;
	private JList<String> listUsuarios;
	private DefaultListModel<String> datosLista = new DefaultListModel<String>();
	private JLabel labelUsrEnLaSala;
	private JLabel lblCantidadBots;
	private JPanel contentPane;
	private JFrame ventanaMenu;
	private String nombreSala;
	private JButton btnEmpezarJuego;
	private JCheckBox chckbxFruta;
	private JCheckBox chckbxTiempo;
	private JCheckBox chckbxSupervivencia;
	private JComboBox<Object> comboMapa;
	private JButton btnSalirDeSala;
	private JLabel lblAdmin;
	private JTextField cantBots;

	public VentanaSala(JFrame ventanaMenu, boolean admin, String nombreSala) {
		this.ventanaMenu = ventanaMenu;
		this.nombreSala = nombreSala;
		this.ventanaMenu.setVisible(false);
		this.setearComponentes(admin);
		addListener();
	}

	protected void verificarBotones() {
		JsonObjectBuilder nombreSalatipoJuegoYMapa = Json.createObjectBuilder();

		// Agrego parametros
		nombreSalatipoJuegoYMapa.add("type", Param.NOTICE_MODIFICAR_PARAM_SALA);

		nombreSalatipoJuegoYMapa.add("sala", this.nombreSala);
		
		nombreSalatipoJuegoYMapa.add("fruta", false);
		nombreSalatipoJuegoYMapa.add("supervivencia", false);
		nombreSalatipoJuegoYMapa.add("tiempo", false);
		btnEmpezarJuego.setEnabled(false);
		
		if (chckbxFruta.isSelected()) {
			nombreSalatipoJuegoYMapa.add("fruta", true);
			btnEmpezarJuego.setEnabled(true);
		}

		if (chckbxSupervivencia.isSelected()) {
			nombreSalatipoJuegoYMapa.add("supervivencia", true);
			btnEmpezarJuego.setEnabled(true);
		}

		if (chckbxTiempo.isSelected()) {
			nombreSalatipoJuegoYMapa.add("tiempo", true);
			btnEmpezarJuego.setEnabled(true);
		}

		nombreSalatipoJuegoYMapa.add("mapa", (String) comboMapa.getSelectedItem());

		Cliente.getconexionServidorBackOff().avisarAlSvQueHagaActualizaciones(nombreSalatipoJuegoYMapa.build());
	}

	// La visibilidad por default es para el admin
	private void setearComponentes(boolean esAdmin) {
		setTitle("Sala de juego");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_SALA_WIDTH, Param.VENTANA_SALA_HEIGHT);

		contentPane = new JPanel();
		contentPane.setToolTipText("Debe seleccionar un mapa.");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		getContentPane().setLayout(null);

		btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.setBounds(326, 346, 162, 40);
		getContentPane().add(btnSalirDeSala);

		btnEmpezarJuego = new JButton("Empezar juego");
		btnEmpezarJuego.setBounds(111, 346, 168, 40);
		getContentPane().add(btnEmpezarJuego);

		this.listUsuarios = new JList<String>(datosLista);
		listUsuarios.setFont(new Font("Century Gothic", Font.BOLD, 14));
		this.listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.listUsuarios.setBounds(33, 83, 168, 222);
		this.listUsuarios.setEnabled(false);
		this.listUsuarios.setOpaque(false);
		getContentPane().add(this.listUsuarios);

		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados a la sala:");
		lblUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuariosConectados.setBounds(33, 48, 186, 24);
		getContentPane().add(lblUsuariosConectados);

		this.labelUsrEnLaSala = new JLabel("");
		labelUsrEnLaSala.setFont(new Font("Tahoma", Font.PLAIN, 14));

		this.labelUsrEnLaSala.setBounds(220, 52, 39, 23);
		getContentPane().add(this.labelUsrEnLaSala);

		JLabel labelSala = new JLabel(this.nombreSala, SwingConstants.CENTER);
		labelSala.setForeground(Color.GRAY);
		labelSala.setFont(new Font("Tahoma", Font.PLAIN, 20));
		labelSala.setBounds(33, 11, 551, 30);
		getContentPane().add(labelSala);

		JLabel labelTipoJuego = new JLabel("Tipo de jugabilidad:");
		labelTipoJuego.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelTipoJuego.setBounds(236, 95, 124, 20);
		contentPane.add(labelTipoJuego);

		JLabel labelMapa = new JLabel("Mapa:");
		labelMapa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelMapa.setBounds(236, 192, 98, 20);
		contentPane.add(labelMapa);

		comboMapa = new JComboBox<Object>();
		comboMapa.setToolTipText("Debe seleccionar un tipo de mapa.");

		comboMapa.setBounds(368, 192, 151, 25);
		comboMapa.addItem("Mapa 1");
		comboMapa.addItem("Mapa 2");
		comboMapa.addItem("Mapa 3");
		contentPane.add(comboMapa);

		chckbxSupervivencia = new JCheckBox("Supervivencia");
		chckbxSupervivencia.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxSupervivencia.setBounds(366, 95, 130, 23);
		contentPane.add(chckbxSupervivencia);

		chckbxFruta = new JCheckBox("Fruta");
		chckbxFruta.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxFruta.setBounds(366, 121, 130, 23);
		contentPane.add(chckbxFruta);

		chckbxTiempo = new JCheckBox("Tiempo");
		chckbxTiempo.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxTiempo.setBounds(366, 147, 130, 23);
		contentPane.add(chckbxTiempo);

		JLabel lblTipoJugabilidad = new JLabel("");
		lblTipoJugabilidad.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblTipoJugabilidad.setBounds(236, 131, 238, 24);
		lblTipoJugabilidad.setVisible(false);
		contentPane.add(lblTipoJugabilidad);

		JLabel lblMapa = new JLabel("");
		lblMapa.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMapa.setBounds(366, 192, 130, 24);
		lblMapa.setVisible(false);
		contentPane.add(lblMapa);

		lblAdmin = new JLabel("");
		lblAdmin.setForeground(new Color(184, 134, 11));
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAdmin.setBounds(382, 48, 202, 24);
		lblAdmin.setVisible(true);
		contentPane.add(lblAdmin);

		lblCantidadBots = new JLabel("Cantidad bots:");
		lblCantidadBots.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidadBots.setBounds(236, 250, 111, 20);
		contentPane.add(lblCantidadBots);

		cantBots = new JTextField();
		cantBots.setBounds(377, 252, 32, 20);
		contentPane.add(cantBots);
		cantBots.setColumns(10);

		if (esAdmin) {
			chckbxSupervivencia.setEnabled(true);
			chckbxFruta.setEnabled(true);
			chckbxTiempo.setEnabled(true);
			comboMapa.setEnabled(true);

			chckbxSupervivencia.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					verificarBotones();
				}
			});
			chckbxFruta.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					verificarBotones();
				}
			});

			chckbxTiempo.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					verificarBotones();
				}
			});

			comboMapa.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					verificarBotones();
				}
			});
			lblAdmin.setText("Vos sos el admin");
		} else {
			lblMapa.setVisible(true);
			comboMapa.setVisible(false);
			chckbxFruta.setVisible(false);
			chckbxSupervivencia.setVisible(false);
			chckbxTiempo.setVisible(false);
			btnSalirDeSala.setBounds(236, 346, 162, 40);
		}

	}

	private void salirSala() {
		ventanaMenu.setVisible(true);
		setVisible(false);
		JsonObject paqueteSalirSala = Json.createObjectBuilder().add("type", Param.NOTICE_SALIR_SALA)
				.add("nombreSala", this.nombreSala).build();

		Cliente.getConexionServidor().SalirSala(this.nombreSala);
		Cliente.getconexionServidorBackOff().avisarAlSvQueHagaActualizaciones(paqueteSalirSala);
	}

	protected void empezarJuego() {
		if (Cliente.getConexionServidor().comenzarJuego(cantBots.getText()) == false) {
			System.out.println("no pudo crear el juego");
			return;
		}
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaJuego(null);
	}

	private void addListener() {
		btnSalirDeSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
				musicaFondo.reproducir();
				salirSala();
			}
		});
		btnEmpezarJuego.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				empezarJuego();
			}
		});

		btnEmpezarJuego.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					empezarJuego();
				}
			}
		});

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					salirSala();
					Cliente.getConexionServidor().cerrarSesionUsuario(((VentanaMenu) ventanaMenu).getUsuario());
					System.exit(0);
				}
			}
		});

	}

	// TODO: falta hacer este metodo
	// Metodo que usa el Thread para refrescar la Sala a cada cliente
	public void refrescarSala() {

	}
}
