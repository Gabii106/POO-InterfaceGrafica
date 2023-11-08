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
    	List<Contact> editedList = new ArrayList<Contact>();
    	editedList.addAll(records.stream().map((current) -> {
            if (current.getId().equals(contact.getId())) {
                return contact;
            }
            return current;
        }).toList());
    	records = editedList;
        return contact;
    }

    public void remove(final Integer id) {
    	List<Contact> removedList = new ArrayList<Contact>();
    	removedList.addAll(
    			records.stream().
    			filter((current) -> !current.getId().equals(id)).toList()
    			);
        records = removedList;
    }

    public List<Contact> findAll() {
        return records;
    }

    public Contact findById(final Integer id) {
        return records.stream().filter((current) -> current.getId().equals(id)).findFirst().orElse(null);
    }
}