package core;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.HeadlessException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import config.Param;
import config.Posicion;
import input.GestorInput;

public class GestorMapa extends JFrame {

	private static final long serialVersionUID = 48427270007064034L;

	private JPanel contentPane;

	private Mapa mapa;

	public GestorMapa() throws HeadlessException {
		super("Snake");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100,100,Param.ANCHO_MAPA,Param.LARGO_MAPA);
		
		contentPane = new JPanel();
		contentPane.setLayout(new BorderLayout(0,0));
		contentPane.setBorder(new EmptyBorder(0,0,0,0));
		
		setContentPane(contentPane);
		setLocationRelativeTo(null);
		setBackground(Color.black);
		
		// TODO: no me gusta poner el -1
		mapa = new Mapa(Param.ANCHO_MAPA-1, Param.LARGO_MAPA-1);		
		mapa.add(new Fruta(1, 1));
		Vibora vibora = new Vibora(new Coordenada(30, 20), 3, Posicion.ESTE);
		mapa.add(vibora);
		
		this.add(mapa);
		
		addKeyListener(GestorInput.teclado);
		setVisible(true);
				
		while (true) {
			mapa.actualizar();
			
			try {
				Thread.sleep(250);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void paint(Graphics g) {
		super.paint(g);
	}

}
