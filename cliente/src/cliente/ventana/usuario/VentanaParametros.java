package cliente.ventana.usuario;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.SwingUtilities;

import com.javaswingcomponents.accordion.JSCAccordion;
import com.javaswingcomponents.accordion.TabOrientation;
import com.javaswingcomponents.accordion.listener.AccordionEvent;
import com.javaswingcomponents.accordion.listener.AccordionListener;
import com.javaswingcomponents.accordion.plaf.AccordionUI;
import com.javaswingcomponents.accordion.plaf.basic.BasicHorizontalTabRenderer;
import com.javaswingcomponents.accordion.plaf.darksteel.DarkSteelAccordionUI;
import com.javaswingcomponents.framework.painters.configurationbound.GradientColorPainter;

import cliente.Cliente;
import config.Param;

public class VentanaParametros extends JPanel{
	static public JPanel j1;
	static public JPanel j2;
	static public JLabel lhost;
	static public JLabel lpuerto1;
	static public JLabel lpuerto2;
	static public JLabel lpuerto3;
	static public JLabel lpuerto4;
	static public JTextField host;
	static public JTextField puerto1;
	static public JTextField puerto2;
	static public JTextField puerto3;
	static public JTextField puerto4;
	static public JButton aceptar;
	
static JFrame frame;

	/**
	 * This is the correct way to launch a swing application.
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() { 
			@Override
			public void run() 
			{
				VentanaParametros codeExample = new VentanaParametros();
				frame = new JFrame();
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				Container panel = frame.getContentPane();
				panel.setLayout(new BorderLayout());
				panel.add(codeExample, BorderLayout.CENTER);
				frame.pack();
				frame.setSize(250,250);
				frame.setVisible(true);
				//Cuando cierro la ventana en realidad en principio no hago nada.
				frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
				//Cuando la ventana se cierra seteo los parametros y lanzo el cliente.
				frame.addWindowListener(new WindowAdapter() {@Override
				public void windowClosing(WindowEvent e) 
				{
					Param.HOST = host.getText(); //El host es requerido si o si.
					//Los demas parametros vienen por defectos y puedo setear difeentes.
					if(!puerto1.getText().isEmpty()) Param.PORT_1 = Integer.parseInt(puerto1.getText());
					if(!puerto2.getText().isEmpty()) Param.PORT_2 = Integer.parseInt(puerto1.getText());
					if(!puerto3.getText().isEmpty()) Param.PORT_3 = Integer.parseInt(puerto1.getText());
					if(!puerto4.getText().isEmpty()) Param.PORT_4 = Integer.parseInt(puerto1.getText());
					//Oculto la ventana.
					frame.setVisible(false);
					//Lanzo la ventana del login.
					new Cliente();
				}});
			}
		});
	}
	
	public VentanaParametros() {
		JSCAccordion accordion = new JSCAccordion();
		howToAddTabs(accordion);
		howToListenForChanges(accordion);
		howToChangeTabOrientation(accordion);
		howToChangeTheLookAndFeel(accordion);
		howToCustomizeALookAndFeel(accordion);
		setLayout(new GridLayout(1,1,30,30));
		accordion.setTabOrientation(TabOrientation.VERTICAL);
		add(accordion);
		
	}

	/**
	 * When adding a tab to the accordion, you must supply text for the tab
	 * as well as a component that will be used as the content contained for tab.
	 * The example below will add five tabs
	 * The first tab will contain the text "Tab 1" and a JButton
	 * The second tab will contain the text "Tab 2" and a JLabel
	 * The third tab will contain the text "Tab 3" and a JTree wrapped in a JScrollpane
	 * The fourth tab will contain the text "Tab 4" and an empty JPanel, with opaque = true
	 * The fifth tab will contain the text "Tab 5" and an empty JPanel with opaque = false
	 * 
	 * The key thing to note is the effect of adding an opaque or non opaque component to
	 * the accordion.
	 * @param accordion
	 */
	private void howToAddTabs(JSCAccordion accordion) {
		JPanel transparentPanel = new JPanel();
		transparentPanel.setOpaque(false);
		transparentPanel.setBackground(Color.GRAY);
		
		JPanel opaquePanel = new JPanel();
		opaquePanel.setOpaque(true);
		opaquePanel.setBackground(Color.GRAY);
		//Pestanas.
		accordion.addTab("Configuracion General", VentanaParametros.j1 = new JPanel());
		accordion.addTab("Configuraciones Avanzadas", VentanaParametros.j2 = new JPanel());
		VentanaParametros.j1.setLayout(null);
		VentanaParametros.j2.setLayout(null);
		//JLabels
		VentanaParametros.j1.add(VentanaParametros.lhost = new JLabel(""));
		VentanaParametros.j2.add(VentanaParametros.lpuerto1 = new JLabel(""));
		VentanaParametros.j2.add(VentanaParametros.lpuerto2 = new JLabel(""));
		VentanaParametros.j2.add(VentanaParametros.lpuerto3 = new JLabel(""));
		VentanaParametros.j2.add(VentanaParametros.lpuerto4 = new JLabel(""));
		//JTextFields
		VentanaParametros.j1.add(VentanaParametros.host = new JTextField(""));
		VentanaParametros.j2.add(VentanaParametros.puerto1 = new JTextField(""));
		VentanaParametros.j2.add(VentanaParametros.puerto2 = new JTextField(""));
		VentanaParametros.j2.add(VentanaParametros.puerto3 = new JTextField(""));
		VentanaParametros.j2.add(VentanaParametros.puerto4 = new JTextField(""));
		//Pestana 1
		VentanaParametros.lhost.setText("Host");
		VentanaParametros.lhost.setBounds(20, 24, 92, 14);
		VentanaParametros.host.setBounds(60, 24, 92, 14);
		//Pestana 2
		VentanaParametros.lpuerto1.setText("Puerto 1");
		VentanaParametros.lpuerto2.setText("Puerto 2");
		VentanaParametros.lpuerto3.setText("Puerto 3");
		VentanaParametros.lpuerto4.setText("Puerto 4");
		VentanaParametros.lpuerto1.setBounds(20, 24, 92, 14);
		VentanaParametros.lpuerto2.setBounds(20, 44, 92, 14);
		VentanaParametros.lpuerto3.setBounds(20, 64, 92, 14);
		VentanaParametros.lpuerto4.setBounds(20, 84, 92, 14);
		VentanaParametros.puerto1.setBounds(90, 24, 92, 14);
		VentanaParametros.puerto2.setBounds(90, 44, 92, 14);
		VentanaParametros.puerto3.setBounds(90, 64, 92, 14);
		VentanaParametros.puerto4.setBounds(90, 84, 92, 14);
		
		//accordion.addTab("Tab 3", new JScrollPane(new JTree()));
		//accordion.addTab("Tab 4", opaquePanel);
		//accordion.addTab("Tab 5", transparentPanel);
	}
	
	/**
	 * It can be useful to be notified when changes occur on the accordion. 
	 * The accordion can notify a listener when a tab is added, selected or removed.
	 * @param accordion
	 */
	private void howToListenForChanges(JSCAccordion accordion) {
		accordion.addAccordionListener(new AccordionListener() {
			
			@Override
			public void accordionChanged(AccordionEvent accordionEvent) {
				//available fields on accordionEvent.
				
				switch (accordionEvent.getEventType()) {
				case TAB_ADDED: {
					//add your logic here to react to a tab being added.
					break;
				}
				case TAB_REMOVED: {
					//add your logic here to react to a tab being removed.
					break;					
				}
				case TAB_SELECTED: {
					//add your logic here to react to a tab being selected.
					break;					
				}
				}
			}
		});
	}
	
	/**
	 * You can change the tab orientation to slide either vertically or horizontally.
	 * @param accordion
	 */
	private void howToChangeTabOrientation(JSCAccordion accordion) {
		//will make the accordion slide from top to bottom
		accordion.setTabOrientation(TabOrientation.VERTICAL);
		
		//will make the accordion slide from left ro right
		accordion.setTabOrientation(TabOrientation.HORITZONTAL);
	}
	
	/**
	 * You can change the look and feel of the component by changing its ui.
	 * In this example we will change the UI to the DarkSteelUI
	 * @param accordion
	 */
	private void howToChangeTheLookAndFeel(JSCAccordion accordion) {
		//We create a new instance of the UI
		AccordionUI newUI = DarkSteelAccordionUI.createUI(accordion);
		//We set the UI
		accordion.setUI(newUI);
	}
	
	/**
	 * The easiest way to customize a AccordionUI is to change the 
	 * default Background Painter, AccordionTabRenderers or tweak values
	 * on the currently installed Background Painter, AccordionTabRenderers and UI.
	 * @param accordion
	 */
	private void howToCustomizeALookAndFeel(JSCAccordion accordion) {
		//example of changing a value on the ui.
		DarkSteelAccordionUI ui = (DarkSteelAccordionUI) accordion.getUI();
		ui.setHorizontalBackgroundPadding(10);
		
		//example of changing the AccordionTabRenderer
		BasicHorizontalTabRenderer tabRenderer = new BasicHorizontalTabRenderer(accordion);
		tabRenderer.setFontColor(Color.RED);
		accordion.setHorizontalAccordionTabRenderer(tabRenderer);
		
		//example of changing the background painter.
		GradientColorPainter backgroundPainter = (GradientColorPainter) accordion.getBackgroundPainter();
		backgroundPainter = (GradientColorPainter) accordion.getBackgroundPainter();
		backgroundPainter.setStartColor(Color.BLACK);
		backgroundPainter.setEndColor(Color.WHITE);
		
		//the outcome of this customization is not the most visually appealing result
		//but it just serves to illustrate how to customize the accordion's look and feel.
		//The UI is darkSteel.
		//The backgroundPainter is a gradient running from Black to White
		//The accordionTabRenderer belongs to the BasicAccordionUI
		//And finally the text of the tab is red!
	}
}
