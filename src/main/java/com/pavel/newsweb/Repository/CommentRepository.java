package com.pavel.newsweb.Repository;

import com.pavel.newsweb.Entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<CommentEntity,Long> {

}
