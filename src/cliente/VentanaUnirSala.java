package cliente;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import config.Param;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class VentanaUnirSala extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private VentanaMenu principal;

	public VentanaUnirSala(VentanaMenu principal) {
		this.principal = principal;
		principal.setVisible(false);
		
		setTitle("Unirse a sala");
		//Por ahora Exit on close. Se puede poner dispose_on_close pero tengo que terminar de ver
		//el evento windowslistener para que no quede invisible la vista del menu ppcal.
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		setBounds(0, 0, Param.VENTANA_CLIENTE_WIDTH, Param.VENTANA_CLIENTE_HEIGHT);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		JLabel lblSalasDisponibles = new JLabel("Salas disponibles:");
		lblSalasDisponibles.setForeground(Color.MAGENTA);
		lblSalasDisponibles.setFont(new Font("Tahoma", Font.BOLD, 17));
		lblSalasDisponibles.setBounds(10, 54, 190, 27);
		contentPane.add(lblSalasDisponibles);
		
		JList list = new JList();
		list.setBounds(12, 98, 454, 209);
		contentPane.add(list);
		//Agregar lista de salas. Ver de donde viene la lista.
		
		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				principal.setVisible(true);
				dispose();	
			}
		});
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
		setLocationRelativeTo(principal);
		
		JButton btnNewButton = new JButton("Refrescar salas");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//Ver como refrescar salas cuando tengamos eso listo.
			}
		});
		btnNewButton.setBounds(267, 55, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnNewButton);
	}
}
