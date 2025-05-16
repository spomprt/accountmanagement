package ru.app.accountmanagement.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.app.accountmanagement.model.Subscription;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, UUID> {

    /**
     * Find all subscriptions for a specific user
     *
     * @param userId the user ID
     * @return list of subscriptions
     */
    List<Subscription> findByUserId(UUID userId);

    /**
     * Count subscriptions by service ID
     *
     * @param serviceId the service ID
     * @return count of subscriptions
     */
    long countByServiceId(UUID serviceId);

    /**
     * Find service IDs ordered by subscription count
     *
     * @param pageable pagination information including limit
     * @return list of service IDs
     */
    @Query("SELECT s.serviceId FROM Subscription s GROUP BY s.serviceId ORDER BY COUNT(s) DESC")
    List<UUID> findServiceIdsOrderedByCount(Pageable pageable);

    /**
     * Find subscriptions by service IDs
     *
     * @param serviceIds list of service IDs
     * @return list of subscriptions
     */
    List<Subscription> findByServiceIdIn(List<UUID> serviceIds);
}
