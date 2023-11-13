//package com.umc.post.data.entity;
//
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.AllArgsConstructor;
//import lombok.NoArgsConstructor;
//import lombok.RequiredArgsConstructor;
//
//import java.time.LocalDateTime;
//
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity
//public class Comment {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(name = "comment_id", nullable = false)
//    private String commentId;
//
//    @Column(name = "text", nullable = false)
//    private String text;
//
//    @ManyToOne
//    @JoinColumn(name = "post_id")
//    private Post post;
//
//    @ManyToOne
//    @JoinColumn(name = "user_name")
//    private User user;
//
//
//    @Column(name = "created_at", nullable = false)
//    private LocalDateTime createdAt;
//
//    @PrePersist
//    protected void onCreate() {
//        createdAt = LocalDateTime.now();
//    }
//
//
//}
