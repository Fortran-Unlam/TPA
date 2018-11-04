package cliente.ventana;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import cliente.ventana.usuario.Login;
import config.Param;
import looby.Usuario;
import javax.swing.JSeparator;

public class VentanaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contenedor;
	private Usuario usuario;

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

		btnCrearSala.setBounds(21, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnCrearSala);

		JButton btnUnirSala = new JButton("Unirse a sala");
		btnUnirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaUnirSala();
			}
		});

		btnUnirSala.setBounds(177, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnUnirSala);

		JLabel lblBienvenidos = new JLabel("Bienvenido/a: " + usuario.getUsername(), SwingConstants.CENTER);
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(21, 11, 451, 38);
		contenedor.add(lblBienvenidos);

		JLabel lblSnakeFortran = new JLabel("Snake by Fortran", SwingConstants.CENTER);
		lblSnakeFortran.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSnakeFortran.setBounds(21, 48, 451, 38);
		contenedor.add(lblSnakeFortran);

		JLabel lblTusEstadsticas = new JLabel("Tus estad\u00EDsticas");
		lblTusEstadsticas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTusEstadsticas.setBounds(10, 103, 145, 14);
		contenedor.add(lblTusEstadsticas);
		
		JLabel lblNewLabel = new JLabel("Puntaje hist\u00F3rico: " + usuario.getPuntos());
		lblNewLabel.setBounds(24, 141, 135, 14);
		contenedor.add(lblNewLabel);
		
		JLabel lblFrutasComidas = new JLabel("Frutas comidas: " + usuario.getCantidadFrutaComida());
		lblFrutasComidas.setBounds(24, 166, 135, 14);
		contenedor.add(lblFrutasComidas);
		
		JLabel lblAsesinatos = new JLabel("Asesinatos: " + usuario.getAsesinatos());
		lblAsesinatos.setBounds(182, 141, 118, 14);
		contenedor.add(lblAsesinatos);
		
		JLabel lblMuertes = new JLabel("Muertes: " + usuario.getMuertes());
		lblMuertes.setBounds(182, 166, 118, 14);
		contenedor.add(lblMuertes);
		
		JLabel lblTPartidasGanadas = new JLabel("T. Partidas Ganadas: " + usuario.getPartidasGanadas());
		lblTPartidasGanadas.setBounds(322, 141, 150, 14);
		contenedor.add(lblTPartidasGanadas);
		
		JLabel lblTRondasGanadas = new JLabel("T. Rondas Ganadas: " + usuario.getRondasGanadas());
		lblTRondasGanadas.setBounds(322, 166, 150, 14);
		contenedor.add(lblTRondasGanadas);
		
		JButton btnAtras = new JButton("Cerrar sesi\u00F3n");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnAtras.setBounds(338, 284, 130, 40);
		contenedor.add(btnAtras);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 128, 462, 2);
		contenedor.add(separator);
	}

	private void abrirVentanaUnirSala() {
		new VentanaUnirSala(this);
	}

	private void abrirVentanaCrearSala() {
		new VentanaCrearSala(this).setVisible(true);
	}
}
