package com.document.service.Repo;

import com.document.service.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    @Query("""
        SELECT DISTINCT d
        FROM Document d
        LEFT JOIN FETCH d.documentTags dt
        LEFT JOIN FETCH dt.tag t
        WHERE LOWER(d.title) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Document> findByTitleContainingIgnoreCaseFetch(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d
        FROM Document d
        LEFT JOIN FETCH d.documentTags dt
        LEFT JOIN FETCH dt.tag t
        WHERE LOWER(d.content) LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Document> findByContentContainingIgnoreCaseFetch(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d
        FROM Document d
        LEFT JOIN FETCH d.documentTags dt
        LEFT JOIN FETCH dt.tag t
        WHERE LOWER(d.title)   LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(d.content) LIKE LOWER(CONCAT('%', :q, '%'))
           OR LOWER(t.name)    LIKE LOWER(CONCAT('%', :q, '%'))
    """)
    List<Document> searchAllFetch(@Param("q") String q);

    @Query("""
        SELECT DISTINCT d
        FROM Document d
        LEFT JOIN FETCH d.documentTags dt
        LEFT JOIN FETCH dt.tag
        WHERE d.id = :id
    """)
    Optional<Document> findByIdWithTags(@Param("id") Long id);
}

