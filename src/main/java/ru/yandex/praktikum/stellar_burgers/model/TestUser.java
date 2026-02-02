package ru.yandex.praktikum.stellar_burgers.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TestUser {
    private String name;
    private String email;
    private String password;

    @Override
    public String toString() {
        return "TestUser{name='" + name + "', email='" + email + "'}";
    }
}