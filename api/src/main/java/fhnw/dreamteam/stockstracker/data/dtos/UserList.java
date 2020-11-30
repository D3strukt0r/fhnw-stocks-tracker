package fhnw.dreamteam.stockstracker.data.dtos;

import fhnw.dreamteam.stockstracker.data.models.User;

import java.util.List;

public class UserList {
    public UserList(List<User> users) {
        this.data = users;
        this.totalElements = users.stream().count();
    }

    public List<User> data;

    public long totalElements;
}
