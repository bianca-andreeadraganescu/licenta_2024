package com.example.demo.userip;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserIpRepository extends JpaRepository<UserIp, Long> {
    Optional<UserIp> findByUsername(String username);
}
