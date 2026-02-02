package ru.yandex.praktikum.stellar_burgers.model;

public class TestUser {
    private String name;
    private String email;
    private String password;

    public TestUser(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "TestUser{name='" + name + "', email='" + email + "'}";
    }
}