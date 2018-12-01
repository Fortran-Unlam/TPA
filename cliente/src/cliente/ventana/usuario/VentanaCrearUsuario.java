package cliente.ventana.usuario;

import java.awt.Event;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

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
import cliente.Message;
import config.Param;

public class VentanaCrearUsuario extends JFrame {

	private static final long serialVersionUID = -8687983933247179801L;
	private JTextField username;
	private JTextField password;
	private JTextField confirmPassword;
	private JButton btnCrearUsuario;
	private JButton btnVolver;
	private JFrame ventanaLogin;

	public VentanaCrearUsuario(JFrame ventanaLogin) {

		this.ventanaLogin = ventanaLogin;
		ventanaLogin.setVisible(false);

		this.setTitle("Registrarse");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 326, 230);
		this.setLocationRelativeTo(null);

		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Usuario");
		lblNewLabel.setBounds(29, 39, 122, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setBounds(29, 64, 132, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Confirmar contrase\u00F1a");
		lblNewLabel_2.setBounds(29, 89, 142, 14);
		getContentPane().add(lblNewLabel_2);

		this.username = new JTextField();
		this.limitar(this.username, Param.LIMITE_CARACTERES_USUARIO);
		
		this.username.setToolTipText("Ingrese el usuario que desee aqu\u00ED . Solo pueden contener letras y numeros. Maximo 20 caracteres.");
		this.username.setBounds(193, 36, 86, 20);
		getContentPane().add(username);
		this.username.setColumns(10);

		this.password = new JPasswordField();
		this.limitar(this.password, Param.LIMITE_CARACTERES_CONTRASENA);
		
		this.password.setToolTipText("Ingrese la contrase\u00F1a que desee aqui. Solo pueden contener letras y numeros. Maximo 10 caracteres.");
		this.password.setBounds(193, 61, 86, 20);
		getContentPane().add(password);
		this.password.setColumns(10);

		this.confirmPassword = new JPasswordField();
		this.limitar(this.confirmPassword, Param.LIMITE_CARACTERES_CONTRASENA);
		
		this.confirmPassword.setToolTipText("Repita la contrase\u00F1a nuevamente. Maximo 10 caracteres.");
		this.confirmPassword.setBounds(193, 86, 86, 20);
		getContentPane().add(confirmPassword);
		this.confirmPassword.setColumns(10);

		this.btnCrearUsuario = new JButton("Crear Cuenta");
		this.btnCrearUsuario.setBounds(92, 125, 122, 23);
		getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Crear Cuenta");
		lblCrearUsuario.setBounds(110, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);

		this.btnVolver = new JButton("Volver");
		this.btnVolver.setBounds(92, 157, 122, 23);
		getContentPane().add(btnVolver);

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

	protected void registrarUsuario(JFrame ventanaLogin) throws IOException {

		if (this.password.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "!Te falto ingresar la contrase�a!", "Error",
					JOptionPane.WARNING_MESSAGE);
			this.password.setFocusable(true);
			this.confirmPassword.setFocusable(true);
			this.confirmPassword.setText("");
			return;
		}

		if (this.confirmPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "!Te falto confirmar la contrase�a!", "Error",
					JOptionPane.WARNING_MESSAGE);
			this.username.setFocusable(true);
			this.confirmPassword.setFocusable(true);
			this.password.setFocusable(true);
			this.password.setText("");
			this.confirmPassword.setText("");
			return;
		}

		if (this.username.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "!Te falto ingresar el usuario!", "Error", JOptionPane.WARNING_MESSAGE);
			this.username.setFocusable(true);
			this.confirmPassword.setFocusable(true);
			this.password.setFocusable(true);
			this.password.setText("");
			this.confirmPassword.setText("");
			return;
		}

		if (!this.username.getText().matches("[a-zA-Z0-9]+")) {
			JOptionPane.showMessageDialog(null, "Los nombres de usuario solo pueden contener letras y numeros.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.username.setText("");
			this.confirmPassword.setFocusable(true);
			this.password.setFocusable(true);
			this.password.setText("");
			this.confirmPassword.setText("");
			this.username.setFocusable(true);
			return;
		}

		if (!this.password.getText().equals(this.confirmPassword.getText())) {
			JOptionPane.showMessageDialog(null, "Las contrase\u00F1as no coinciden, por favor intentelo nuevamente.",
					"Aviso", JOptionPane.WARNING_MESSAGE);
			this.username.setText("");
			this.password.setText("");
			this.confirmPassword.setText("");
			this.username.setFocusable(true);
			this.confirmPassword.setFocusable(true);
			this.password.setFocusable(true);
			return;
		}

		String hashPassword = DigestUtils.md5Hex(this.password.getText());
		Message message = Cliente.getConexionServidor().registrar(this.username.getText(), hashPassword);

		switch (message.getType()) {
		case Param.REQUEST_REGISTRO_INCORRECTO:
			JOptionPane.showMessageDialog(null, "No se ha podido registrar el usuario, intentelo nuevamente",
					"Error login", JOptionPane.ERROR_MESSAGE);
			this.username.setText("");
			this.password.setText("");
			this.confirmPassword.setText("");
			this.username.setFocusable(true);
			break;

		case Param.REQUEST_REGISTRO_CORRECTO:
			JOptionPane.showMessageDialog(null, "!El usuario se ha registrado exitosamente!", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			this.dispose();
			ventanaLogin.setVisible(true);
			break;

		case Param.REQUEST_REGISTRO_DUPLICADO:
			JOptionPane.showMessageDialog(null, "El nombre de usuario ya existe, intentelo nuevamente", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			this.username.setText("");
			this.password.setText("");
			this.confirmPassword.setText("");
			this.username.setFocusable(true);
			break;
		}
	}

	private void addListener() {

		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					registrarUsuario(ventanaLogin);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ventanaLogin.setVisible(true);
			}
		});
	}
}
