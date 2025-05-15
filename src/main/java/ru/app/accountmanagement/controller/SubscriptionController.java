package ru.app.accountmanagement.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.app.accountmanagement.dto.SubscriptionDto;
import ru.app.accountmanagement.mapper.SubscriptionMapper;
import ru.app.accountmanagement.model.Subscription;
import ru.app.accountmanagement.service.SubscriptionService;

import java.util.List;

@RestController
public class SubscriptionController {

    private final SubscriptionService subscriptionService;
    private final SubscriptionMapper subscriptionMapper;

    @Autowired
    public SubscriptionController(SubscriptionService subscriptionService, SubscriptionMapper subscriptionMapper) {
        this.subscriptionService = subscriptionService;
        this.subscriptionMapper = subscriptionMapper;
    }

    @GetMapping("/users/{userId}/subscriptions")
    public ResponseEntity<List<SubscriptionDto>> getUserSubscriptions(@PathVariable Long userId) {
        List<Subscription> subscriptions = subscriptionService.getUserSubscriptions(userId);
        List<SubscriptionDto> subscriptionDtos = subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .toList();
        return new ResponseEntity<>(subscriptionDtos, HttpStatus.OK);
    }

    @PostMapping("/users/{userId}/subscriptions")
    public ResponseEntity<SubscriptionDto> addSubscription(@PathVariable Long userId, @RequestBody SubscriptionDto subscriptionDto) {
        Subscription subscription = subscriptionMapper.toEntity(subscriptionDto);
        return subscriptionService.addSubscription(userId, subscription)
                .map(createdSubscription -> new ResponseEntity<>(subscriptionMapper.toDto(createdSubscription), HttpStatus.CREATED))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/users/{userId}/subscriptions/{subscriptionId}")
    public ResponseEntity<Void> deleteSubscription(@PathVariable Long userId, @PathVariable Long subscriptionId) {
        boolean deleted = subscriptionService.deleteSubscription(userId, subscriptionId);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/subscriptions/top")
    public ResponseEntity<List<String>> getTopSubscriptions() {
        List<String> topSubscriptions = subscriptionService.getTopSubscriptions();
        return new ResponseEntity<>(topSubscriptions, HttpStatus.OK);
    }
}
