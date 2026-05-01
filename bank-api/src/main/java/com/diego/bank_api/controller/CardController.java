package com.diego.bank_api.controller;
import com.diego.bank_api.service.CardService;
import com.diego.bank_api.dto.card.*;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cards")
public class CardController {

    private final CardService cardService;

    @PostMapping
    public ResponseEntity<CardResponse> createCard(
            @Valid @RequestBody CardCreateRequest request
    ) {
        CardResponse response = cardService.createCard(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardResponse> findCardById(@PathVariable("id") Long cardId) {
        return ResponseEntity.ok(cardService.findCardById(cardId));
    }

    @GetMapping
    public ResponseEntity<List<CardResponse>> findAllCards() {
        return ResponseEntity.ok(cardService.findAllCards());
    }

    @PatchMapping("/{id}/block")
    public ResponseEntity<CardResponse> blockCard(@PathVariable("id") Long cardId) {
        return ResponseEntity.ok(cardService.blockCard(cardId));
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<CardResponse> activateCard(@PathVariable("id") Long cardId) {
        return ResponseEntity.ok(cardService.activateCard(cardId));
    }
}
