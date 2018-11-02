package cliente.ventana;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import cliente.ventana.usuario.Login;
import config.Param;
import looby.Sala;
import looby.Usuario;

public class VentanaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contenedor;
	private JList<String> jListSalas;
	private JTextPane estadistica;
	private Usuario usuario;

	/**
	 * Create the frame.
	 */
	public VentanaMenu() {
		
		usuario = Main.getConexionServidor().getUsuario();
		
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contenedor = new JPanel();
		contenedor.setBorder(new EmptyBorder(5, 5, 5, 5));
		contenedor.setLayout(null);
		setContentPane(contenedor);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnCrearSala = new JButton("Crear sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaCrearSala();
			}
		});

		btnCrearSala.setBounds(166, 259, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnCrearSala);

		JButton btnUnirSala = new JButton("Unirse a sala");
		btnUnirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaUnirSala();
			}
		});

		btnUnirSala.setBounds(166, 310, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnUnirSala);

		JLabel lblBienvenidos = new JLabel("Bienvenido/a: " + usuario.getUsername());
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(141, 11, 258, 38);
		contenedor.add(lblBienvenidos);

		JLabel lblSnakeFortran = new JLabel("Snake - Fortran");
		lblSnakeFortran.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblSnakeFortran.setBounds(167, 48, 129, 38);
		contenedor.add(lblSnakeFortran);

		JLabel lblTusEstadsticas = new JLabel("Tus estad\u00EDsticas");
		lblTusEstadsticas.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTusEstadsticas.setBounds(10, 103, 145, 14);
		contenedor.add(lblTusEstadsticas);
		
		JLabel lblNewLabel = new JLabel("Puntaje hist\u00F3rico: " + usuario.getPuntos());
		lblNewLabel.setBounds(20, 148, 135, 14);
		contenedor.add(lblNewLabel);
		
		JLabel lblFrutasComidas = new JLabel("Frutas comidas: " + usuario.getCantidadFrutaComida());
		lblFrutasComidas.setBounds(20, 173, 135, 14);
		contenedor.add(lblFrutasComidas);
		
		JLabel lblAsesinatos = new JLabel("Asesinatos: " + usuario.getAsesinatos());
		lblAsesinatos.setBounds(178, 148, 118, 14);
		contenedor.add(lblAsesinatos);
		
		JLabel lblMuertes = new JLabel("Muertes: " + usuario.getMuertes());
		lblMuertes.setBounds(178, 173, 118, 14);
		contenedor.add(lblMuertes);
		
		JLabel lblTPartidasGanadas = new JLabel("T. Partidas Ganadas: " + usuario.getPartidasGanadas());
		lblTPartidasGanadas.setBounds(318, 148, 150, 14);
		contenedor.add(lblTPartidasGanadas);
		
		JLabel lblTRondasGanadas = new JLabel("T. Rondas Ganadas: " + usuario.getRondasGanadas());
		lblTRondasGanadas.setBounds(318, 173, 150, 14);
		contenedor.add(lblTRondasGanadas);
<<<<<<< HEAD
		
		JButton btnAtras = new JButton("Atras");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnAtras.setBounds(354, 310, 130, 40);
		contenedor.add(btnAtras);

//		jListSalas = new JList<String>();
//		jListSalas.setBackground(SystemColor.control);
//		jListSalas.setBorder(null);
//		jListSalas.setBounds(10, 180, 100, 100);
//		jListSalas.setEnabled(true);

//		// TODO: pasar esto a unir sala
//		contenedor.add(jListSalas);
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					jListSalas.setListData(pedirSalas());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
=======
>>>>>>> b9dcc7c0df3db16a8dcfb00a3ae41d4877d94e39
	}

	private void abrirVentanaUnirSala() {
		new VentanaUnirSala(this);
	}

	private void abrirVentanaCrearSala() {
		new VentanaCrearSala(this).setVisible(true);
	}

}
