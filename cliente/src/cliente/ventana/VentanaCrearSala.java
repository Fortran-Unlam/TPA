package cliente.ventana;

import java.awt.Color;
import java.awt.Event;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.Sonido;
import config.Param;

public class VentanaCrearSala extends JFrame {

	private static final long serialVersionUID = 1876694895241653019L;
	private JPanel contentPane;
	private JTextField nombreField;
	private VentanaMenu ventanaMenu;
	private JTextField maxUsuarioField;
	private VentanaSala ventanaSala;
	private JButton btnAceptar;
	private JButton btnVolver;

	public VentanaCrearSala(VentanaMenu ventanaMenu) {
		this.ventanaMenu = ventanaMenu;
		this.ventanaMenu.setVisible(false);

		setTitle("Nueva Sala");

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);

		btnAceptar = new JButton("Aceptar");
		btnAceptar.setBounds(98, 281, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnAceptar);

		btnVolver = new JButton("Volver");
		btnVolver.setBounds(259, 281, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(this.ventanaMenu);

		JLabel lblCreacionDeSala = new JLabel("Creacion de sala nueva");
		lblCreacionDeSala.setHorizontalAlignment(SwingConstants.CENTER);
		lblCreacionDeSala.setForeground(Color.GRAY);
		lblCreacionDeSala.setFont(new Font("Tahoma", Font.PLAIN, 23));
		lblCreacionDeSala.setBounds(49, 23, 384, 52);
		contentPane.add(lblCreacionDeSala);

		JLabel lblNewLabel = new JLabel("Nombre de la sala:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(27, 118, 175, 25);
		contentPane.add(lblNewLabel);

		nombreField = new JTextField();
		this.limitar(this.nombreField, Param.LIMITE_CARACTERES_NOMBRE_SALA);
		
		nombreField.setToolTipText(
				"Ingrese el nombre de la sala que desea. Solo pueden contener letras y numeros (sin espacios). Maximo 20 caracteres.");
		nombreField.setBounds(289, 120, 151, 25);
		contentPane.add(nombreField);
		nombreField.setColumns(10);

		JLabel lblMaxUsuarios = new JLabel("Cantidad m\u00E1xima de usuarios:");
		lblMaxUsuarios.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblMaxUsuarios.setBounds(27, 154, 218, 33);
		contentPane.add(lblMaxUsuarios);

		maxUsuarioField = new JTextField();
		maxUsuarioField.setText("2");
		this.limitar(this.maxUsuarioField, Param.LIMITE_CARACTERES_USUARIOS_MAX);
		maxUsuarioField.setToolTipText(
				"Ingrese la cantidad m\u00E1xima de usuarios. Debe ser num\u00E9rico. Maximo 99 usuarios.");
		maxUsuarioField.setBounds(400, 159, 40, 26);
		contentPane.add(maxUsuarioField);
		maxUsuarioField.setColumns(10);

		addListener();

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Cliente.getConexionServidor().cerrarSesionUsuario(ventanaMenu.getUsuario());
					System.exit(0);
				}
			}
		});
	}
	
	/**
	 * Limitar cantidad de caracteres a ingresar en el campo de texto Bloquea el
	 * control c y control v
	 * 
	 * @param jTextField
	 * @param limiteCaracteres
	 */	
	 private void limitar(final JTextField jTextField, final int limiteCaracteres) {
		InputMap mapUsername = jTextField.getInputMap(JComponent.WHEN_FOCUSED);
		mapUsername.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");

		jTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (jTextField.getText().length() >= limiteCaracteres) {
					e.consume();
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	protected void crearSala() {

		if (this.nombreField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "El nombre de la sala no puede estar vacio.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			this.nombreField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			this.maxUsuarioField.setText("2");
			return;
		}

		if (this.maxUsuarioField.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "La cantidad de usuarios maximos no puede estar vacio.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			this.maxUsuarioField.setText("2");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			return;
		}

		int cantidadMaxJugadores = Integer.valueOf(this.maxUsuarioField.getText());
		if (cantidadMaxJugadores < 2 || cantidadMaxJugadores >= 100) {
			JOptionPane.showMessageDialog(null,
					"La cantidad de usuarios m�ximos debe ser un numero mayor a 2 y menor a 100", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			this.maxUsuarioField.setText("");
			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
			return;
		}

		if (this.nombreField.getText().matches("[a-zA-Z0-9]+")) {

			ArrayList<String> datosSala = new ArrayList<String>();
			// 0: nombre, 1: cantUsuariosMax
			datosSala.add(this.nombreField.getText());
			datosSala.add(this.maxUsuarioField.getText());

			if (Cliente.getConexionServidor().crearSala(datosSala)) {
				Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
				musicaFondo.reproducir();
				
				this.ventanaSala = new VentanaSala(this.ventanaMenu, true, this.nombreField.getText());
				Sincronismo.setVentanaSala(ventanaSala);

				this.ventanaSala.setVisible(false);
				JsonObject paqueteCrearSala = Json.createObjectBuilder().add("type", Param.NOTICE_CREACION_SALA)
						.add("sala", datosSala.get(0)).build();
				Cliente.getconexionServidorBackOff().enviarAlServer(paqueteCrearSala);
				this.ventanaSala.setVisible(true);
				
				this.dispose();
			} else {
				JOptionPane.showMessageDialog(null,
						"Hubo un error en la creacion de la sala. Puede que el nombre ya exista", "Error creacion sala",
						JOptionPane.WARNING_MESSAGE);
			}

		} else {
			JOptionPane.showMessageDialog(null,
					"Los nombres de sala solo pueden contener letras y numeros (sin espacios).", "Aviso",
					JOptionPane.WARNING_MESSAGE);

			this.nombreField.setText("");
			this.maxUsuarioField.setText("2");

			this.nombreField.setFocusable(true);
			this.maxUsuarioField.setFocusable(true);
		}
	}

	private void addListener() {
		btnAceptar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
		btnAceptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				crearSala();
			}
		});
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
				musicaFondo.reproducir();
				ventanaMenu.setVisible(true);
				dispose();
			}
		});

		btnVolver.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
					musicaFondo.reproducir();
					ventanaMenu.setVisible(true);
					dispose();
				}
			}
		});

		nombreField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});

		maxUsuarioField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					crearSala();
				}
			}
		});
	}
}
