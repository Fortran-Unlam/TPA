package cliente.ventana;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import config.Param;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextPane;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
	}

	/**
	 * Create the frame.
	 */
	public VentanaMenu() {
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JButton btnCrearSala = new JButton("Crear sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaCrearSala();
			}
		});
		
		btnCrearSala.setBounds(167, 230, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnCrearSala);
		
		JButton btnUnirSala = new JButton("Unirse a sala");
		btnUnirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaUnirSala();
			}
		});
		
		btnUnirSala.setBounds(167, 293, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirSala);
		
		JLabel lblBienvenidos = new JLabel("BIENVENIDOS !");
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(158, 12, 138, 38);
		contentPane.add(lblBienvenidos);
		
		JLabel lblSnakeFortran = new JLabel("Snake - Fortran");
		lblSnakeFortran.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblSnakeFortran.setBounds(167, 48, 129, 38);
		contentPane.add(lblSnakeFortran);
		
		JLabel lblTusEstadsticas = new JLabel("Tus estad\u00EDsticas");
		lblTusEstadsticas.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTusEstadsticas.setBounds(10, 103, 145, 14);
		contentPane.add(lblTusEstadsticas);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 131, 476, 48);
		contentPane.add(textPane);
	}
	
	private void abrirVentanaUnirSala() {
		new VentanaUnirSala(this).setVisible(true);
	}
	
	private void abrirVentanaCrearSala() {
		new VentanaCrearSala(this).setVisible(true);
	}

}
