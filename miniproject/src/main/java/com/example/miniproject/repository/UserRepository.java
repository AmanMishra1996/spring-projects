package com.example.miniproject.repository;

import com.example.miniproject.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAllByUserStatus(String status, Pageable page);
    User findAllByIdAndUserStatus(long id,String status);
}