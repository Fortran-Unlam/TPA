package cliente.ventana.usuario;

import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.json.Json;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.codec.digest.DigestUtils;

import cliente.Cliente;
import cliente.Sonido;
import cliente.Usuario;
import cliente.ventana.VentanaMenu;
import config.Param;

public class VentanaLoginUsuario extends JFrame {

	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField username;
	private JTextField password;
	private JButton btnRegistrarse;
	private JButton btnCrearUsuario;

	public VentanaLoginUsuario() {

		this.setTitle("Snake");
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		JLabel usernameLabel = new JLabel("Usuario");
		usernameLabel.setToolTipText("");
		usernameLabel.setBounds(60, 64, 92, 14);
		this.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Contrase\u00F1a");
		passwordLabel.setBounds(60, 89, 92, 14);
		this.getContentPane().add(passwordLabel);

		this.username = new JTextField();	
		this.limitar(this.username, Param.LIMITE_CARACTERES_USUARIO);

		this.username.setToolTipText("Ingrese su usuario aqu\u00ED. Maximo 20 caracteres.");
		this.username.setBounds(162, 61, 86, 20);
		this.username.setColumns(10);
		this.getContentPane().add(this.username);

		this.password = new JPasswordField();
		this.limitar(this.password, Param.LIMITE_CARACTERES_CONTRASENA);

		this.password.setToolTipText("Ingrese su contrase\u00F1a aqu\u00ED. Maximo 10 caracteres.");
		this.password.setBounds(162, 86, 86, 20);
		this.password.setColumns(10);
		this.getContentPane().add(this.password);

		this.btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		//Minimo tamano para que el texto Iniciar Sesion se muestre con el tipo de letra elegido.
		this.btnCrearUsuario.setBounds(96, 131, 129, 23);
		this.getContentPane().add(this.btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(108, 25, 122, 14);
		this.getContentPane().add(lblCrearUsuario);

		this.btnRegistrarse = new JButton("Registrarse");
		this.btnRegistrarse.setBounds(96, 165, 129, 23);
		getContentPane().add(this.btnRegistrarse);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 326, 230);
		this.setLocationRelativeTo(null);

		addListener();

	}

	/**
	 * Limitar cantidad de caracteres a ingresar en el campo de texto Bloquea el
	 * control c y control v
	 * 
	 * @param jTextField
	 * @param limiteCaracteresUsuario
	 */	
	
	 private void limitar(final JTextField jTextField, final int limiteCaracteresUsuario) {
		InputMap mapUsername = jTextField.getInputMap(JComponent.WHEN_FOCUSED);
		mapUsername.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");

		jTextField.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				if (jTextField.getText().length() >= limiteCaracteresUsuario) {
					e.consume();
					Toolkit.getDefaultToolkit().beep();
				}
			}
		});
	}

	/**
	 * Le dice al servidor que el usuario quiere loguearse. El servidor va a
	 * responder en su debido tiempo y voy a crear un usuario el cual lo uso para
	 * guardarlo y abro la ventana menu cuando este es distinto de null
	 * 
	 * @throws IOException
	 * 
	 */
	protected void iniciarSession() throws IOException {

		if (this.password.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "!Te falto ingresar la contrase\u00F1a!", "Error login",
					JOptionPane.WARNING_MESSAGE);
			this.password.setFocusable(true);
			return;
		}

		if (this.username.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "!Te falto ingresar el usuario!", "Error login",
					JOptionPane.WARNING_MESSAGE);
			this.username.setFocusable(true);
			this.password.setText("");
			return;
		}

		String hashPassword = DigestUtils.md5Hex(this.password.getText());

		Usuario usuario = Cliente.getConexionServidor().loguear(this.username.getText(), hashPassword);

		Cliente.getconexionServidorBackOff().enviarAlServer(Json.createObjectBuilder()
				.add("type", Param.REQUEST_LOGUEO_BACKOFF_CLIENTE).add("username", this.username.getText()).build());

		if (usuario != null && usuario.getId() != -1) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
					musicaFondo.reproducir();
					try {
						VentanaMenu frame = new VentanaMenu();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			this.dispose();

		} else if (usuario != null && usuario.getId() == -1) {
			JOptionPane.showMessageDialog(null, "Usuario ya logeado", "Error login", JOptionPane.ERROR_MESSAGE);
			this.username.setText("");
			this.password.setText("");
			this.username.setFocusable(true);
		} else {
			JOptionPane.showMessageDialog(null, "Usted ha introducido un usuario y/o clave incorrecta", "Error login",
					JOptionPane.ERROR_MESSAGE);
			this.username.setText("");
			this.password.setText("");
			this.username.setFocusable(true);
		}
	}

	private void registrarUsuario() {
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaCrearUsuario(this).setVisible(true);
	}

	private void addListener() {

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(getContentPane(), Param.MENSAJE_CERRAR_VENTANA,
						Param.TITLE_CERRAR_VENTANA, JOptionPane.YES_NO_OPTION,
						JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					System.exit(0);
				}
			}
		});

		this.btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarUsuario();
			}
		});
		this.username.addActionListener(iniciarSessionPerformed());

		this.password.addActionListener(iniciarSessionPerformed());

		this.btnCrearUsuario.addActionListener(iniciarSessionPerformed());

	}

	private ActionListener iniciarSessionPerformed() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSession();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
	}
}
