package com.pavel.newsweb.Repository;

import com.pavel.newsweb.Entity.CategoryEntity;
import com.pavel.newsweb.Entity.NewsEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity,Long> {
    Optional<CategoryEntity> findByName(String name);

    Optional<CategoryEntity> findByNewsEntity(NewsEntity newsEntity);
}
