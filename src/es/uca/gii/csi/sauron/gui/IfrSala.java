package es.uca.gii.csi.sauron.gui;

import javax.swing.JInternalFrame;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;

import es.uca.gii.csi.sauron.data.Sala;
import es.uca.gii.csi.sauron.data.TipoSala;

import com.jgoodies.forms.layout.FormSpecs;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JComboBox;

public class IfrSala extends JInternalFrame {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;
	private JTextField _txtNombre;
	private JTextField _txtEslogan;
	private JButton _butSave;
	private Sala _sala = null;
	private JLabel _lblCapacidadActual;
	private JTextField _txtCapacidadActual;
	private JComboBox<TipoSala> _cmbTipoSala;
	private JLabel _lblTipoDeSala;
    
	public Sala getSala(){
		return _sala;
	}
	/**
	* Create the frame.
	*/
	public IfrSala(Sala sala) {
		setClosable(true);
		setResizable(true);
		setTitle("Sala");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				FormSpecs.DEFAULT_COLSPEC,
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.RELATED_GAP_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,}));

		JLabel lblNombre = new JLabel("Nombre");
		getContentPane().add(lblNombre, "2, 4, right, default");

		_txtNombre = new JTextField();
		getContentPane().add(_txtNombre, "4, 4, fill, default");
		_txtNombre.setColumns(10);

		JLabel lblEslogan = new JLabel("Eslogan");
		getContentPane().add(lblEslogan, "2, 6, right, default");

		_txtEslogan = new JTextField();
		getContentPane().add(_txtEslogan, "4, 6, fill, default");
		_txtEslogan.setColumns(10);

		_lblCapacidadActual = new JLabel("Capacidad Actual");
		getContentPane().add(_lblCapacidadActual, "2, 8, right, default");

		_txtCapacidadActual = new JTextField();
		getContentPane().add(_txtCapacidadActual, "4, 8, fill, default");
		_txtCapacidadActual.setColumns(10);
		
		
		//P6
		_lblTipoDeSala = new JLabel("Tipo de Sala");
		getContentPane().add(_lblTipoDeSala, "2, 10, right, default");
		
		try{
			_cmbTipoSala = new JComboBox<TipoSala>();
			_cmbTipoSala.setModel(
					 new TipoSalaListModel(TipoSala.Select()));
		}catch(Exception e1){
			JOptionPane.showMessageDialog(null, "¡Error al buscar los tipos de sala!");
		}
		getContentPane().add(_cmbTipoSala, "4, 10, fill, default");
		
		// Si la sala recibida no es nula, inicializamos los valores (Caso en el que venimos de la búsqueda)
		if(sala != null){
			_sala = sala;
			_txtNombre.setText(_sala.getNombre());
			_txtEslogan.setText(_sala.getEslogan());
			_txtCapacidadActual.setText(Integer.toString(sala.getCapacidadActual())); 
			_cmbTipoSala.getModel().setSelectedItem(_sala.getTipo());;
		}
		
		_butSave = new JButton("Guardar");
		_butSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(_sala == null)
					try {
						_sala = Sala.Create(Integer.parseInt(
								_txtCapacidadActual.getText()), 
								_txtNombre.getText(), 
								_txtEslogan.getText(), 
								(TipoSala) _cmbTipoSala.getSelectedItem());
					} catch (Exception e1){
						JOptionPane.showMessageDialog(null, "¡Error al crear la sala! "
								+ "Recuerde que todos los campos son obligatorios.");
					}
				else{
					_sala.setNombre(_txtNombre.getText());
					_sala.setEslogan(_txtEslogan.getText());
					_sala.setCapacidadActual(Integer.parseInt(_txtCapacidadActual.getText()));
					_sala.setTipoSala((TipoSala) _cmbTipoSala.getSelectedItem());
					try {
						_sala.Update();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(null, "¡Error al actualizar la sala!");
					}
				}
			}
		});
		getContentPane().add(_butSave, "2, 12, 3, 1");
	}
}