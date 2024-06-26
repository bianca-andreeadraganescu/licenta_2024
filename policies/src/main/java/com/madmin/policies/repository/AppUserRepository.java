package com.madmin.policies.repository;

import com.madmin.policies.object.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AppUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
