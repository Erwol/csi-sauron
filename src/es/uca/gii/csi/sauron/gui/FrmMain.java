package es.uca.gii.csi.sauron.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class FrmMain {
	private JFrame _frame;
	private static ArrayList<IfrSala> _aIfrSala = new ArrayList<IfrSala>();
	
	public static void addIfrSala(IfrSala ifrSala){
		_aIfrSala.add(ifrSala);
	}
	
	// Función que determina si hay un formulario de detalle abierto de una determinada $Entidad$
	public static boolean isIfrSalaOpen(IfrSala ifrSala){
		boolean bIsOpen = false, bFin = false;
		// Recorremos el _aIfrSala en busca de una instancia de IfrSala cuya Sala tenga una Id igual 
		// a la nueva instancia de IfrSala que tratamos de abrir (la que recibimos como parámetro)
		for(int i = 0; i < _aIfrSala.size() && !bFin; i++)
			// Comprobamos si la Id es igual, y luego, si la instancia no está cerrada (de estarlo, return true)
			if(_aIfrSala.get(i).getSala().getId() == ifrSala.getSala().getId() &&
					!_aIfrSala.get(i).isClosed()){
				// Con bFin ahorramos iteraciones
				bFin = true;
				bIsOpen = true;
			}
		return bIsOpen;
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmMain window = new FrmMain();
					window._frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public FrmMain() throws Exception{
		initialize();
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() throws Exception{
		_frame = new JFrame();
		_frame.setTitle("Gimnasio Hammer");
		_frame.setBounds(100, 100, 450, 300);
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		
		JMenu mitNew = new JMenu("Nuevo");
		menuBar.add(mitNew);
		
		JMenuItem mitNewSala = new JMenuItem("Sala");
		mitNewSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				IfrSala ifrSala = new IfrSala(null);
				ifrSala.setBounds(0, 0, _frame.getWidth() / 2, _frame.getHeight());
				_frame.getContentPane().add(ifrSala);
				ifrSala.setVisible(true);
			}
		});
		mitNew.add(mitNewSala);
		
		JMenu mitSearch = new JMenu("Buscar");
		menuBar.add(mitSearch);
		
		JMenuItem mitSearchSala = new JMenuItem("Sala");
		mitSearchSala.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					IfrSalas ifrSalas = null;
					ifrSalas = new IfrSalas(_frame);
					ifrSalas.setBounds(_frame.getWidth() / 2, 0, _frame.getWidth() / 2, _frame.getHeight());
					// El segundo parámetro es para que siempre aparezca delante
					_frame.getContentPane().add(ifrSalas, 0);
					ifrSalas.setVisible(true);
				}catch(Exception e1){
					JOptionPane.showMessageDialog(null, "¡Error en la búsqueda!");
				}
				
			}
		});
		mitSearch.add(mitSearchSala);
		_frame.getContentPane().setLayout(null);
	}
}