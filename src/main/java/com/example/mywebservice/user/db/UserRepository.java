package com.example.mywebservice.user.db;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    // JPA에서 제공해주는 기본 CRUD 기능 외  쿼리메서드 작성해서 추가 기능 구현 가능
    // 이메일 기반으로 사용자 정보를 조회
    // select * from users where email = #{email}
    Optional<UserEntity> findByEmail(String email);
}
