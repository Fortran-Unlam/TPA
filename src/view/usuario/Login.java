package view.usuario;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Login extends JFrame {
	
	private static final long serialVersionUID = 6592698064274884489L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;

	public Login() {
		getContentPane().setLayout(null);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(41, 50, 122, 14);
		getContentPane().add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Contrase\u00F1a");
		lblNewLabel_1.setBounds(41, 75, 132, 14);
		getContentPane().add(lblNewLabel_1);

		textField = new JTextField();
		textField.setBounds(193, 47, 86, 20);
		getContentPane().add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBounds(193, 72, 86, 20);
		getContentPane().add(textField_1);
		textField_1.setColumns(10);

		textField_2 = new JTextField();
		textField_2.setBounds(193, 97, 86, 20);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);

		JButton btnCrearUsuario = new JButton("Iniciar Sesi\u00F3n");
		btnCrearUsuario.setBounds(109, 146, 122, 23);
		getContentPane().add(btnCrearUsuario);

		JLabel lblCrearUsuario = new JLabel("Inciar Sesi\u00F3n");
		lblCrearUsuario.setBounds(119, 11, 86, 14);
		getContentPane().add(lblCrearUsuario);
	}
}
