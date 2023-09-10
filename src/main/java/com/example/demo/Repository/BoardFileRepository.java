package com.example.demo.Repository;

import com.example.demo.entity.BoardFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardFileRepository extends JpaRepository <BoardFileEntity,Long> {
}
