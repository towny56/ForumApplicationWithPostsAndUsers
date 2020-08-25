package com.homework.first.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("posts")
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    private String id;
    @NonNull
    private String title;
    @NonNull
    private String author;
    private String content;
    private List<String> tags;
    private Boolean status;
    private LocalDateTime created = LocalDateTime.now();

    //private URL picture;
}
