package com.umc.post.data.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.umc.post.data.dto.PostResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "게시글")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(unique = true)
    private String postId;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user")
    private User user;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "content")
    private String content;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.postId = LocalDateTime.now().toString();
    }

    public PostResponseDto toDto() {
        return PostResponseDto.builder()
                .postId(this.id)
                .content(this.content)
                .imageUrl(this.imageUrl)
                .createdAt(this.createdAt)
                .userId(this.user.getId())
                .userNickName(this.user.getUserNickName())
                .userName(this.user.getUsername())
                .build();
    }
}
