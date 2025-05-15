package ru.app.accountmanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.repository.SubscriptionRepository;
import ru.app.accountmanagement.repository.UserRepository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Autowired
    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
    }

    public List<Subscription> getUserSubscriptions(Long userId) {
        return userRepository.findById(userId)
                .map(subscriptionRepository::findByUser)
                .orElse(List.of());
    }

    public Optional<Subscription> addSubscription(Long userId, Subscription subscription) {
        return userRepository.findById(userId)
                .map(user -> {
                    subscription.setUser(user);
                    return subscriptionRepository.save(subscription);
                });
    }

    public boolean deleteSubscription(Long userId, Long subscriptionId) {
        return userRepository.findById(userId)
                .map(user -> subscriptionRepository.findById(subscriptionId)
                        .filter(subscription -> subscription.getUser().getId().equals(userId))
                        .map(subscription -> {
                            subscriptionRepository.delete(subscription);
                            return true;
                        })
                        .orElse(false))
                .orElse(false);
    }

    public List<String> getTopSubscriptions() {
        List<Subscription> allSubscriptions = subscriptionRepository.findAll();
        
        // Group subscriptions by plan name and count occurrences
        Map<String, Long> subscriptionCounts = allSubscriptions.stream()
                .collect(Collectors.groupingBy(Subscription::getPlanName, Collectors.counting()));
        
        // Sort by count (descending) and take top 3
        return subscriptionCounts.entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}