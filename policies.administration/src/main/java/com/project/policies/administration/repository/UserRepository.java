package com.project.policies.administration.repository;

import com.project.policies.administration.object.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<AppUser, Long> {

    @Query("SELECT u FROM AppUser u WHERE u.ipAddress = :ipAddress")
    AppUser findByIpAddress(@Param("ipAddress") String ipAddress);
}
