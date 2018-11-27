package cliente.ventana.usuario;

import java.awt.Event;
import java.awt.EventQueue;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import org.apache.commons.codec.digest.DigestUtils;

import cliente.Cliente;
import cliente.Sonido;
import cliente.ventana.VentanaMenu;
import config.Param;
import looby.Usuario;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class VentanaLoginUsuario extends JFrame {

	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField username;
	private JTextField password;
	private JButton btnRegistrarse;
	private JButton btnCrearUsuario;

	public VentanaLoginUsuario() {

		setTitle("Snake");
		setResizable(false);
		setLocationRelativeTo(null);
		this.getContentPane().setLayout(null);
		JLabel usernameLabel = new JLabel("Usuario");
		usernameLabel.setToolTipText("");
		usernameLabel.setBounds(60, 64, 92, 14);
		this.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Contrase\u00F1a");
		passwordLabel.setBounds(60, 89, 92, 14);
		this.getContentPane().add(passwordLabel);

		this.username = new JTextField();
		/*Bloquea el control c y control v*/
		InputMap mapUsername = username.getInputMap(username.WHEN_FOCUSED);
		mapUsername.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
		/*Limitar cantidad de caracteres a ingresar en el campo de texto usuario*/
		username.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			if (username.getText().length() >= Param.LIMITE_CARACTERES_USUARIO) {
				e.consume();
				Toolkit.getDefaultToolkit().beep();
	     		}
			}
	    });
			
		this.username.setToolTipText("Ingrese su usuario aqu\u00ED. Maximo 20 caracteres.");
		this.username.setBounds(162, 61, 86, 20);
		this.getContentPane().add(username);
		this.username.setColumns(10);

		this.password = new JPasswordField();
		/*Bloquea el control c y control v*/
		InputMap mapPassword = password.getInputMap(username.WHEN_FOCUSED);
		mapPassword.put(KeyStroke.getKeyStroke(KeyEvent.VK_V, Event.CTRL_MASK), "null");
		/*Limitar cantidad de caracteres a ingresar en el campo de texto contrase�a*/
		password.addKeyListener(new KeyAdapter() {
		public void keyTyped(KeyEvent e) {
			if (password.getText().length() >= Param.LIMITE_CARACTERES_CONTRASENA) {
				e.consume();
				Toolkit.getDefaultToolkit().beep();
	     		}
			}
	    });
		this.password.setToolTipText("Ingrese su contrase\u00F1a aqu\u00ED. Maximo 10 caracteres.");
		this.password.setBounds(162, 86, 86, 20);
		this.getContentPane().add(password);
		this.password.setColumns(10);

		btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		btnCrearUsuario.setBounds(96, 131, 122, 23);
		this.getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(108, 25, 122, 14);
		this.getContentPane().add(lblCrearUsuario);

		btnRegistrarse = new JButton("Registrarse");
		btnRegistrarse.setBounds(96, 165, 122, 23);
		getContentPane().add(btnRegistrarse);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 326, 230);
		this.setLocationRelativeTo(null);
		
		addListener();
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (JOptionPane.showConfirmDialog(getContentPane(), Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA, 
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		    	System.exit(0);
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

		// Calculo hash MD5
		String hashPassword = DigestUtils.md5Hex(this.password.getText());
		//Primero loguea con el server
		Usuario usuario = Cliente.getConexionServidor().loguear(this.username.getText(), hashPassword);
		
		
		JsonObject paqueteLogueoBackoff = 
		Json.createObjectBuilder().add("type", Param.REQUEST_LOGUEO_BACKOFF_CLIENTE)
		.add("username", this.username.getText()).build();
		
		//Luego loguea con el backoff
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteLogueoBackoff);
		
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
			// Usuario duplicado.
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
		btnRegistrarse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				registrarUsuario();
			}
		});
		username.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSession();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		password.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSession();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					iniciarSession();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
}
