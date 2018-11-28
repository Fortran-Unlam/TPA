package cliente.ventana;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.DefaultListModel;
import javax.swing.InputMap;
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
import javax.swing.KeyStroke;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class VentanaSala extends JFrame {

	private static final long serialVersionUID = -1128641003929339105L;
	private JList<String> listUsuarios;
	private DefaultListModel<String> modelUsuariosLista = new DefaultListModel<String>();
	private JLabel labelUsrEnLaSala;
	private JLabel lblCantidadBots;
	private JLabel lblBots;
	private JPanel contentPane;
	private JFrame ventanaMenu;
	private String nombreSala;
	private JButton btnEmpezarJuego;
	private JCheckBox chckbxFruta;
	private JCheckBox chckbxTiempo;
	private JComboBox<Object> comboMapa;
	private JComboBox<Object> comboCantRondas;
	private JButton btnSalirDeSala;
	private JLabel lblAdmin;
	private JTextField cantBots;
	private boolean visibiliadAdmin;
	private JLabel mapaParaNoAdmin;
	private JTextField rondasParaNoAdmin;
	private JTextField cantidadDeFrutasTextField;
	private JTextField tiempoTextField;
	private boolean cantidadDeFrutasCorrectas = true;
	private boolean cantidadDeTiempoCorrecto = true;
	private JLabel cantidadDeFrutasLabel;
	private JLabel cantidadDeTiempoLabel;

	public VentanaSala(JFrame ventanaMenu, boolean admin, String nombreSala) {
		this.ventanaMenu = ventanaMenu;
		this.nombreSala = nombreSala;
		this.ventanaMenu.setVisible(false);
		this.visibiliadAdmin = admin;
		this.setearComponentes();
		this.addListener();
	}

	protected void verificarBotonesYRefrescarCambios() {
		if (this.cantidadDeFrutasCorrectas && this.cantidadDeTiempoCorrecto && comboMapa.getSelectedIndex() != 0
				&& !cantBots.getText().isEmpty() && comboCantRondas.getSelectedIndex() != 0) {
			btnEmpezarJuego.setEnabled(true);
		} else {
			btnEmpezarJuego.setEnabled(false);
		}

		if (chckbxFruta.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeFrutasTextField.setEnabled(true);
		}

		if (!chckbxFruta.isSelected() && this.visibiliadAdmin) {
			this.cantidadDeFrutasTextField.setEnabled(false);
			this.cantidadDeFrutasTextField.setText("");
			this.cantidadDeFrutasCorrectas = true;
		}

		if (chckbxTiempo.isSelected() && this.visibiliadAdmin) {
			this.tiempoTextField.setEnabled(true);
		}

		if (!chckbxTiempo.isSelected() && this.visibiliadAdmin) {
			this.tiempoTextField.setEnabled(false);
			this.tiempoTextField.setText("");
			this.cantidadDeTiempoCorrecto = true;
		}

		if (cantBots.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La cantidad de bots no puede estar vacia", "Atencion",
					JOptionPane.WARNING_MESSAGE);
			cantBots.setText("0");
		} else {
			if (!cantBots.getText().matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null, "La cantidad de bots debe ser numerica", "Atencion",
						JOptionPane.WARNING_MESSAGE);
				cantBots.setText("0");
			}
		}

		JsonObjectBuilder nombreSalatipoJuegoMapaYBots = Json.createObjectBuilder();

		// Agrego parametros
		nombreSalatipoJuegoMapaYBots.add("type", Param.NOTICE_REFRESCAR_PARAM_SALA_PARTICULAR);
		nombreSalatipoJuegoMapaYBots.add("sala", this.nombreSala);

		nombreSalatipoJuegoMapaYBots.add("fruta", chckbxFruta.isSelected());
		nombreSalatipoJuegoMapaYBots.add("cantidadDeFrutas", this.cantidadDeFrutasTextField.getText());

		nombreSalatipoJuegoMapaYBots.add("tiempo", chckbxTiempo.isSelected());
		nombreSalatipoJuegoMapaYBots.add("cantidadDeTiempo", this.tiempoTextField.getText());

		if (comboMapa.getSelectedIndex() == 0) {
			nombreSalatipoJuegoMapaYBots.add("mapa", "Aun no se ha determinado");
		} else {
			nombreSalatipoJuegoMapaYBots.add("mapa", (String) comboMapa.getSelectedItem());
		}

		if (comboCantRondas.getSelectedIndex() == 0) {
			nombreSalatipoJuegoMapaYBots.add("rondas", "0");
		} else {
			nombreSalatipoJuegoMapaYBots.add("rondas", (String) comboCantRondas.getSelectedItem());
		}

		nombreSalatipoJuegoMapaYBots.add("bots", cantBots.getText());

		Cliente.getconexionServidorBackOff().enviarAlServer(nombreSalatipoJuegoMapaYBots.build());
	}

	private void verificarFrutas() {
		if (chckbxFruta.isSelected()
				&& (cantidadDeFrutasTextField.getText().isEmpty()
						|| Integer.valueOf(cantidadDeFrutasTextField.getText()) <= 0
						|| Integer.valueOf(cantidadDeFrutasTextField.getText()) > 1000)
				|| !this.cantidadDeFrutasTextField.getText().matches("[0-9]+")) {
			JOptionPane.showMessageDialog(null,
					"La cantidad de fruta no puede estar vacia, ser negativa o mayor a mil.", "Atencion",
					JOptionPane.WARNING_MESSAGE);
			this.cantidadDeFrutasCorrectas = false;
		} else
			this.cantidadDeFrutasCorrectas = true;
	}

	private void verificarTiempo() {
		if (chckbxTiempo.isSelected()
				&& (tiempoTextField.getText().isEmpty() || Integer.valueOf(tiempoTextField.getText()) <= 0
						|| Integer.valueOf(tiempoTextField.getText()) > 1000)) {
			JOptionPane.showMessageDialog(null,
					"La cantidad de tiempo no puede estar vacia, ser negativa o mayor a mil segundos.", "Atencion",
					JOptionPane.WARNING_MESSAGE);
			this.cantidadDeTiempoCorrecto = false;
		} else
			this.cantidadDeTiempoCorrecto = true;
	}

	// Este metodo pretende avisarle a los otros usuarios de la sala que ya pueden
	// arrancar la VentanaJuego.
	private void AvisarAOtrosUsuariosSala() {
		JsonObjectBuilder JuegoEmpezado = Json.createObjectBuilder();
		JuegoEmpezado.add("sala", this.nombreSala);

		// Agrego parametros
		JuegoEmpezado.add("type", Param.NOTICE_EMPEZAR_JUEGO);
		Cliente.getconexionServidorBackOff().enviarAlServer(JuegoEmpezado.build());
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
		comboMapa.addItem("Seleccione un mapa");
		comboMapa.addItem("Mapa 1");
		comboMapa.addItem("Mapa 2");
		comboMapa.addItem("Mapa 3");
		contentPane.add(comboMapa);

		chckbxFruta = new JCheckBox("Fruta");
		chckbxFruta.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxFruta.setBounds(366, 95, 130, 23);
		contentPane.add(chckbxFruta);

		chckbxTiempo = new JCheckBox("Tiempo");
		chckbxTiempo.setToolTipText("Debe ckeckear un tipo de jugabiliad.");
		chckbxTiempo.setBounds(366, 136, 130, 23);
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
		cantBots = new JTextField();
		/* Bloquea el control c y control v */
		InputMap MapCantBotsField = cantBots.getInputMap(cantBots.WHEN_FOCUSED);
		MapCantBotsField.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");

		cantBots.setHorizontalAlignment(SwingConstants.LEFT);
		cantBots.setToolTipText("Debe ingresar la cantidad de bots si lo desea.");
		cantBots.setFont(new Font("Tahoma", Font.PLAIN, 14));
		cantBots.setText("0");
		cantBots.setBounds(368, 237, 32, 20);
		contentPane.add(cantBots);
		cantBots.setColumns(10);

		lblBots = new JLabel("");
		lblBots.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBots.setBounds(377, 250, 32, 20);
		contentPane.add(lblBots);

		mapaParaNoAdmin = new JLabel("Aun no se ha determinado");
		mapaParaNoAdmin.setBounds(368, 194, 151, 20);
		contentPane.add(mapaParaNoAdmin);

		JLabel labelCantRondas = new JLabel("Cantidad Rondas:");
		labelCantRondas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		labelCantRondas.setBounds(236, 268, 111, 20);
		contentPane.add(labelCantRondas);

		comboCantRondas = new JComboBox<Object>();
		comboCantRondas.setToolTipText("Debe seleccionar cantidad de rondas.");
		comboCantRondas.setBounds(368, 268, 151, 25);
		contentPane.add(comboCantRondas);
		comboCantRondas.setBounds(368, 268, 151, 20);
		comboCantRondas.addItem("Seleccione Rondas");
		comboCantRondas.addItem("1");
		comboCantRondas.addItem("2");
		comboCantRondas.addItem("3");
		comboCantRondas.addItem("4");
		comboCantRondas.addItem("5");
		contentPane.add(comboCantRondas);

		rondasParaNoAdmin = new JTextField();
		rondasParaNoAdmin.setText("0");
		rondasParaNoAdmin.setHorizontalAlignment(SwingConstants.LEFT);
		rondasParaNoAdmin.setFont(new Font("Tahoma", Font.PLAIN, 14));
		rondasParaNoAdmin.setColumns(10);
		rondasParaNoAdmin.setBounds(368, 268, 32, 20);
		contentPane.add(rondasParaNoAdmin);

		cantidadDeFrutasTextField = new JTextField();
		cantidadDeFrutasTextField.setEnabled(false);
		cantidadDeFrutasTextField.setBounds(498, 96, 86, 20);
		contentPane.add(cantidadDeFrutasTextField);
		cantidadDeFrutasTextField.setColumns(10);

		tiempoTextField = new JTextField();
		tiempoTextField.setEnabled(false);
		tiempoTextField.setBounds(498, 137, 86, 20);
		contentPane.add(tiempoTextField);
		tiempoTextField.setColumns(10);

		cantidadDeFrutasLabel = new JLabel();
		cantidadDeFrutasLabel.setBounds(427, 95, 69, 20);
		contentPane.add(cantidadDeFrutasLabel);

		cantidadDeTiempoLabel = new JLabel();
		cantidadDeTiempoLabel.setBounds(430, 138, 66, 18);
		contentPane.add(cantidadDeTiempoLabel);

		if (this.visibiliadAdmin) {
			chckbxFruta.setEnabled(true);
			chckbxTiempo.setEnabled(true);
			comboMapa.setEnabled(true);
			comboCantRondas.setEnabled(true);
			mapaParaNoAdmin.setVisible(false);
			rondasParaNoAdmin.setVisible(false);
			cantidadDeFrutasLabel.setVisible(false);
			cantidadDeTiempoLabel.setVisible(false);
			lblAdmin.setText("Tu eres el Administrador");
		} else {
			comboMapa.setVisible(false);
			btnSalirDeSala.setBounds(236, 346, 162, 40);
			btnEmpezarJuego.setVisible(false);
			cantBots.setEnabled(false);
			chckbxFruta.setEnabled(false);
			chckbxTiempo.setEnabled(false);
			comboCantRondas.setVisible(false);
			mapaParaNoAdmin.setVisible(true);
			rondasParaNoAdmin.setEnabled(false);
			cantidadDeFrutasTextField.setVisible(false);
			tiempoTextField.setVisible(false);
			cantidadDeFrutasLabel.setVisible(true);
			cantidadDeTiempoLabel.setVisible(true);
		}

	}

	private void salirSala() {
		ventanaMenu.setVisible(true);
		setVisible(false);
		JsonObject paqueteSalirSala = Json.createObjectBuilder().add("type", Param.NOTICE_SALIR_SALA)
				.add("nombreSala", this.nombreSala).build();

		Cliente.getConexionServidor().SalirSala(this.nombreSala);
		Sincronismo.setVentanaSala(null);
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteSalirSala);
	}

	protected void empezarJuego() {

		if ((this.cantBots.getText().isEmpty()) || (!cantBots.getText().matches("[0-9]+"))) {
			return;
		}
		int totalRondas = Integer.parseInt((String) comboCantRondas.getSelectedItem());
		if (Cliente.getConexionServidor().comenzarJuego(cantBots.getText(),
				totalRondas) == false) {
			System.out.println("No se pudo creear el Juego");
			return;
		}
		AvisarAOtrosUsuariosSala();
		// this.dispose(); Cuando termina el VentanaJuego y se aprieta salir es mejor
		// volver a la VentanaSala que volver a crear una nueva instancia.
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaJuego(totalRondas);
	}

	// Esto en realidad deberia ser el mismo que empezarJuego y que haya una
	// variable que condicione el if.
	protected void empezarJuegoNoAdmin() {
		// this.dispose(); Cuando termina el VentanaJuego y se aprieta salir es mejor
		// volver a la VentanaSala que volver a crear una nueva instancia.
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaJuego(Integer.parseInt((String) comboCantRondas.getSelectedItem()));
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

		cantBots.addActionListener(new ActionListener() {
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
				if (!cantidadDeFrutasTextField.isEnabled())
					cantidadDeFrutasTextField.setEnabled(true);
				else
					cantidadDeFrutasTextField.setEnabled(false);
			}
		});

		cantidadDeFrutasTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				verificarFrutas();
				verificarBotonesYRefrescarCambios();
			}
		});

		chckbxTiempo.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (!tiempoTextField.isEnabled())
					tiempoTextField.setEnabled(true);
				else
					tiempoTextField.setEnabled(false);
			}
		});

		tiempoTextField.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent e) {
				verificarTiempo();
				verificarBotonesYRefrescarCambios();
			}
		});

		comboMapa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				verificarBotonesYRefrescarCambios();
			}
		});

		comboCantRondas.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				verificarBotonesYRefrescarCambios();
			}
		});

		cantBots.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				if (cantBots.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "La cantidad de bots no puede estar vacia", "Atencion",
							JOptionPane.WARNING_MESSAGE);
					cantBots.setText("0");
				}

				verificarBotonesYRefrescarCambios();
			}
		});

		/* Limita cantidad de caracteres a ingresar en el campo cantidad de bots */
		cantBots.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent evt) {
				if (cantBots.getText().length() >= Param.LIMITE_CARACTERES_CANT_BOTS) {
					evt.consume();
					Toolkit.getDefaultToolkit().beep();
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

	// Metodo que usa el Thread para refrescar la Sala a cada cliente
	public void refrescarSala(JsonObject datosParaRefrescarSala) {
		String tipoDeActualizacion = datosParaRefrescarSala.getString("type");

		if (tipoDeActualizacion.equals(Param.NOTICE_REFRESCAR_USUARIOS_PARTICULAR)) {
			JsonArray arrayUsuariosConectados = datosParaRefrescarSala.getJsonArray("usuarios");
			this.modelUsuariosLista.clear(); // Limpio
			// Cargo
			for (int i = 0; i < arrayUsuariosConectados.size(); i++) {
				this.modelUsuariosLista.addElement(arrayUsuariosConectados.getString(i));

			}
			// Seteo
			this.listUsuarios.setModel(this.modelUsuariosLista);

			if (!this.visibiliadAdmin)
				this.lblAdmin.setText("El admin es: " + datosParaRefrescarSala.getString("admin"));
		} else {

			if (!this.visibiliadAdmin) {// Si no es admin
				System.out.println("CONDICION FRUTA" + datosParaRefrescarSala.getBoolean("fruta"));
				System.err.println(datosParaRefrescarSala.getString("cantidadDeFrutas"));
				System.err.println(datosParaRefrescarSala.getString("rondas"));
				System.err.println(datosParaRefrescarSala.getString("tipoMapa"));
				System.err.println(datosParaRefrescarSala.getString("bots"));
				System.err.println("Condicion Tiempo" + datosParaRefrescarSala.getBoolean("tiempo"));
				System.err.println(datosParaRefrescarSala.getString("cantidadDeTiempo"));

				if (datosParaRefrescarSala.getBoolean("fruta")) {
					chckbxFruta.setSelected(true);
					this.cantidadDeFrutasLabel.setText(datosParaRefrescarSala.getString("cantidadDeFrutas"));
				} else {
					chckbxFruta.setSelected(false);
				}

				if (datosParaRefrescarSala.getBoolean("tiempo")) {
					chckbxTiempo.setSelected(true);
					this.cantidadDeTiempoLabel.setText(datosParaRefrescarSala.getString("cantidadDeTiempo"));
				} else {
					chckbxTiempo.setSelected(false);
				}

				this.rondasParaNoAdmin.setText(datosParaRefrescarSala.getString("rondas"));
				this.mapaParaNoAdmin.setText(datosParaRefrescarSala.getString("tipoMapa"));
				this.cantBots.setText(datosParaRefrescarSala.getString("bots"));
			}
		}
	}
}
