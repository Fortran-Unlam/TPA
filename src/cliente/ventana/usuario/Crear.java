package cliente.ventana.usuario;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.apache.commons.codec.digest.DigestUtils;

import cliente.Main;
import cliente.ventana.VentanaMenu;
import looby.Usuario;

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
		lblNewLabel.setBounds(29, 50, 122, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setBounds(29, 75, 132, 14);
		getContentPane().add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("Confirmar contrase\u00F1a");
		lblNewLabel_2.setBounds(29, 100, 142, 14);
		getContentPane().add(lblNewLabel_2);

		username = new JTextField();
		username.setBounds(193, 47, 86, 20);
		getContentPane().add(username);
		username.setColumns(10);

		password = new JTextField();
		password.setBounds(193, 72, 86, 20);
		getContentPane().add(password);
		password.setColumns(10);

		confirmPassword = new JTextField();
		confirmPassword.setBounds(193, 97, 86, 20);
		getContentPane().add(confirmPassword);
		confirmPassword.setColumns(10);

		JButton btnCrearUsuario = new JButton("Crear Usuario");
		btnCrearUsuario.setBounds(109, 146, 122, 23);
		getContentPane().add(btnCrearUsuario);
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					registrarUsuario();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		JLabel lblCrearUsuario = new JLabel("Crear Usuario");
		lblCrearUsuario.setBounds(119, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);
	}
	

	protected void registrarUsuario() throws IOException {

		String hashPassword = DigestUtils.md5Hex(this.password.getText());
		Usuario usuario = Main.getConexionServidor().registrar(this.username.getText(), hashPassword);

		if (usuario != null) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentanaMenu frame = new VentanaMenu();
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

			this.dispose();
		} else {
			JOptionPane.showMessageDialog(null, "No se ha podido registrar el usuario, intentelo nuevamente", "Error login",
					JOptionPane.WARNING_MESSAGE);
		}
	}
	
}
