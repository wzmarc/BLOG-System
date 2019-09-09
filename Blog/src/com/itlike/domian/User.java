package com.itlike.domian;

import lombok.Getter;
import lombok.Setter;

@Setter@Getter
public class User {
    private Integer id;
    private String username;
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
