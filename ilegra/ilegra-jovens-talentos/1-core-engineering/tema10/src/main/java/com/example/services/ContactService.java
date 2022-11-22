package com.example.services;

import com.example.entities.Contact;
import com.example.repositories.ContactRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class ContactService {

    private ContactRepository contactRepository = new ContactRepository();

    public List<Contact> findAll() throws SQLException {
        return contactRepository.findAll();
    }

    public Contact save(Contact contact) throws SQLException {
        return contactRepository.save(contact);
    }

    public boolean deleteById(long id) throws SQLException {
        return contactRepository.deleteById(id);
    }

    public List<Contact> findByName(String name) throws SQLException {
        return contactRepository.findByName(name);
    }

    public Optional<Contact> findById(long id) throws SQLException {
        return contactRepository.findById(id);
    }

    public List<Contact> sortByIdAsc() throws SQLException {
        return contactRepository.sortByIdAsc();
    }

    public List<Contact> sortByIdDesc() throws SQLException {
        return contactRepository.sortByIdDesc();
    }

    public List<Contact> sortByNameAsc() throws SQLException {
        return contactRepository.sortByNameAsc();
    }

    public List<Contact> sortByNameDesc() throws SQLException {
        return contactRepository.sortByNameDesc();
    }
}
