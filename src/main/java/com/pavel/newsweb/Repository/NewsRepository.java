package com.pavel.newsweb.Repository;

import com.pavel.newsweb.Entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NewsRepository extends JpaRepository<NewsEntity,Long> {
}
