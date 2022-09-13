package com.pavel.newsweb.Repository;

import com.pavel.newsweb.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
    Optional<UsersEntity> findByUsername(String username);

    Optional<UsersEntity> findByEmail(String email);

    Optional<UsersEntity> findByActivationcode(String activationcode);
}
