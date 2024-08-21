package com.botox.repository;

import com.botox.domain.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.user WHERE p.postId = :id")
    Optional<Post> findByIdWithUser(@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p.date BETWEEN :start AND :end AND p.likesCount = " +
            "(SELECT MAX(p2.likesCount) FROM Post p2 WHERE p2.date BETWEEN :start AND :end)")
    List<Post> findTopPostsByLikesCountForDate(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);


}