package com.workdoc.repo;

import com.workdoc.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    List<Account> findAllByStatusTrue();
    List<Account> findAllByStatusFalse();
}