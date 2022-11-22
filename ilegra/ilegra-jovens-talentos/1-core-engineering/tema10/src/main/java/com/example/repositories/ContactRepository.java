package com.example.repositories;

import com.example.builder.ContactBuilder;
import com.example.db.DB;
import com.example.entities.Contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ContactRepository {

    private final DB database = new DB();

    public List<Contact> findAll() throws SQLException {
        List<Contact> list = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_contact")
        ) {
            saveStatementToContactList(list, statement);
        }
        return list;
    }

    public Optional<Contact> findById(long id) throws SQLException {
        Optional<Contact> contact = Optional.empty();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM tb_contact " +
                                "WHERE " +
                                "id = ?")
        ) {
            statement.setLong(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    contact = Optional.ofNullable(ContactBuilder.builder()
                            .withId(resultSet.getLong("id"))
                            .withName(resultSet.getString("name"))
                            .withEmail(resultSet.getString("email"))
                            .withPhone(resultSet.getString("phone"))
                            .build());
                }
            }
        }
        return contact;
    }

    public List<Contact> findByName(String name) throws SQLException {
        List<Contact> contactList = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT * FROM tb_contact " +
                                "WHERE " +
                                "name = ?")
        ) {
            statement.setString(1, name);

            saveStatementToContactList(contactList, statement);
        }
        return contactList;
    }

    public List<Contact> sortByIdAsc() throws SQLException {
        List<Contact> list = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_contact ORDER BY id ASC")
        ) {
            saveStatementToContactList(list, statement);
        }
        return list;
    }

    public List<Contact> sortByIdDesc() throws SQLException {
        List<Contact> list = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_contact ORDER BY id DESC")
        ) {
            saveStatementToContactList(list, statement);
        }
        return list;
    }

    public List<Contact> sortByNameAsc() throws SQLException {
        List<Contact> list = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_contact ORDER BY name ASC")
        ) {
            saveStatementToContactList(list, statement);
        }
        return list;
    }

    public List<Contact> sortByNameDesc() throws SQLException {
        List<Contact> list = new ArrayList<>();

        try (
                Connection connection = database.getConnection();
                PreparedStatement statement = connection.prepareStatement("SELECT * FROM tb_contact ORDER BY name DESC")
        ) {
            saveStatementToContactList(list, statement);
        }
        return list;
    }

    public Contact save(Contact contact) throws SQLException {
        String name = contact.getName();
        String email = contact.getEmail();
        String phone = contact.getPhone();

        try (
                Connection connection = database.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "INSERT INTO tb_contact "
                                + "(name, email, phone) "
                                + "VALUES "
                                + "(?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS)
        ) {
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, phone);
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                while (rs.next()) {
                    int id = rs.getInt(1);
                    contact.setId((long) id);
                    System.out.println("Saved! ID: " + id + ".");
                }
            }
        }
        return contact;
    }

    public boolean deleteById(long id) throws SQLException {
        boolean wasDeleted = false;
        try (
                Connection connection = database.getConnection();
                PreparedStatement st = connection.prepareStatement(
                        "DELETE FROM tb_contact " +
                                "WHERE " +
                                "id = ?")
        ) {
            st.setLong(1, id);
            int i = st.executeUpdate();

            if (!Objects.equals(i, 0)) {
                wasDeleted = true;
                System.out.println("Deleted! ID: " + id + ".");
            }
        }
        return wasDeleted;
    }

    private void saveStatementToContactList(List<Contact> contactList, PreparedStatement statement) throws SQLException {
        Contact contact;
        try (ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                contact = ContactBuilder.builder()
                        .withId(resultSet.getLong("id"))
                        .withName(resultSet.getString("name"))
                        .withEmail(resultSet.getString("email"))
                        .withPhone(resultSet.getString("phone"))
                        .build();
                contactList.add(contact);
            }
        }
    }
}
