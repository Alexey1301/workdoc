package com.workdoc.repo;

import com.workdoc.model.Document;
import com.workdoc.model.enums.DocumentCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
    List<Document> findAllByCategory(DocumentCategory category);
}