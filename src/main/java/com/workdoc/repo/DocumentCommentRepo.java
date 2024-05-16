package com.workdoc.repo;

import com.workdoc.model.DocumentComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentCommentRepo extends JpaRepository<DocumentComment, Long> {
}