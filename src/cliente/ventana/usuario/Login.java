package cliente.ventana.usuario;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import cliente.ConexionServidor;

public class Login extends JFrame {
	
	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField username;
	private JTextField password;
	private ConexionServidor conexionServidor;

	public Login(ConexionServidor conexionServidor) {
		this.conexionServidor = conexionServidor;
		
		getContentPane().setLayout(null);
		JLabel usernameLabel = new JLabel("Nombre");
		usernameLabel.setBounds(41, 50, 92, 14);
		getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Contrase\u00F1a");
		passwordLabel.setBounds(41, 75, 92, 14);
		getContentPane().add(passwordLabel);

		username = new JTextField();
		username.setBounds(143, 47, 86, 20);
		getContentPane().add(username);
		username.setColumns(10);

		password = new JTextField();
		password.setBounds(143, 72, 86, 20);
		getContentPane().add(password);
		password.setColumns(10);

		JButton btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarSession();
			}
		});
		btnCrearUsuario.setBounds(75, 103, 122, 23);
		getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(119, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 286, 162);
		this.setLocationRelativeTo(null);
	}

	protected void iniciarSession() {
		this.conexionServidor.loguear(this.username.getText(), this.password.getText());
	}
}
