package ru.app.accountmanagement.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.SubscriptionStatus;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.repository.SubscriptionRepository;
import ru.app.accountmanagement.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    /**
     * Add a subscription for a user
     *
     * @param userId       the user ID
     * @param subscription the subscription to add
     * @return the created subscription
     */
    public Subscription addSubscription(UUID userId, Subscription subscription) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        subscription.setUserId(userId);

        if (subscription.getStartDate() == null) {
            subscription.setStartDate(LocalDateTime.now());
        }

        if (subscription.getStatus() == null) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        return subscriptionRepository.save(subscription);
    }

    /**
     * Get all subscriptions for a user
     *
     * @param userId the user ID
     * @return list of subscriptions
     */
    public List<Subscription> getUserSubscriptions(UUID userId) {
        userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return subscriptionRepository.findByUserId(userId);
    }

    /**
     * Delete a subscription
     *
     * @param userId         the user ID
     * @param subscriptionId the subscription ID
     */
    public void deleteSubscription(UUID userId, UUID subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (!subscription.getUserId().equals(userId)) {
            throw new RuntimeException("Subscription does not belong to the user");
        }

        subscriptionRepository.delete(subscription);
    }

    /**
     * Get top 3 popular subscriptions based on count
     *
     * @return list of top subscriptions
     */
    public List<Subscription> getTopSubscriptions() {
        // Get top 3 service IDs ordered by subscription count
        List<UUID> topServiceIds = subscriptionRepository.findServiceIdsOrderedByCount(PageRequest.of(0, 3));

        // Find subscriptions for top services
        return subscriptionRepository.findByServiceIdIn(topServiceIds);
    }
}
