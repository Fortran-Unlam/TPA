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

	public VentanaUnirSala() {
		setTitle("Unirse a sala");
		
		//Para mi no deberia cerrarse con la cruz, sino que se deberian utilizar los botones.
		//setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
		list.setBounds(10, 89, 454, 209);
		contentPane.add(list);
		//Agregar lista de salas.
		
		JButton btnUnirse = new JButton("Unirse");
		btnUnirse.setBounds(68, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnUnirse);
		
		JButton btnVolver = new JButton("Volver");
		btnVolver.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();	
			}
		});
		btnVolver.setBounds(267, 309, Param.BOTON_WIDTH, Param.BOTON_HEIGHT);
		contentPane.add(btnVolver);
	}
}
