package cliente.ventana;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Main;
import cliente.Sonido;
import cliente.ventana.usuario.Login;
import config.Param;
import looby.Usuario;
import javax.swing.JSeparator;

public class VentanaMenu extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Usuario usuario;

	public VentanaMenu() {
		
		usuario = Main.getConexionServidor().getUsuario();
		
		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setLocationRelativeTo(null);

		JButton btnCrearSala = new JButton("Crear sala");
		btnCrearSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
				abrirVentanaCrearSala();
			}
		});

		btnCrearSala.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					abrirVentanaCrearSala();
				}
			}
		});
		
		btnCrearSala.setBounds(21, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnCrearSala);

		JButton btnUnirSala = new JButton("Unirse a sala");
		btnUnirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaUnirSala();
			}
		});

		btnUnirSala.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					abrirVentanaUnirSala();
				}
			}
		});
		
		btnUnirSala.setBounds(177, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirSala);

		JLabel lblBienvenidos = new JLabel("Bienvenido/a: " + usuario.getUsername(), SwingConstants.CENTER);
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(21, 11, 451, 38);
		contentPane.add(lblBienvenidos);

		JLabel lblSnakeFortran = new JLabel("Snake by Fortran", SwingConstants.CENTER);
		lblSnakeFortran.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSnakeFortran.setBounds(21, 48, 451, 38);
		contentPane.add(lblSnakeFortran);

		JLabel lblTusEstadsticas = new JLabel("Tus estad\u00EDsticas");
		lblTusEstadsticas.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblTusEstadsticas.setBounds(10, 103, 145, 14);
		contentPane.add(lblTusEstadsticas);
		
		JLabel lblNewLabel = new JLabel("Puntaje hist\u00F3rico: " + usuario.getPuntos());
		lblNewLabel.setBounds(24, 141, 135, 14);
		contentPane.add(lblNewLabel);
		
		JLabel lblFrutasComidas = new JLabel("Frutas comidas: " + usuario.getCantidadFrutaComida());
		lblFrutasComidas.setBounds(24, 166, 135, 14);
		contentPane.add(lblFrutasComidas);
		
		JLabel lblAsesinatos = new JLabel("Asesinatos: " + usuario.getAsesinatos());
		lblAsesinatos.setBounds(182, 141, 118, 14);
		contentPane.add(lblAsesinatos);
		
		JLabel lblMuertes = new JLabel("Muertes: " + usuario.getMuertes());
		lblMuertes.setBounds(182, 166, 118, 14);
		contentPane.add(lblMuertes);
		
		JLabel lblTPartidasGanadas = new JLabel("T. Partidas Ganadas: " + usuario.getPartidasGanadas());
		lblTPartidasGanadas.setBounds(322, 141, 150, 14);
		contentPane.add(lblTPartidasGanadas);
		
		JLabel lblTRondasGanadas = new JLabel("T. Rondas Ganadas: " + usuario.getRondasGanadas());
		lblTRondasGanadas.setBounds(322, 166, 150, 14);
		contentPane.add(lblTRondasGanadas);
		
		JButton btnAtras = new JButton("Cerrar sesi\u00F3n");
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Main.getConexionServidor().cerrarSesionUsuario(usuario);
				dispose();
				Login login = new Login();
				login.setVisible(true);
			}
		});
		btnAtras.setBounds(338, 284, 130, 40);
		contentPane.add(btnAtras);
		
		JSeparator separator = new JSeparator();
		separator.setBounds(10, 128, 462, 2);
		contentPane.add(separator);
		
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA, 
		                JOptionPane.YES_NO_OPTION,
		                JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION){
		    	Main.getConexionServidor().cerrarSesionUsuario(usuario);
		    	System.exit(0);
		    	}
		    }
		});
		
	}

	private void abrirVentanaUnirSala() {
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaUnirSala(this);
	}

	private void abrirVentanaCrearSala() {
		Sonido musicaFondo = new Sonido(Param.GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaCrearSala(this).setVisible(true);
	}
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
}
