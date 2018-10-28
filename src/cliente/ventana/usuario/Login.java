package cliente.ventana.usuario;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login extends JFrame {
	
	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField nombre;
	private JTextField contrasena;

	public Login() {
		getContentPane().setLayout(null);

		JLabel nombreLabel = new JLabel("Nombre");
		nombreLabel.setBounds(41, 50, 92, 14);
		getContentPane().add(nombreLabel);

		JLabel contrasenaLabel = new JLabel("Contrase\u00F1a");
		contrasenaLabel.setBounds(41, 75, 92, 14);
		getContentPane().add(contrasenaLabel);

		nombre = new JTextField();
		nombre.setBounds(143, 47, 86, 20);
		getContentPane().add(nombre);
		nombre.setColumns(10);

		contrasena = new JTextField();
		contrasena.setBounds(143, 72, 86, 20);
		getContentPane().add(contrasena);
		contrasena.setColumns(10);

		JButton btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		btnCrearUsuario.setBounds(75, 103, 122, 23);
		getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(119, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);
	}
}
