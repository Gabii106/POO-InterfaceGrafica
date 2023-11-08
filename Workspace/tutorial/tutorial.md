# TUTORIAL CONTACTS APP

**Passo 1: Criação da Classe de Modelo `Contact`**

A classe `Contact` é a representação de um contato na aplicação. Ela contém os atributos `id`, `name`, `email` e `phone`. Aqui está o passo a passo para criar a classe `Contact`:

```java
package br.com.unisenai.model;

import java.util.Objects;

public class Contact {

    private Integer id;
    private String name;
    private String email;
    private String phone;

    public Contact() {
        super();
    }

    public Contact(Integer id, String name, String email, String phone) {
        super();
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Contact other = (Contact) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public String toString() {
        return "Contact [id=" + id + ", name=" + name + ", email=" + email + ", phone=" + phone + "]";
    }
}
```

Nesta classe, definimos os atributos, construtores, getters, setters e métodos de hashCode, equals e toString para facilitar o uso dos objetos `Contact` na aplicação.

**Passo 2: Criação da Classe de Repositório `ContactRepository`**

A classe `ContactRepository` é responsável por gerenciar a coleção de contatos e realizar operações como inserção, edição, remoção, consulta e recuperação dos contatos. Aqui está o passo a passo para criar a classe `ContactRepository`:

```java
package br.com.unisenai.model;

import java.util.ArrayList;
import java.util.List;

public class ContactRepository {

    private static Integer nextId = 1;
    private static List<Contact> records = new ArrayList<>();

    public Contact insert(Contact contact) {
        contact.setId(nextId++);
        records.add(contact);
        return contact;
    }

    public Contact edit(final Contact contact) {
        records = records.stream().map((current) -> {
            if (current.getId().equals(contact.getId())) {
                return contact;
            }
            return current;
        }).toList();
        return contact;
    }

    public void remove(final Integer id) {
        records = records.stream().filter((current) -> !current.getId().equals(id)).toList();
    }

    public List<Contact> findAll() {
        return records;
    }

    public Contact findById(final Integer id) {
        return records.stream().filter((current) -> current.getId().equals(id)).findFirst().orElse(null);
    }
}
```

Nesta classe, criamos um repositório de contatos usando uma lista `records` e métodos para inserção, edição, remoção, consulta de todos os contatos e consulta por ID.


**Passo 3: Criação da Classe `ContactListController`**

A classe `ContactListController` é parte da camada de controlador e desempenha um papel fundamental na gestão da lista de contatos exibida na visão. Aqui está o passo a passo para criar a classe `ContactListController`:

 Certifique-se de criar esta classe no pacote correto, que é `br.com.unisenai.controller`.

```java
package br.com.unisenai.controller;

import java.util.List;
import br.com.unisenai.model.Contact;
import br.com.unisenai.model.ContactRepository;

public class ContactListController {

    private ContactRepository repository = new ContactRepository();
    private Integer selectedId;
    private String[] columnNames = new String[] {"Código", "Nome", "Email", "Telefone"};

    public List<Contact> findAll(){
        return repository.findAll();
    }

    public void setSelectedRecord(int row) {
        if(row > -1 && row < repository.findAll().size()) {
            selectedId = repository.findAll().get(row).getId();
        } else {
            selectedId = null;
        }
    }

    public Integer getSelectedId() {
        return selectedId;
    }

    public void remove() {
        if(selectedId != null) {
            repository.remove(selectedId);
        }
    }

    public List<String> getColumnNames() {
        return List.of(columnNames);
    }

    public Contact findById(Integer id) {
        return repository.findById(id);
    }

    public int getPosition(Contact element) {
        return repository.findAll().indexOf(element);
    }
}
```

Nesta classe, criamos o `ContactListController` que possui os seguintes métodos:

- `findAll()`: Retorna a lista de todos os contatos a serem exibidos na visão.
- `setSelectedRecord(int row)`: Define o registro selecionado com base no índice da linha na tabela.
- `getSelectedId()`: Obtém o ID do registro selecionado.
- `remove()`: Remove o registro selecionado, se houver um.
- `getColumnNames()`: Retorna os nomes das colunas a serem exibidas na tabela.
- `findById(Integer id)`: Retorna um contato com base no ID fornecido.
- `getPosition(Contact element)`: Retorna a posição de um contato na lista.

Essa classe permite gerenciar a lista de contatos exibida na visão e responder a ações do usuário, como seleção e remoção de contatos.


**Passo 4: Criação da Classe `ContactActionResponse`**

Certifique-se de criar essas classes no pacote correto, que é `br.com.unisenai.controller`.

A classe `ContactActionResponse` é responsável por encapsular a resposta de uma ação realizada em um contato. Ela contém informações sobre o resultado da ação e o contato relacionado. Aqui está o passo a passo para criar a classe `ContactActionResponse`:

```java
package br.com.unisenai.controller;

import br.com.unisenai.model.Contact;

public class ContactActionResponse {

    private String actionResult;
    private Contact contact;

    public ContactActionResponse(String actionResult, Contact contact) {
        this.actionResult = actionResult;
        this.contact = contact;
    }

    public String getActionResult() {
        return actionResult;
    }

    public Contact getContact() {
        return contact;
    }
}
```

Nesta classe, definimos atributos para armazenar o resultado da ação (por exemplo, "INSERT" para inserção, "EDIT" para edição) e o contato relacionado a essa ação.

**Passo 5: Criação da Classe `ContactFormController`**

A classe `ContactFormController` é responsável por gerenciar o formulário de edição de contatos na aplicação. Ela permite que o usuário insira ou edite informações de contato. Aqui está o passo a passo para criar a classe `ContactFormController`:

```java
package br.com.unisenai.controller;

import br.com.unisenai.model.Contact;
import br.com.unisenai.model.ContactRepository;

public class ContactFormController {

    private ContactRepository repository = new ContactRepository();
    private Contact contact;

    public ContactFormController(Integer id) {
        contact = id == null ? new Contact() : repository.findById(id);
    }

    public String getId() {
        if (contact != null && contact.getId() != null) {
            return contact.getId().toString();
        }
        return "";
    }

    public String getName() {
        if (contact != null && contact.getName() != null) {
            return contact.getName();
        }
        return "";
    }

    public void setName(String name) {
        if (contact != null) {
            contact.setName(name);
        }
    }

    public String getEmail() {
        if (contact != null && contact.getEmail() != null) {
            return contact.getEmail();
        }
        return "";
    }

    public void setEmail(String email) {
        if (contact != null) {
            contact.setEmail(email);
        }
    }

    public String getPhone() {
        if (contact != null && contact.getPhone() != null) {
            return contact.getPhone();
        }
        return "";
    }

    public void setPhone(String phone) {
        if (contact != null) {
            contact.setPhone(phone);
        }
    }

    public ContactActionResponse save() {
        if (contact != null) {
            return contact.getId() != null ?
                    new ContactActionResponse("EDIT", repository.edit(contact)) :
                    new ContactActionResponse("INSERT", repository.insert(contact));
        }
        return null;
    }
}
```

Nesta classe, criamos o `ContactFormController` que gerencia as operações de edição de contatos. Ele permite que o usuário insira ou edite informações de contato e, em seguida, salva as alterações no repositório de contatos. O método `save` retorna um `ContactActionResponse` para informar sobre o resultado da ação (inserção ou edição).


A seguir, descrevo os objetivos dos principais métodos da classe `ContactFormController`:

1. **`public ContactFormController(Integer id)`**:
   - **Objetivo**: O construtor da classe `ContactFormController` é responsável por criar uma instância do controlador para o formulário de contato. Ele recebe um parâmetro `id`, que pode ser nulo ou um valor inteiro, para determinar se o controlador deve ser usado para criar um novo contato (quando `id` é nulo) ou editar um contato existente (quando `id` não é nulo).

2. **`public String getId()`**:
   - **Objetivo**: Este método retorna o ID do contato em formato de string. É usado para exibir o ID do contato no formulário de edição.

3. **`public String getName()`**:
   - **Objetivo**: Este método retorna o nome do contato em formato de string. É usado para exibir o nome do contato no formulário de edição.

4. **`public void setName(String name)`**:
   - **Objetivo**: Este método permite definir o nome do contato com base no valor fornecido como parâmetro. É usado para atualizar o nome do contato no formulário de edição.

5. **`public String getEmail()`**:
   - **Objetivo**: Este método retorna o endereço de email do contato em formato de string. É usado para exibir o email do contato no formulário de edição.

6. **`public void setEmail(String email)`**:
   - **Objetivo**: Este método permite definir o endereço de email do contato com base no valor fornecido como parâmetro. É usado para atualizar o email do contato no formulário de edição.

7. **`public String getPhone()`**:
   - **Objetivo**: Este método retorna o número de telefone do contato em formato de string. É usado para exibir o telefone do contato no formulário de edição.

8. **`public void setPhone(String phone)`**:
   - **Objetivo**: Este método permite definir o número de telefone do contato com base no valor fornecido como parâmetro. É usado para atualizar o telefone do contato no formulário de edição.

9. **`public ContactActionResponse save()`**:
   - **Objetivo**: Este método é acionado quando o usuário decide salvar as informações do contato no formulário de edição. Ele verifica se o contato já possui um ID (ou seja, se é uma edição) e, com base nisso, realiza a ação de inserção ou edição no repositório de contatos (`ContactRepository`). Em seguida, retorna um objeto `ContactActionResponse` que informa o resultado da ação (inserção ou edição) e o contato relacionado.

Esses métodos permitem que a classe `ContactFormController` seja responsável por interagir com a GUI, coletar informações do usuário e atualizar o modelo de dados correspondente. Eles garantem que as operações de edição de contatos sejam controladas de maneira eficaz e seguindo o padrão MVC.


Certifique-se de que a classe `ContactFormController` esteja no pacote `br.com.unisenai.controller`.



**Passo 6: Criação da Interface `IFormActionListener`**

A interface `IFormActionListener` é responsável por definir um método que permitirá notificar o proprietário da janela sobre as ações realizadas no formulário. Ela é genérica e pode ser usada para diferentes tipos de elementos (no exemplo, é usado para contatos). Aqui está o passo a passo para criar a interface `IFormActionListener`:

```java
package br.com.unisenai.view;

public interface IFormActionListener<E> {

    public void onSave(String action, E element);

}
```

Esta interface define o método `onSave` que recebe dois parâmetros: uma ação (como "INSERT" ou "EDIT") e um elemento genérico `E` (por exemplo, um objeto `Contact`). Essa interface permitirá que a classe `ContactFormView` notifique o proprietário da janela sobre a ação realizada no formulário.

Agora, com a interface criada, podemos prosseguir para a descrição da classe `ContactFormView`, que a utiliza.

**Passo 7: Criação da Classe `ContactFormView`**

A classe `ContactFormView` é uma janela de diálogo que permite ao usuário editar as informações de um contato. Ela inclui campos de entrada para o código, nome, email e telefone do contato, além de botões para salvar e cancelar as ações. A classe também implementa a interface `IFormActionListener`, permitindo notificar o proprietário da janela sobre as ações realizadas. Aqui está o passo a passo para criar a classe `ContactFormView`:

```java
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

```

Nesta classe, a janela de diálogo é configurada com campos de entrada para editar as informações do contato, botões para salvar e cancelar a edição e os métodos de atualização do controlador. A classe também lida com eventos de teclado para atualizar as informações em tempo real à medida que o usuário digita. Quando o usuário decide salvar as informações, a classe notifica o proprietário da janela por meio da interface `IFormActionListener`, permitindo que a ação realizada seja repassada ao restante da aplicação.

Agora, você tem uma visão geral de como a interface `IFormActionListener` e a classe `ContactFormView` trabalham juntas para permitir a edição de contatos e notificar sobre as ações realizadas no formulário.



**Passo 8: Criação da Classe `ContactListView`**

A classe `ContactListView` é responsável por exibir a lista de contatos em uma tabela e fornecer botões para adicionar, editar e excluir contatos. Ela também implementa a interface `IFormActionListener` para ouvir eventos de ação do formulário de edição de contatos e atualizar a lista de contatos exibida na tabela. Aqui estão os passos para criar a classe `ContactListView`:

```java
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
```

Nesta classe, a interface gráfica é configurada para exibir a lista de contatos em uma tabela (`tbContacts`). A classe `ContactListModel` é usada para preencher a tabela com os dados de contatos. Os botões "Novo", "Editar" e "Remover" permitem interagir com os contatos. A classe também implementa a interface `IFormActionListener` para ouvir eventos do formulário de edição de contatos e atualizar a lista de contatos na tabela quando um novo contato é inserido ou editado.


**Passo 9: Criação da Classe `App`**

A classe `App` é a classe principal da sua aplicação que inicia a interface de usuário. Neste caso, ela cria uma instância da classe `ContactListView` e a torna visível para o usuário. Aqui estão os passos para criar a classe `App`:

```java
package br.com.unisenai.app;

import br.com.unisenai.view.ContactListView;

public class App {

    public static void main(String[] args) {
        // Cria uma instância da classe ContactListView.
        ContactListView view = new ContactListView();
        
        // Torna a janela visível para o usuário.
        view.setVisible(true);
    }
}
```

Esta classe `App` é a entrada principal para a sua aplicação. Quando você a executa, ela cria uma instância da classe `ContactListView`, que é a janela principal da aplicação. A janela é então tornada visível para o usuário, permitindo que eles interajam com a lista de contatos, adicionem novos contatos, editem ou removam contatos existentes.

Com isso, o tutorial está concluído, e você tem todas as classes necessárias para criar uma aplicação desktop Java usando o conceito de MVC para gerenciar uma lista de contatos. Você pode executar a classe `App` para iniciar a aplicação e começar a trabalhar com a lista de contatos.


