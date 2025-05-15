package ru.app.accountmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.User;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    List<Subscription> findByUser(User user);
    List<Subscription> findByUserAndStatus(User user, String status);
    List<Subscription> findByStatus(String status);
}