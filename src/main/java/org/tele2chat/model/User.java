package org.tele2chat.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@JsonFormat
@Table(name = "chat_users", schema = "tele2_chat")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column
    private String password;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private Set<Message> messages;
}
