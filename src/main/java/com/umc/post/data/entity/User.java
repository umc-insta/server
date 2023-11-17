package com.umc.post.data.entity;

import com.umc.post.config.security.Role;
import com.umc.post.data.dto.UserDto;
import jakarta.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
@Table(name = "유저")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String userLoginId; // login id

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String userNickName; // user nickname

    @Column(nullable = false)
    private String userName; // userName

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Post> posts = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        System.out.println("entity " + authorities);
        authorities.add(new SimpleGrantedAuthority(role.toString()));
        System.out.println("entity " + authorities);
        return authorities;
    }

    @Override
    public String getUsername() {
        return this.userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void addPost(Post post) {
        this.posts.add(post);
    }

    public UserDto toDto(){
        return UserDto.builder()
                .userLoginId(this.userLoginId)
                .userNickname(this.userNickName)
                .userName(this.userName)
                .profileImage(null)
                .build();
    }

}
