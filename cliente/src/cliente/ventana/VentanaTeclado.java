package cliente.ventana;

import java.awt.Font;
import java.awt.Window;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import cliente.Cliente;
import cliente.Usuario;
import config.Param;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaTeclado extends JFrame {

	private static final long serialVersionUID = 4181375175617378911L;
	private JPanel contentPane;
	private Usuario usuario;
	private JButton btnCancelar;
	private JButton btnGuardar;
	private JTextField inputArriba;
	private JTextField inputDerecha;
	private JTextField inputAbajo;
	private JTextField inputIzquierda;
	private JFrame ventanaAnterior;
	private VentanaTeclado ventana;

	public VentanaTeclado(JFrame frame) {
		this.ventana = this;
		this.ventanaAnterior = frame;
		this.ventanaAnterior.setVisible(false);
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

		btnGuardar = new JButton("Guardar");

		btnGuardar.setBounds(55, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnGuardar);

		btnCancelar = new JButton("Cancelar");

		btnCancelar.setBounds(233, 284, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnCancelar);

		JLabel lblBienvenidos = new JLabel("Configure el teclado", SwingConstants.CENTER);
		lblBienvenidos.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblBienvenidos.setBounds(21, 11, 451, 38);
		contentPane.add(lblBienvenidos);

		JLabel lblSnakeFortran = new JLabel("Snake by Fortran", SwingConstants.CENTER);
		lblSnakeFortran.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblSnakeFortran.setBounds(21, 48, 451, 38);
		contentPane.add(lblSnakeFortran);

		JLabel lblNewLabel = new JLabel("Arriba");
		lblNewLabel.setBounds(21, 97, 135, 14);
		lblNewLabel.setSize(lblNewLabel.getPreferredSize());
		contentPane.add(lblNewLabel);

		JLabel labelAbajo = new JLabel("Abajo");
		labelAbajo.setBounds(22, 136, 135, 14);
		labelAbajo.setSize(labelAbajo.getPreferredSize());
		contentPane.add(labelAbajo);
		

		JLabel labelDerecha = new JLabel("Derecha");
		labelDerecha.setBounds(21, 206, 56, 14);
		contentPane.add(labelDerecha);
		labelDerecha.setVisible(false);

		JLabel lblMuertes = new JLabel("Izquierda");
		lblMuertes.setBounds(21, 175, 56, 14);
		contentPane.add(lblMuertes);
		
		inputArriba = new JTextField();
		inputArriba.setBounds(86, 97, 20, 20);
		contentPane.add(inputArriba);
		inputArriba.setColumns(10);
		
		inputDerecha = new JTextField();
		inputDerecha.setBounds(87, 203, 20, 20);
		contentPane.add(inputDerecha);
		inputDerecha.setColumns(10);
		
		inputAbajo = new JTextField();
		inputAbajo.setColumns(10);
		inputAbajo.setBounds(86, 133, 20, 20);
		contentPane.add(inputAbajo);
		
		inputIzquierda = new JTextField();
		inputIzquierda.setColumns(10);
		inputIzquierda.setBounds(86, 172, 20, 20);
		contentPane.add(inputIzquierda);

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
		
		setVisible(true);
	}


	public Usuario getUsuario() {
		return this.usuario;
	}

	private void addListener() {
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (inputArriba.hasFocus()) {
					inputArriba.setText(String.valueOf(e.getKeyChar()));
				}

				if (inputAbajo.hasFocus()) {
					inputAbajo.setText(String.valueOf(e.getKeyChar()));
				}
				
				if (inputIzquierda.hasFocus()) {
					inputIzquierda.setText(String.valueOf(e.getKeyChar()));
				}
				
				if (inputDerecha.hasFocus()) {
					inputDerecha.setText(String.valueOf(e.getKeyChar()));
				}
			}
		});
		
		btnCancelar.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ventanaAnterior.setVisible(true);
				ventana.dispose();
			}
		});
	}
}
