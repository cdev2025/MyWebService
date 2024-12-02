package com.example.mywebservice.user.db;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users" )
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity implements UserDetails { //UserDetails 인터페이스를 상속받아서 인증 객체로 사용

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", updatable=false)
    private  Long id;

    @Column(name="email", nullable = false, unique=true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

    @Builder
    public UserEntity(String email, String password) {
        this.email = email;
        this.password = password;
    }

    //권한을 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    // 계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        //return UserDetails.super.isAccountNonExpired();
        return true; // true ->만료되지 않았음
    }

    // 계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked() {
        // 계정이 잠금되었는지 확인하는 로직
        //return UserDetails.super.isAccountNonLocked();
        return true; // true -> 잠금되지 않았음
    }

    // 패스워드 만료료 여부 반환
    @Override
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
       // return UserDetails.super.isCredentialsNonExpired();
        return true; //true -> 만료되지 않았음
    }

    // 계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        // return UserDetails.super.isEnabled();
        return true; // true->사용 가능
    }
}
