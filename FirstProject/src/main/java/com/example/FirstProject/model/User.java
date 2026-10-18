package com.example.FirstProject.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users",

uniqueConstraints = {
        @UniqueConstraint(columnNames = "userName"),
        @UniqueConstraint(columnNames = "userEmail")
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private Long userId;
    @NotBlank
//    @Size(  min = )
    @Column(name = "userName")
    private String userName;
    @NotBlank
    @Size( max = 50)
    @Email
    @Column(name = "userEmail")
    private String userEmail;
    @NotBlank
    @Size( max = 120)
    @Column(name = "userPassword")
    private String userPassword;
    @ToString.Exclude
@OneToOne(mappedBy = "user",cascade = {CascadeType.MERGE,CascadeType.PERSIST},orphanRemoval = true)
private Cart cart;
    public User(String userName, String userEmail, String userPassword) {
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userName = userName;
    }
    @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE }, fetch = FetchType.EAGER)
   @JoinTable(
           name = "user_role",
            joinColumns=@JoinColumn(name = "user_id"),
           inverseJoinColumns = @JoinColumn(name="role_id")
   )
    @Getter
    @Setter
    public Set<Role> roles=new HashSet<>();
    @OneToMany(mappedBy = "user",cascade = {CascadeType.PERSIST, CascadeType.MERGE},
    orphanRemoval = true)
    @ToString.Exclude
    private Set<Product> prouducts;
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinTable(
            name = "user_address",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id")
    )
    @Getter
    @Setter
    private List<Address> addresses=new ArrayList<>();

    public User(@NotBlank @Size(min = 3, max = 20) String username, @NotBlank @Size(min = 6, max = 40) String password) {
    }
}
