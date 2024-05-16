package com.workdoc.repo;

import com.workdoc.model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractorRepo extends JpaRepository<Contractor, Long> {
}