package es.uca.gii.csi.sauron.gui;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import es.uca.gii.csi.sauron.data.Sala;
import es.uca.gii.csi.sauron.data.TipoSala;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;

public class IfrSalas extends JInternalFrame {
	private static final long serialVersionUID = 1L;
	private JTextField _txtNombre;
	private JTextField _txtCapacidadActual;
	private JTextField _txtEslogan;
	private JTable _table;
	private Container _pnlParent;
	private JComboBox<TipoSala> _cmbTipoSala;

	/**
	 * Create the frame.
	 */
	public IfrSalas(JFrame frame) throws Exception{
		_pnlParent = frame;
		setTitle("Salas");
		setResizable(true);
		setClosable(true);
		setBounds(100, 100, 429, 300);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(1, 0, 0, 0));
		
		JLabel lblNombre = new JLabel("Nombre");
		panel.add(lblNombre);
		
		_txtNombre = new JTextField();
		panel.add(_txtNombre);
		_txtNombre.setColumns(10);
		
		JLabel lblCapacidadActual = new JLabel("Capacidad actual");
		panel.add(lblCapacidadActual);
		
		_txtCapacidadActual = new JTextField();
		panel.add(_txtCapacidadActual);
		_txtCapacidadActual.setColumns(10);
		
		JLabel lblEslogan = new JLabel("Eslogan");
		panel.add(lblEslogan);
		
		_txtEslogan = new JTextField();
		panel.add(_txtEslogan);
		_txtEslogan.setColumns(10);
		
		
		
		JLabel lblNombreTipoSala = new JLabel("Nombre de tipo");
		panel.add(lblNombreTipoSala);
		
		_cmbTipoSala = new JComboBox<TipoSala>();
		_cmbTipoSala.setModel(
				 new TipoSalaListModel(TipoSala.Select()));
		_cmbTipoSala.setEditable(true);
		panel.add(_cmbTipoSala);
		
		
		JButton butBuscar = new JButton("Buscar");
		panel.add(butBuscar);
		butBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					_table.setModel(
						new SalasTableModel(
							Sala.Select(
								_txtCapacidadActual.getText().length() == 0 ? -1 : 
									Integer.parseInt(_txtCapacidadActual.getText()),
								_txtNombre.getText().length() == 0 ? null : 
									_txtNombre.getText(),
								_txtEslogan.getText().length() == 0 ? null : 
									_txtEslogan.getText(),
								_cmbTipoSala.getSelectedItem() == null ? null : 
									_cmbTipoSala.getSelectedItem().toString()
							)
						)
					);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(null, "¡Error al buscar la sala " + _txtNombre.getText() + "!");
				}
			}
		});
		_table = new JTable();
		_table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
				// Se activa con doble clic sobre una fila
				if (e.getClickCount() == 2) {
					int iRow = ((JTable)e.getSource()).getSelectedRow();
					Sala sala = null;
					sala = ((SalasTableModel)_table.getModel()).getData(iRow);
					if (sala != null) {
						IfrSala ifrSala = new IfrSala(sala);
						// Comprobamos si el formulario de detalle ha sido abierto para esta sala
						if(!FrmMain.isIfrSalaOpen(ifrSala)){
							FrmMain.addIfrSala(ifrSala);
							ifrSala.setBounds(0, 0, getWidth(), getHeight());
							_pnlParent.add(ifrSala, 0);
							ifrSala.setVisible(true);
						}
						else
							JOptionPane.showMessageDialog(null, "Parece que ya está "
									+ "editando la sala " + sala.getNombre()  +".");	
					}
				}
				} catch (Exception e1) {
					e1.printStackTrace();
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(null, 
							"Por favor, introduzca algún criterio de búsqueda y seleccione"
							+ " una fila válida.");
				}
			}
		});
		getContentPane().add(_table, BorderLayout.CENTER);
	}
}