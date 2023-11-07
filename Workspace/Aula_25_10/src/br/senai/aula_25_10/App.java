package br.senai.aula_25_10;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;
import javax.swing.text.TabableView;

import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.FormSpecs;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class App extends JFrame implements TableModel, ActionBarListener{ //View

	private static final long serialVersionUID = 1L;
	private JTextField tfCode;
	private JTextField tfDescription;
	private TaskController controller = new TaskController();
	private JTable table;
	private String[] columns = new String[] {"Código", "Descrição"};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App frame = new App();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public App() {
		controller.setActionBarListener(this);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.DEFAULT_ROWSPEC,
				FormSpecs.UNRELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, "2, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50dlu"),
				ColumnSpec.decode("50dlu"),},
			new RowSpec[] {
				FormSpecs.DEFAULT_ROWSPEC,}));
		
		JButton btnSave = new JButton("Salvar");
		panel.add(btnSave, "1, 1");
		
		JButton btnDelete = new JButton("Deletar");
		panel.add(btnDelete, "2, 1");
		
		JLabel lbCode = new JLabel("Código");
		getContentPane().add(lbCode, "2, 3");
		
		tfCode = new JTextField();
		getContentPane().add(tfCode, "2, 4, fill, default");
		tfCode.setColumns(10);
		
		JLabel lbDescription = new JLabel("Descrição");
		getContentPane().add(lbDescription, "2, 6");
		
		tfDescription = new JTextField();
		getContentPane().add(tfDescription, "2, 7, fill, default");
		tfDescription.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, "2, 9, fill, fill");
		
		table = createTable();
		scrollPane.setViewportView(table);
		
		
		btnSave.addActionListener(new ActionListener() { //Classe anonima - pois não tem nome
			@Override
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(null, "Cliquei no botão Salvar");
				controller.save();
			}
		});
		
		btnDelete.addActionListener((event) -> { //Jeito de fazer com lambda - ActionEvent event, mas pode só escrever event
			//JOptionPane.showMessageDialog(null, "Cliquei no Remover");
			controller.delete();
		}); 
		
		//KeyListener = pega a digitação
		
		tfCode.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {}

			public void keyPressed(KeyEvent e) {}

			public void keyReleased(KeyEvent e) {
				System.out.println("[tfCode] Digitando a letra "+e.getKeyChar());
				System.out.println("[tfCode] O testo agora é "+tfCode.getText());
			}
			
		});
		
		tfDescription.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//System.out.println("[tfDescription] Digitando a letra "+e.getKeyChar());
				//System.out.println("[tfDescription] O testo agora é "+tfDescription.getText());
				controller.changeDescription(tfDescription.getText());
			}
		});
	}
	
	//Coluna = tipo de informação
	//Linha = Cada task
	
	private JTable createTable() {
		JTable table =  new JTable(this);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent e) {
				int index = ((DefaultListSelectionModel) e.getSource()).getSelectedIndices()[0];
				controller.selectTask(index);
				tfCode.setText(controller.getCode());
				tfDescription.setText(controller.getDescription());
				
			}
		});
		
		return table;
	}

	@Override
	public int getRowCount() {
		return controller.getTotalTasks();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return columns[columnIndex];
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class; //Retorna o tipo de dado da coluna
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return controller.getValueAt(rowIndex, columnIndex);
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSave() {
		table = createTable();
		tfDescription.setText(controller.getDescription());
		SwingUtilities.updateComponentTreeUI(this); //this = referencia do próprio objeto, referencia da instancia atual
	}

	@Override
	public void onDelete() {
		table = createTable();
		tfCode.setText(controller.getCode());
		tfDescription.setText(controller.getDescription());
		SwingUtilities.updateComponentTreeUI(this);
		
	}

}
