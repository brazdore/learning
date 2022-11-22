package com.example.services;

import com.example.entities.User;
import com.example.exceptions.UserException;
import com.example.repositories.UserRepository;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository = new UserRepository();

    public UserService() throws IOException {
    }

    public List<User> getAll() throws IOException {
        return userRepository.getAll();
    }

    public Optional<User> findUserByID(long id) throws IOException {
        return userRepository.findById(id);
    }

    public User save(User user) throws IOException {
        if (!existsByID(user.getUserID())) {
            return userRepository.save(user);
        } else throw new UserException("ID conflict: Invalid operation. ID: [" + user.getUserID() + "].");
    }

    public List<User> saveList(List<User> users) throws IOException {
        int invalidUserIDAmount = 0;

        for (User user : users) {
            if (existsByID(user.getUserID())) {
                invalidUserIDAmount++;
            }
        }
        if (Objects.equals(invalidUserIDAmount, 0)) {
            users.forEach(x -> {
                try {
                    userRepository.save(x);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            return users;
        } else throw new UserException("ID conflict: Invalid operation. ID: " + getInvalidIDs(users) + ".");
    }

    public void increaseHoldingAndRented(long userID, int i) throws IOException {
        if (!existsByID(userID)) {
            throw new UserException("User with ID [" + userID + "] was not found.");
        } else {
            User user = findUserByID(userID).get();
            userRepository.deleteById(userID);
            user.increaseCurrentHolding(i);
            user.increaseAllRentedAmount(i);
            userRepository.save(user);
        }
    }

    public void decreaseHolding(long userID, int i) throws IOException {
        if (!existsByID(userID)) {
            throw new UserException("User with ID [" + userID + "] was not found.");
        } else {
            User user = findUserByID(userID).get();
            userRepository.deleteById(userID);
            user.decreaseCurrentHolding(i);
            userRepository.save(user);
        }
    }

    public boolean canLoanAmount(long userID, int booksToLoan, int maxLoanAmount) throws IOException {
        return existsByID(userID) && findUserByID(userID).get().getCurrentHolding() + booksToLoan < maxLoanAmount + 1;
    }

    public List<User> topUsers(int n) throws IOException {
        return getAll().stream()
                .parallel()
                .sorted(Comparator.comparingInt(User::getAllRentedAmount).reversed().thenComparing(User::getName))
                .limit(n)
                .collect(Collectors.toList());
    }

    public int getHoldingAmount(long userID) throws IOException {
        return findUserByID(userID).get().getCurrentHolding();
    }

    public void deleteAll() throws IOException {
        userRepository.deleteAll();
    }

    public boolean existsByID(long id) throws IOException {
        return userRepository.existsById(id);
    }

    private List<Long> getInvalidIDs(List<User> users) throws IOException {
        List<Long> idList = new ArrayList<>();

        for (User user : users) {
            if (existsByID(user.getUserID())) {
                idList.add(user.getUserID());
            }
        }
        return idList;
    }
}
