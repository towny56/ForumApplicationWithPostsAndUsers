package com.homework.first.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("users")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    private String id;
    @NonNull
    private String firstName;
    @NonNull
    private String secondName;
    private String email;
    private String password;
    private String role;

    //private URL picture;
}
