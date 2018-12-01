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
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
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

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = -1128641003929339105L;
	private JList<String> listUsuarios;
	private DefaultListModel<String> modelUsuariosLista = new DefaultListModel<String>();
	private JLabel labelUsrEnLaSala;
	private JLabel lblCantidadBots;
	private JPanel contentPane;
	private VentanaMenu ventanaMenu;
	private String nombreSala;
	private JButton btnEmpezarJuego;
	private JCheckBox chckbxFruta;
	private JCheckBox chckbxTiempo;
	private JButton btnSalirDeSala;
	private JLabel lblAdmin;
	private boolean visibiliadAdmin;
	private JLabel mapaParaNoAdmin;
	private JLabel cantidadDeFrutasLabel;
	private JLabel cantidadDeTiempoLabel;
	private JLabel cantidadDeBotsLabel;
	private JLabel cantidadDeRondasLabel;
	private JComboBox<Object> comboMapa;
	private JComboBox<Object> comboCantRondas;
	private JComboBox<Object> cantidadDeTiempoComboBox;
	private JComboBox<Object> cantidadDeFrutascomboBox;
	private JComboBox<Object> cantidadDeBotsComboBox;
	private char numeroDeMapa;
	private int totalRondas;

	public VentanaSala(VentanaMenu ventanaMenu, boolean admin, String nombreSala) {
		this.ventanaMenu = ventanaMenu;
		this.nombreSala = nombreSala;
		this.ventanaMenu.setVisible(false);
		this.visibiliadAdmin = admin;
		this.setearComponentes();
		this.addListener();
	}

	protected void verificarBotonesYRefrescarCambios() {
		if (comboMapa.getSelectedIndex() != 0 && comboCantRondas.getSelectedIndex() != 0 && (this.listUsuarios.getModel().getSize() >= 2 || this.cantidadDeBotsComboBox.getSelectedIndex() != 0)) {
			btnEmpezarJuego.setEnabled(true);
		} else {
			btnEmpezarJuego.setEnabled(false);
		}

		if (chckbxFruta.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeFrutascomboBox.setEnabled(true);
		}

		if (!chckbxFruta.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeFrutascomboBox.setSelectedIndex(0);
			this.cantidadDeFrutascomboBox.setEnabled(false);
		}

		if (chckbxTiempo.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeTiempoComboBox.setEnabled(true);
		}

		if (!chckbxTiempo.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeTiempoComboBox.setSelectedIndex(0);
			this.cantidadDeTiempoComboBox.setEnabled(false);
		}
	}

	void prepararYEnviarCambiosDeSala() {
		JsonObjectBuilder nombreSalatipoJuegoMapaYBots = Json.createObjectBuilder();

		// Agrego parametros
		nombreSalatipoJuegoMapaYBots.add("type", Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR);
		nombreSalatipoJuegoMapaYBots.add("sala", this.nombreSala);

		nombreSalatipoJuegoMapaYBots.add("fruta", chckbxFruta.isSelected());

		nombreSalatipoJuegoMapaYBots.add("cantidadDeFrutas",
				String.valueOf(chckbxFruta.isSelected() ? this.cantidadDeFrutascomboBox.getSelectedItem() : "0"));

		nombreSalatipoJuegoMapaYBots.add("tiempo", chckbxTiempo.isSelected());

		nombreSalatipoJuegoMapaYBots.add("cantTiempo",
				String.valueOf(chckbxTiempo.isSelected() ? this.cantidadDeTiempoComboBox.getSelectedItem() : "0"));

		if (comboMapa.getSelectedIndex() == 0) {
			nombreSalatipoJuegoMapaYBots.add("mapa", "Aun no se ha determinado");
		} else {
			nombreSalatipoJuegoMapaYBots.add("mapa", (String) comboMapa.getSelectedItem());
		}

		if (comboCantRondas.getSelectedIndex() == 0) {
			nombreSalatipoJuegoMapaYBots.add("rondas", "Aun no se ha determinado");
		} else {
			nombreSalatipoJuegoMapaYBots.add("rondas", (String) comboCantRondas.getSelectedItem());
		}

		nombreSalatipoJuegoMapaYBots.add("bots", String.valueOf(cantidadDeBotsComboBox.getSelectedItem()));

		Cliente.getconexionServidorBackOff().enviarAlServer(nombreSalatipoJuegoMapaYBots.build());
	}

	// La visibilidad por default es para el admin
	private void setearComponentes() {
		setTitle("Sala de juego");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_SALA_WIDTH, Param.VENTANA_SALA_HEIGHT);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);

		getContentPane().setLayout(null);

		cantidadDeBotsLabel = new JLabel();
		cantidadDeBotsLabel.setBounds(368, 238, 72, 18);
		contentPane.add(cantidadDeBotsLabel);

		cantidadDeTiempoLabel = new JLabel();
		cantidadDeTiempoLabel.setBounds(436, 135, 52, 24);
		contentPane.add(cantidadDeTiempoLabel);

		cantidadDeFrutascomboBox = new JComboBox<Object>();
		cantidadDeFrutascomboBox.setToolTipText("Debe seleccionar cantidad de frutas");

		cantidadDeFrutascomboBox
				.setModel(new DefaultComboBoxModel<Object>(new String[] { "10", "15", "20", "25", "30", "35" }));
		cantidadDeFrutascomboBox.setBounds(496, 95, 72, 24);
		contentPane.add(cantidadDeFrutascomboBox);

		cantidadDeTiempoComboBox = new JComboBox<Object>();
		cantidadDeTiempoComboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "20", "30", "40", "50" }));
		cantidadDeTiempoComboBox.setBounds(496, 135, 72, 24);
		contentPane.add(cantidadDeTiempoComboBox);

		btnSalirDeSala = new JButton("Salir de sala");
		btnSalirDeSala.setBounds(326, 346, 162, 40);
		getContentPane().add(btnSalirDeSala);

		btnEmpezarJuego = new JButton("Empezar juego");
		btnEmpezarJuego.setBounds(111, 346, 168, 40);
		btnEmpezarJuego.setEnabled(false);
		getContentPane().add(btnEmpezarJuego);

		this.listUsuarios = new JList<String>(modelUsuariosLista);
		listUsuarios.setFont(new Font("Century Gothic", Font.BOLD, 14));
		this.listUsuarios.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.listUsuarios.setBounds(33, 83, 168, 222);
		this.listUsuarios.setEnabled(false);
		this.listUsuarios.setOpaque(false);
		getContentPane().add(this.listUsuarios);

		JLabel lblUsuariosConectados = new JLabel("Usuarios conectados a la sala:");
		lblUsuariosConectados.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblUsuariosConectados.setBounds(33, 48, 200, 24);
		lblUsuariosConectados.setSize(lblUsuariosConectados.getPreferredSize());
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
		labelTipoJuego.setSize(labelTipoJuego.getPreferredSize());
		contentPane.add(labelTipoJuego);

		JLabel labelMapa = new JLabel("Mapa:");
		labelMapa.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelMapa.setBounds(236, 192, 98, 20);
		contentPane.add(labelMapa);

		comboMapa = new JComboBox<Object>();
		comboMapa.setToolTipText("Debe seleccionar un tipo de mapa.");

		comboMapa.setBounds(368, 192, 151, 25);
		comboMapa.addItem("Seleccione un mapa");
		comboMapa.addItem("Arena 1");
		comboMapa.addItem("Laberinto 2");
		comboMapa.addItem("Selva 3");
		comboMapa.setSize(comboMapa.getPreferredSize());
		contentPane.add(comboMapa);

		chckbxFruta = new JCheckBox("Fruta");
		chckbxFruta.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxFruta.setBounds(366, 95, 68, 23);
		contentPane.add(chckbxFruta);

		chckbxTiempo = new JCheckBox("Tiempo");
		chckbxTiempo.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxTiempo.setBounds(366, 136, 68, 23);
		chckbxTiempo.setSize(chckbxTiempo.getPreferredSize());
		contentPane.add(chckbxTiempo);

		this.lblAdmin = new JLabel("");
		lblAdmin.setForeground(new Color(184, 134, 11));
		lblAdmin.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblAdmin.setBounds(382, 48, 202, 24);
		lblAdmin.setVisible(true);
		contentPane.add(lblAdmin);

		lblCantidadBots = new JLabel("Cantidad bots:");
		lblCantidadBots.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidadBots.setBounds(236, 237, 111, 20);
		contentPane.add(lblCantidadBots);

		mapaParaNoAdmin = new JLabel("Aun no se ha determinado");
		mapaParaNoAdmin.setBounds(368, 194, 151, 20);
		contentPane.add(mapaParaNoAdmin);

		JLabel labelCantRondas = new JLabel("Cantidad Rondas:");
		labelCantRondas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCantRondas.setBounds(236, 268, 111, 20);
		labelCantRondas.setSize(labelCantRondas.getPreferredSize());
		contentPane.add(labelCantRondas);

		comboCantRondas = new JComboBox<Object>();
		comboCantRondas.setToolTipText("Debe seleccionar cantidad de rondas.");
		comboCantRondas.setBounds(368, 268, 151, 51);
		contentPane.add(comboCantRondas);
		comboCantRondas.setBounds(368, 268, 151, 20);
		comboCantRondas.addItem("Seleccione Rondas");
		comboCantRondas.addItem("1");
		comboCantRondas.addItem("2");
		comboCantRondas.addItem("3");
		comboCantRondas.addItem("4");
		comboCantRondas.addItem("5");
		comboCantRondas.setSize(comboCantRondas.getPreferredSize());
		contentPane.add(comboCantRondas);

		cantidadDeFrutasLabel = new JLabel();
		cantidadDeFrutasLabel.setBounds(436, 95, 52, 24);
		contentPane.add(cantidadDeFrutasLabel);

		cantidadDeBotsComboBox = new JComboBox<Object>();
		cantidadDeBotsComboBox.setToolTipText("Debe seleccionar cantidad de bots");
		cantidadDeBotsComboBox
				.setModel(new DefaultComboBoxModel<Object>(new String[] { "0", "1", "2", "3", "4", "5" }));
		cantidadDeBotsComboBox.setBounds(411, 236, 69, 20);
		contentPane.add(cantidadDeBotsComboBox);

		cantidadDeRondasLabel = new JLabel();
		cantidadDeRondasLabel.setBounds(368, 268, 151, 20);
		contentPane.add(cantidadDeRondasLabel);

		if (this.visibiliadAdmin) {
			chckbxFruta.setEnabled(true);
			chckbxTiempo.setEnabled(true);
			comboMapa.setEnabled(true);
			comboCantRondas.setEnabled(true);
			mapaParaNoAdmin.setVisible(false);
			cantidadDeFrutascomboBox.setEnabled(false);
			cantidadDeTiempoComboBox.setEnabled(false);
			cantidadDeFrutasLabel.setVisible(false);
			cantidadDeTiempoLabel.setVisible(false);
			cantidadDeBotsLabel.setVisible(false);
			cantidadDeRondasLabel.setVisible(false);
			lblAdmin.setText("Tu eres el Administrador");
		} else {
			comboMapa.setVisible(false);
			btnSalirDeSala.setBounds(236, 346, 162, 40);
			btnEmpezarJuego.setVisible(false);
			chckbxFruta.setEnabled(false);
			chckbxTiempo.setEnabled(false);
			comboCantRondas.setVisible(false);
			mapaParaNoAdmin.setVisible(true);
			cantidadDeFrutasLabel.setVisible(true);
			cantidadDeTiempoLabel.setVisible(true);
			cantidadDeBotsLabel.setVisible(true);
			cantidadDeRondasLabel.setVisible(true);
			cantidadDeFrutascomboBox.setVisible(false);
			cantidadDeTiempoComboBox.setVisible(false);
			cantidadDeBotsComboBox.setVisible(false);
		}

	}

	private void salirSala() {
		ventanaMenu.setVisible(true);
		setVisible(false);
		JsonObject paqueteSalirSala = Json.createObjectBuilder().add("type", Param.NOTICE_SALIR_SALA)
				.add("sala", this.nombreSala).build();

		Cliente.getConexionServidor().SalirSala(this.nombreSala);
		Sincronismo.setVentanaSala(null);
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteSalirSala);
	}
	
	protected void prepararParaArranqueJuego() {
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		this.setVisible(false);
		if(!this.visibiliadAdmin) {
			new VentanaJuego(Integer.valueOf(this.cantidadDeRondasLabel.getText()),
					mapaParaNoAdmin.getText().charAt(mapaParaNoAdmin.getText().length()-1), ventanaMenu.getUsuario(),this);
		}else {
			new VentanaJuego(totalRondas, this.numeroDeMapa, ventanaMenu.getUsuario(), this);
		}
	}

	protected void empezarJuego() {
		int totalBots = Integer.parseInt((String) cantidadDeBotsComboBox.getSelectedItem());
		this.totalRondas = Integer.parseInt((String) comboCantRondas.getSelectedItem());
		boolean tipoDeJuegoFruta = chckbxFruta.isSelected();
		boolean tipoDeJuegoTiempo = chckbxTiempo.isSelected();
		int cantidadDeFrutas = chckbxFruta.isSelected()
				? Integer.parseInt((String) cantidadDeFrutascomboBox.getSelectedItem())
				: 0;
		int cantidadDeTiempo = chckbxTiempo.isSelected()
				? Integer.parseInt((String) cantidadDeTiempoComboBox.getSelectedItem())
				: 0;
		String mapa = (String) comboMapa.getSelectedItem();
		this.numeroDeMapa = mapa.charAt(mapa.length() - 1);

		if (Cliente.getConexionServidor().comenzarJuego(totalBots, totalRondas, tipoDeJuegoFruta, cantidadDeFrutas,
				tipoDeJuegoTiempo, cantidadDeTiempo, mapa) == false) {
			Cliente.LOGGER.error("No se pudo creear el Juego");
			System.out.println("no lo crea al juego");
			return;
		}

		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();

		this.setVisible(false);
	}

	private void addListener() {
		btnSalirDeSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
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
		chckbxFruta.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		chckbxTiempo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		comboMapa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		comboCantRondas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		cantidadDeFrutascomboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		cantidadDeBotsComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		cantidadDeTiempoComboBox.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (visibiliadAdmin) {
					verificarBotonesYRefrescarCambios();
					prepararYEnviarCambiosDeSala();
				}
			}
		});

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					salirSala();
					Cliente.getConexionServidor().cerrarSesionUsuario(ventanaMenu.getUsuario());
					System.exit(0);
				}
			}
		});

	}

	// Metodo que usa el Thread para refrescar la Sala a cada cliente
	public void refrescarSala(JsonObject datosParaRefrescarSala) {
		String tipoDeActualizacion = datosParaRefrescarSala.getString("type");

		if (tipoDeActualizacion.equals(Param.NOTICE_REFRESCAR_USUARIOS_SALA_PARTICULAR)) {
			JsonArray arrayUsuariosConectados = datosParaRefrescarSala.getJsonArray("usuarios");
			this.modelUsuariosLista.clear(); // Limpio
			// Cargo
			for (int i = 0; i < arrayUsuariosConectados.size(); i++) {
				this.modelUsuariosLista.addElement(arrayUsuariosConectados.getString(i));

			}
			this.listUsuarios.setModel(this.modelUsuariosLista);

			if (!this.visibiliadAdmin)
				this.lblAdmin.setText("El admin es: " + datosParaRefrescarSala.getString("admin"));
		} else {
			if (!this.visibiliadAdmin) {// USUARIO INVITADO
				if (datosParaRefrescarSala.getBoolean("fruta")) {
					chckbxFruta.setSelected(true);
					this.cantidadDeFrutasLabel.setText(datosParaRefrescarSala.getString("cantidadDeFrutas"));
				} else {
					chckbxFruta.setSelected(false);
					this.cantidadDeFrutasLabel.setText("");
				}

				if (datosParaRefrescarSala.getBoolean("tiempo")) {
					chckbxTiempo.setSelected(true);
					this.cantidadDeTiempoLabel.setText(datosParaRefrescarSala.getString("cantTiempo"));
				} else {
					chckbxTiempo.setSelected(false);
					this.cantidadDeTiempoLabel.setText("");
				}

				this.cantidadDeRondasLabel.setText(datosParaRefrescarSala.getString("rondas"));
				this.mapaParaNoAdmin.setText(datosParaRefrescarSala.getString("tipoMapa"));
				this.cantidadDeBotsLabel.setText(datosParaRefrescarSala.getString("bots"));
			}
		}
	}

	public void cerrarSalaPorqueSalioAdmin() {
		JOptionPane.showMessageDialog(this, "Ha salido el admin de la sala", "Sala terminada",
				JOptionPane.INFORMATION_MESSAGE);
		this.setVisible(false);
	}
}
