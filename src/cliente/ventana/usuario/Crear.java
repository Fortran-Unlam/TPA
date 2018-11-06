package cliente.ventana.usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import org.apache.commons.codec.digest.DigestUtils;
import cliente.Main;
import config.Param;
import servidor.Message;

public class Crear extends JFrame {

	private static final long serialVersionUID = -8687983933247179801L;
	private JTextField username;
	private JTextField password;
	private JTextField confirmPassword;

	public Crear(JFrame ventanaLogin) {

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

		username = new JTextField();
		username.setToolTipText("Ingrese el usuario que desee aqu\u00ED . Solo pueden contener letras y numeros.");
		username.setBounds(193, 36, 86, 20);
		getContentPane().add(username);
		username.setColumns(10);

		password = new JPasswordField();
		password.setToolTipText("Ingrese la contrase\u00F1a que desee aqu\u00ED . Solo pueden contener letras y numeros.");
		password.setBounds(193, 61, 86, 20);
		getContentPane().add(password);
		password.setColumns(10);

		confirmPassword = new JPasswordField();
		confirmPassword.setToolTipText("Repita la contrase\u00F1a nuevamente.");
		confirmPassword.setBounds(193, 86, 86, 20);
		getContentPane().add(confirmPassword);
		confirmPassword.setColumns(10);

		JButton btnCrearUsuario = new JButton("Crear Cuenta");
		btnCrearUsuario.setBounds(92, 125, 122, 23);
		getContentPane().add(btnCrearUsuario);
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					registrarUsuario(ventanaLogin);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JLabel lblCrearUsuario = new JLabel("Crear Cuenta");
		lblCrearUsuario.setBounds(110, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);

		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				ventanaLogin.setVisible(true);
			}
		});
		btnVolver.setBounds(92, 157, 122, 23);
		getContentPane().add(btnVolver);
	}

	protected void registrarUsuario(JFrame ventanaLogin) throws IOException {

		if (this.password.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "¡Te falto ingresar la contraseña!", "Error",
					JOptionPane.WARNING_MESSAGE);
			this.password.setFocusable(true);
			return;
		}

		if (this.confirmPassword.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "¡Te falto confirmar la contraseña!", "Error",
					JOptionPane.WARNING_MESSAGE);
			this.username.setFocusable(true);
			this.confirmPassword.setFocusable(true);
			this.password.setFocusable(true);
			this.password.setText("");
			this.confirmPassword.setText("");
			return;
		}

		if (this.username.getText().isEmpty()) {
			JOptionPane.showMessageDialog(null, "¡Te falto ingresar el usuario!", "Error", JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog(null, "Las contraseñas no coinciden, por favor intentelo nuevamente.",
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
		Message message = Main.getConexionServidor().registrar(this.username.getText(), hashPassword);

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
				JOptionPane.showMessageDialog(null, "¡El usuario se ha registrado exitosamente!", "Aviso",
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
}
