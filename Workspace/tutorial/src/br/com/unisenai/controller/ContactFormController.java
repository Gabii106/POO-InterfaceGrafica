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