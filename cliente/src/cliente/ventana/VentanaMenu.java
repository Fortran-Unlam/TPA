package cliente.ventana;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.json.Json;
import javax.json.JsonObject;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.Sonido;
import cliente.Usuario;
import cliente.ventana.usuario.VentanaLoginUsuario;
import config.Param;

public class VentanaMenu extends JFrame {

	private static final long serialVersionUID = 4181375175617378911L;
	private JPanel contentPane;
	private Usuario usuario;
	private JButton btnAtras;
	private JButton btnUnirSala;
	private JButton btnCrearSala;

	public VentanaMenu() {

		usuario = Cliente.getConexionServidor().getUsuario();

		setTitle("Menu principal");
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setContentPane(contentPane);
		setResizable(false);
		setLocationRelativeTo(null);

		btnCrearSala = new JButton("Crear sala");

		btnCrearSala.setBounds(21, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnCrearSala);

		btnUnirSala = new JButton("Unirse a sala");

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
		lblNewLabel.setBounds(14, 141, 135, 14);
		lblNewLabel.setSize(lblNewLabel.getPreferredSize());
		contentPane.add(lblNewLabel);

		JLabel lblFrutasComidas = new JLabel("Frutas comidas: " + usuario.getCantidadFrutaComida());
		lblFrutasComidas.setBounds(14, 166, 135, 14);
		lblFrutasComidas.setSize(lblFrutasComidas.getPreferredSize());
		contentPane.add(lblFrutasComidas);
		

		JLabel lblAsesinatos = new JLabel("Asesinatos: " + usuario.getAsesinatos());
		lblAsesinatos.setBounds(182, 141, 118, 14);
		contentPane.add(lblAsesinatos);

		JLabel lblMuertes = new JLabel("Muertes: " + usuario.getMuertes());
		lblMuertes.setBounds(182, 166, 118, 14);
		contentPane.add(lblMuertes);

		JLabel lblTPartidasGanadas = new JLabel("T. Partidas Ganadas: " + usuario.getPartidasGanadas());
		lblTPartidasGanadas.setBounds(302, 141, 150, 14);
		lblTPartidasGanadas.setSize(lblTPartidasGanadas.getPreferredSize());
		contentPane.add(lblTPartidasGanadas);

		JLabel lblTRondasGanadas = new JLabel("T. Rondas Ganadas: " + usuario.getRondasGanadas());
		lblTRondasGanadas.setBounds(302, 166, 150, 14);
		lblTRondasGanadas.setSize(lblTRondasGanadas.getPreferredSize());
		contentPane.add(lblTRondasGanadas);

		btnAtras = new JButton("Cerrar sesi\u00F3n");

		btnAtras.setBounds(338, 284, 130, 40);
		contentPane.add(btnAtras);

		JSeparator separator = new JSeparator();
		separator.setBounds(10, 128, 462, 2);
		contentPane.add(separator);

		addListener();

		this.addWindowListener(new java.awt.event.WindowAdapter() {
			@Override
			public void windowClosing(java.awt.event.WindowEvent windowEvent) {
				if (JOptionPane.showConfirmDialog(contentPane, Param.MENSAJE_CERRAR_VENTANA, Param.TITLE_CERRAR_VENTANA,
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
					Cliente.getConexionServidor().cerrarSesionUsuario(usuario);
					System.exit(0);
				}
			}
		});

	}

	private void abrirVentanaUnirSala() {
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		VentanaUnirSala ventanaUnirSala = new VentanaUnirSala(this);
		Sincronismo.setVentanaUnirSala(ventanaUnirSala);
		
		JsonObject paqueteIngresoVentanaUnirSala = Json.createObjectBuilder()
				.add("type", Param.NOTICE_ENTRAR_A_VER_SALAS).build();

		// Le aviso al sv que me actualice las salas, el cliente se las auto-actualiza
		Cliente.getconexionServidorBackOff().enviarAlServer(paqueteIngresoVentanaUnirSala);

		ventanaUnirSala.setVisible(true);
		this.setVisible(false);
	}

	private void abrirVentanaCrearSala() {
		Sonido musicaFondo = new Sonido(Param.SONIDO_GOLPE_PATH);
		musicaFondo.reproducir();
		new VentanaCrearSala(this).setVisible(true);
	}

	public Usuario getUsuario() {
		return this.usuario;
	}

	private void addListener() {
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
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Cliente.getConexionServidor().cerrarSesionUsuario(usuario);
				dispose();
				VentanaLoginUsuario login = new VentanaLoginUsuario();
				login.setVisible(true);
			}
		});
		btnAtras.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				Cliente.getConexionServidor().cerrarSesionUsuario(usuario);
				dispose();
				VentanaLoginUsuario login = new VentanaLoginUsuario();
				login.setVisible(true);
			}
		});
	}
}
