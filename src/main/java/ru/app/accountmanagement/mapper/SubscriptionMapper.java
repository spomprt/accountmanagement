package ru.app.accountmanagement.mapper;

import org.springframework.stereotype.Component;
import ru.app.accountmanagement.dto.SubscriptionDto;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.model.SubscriptionStatus;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Component
public class SubscriptionMapper {

    public Subscription toEntity(SubscriptionDto subscriptionDto) {
        if (subscriptionDto == null) {
            return null;
        }

        Subscription subscription = new Subscription();
        subscription.setId(subscriptionDto.getId());
        subscription.setUserId(subscriptionDto.getUserId());
        subscription.setServiceId(subscriptionDto.getServiceId());
        
        if (subscriptionDto.getPrice() != null) {
            subscription.setPrice(java.math.BigDecimal.valueOf(subscriptionDto.getPrice()));
        }
        
        if (subscriptionDto.getStatus() != null) {
            subscription.setStatus(SubscriptionStatus.valueOf(subscriptionDto.getStatus().name()));
        }
        
        if (subscriptionDto.getStartDate() != null) {
            subscription.setStartDate(subscriptionDto.getStartDate().toLocalDateTime());
        }
        
        if (subscriptionDto.getEndDate() != null) {
            subscription.setEndDate(subscriptionDto.getEndDate().toLocalDateTime());
        }
        
        return subscription;
    }

    public SubscriptionDto toDto(Subscription subscription) {
        if (subscription == null) {
            return null;
        }

        SubscriptionDto dto = new SubscriptionDto();
        dto.setId(subscription.getId());
        dto.setUserId(subscription.getUserId());
        dto.setServiceId(subscription.getServiceId());
        
        if (subscription.getPrice() != null) {
            dto.setPrice(subscription.getPrice().doubleValue());
        }
        
        if (subscription.getStatus() != null) {
            dto.setStatus(SubscriptionDto.StatusEnum.valueOf(subscription.getStatus().name()));
        }
        
        if (subscription.getStartDate() != null) {
            dto.setStartDate(OffsetDateTime.of(subscription.getStartDate(), ZoneOffset.UTC));
        }
        
        if (subscription.getEndDate() != null) {
            dto.setEndDate(OffsetDateTime.of(subscription.getEndDate(), ZoneOffset.UTC));
        }
        
        return dto;
    }
}