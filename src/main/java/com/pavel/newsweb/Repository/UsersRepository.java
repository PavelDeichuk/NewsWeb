package com.pavel.newsweb.Repository;

import com.pavel.newsweb.Entity.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<UsersEntity,Long> {
}
