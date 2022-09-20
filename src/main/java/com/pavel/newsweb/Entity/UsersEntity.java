package com.pavel.newsweb.Entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class UsersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Username null!")
    @Size(min = 3, max = 20, message = "Username is min 3, max 20")
    private String username;

    @NotNull(message = "Password null!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "error validation password")
    @ToString.Exclude
    private String password;

    @Transient
    @ToString.Exclude
    private String password2;

    @Email(message = "email is not valid")
    @Size(max = 24, message = "email is max size is 24")
    private String email;

    private String roles;

    private String activationcode = UUID.randomUUID().toString();

    @ManyToMany(mappedBy = "usersEntities")
    @ToString.Exclude
    private List<NewsEntity> newsEntities;


}
