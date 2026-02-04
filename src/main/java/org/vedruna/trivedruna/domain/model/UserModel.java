package org.vedruna.trivedruna.domain.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserModel {

    Integer userId;
    String email;
    String password;
    String username;
    String avatarUrl;
    String name;
    String surname1;
    String surname2;
    LocalDateTime createDate;
    Integer userScore;
    RolModel role;
    Integer coursesCourseId;
    String courseName;

     public String getScope() {
        return role.getScopes();
    } 
}
