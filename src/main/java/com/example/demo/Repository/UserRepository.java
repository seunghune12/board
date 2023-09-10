package com.example.demo.Repository;

import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface UserRepository extends JpaRepository<UserEntity, Long> {
    //findBy규칙-> Username문법
    //select * from where username = ?



    UserEntity findByUsername(String username);

    public UserEntity findByEmail(String email);
}
