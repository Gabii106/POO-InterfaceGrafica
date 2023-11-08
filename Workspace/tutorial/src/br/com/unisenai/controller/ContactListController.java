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