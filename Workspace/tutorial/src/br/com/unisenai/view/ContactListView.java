package br.com.unisenai.view;

import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;
import br.com.unisenai.controller.ContactListController;
import br.com.unisenai.model.Contact;

public class ContactListView extends JFrame implements IFormActionListener<Contact> {

    private static final long serialVersionUID = 1L;

    private JPanel pnActions;
    private JScrollPane pnTable;
    private JTable tbContacts;
    private JButton btnNew;
    private JButton btnEdit;
    private JButton btnDelete;
    private ContactListController controller = new ContactListController();
    private ContactTableModel tableModel = new ContactTableModel(controller.findAll(), controller.getColumnNames());

    public ContactListView() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);

        getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("5dlu"),
                ColumnSpec.decode("default:grow"),
                ColumnSpec.decode("5dlu"),
            },
            new RowSpec[] {
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:20dlu"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("default:grow"),
                RowSpec.decode("5dlu"),
            }));

        pnActions = new JPanel();
        getContentPane().add(pnActions, "2, 2, fill, fill");
        pnActions.setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("50dlu"),
                ColumnSpec.decode("5dlu"),
                ColumnSpec.decode("50dlu"),
                ColumnSpec.decode("5dlu"),
                ColumnSpec.decode("50dlu"),
            },
            new RowSpec[] {
                RowSpec.decode("fill:20dlu"),
            }));

        btnNew = new JButton("Novo");
        pnActions.add(btnNew, "1, 1, fill, fill");
        btnNew.addActionListener((event) -> {
            JDialog dialog = new ContactFormView(this, null);
            dialog.setVisible(true);
        });

        btnEdit = new JButton("Editar");
        btnEdit.setEnabled(false);
        pnActions.add(btnEdit, "3, 1, fill, fill");
        btnEdit.addActionListener((event) -> {
            JDialog dialog = new ContactFormView(this, controller.getSelectedId());
            dialog.setVisible(true);
        });

        btnDelete = new JButton("Remover");
        btnDelete.setEnabled(false);
        pnActions.add(btnDelete, "5, 1, fill, fill");

        btnDelete.addActionListener((event) -> {
            controller.remove();
            tableModel.removeRow(tbContacts.getSelectedRow());
        });

        pnTable = new JScrollPane();
        getContentPane().add(pnTable, "2, 4, fill, fill");

        tbContacts = new JTable(tableModel);
        pnTable.setViewportView(tbContacts);

        tbContacts.getSelectionModel().addListSelectionListener((event) -> {
            int selectedRow = tbContacts.getSelectedRow();
            controller.setSelectedRecord(selectedRow);
            btnEdit.setEnabled(selectedRow > -1);
            btnDelete.setEnabled(selectedRow > -1);
        });
    }

    private class ContactTableModel extends EntityTableModel<Contact> {

        private static final long serialVersionUID = 1L;

        public ContactTableModel(List<Contact> elements, List<String> columnNames) {
            super(elements, columnNames);
        }

        @Override
        public Object getValue(Contact element, String columnName) {
            return switch (columnName) {
                case "Código" -> element.getId();
                case "Nome" -> element.getName();
                case "Email" -> element.getEmail();
                case "Telefone" -> element.getPhone();
                default -> "";
            };
        }

        @Override
        public void editRow(Contact element, int position) {
            this.setValueAt(element.getId(), position, 0);
            this.setValueAt(element.getName(), position, 1);
            this.setValueAt(element.getEmail(), position, 2);
            this.setValueAt(element.getPhone(), position, 3);
            this.fireTableRowsUpdated(position, position);
        }
    }

    @Override
    public void onSave(String action, Contact element) {
        if (action.equals("INSERT")) {
            tableModel.addRow(element);
        } else {
            int position = controller.getPosition(element);
            tableModel.editRow(element, position);
        }
    }
}