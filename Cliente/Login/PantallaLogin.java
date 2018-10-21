package Login;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import ConexionServidor.*;
import Seguridad.*;

import javax.swing.JPasswordField;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

@SuppressWarnings("serial")
public class PantallaLogin extends JFrame {

	private JFrame frame;
	private JTextField textUsuario;
	private JPasswordField textPassword;
	private Socket socket;
	public static JLabel lblEstado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PantallaLogin window = new PantallaLogin();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public PantallaLogin() 
	{
		//Nombre de la ventana.
        super("Viborita Login");
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setBounds(12, 25, 70, 15);
		frame.getContentPane().add(lblUsuario);
		
		JLabel lblContrasena = new JLabel("Contrasena");
		lblContrasena.setBounds(12, 89, 83, 15);
		frame.getContentPane().add(lblContrasena);
		
		textUsuario = new JTextField();
		textUsuario.setBounds(117, 23, 114, 19);
		frame.getContentPane().add(textUsuario);
		textUsuario.setColumns(10);
		
		textPassword = new JPasswordField();
		textPassword.setBounds(113, 87, 118, 19);
		frame.getContentPane().add(textPassword);
		
		// Se crea el socket para conectar con el Servidor del juego.
        try 
        {
            this.socket = new Socket(Configuraciones.HOST, Configuraciones.PUERTO);
        } 
        catch (UnknownHostException ex) 
        {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        } 
        catch (IOException ex) 
        {
            System.out.println("No se ha podido conectar con el servidor (" + ex.getMessage() + ").");
        }
        
        
		
		JButton btnIngresar = new JButton("Ingresar");
		//Clickear sobre el boton ingresar.
		btnIngresar.addMouseListener(new MouseAdapter() 
		{
			@Override
			public void mouseClicked(MouseEvent e) 
			{
				ConexionServidor con = new ConexionServidor(socket);
				//Encripto ambos campos.
				String usuario = Seguridad.encrypt(textUsuario.getText());
				String contrasena = Seguridad.encrypt(String.valueOf(textPassword.getPassword()));
				con.logear(usuario, contrasena);
				//Empiezo a escuchar del servidor.
				con.recibirMensajesServidor();
			}
		});
		btnIngresar.setBounds(156, 212, 117, 25);
		frame.getContentPane().add(btnIngresar);
		
		lblEstado = new JLabel("");
		lblEstado.setBounds(12, 245, 426, 15);
		frame.getContentPane().add(lblEstado);
	}
}
