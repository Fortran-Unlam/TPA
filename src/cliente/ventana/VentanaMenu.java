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

import cliente.ConexionServidor;
import config.Param;
import looby.Sala;


public class VentanaMenu extends JFrame {
	
	//Main para ir probando, borrar luego.
//	public static void main (String[] args) {
//		new VentanaMenu().setVisible(true);
//	}

	private static final long serialVersionUID = 1L;
	private JPanel contenedor;
	private ConexionServidor conexionServidor;
	private JList<String> jListSalas;

	/**
	 * Create the frame.
	 */
	public VentanaMenu(ConexionServidor conexionServidor) {
		this.conexionServidor = conexionServidor;
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
		
		btnCrearSala.setBounds(167, 230, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnCrearSala);
		
		JButton btnUnirSala = new JButton("Unirse a sala");
		btnUnirSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abrirVentanaUnirSala();
			}
		});
		
		btnUnirSala.setBounds(167, 293, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contenedor.add(btnUnirSala);
		
		JLabel lblBienvenidos = new JLabel("BIENVENIDOS !");
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(158, 12, 138, 38);
		contenedor.add(lblBienvenidos);
		
		JLabel lblSnakeFortran = new JLabel("Snake - Fortran");
		lblSnakeFortran.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 14));
		lblSnakeFortran.setBounds(167, 48, 129, 38);
		contenedor.add(lblSnakeFortran);
		
		JLabel lblTusEstadsticas = new JLabel("Tus estad\u00EDsticas");
		lblTusEstadsticas.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblTusEstadsticas.setBounds(10, 103, 145, 14);
		contenedor.add(lblTusEstadsticas);
		
		JTextPane textPane = new JTextPane();
		textPane.setBounds(10, 131, 476, 48);
		contenedor.add(textPane);
		
		
		jListSalas = new JList<String>();
		jListSalas.setBackground(SystemColor.control);
		jListSalas.setBorder(null);
		jListSalas.setBounds(10, 180, 100, 100);
		jListSalas.setEnabled(true);
		
		contenedor.add(jListSalas);
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					jListSalas.setListData(pedirSalas());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		System.out.println("tendria que mostrar");
	}
	
	private void abrirVentanaUnirSala() {
		new VentanaUnirSala(this).setVisible(true);
	}
	
	private void abrirVentanaCrearSala() {
		new VentanaCrearSala(this).setVisible(true);
	}
	
	public String[] pedirSalas() throws ClassNotFoundException {
		System.out.println("va a pedir las salas");
		java.util.List<Sala> salas = this.conexionServidor.getAllSalas();
		System.out.println(salas);
		if (salas == null) {
			return new String[0];
		}
		
		String[] ret = new String[salas.size()];
		for (Iterator<Sala> iterator = salas.iterator(); iterator.hasNext();) {
			Sala sala = (Sala) iterator.next();
			ret[ret.length+1] = sala.getNombre();
		}
		return ret;
	}

}
