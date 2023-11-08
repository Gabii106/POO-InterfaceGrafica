package br.com.unisenai.view;

import java.awt.Container;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import br.com.unisenai.controller.ContactActionResponse;
import br.com.unisenai.controller.ContactFormController;
import br.com.unisenai.model.Contact;
import com.jgoodies.forms.layout.FormSpecs;

public class ContactFormView extends JDialog {
    private static final long serialVersionUID = 1L;
    private JButton btnSave;
    private JButton btnCancel;
    private JLabel lbCode;
    private JLabel lbName;
    private JLabel lbEmail;
    private JLabel lbPhone;
    private JTextField tfCode;
    private JTextField tfName;
    private JTextField tfEmail;
    private JTextField tfPhone;
    private JPanel pnForm;
    private JPanel pnActions;
    private ContactFormController controller;
    IFormActionListener<Contact> owner;

    public ContactFormView() {
        this(null, null);
    }

    public ContactFormView(IFormActionListener<Contact> owner, Integer id) {
        this.owner = owner;
        controller = new ContactFormController(id);

        setSize(600, 350);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("5dlu"),
                ColumnSpec.decode("default:grow"),
                ColumnSpec.decode("5dlu"),},
            new RowSpec[] {
                RowSpec.decode("5dlu"),
                FormSpecs.DEFAULT_ROWSPEC,
                RowSpec.decode("5dlu"),
                RowSpec.decode("20dlu"),
                RowSpec.decode("5dlu"),}));

        configurePanelForm(getContentPane());
        configurePanelActions(getContentPane());
    }

    private void configurePanelForm(Container container) {
        pnForm = new JPanel();
        container.add(pnForm, "2, 2, fill, fill");

        pnForm.setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("default:grow"),},
            new RowSpec[] {
                RowSpec.decode("fill:default"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:18dlu"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:default"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:18dlu"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:default"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:18dlu"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:default"),
                RowSpec.decode("5dlu"),
                RowSpec.decode("fill:18dlu"),
                RowSpec.decode("5dlu"),}));

        lbCode = new JLabel("Código");
        pnForm.add(lbCode, "1, 1, fill, fill");

        tfCode = new JTextField();
        tfCode.setText(controller.getId());
        tfCode.setEditable(false);
        pnForm.add(tfCode, "1, 3, fill, fill");

        lbName = new JLabel("Nome");
        pnForm.add(lbName, "1, 5, fill, fill");

        tfName = new JTextField();
        pnForm.add(tfName, "1, 7, fill, fill");
        tfName.setText(controller.getName());
        tfName.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                controller.setName(tfName.getText());
            }
        });

        lbEmail = new JLabel("Email");
        pnForm.add(lbEmail, "1, 9, fill, fill");

        tfEmail = new JTextField();
        pnForm.add(tfEmail, "1, 11, fill, fill");
        tfEmail.setText(controller.getEmail());
        tfEmail.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                controller.setEmail(tfEmail.getText());
            }
        });

        lbPhone = new JLabel("Telefone");
        pnForm.add(lbPhone, "1, 13, fill, fill");

        tfPhone = new JTextField();
        pnForm.add(tfPhone, "1, 15, fill, fill");
        tfPhone.setText(controller.getPhone());
        tfPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                controller.setPhone(tfPhone.getText());
            }
        });
    }

    private void configurePanelActions(Container container) {
        pnActions = new JPanel();
        container.add(pnActions, "2, 4, right, fill");
        pnActions.setLayout(new FormLayout(new ColumnSpec[] {
                ColumnSpec.decode("50dlu"),
                FormSpecs.RELATED_GAP_COLSPEC,
                ColumnSpec.decode("50dlu"),},
            new RowSpec[] {
                RowSpec.decode("fill:default:grow"),}));

        btnSave = new JButton("Salvar");
        pnActions.add(btnSave, "3, 1");
        btnSave.addActionListener((event) -> {
            ContactActionResponse response = controller.save();
            owner.onSave(response.getActionResult(), response.getContact());
            dispose();
        });

        btnCancel = new JButton("Cancelar");
        pnActions.add(btnCancel, "1, 1");
        btnCancel.addActionListener((event) -> {
            dispose();
        });
    }
}
