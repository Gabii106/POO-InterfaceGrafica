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