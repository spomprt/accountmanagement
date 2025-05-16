package ru.app.accountmanagement.controller;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.app.accountmanagement.dto.SubscriptionDto;
import ru.app.accountmanagement.mapper.SubscriptionMapper;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.service.SubscriptionService;

@RestController
@RequiredArgsConstructor
public class SubscriptionController implements SubscriptionManagementApi {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @Override
    public ResponseEntity<SubscriptionDto> addSubscription(
            @PathVariable("id") UUID id,
            @RequestBody SubscriptionDto subscriptionDto) {

        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        Subscription created = subscriptionService.addSubscription(id, subscription);
        SubscriptionDto response = subscriptionMapper.toDto(created);

        return ResponseEntity.status(201).body(response);
    }

    @Override
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(
            @PathVariable("id") UUID id) {

        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(id);
        List<SubscriptionDto> response = subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<Void> deleteSubscription(
            @PathVariable("id") UUID id,
            @PathVariable("sub_id") UUID subId) {

        subscriptionService.deleteSubscription(id, subId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<List<SubscriptionDto>> getTopSubscriptions() {
        List<Subscription> topSubscriptions = subscriptionService.getTopSubscriptions();
        List<SubscriptionDto> response = topSubscriptions.stream()
                .map(subscriptionMapper::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }
}
