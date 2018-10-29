package cliente.ventana.usuario;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import PasswordUtils.HashSalt;
import PasswordUtils.PasswordUtil;
import cliente.ConexionServidor;
import cliente.ventana.VentanaMenu;
import looby.Usuario;

public class Login extends JFrame {

	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField username;
	private JTextField password;
	private ConexionServidor conexionServidor;

	public Login(ConexionServidor conexionServidor) {
		this.conexionServidor = conexionServidor;

		this.getContentPane().setLayout(null);
		JLabel usernameLabel = new JLabel("Nombre");
		usernameLabel.setBounds(41, 50, 92, 14);
		this.getContentPane().add(usernameLabel);

		JLabel passwordLabel = new JLabel("Contrase\u00F1a");
		passwordLabel.setBounds(41, 75, 92, 14);
		this.getContentPane().add(passwordLabel);

		this.username = new JTextField();
		this.username.setBounds(143, 47, 86, 20);
		this.getContentPane().add(username);
		this.username.setColumns(10);

		this.password = new JTextField();
		this.password.setBounds(143, 72, 86, 20);
		this.getContentPane().add(password);
		this.password.setColumns(10);

		JButton btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		btnCrearUsuario.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				iniciarSession();
			}
		});
		btnCrearUsuario.setBounds(75, 103, 122, 23);
		this.getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(75, 12, 122, 14);
		this.getContentPane().add(lblCrearUsuario);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(0, 0, 286, 162);
		this.setLocationRelativeTo(null);
	}

	/**
	 * Le dice al servidor que el usuario quiere loguearse. El servidor va a
	 * responder en su debido tiempo y voy a crear un usuario el cual lo uso para
	 * guardarlo y abro la ventana menu cuando este es distinto de null
	 * 
	 */
	protected void iniciarSession() {
		HashSalt hsPassword = null;
		try {
			hsPassword = PasswordUtil.getHash(this.password.getText());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		
		this.conexionServidor.loguear(this.username.getText(), hsPassword);
		Usuario usuario = this.conexionServidor.recibirLogueo();

		
		
		if (usuario != null) {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						VentanaMenu frame = new VentanaMenu(conexionServidor);
						frame.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			this.dispose();
		} else {
			//TODO: mostrar mensaje de error en login
		}
	}
}
