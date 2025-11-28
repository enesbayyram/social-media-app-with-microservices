package com.enesbayram.sm_api_gateway.repository;

import com.enesbayram.sm_api_gateway.model.UserDef;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDefRepository extends JpaRepository<UserDef, String> {

    @Query(value = "from UserDef u WHERE u.username= :username")
    Optional<UserDef> findByUsername(String username);
}
