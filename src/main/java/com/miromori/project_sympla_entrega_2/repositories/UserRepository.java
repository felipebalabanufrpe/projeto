package com.miromori.project_sympla_entrega_2.repositories;

import com.miromori.project_sympla_entrega_2.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
