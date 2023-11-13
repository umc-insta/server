package com.umc.post.data.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Table(name = "article for test")
public class ArticleForTest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "article_id")
    private String articleId;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;
}

