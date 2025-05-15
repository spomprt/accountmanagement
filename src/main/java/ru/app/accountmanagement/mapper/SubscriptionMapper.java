package ru.app.accountmanagement.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;
import ru.app.accountmanagement.dto.SubscriptionDto;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.User;
import ru.app.accountmanagement.repository.UserRepository;

@Component
public class SubscriptionMapper {

    private final UserRepository userRepository;

    public SubscriptionMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public SubscriptionDto toDto(Subscription subscription) {
        if (subscription == null) {
            return null;
        }

        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(subscription.getId());
        subscriptionDto.setUserId(subscription.getUser().getId());
        subscriptionDto.setPlanName(subscription.getPlanName());
        subscriptionDto.setPrice(subscription.getPrice());
        subscriptionDto.setStatus(subscription.getStatus());
        subscriptionDto.setStartDate(subscription.getStartDate());
        subscriptionDto.setEndDate(subscription.getEndDate());

        return subscriptionDto;
    }

    public Subscription toEntity(SubscriptionDto subscriptionDto) {
        if (subscriptionDto == null) {
            return null;
        }

        Subscription subscription = new Subscription();
        subscription.setId(subscriptionDto.getId());

        if (subscriptionDto.getUserId() != null) {
            Optional<User> userOptional = userRepository.findById(subscriptionDto.getUserId());
            userOptional.ifPresent(subscription::setUser);
        }

        subscription.setPlanName(subscriptionDto.getPlanName());
        subscription.setPrice(subscriptionDto.getPrice());
        subscription.setStatus(subscriptionDto.getStatus());
        subscription.setStartDate(subscriptionDto.getStartDate());
        subscription.setEndDate(subscriptionDto.getEndDate());

        return subscription;
    }

    public void updateEntityFromDto(SubscriptionDto subscriptionDto, Subscription subscription) {
        if (subscriptionDto == null || subscription == null) {
            return;
        }

        if (subscriptionDto.getPlanName() != null) {
            subscription.setPlanName(subscriptionDto.getPlanName());
        }
        if (subscriptionDto.getPrice() != null) {
            subscription.setPrice(subscriptionDto.getPrice());
        }
        if (subscriptionDto.getStatus() != null) {
            subscription.setStatus(subscriptionDto.getStatus());
        }
        if (subscriptionDto.getStartDate() != null) {
            subscription.setStartDate(subscriptionDto.getStartDate());
        }
        if (subscriptionDto.getEndDate() != null) {
            subscription.setEndDate(subscriptionDto.getEndDate());
        }
    }
}