package ru.app.accountmanagement.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.accountmanagement.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
}