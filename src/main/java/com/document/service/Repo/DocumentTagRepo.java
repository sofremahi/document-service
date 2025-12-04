package com.document.service.Repo;


import com.document.service.entity.Document;
import com.document.service.entity.DocumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentTagRepo extends JpaRepository<DocumentTag, Integer> {
    @Query("""
        SELECT DISTINCT d
        FROM DocumentTag dt
        JOIN dt.document d
        JOIN dt.tag t
        LEFT JOIN FETCH d.documentTags ddt
        LEFT JOIN FETCH ddt.tag
        WHERE LOWER(t.name) LIKE LOWER(CONCAT('%', :name, '%'))
    """)
    List<Document> findDocumentsByTagNameContainingFetch(@Param("name") String name);
}

