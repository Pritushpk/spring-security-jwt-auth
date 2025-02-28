package com.example.employeemanagementsystem.Repo;

import com.example.employeemanagementsystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<User,String> {
    User findByEmail(String username);
}
