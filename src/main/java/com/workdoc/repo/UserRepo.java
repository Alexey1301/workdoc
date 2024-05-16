package com.workdoc.repo;

import com.workdoc.model.AppUser;
import com.workdoc.model.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<AppUser, Long> {
    AppUser findByUsername(String username);

    List<AppUser> findAllByRole(Role role);
}
