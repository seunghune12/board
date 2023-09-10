package com.example.demo.Repository;

import com.example.demo.entity.BoardEntity;


import com.example.demo.entity.HeartEntity;
import com.example.demo.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface HeartRepository extends JpaRepository<HeartEntity, Long> {

    boolean existsByUserAndBoard(UserEntity user, BoardEntity board);

    void deleteByUserAndBoard(UserEntity user, BoardEntity board);

}