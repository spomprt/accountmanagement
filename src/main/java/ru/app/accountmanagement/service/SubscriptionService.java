package ru.app.accountmanagement.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.SubscriptionStatus;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.repository.SubscriptionRepository;
import ru.app.accountmanagement.repository.UserRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    /**
     * Add a subscription for a user
     *
     * @param userId the user ID
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
     * @param userId the user ID
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
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        
        // Group subscriptions by serviceId and count them
        Map<UUID, Long> serviceCountMap = allSubscriptions.stream()
                .collect(Collectors.groupingBy(Subscription::getServiceId, Collectors.counting()));
        
        // Sort by count in descending order and take top 3
        List<UUID> topServiceIds = serviceCountMap.entrySet().stream()
                .sorted(Map.Entry.<UUID, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
        
        // Find subscriptions for top services
        return allSubscriptions.stream()
                .filter(s -> topServiceIds.contains(s.getServiceId()))
                .collect(Collectors.toList());
    }
}