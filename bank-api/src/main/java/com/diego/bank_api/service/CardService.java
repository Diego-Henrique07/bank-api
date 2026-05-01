package com.diego.bank_api.service;
import com.diego.bank_api.dto.card.*;
import com.diego.bank_api.entity.Card;
import com.diego.bank_api.entity.enums.AccountStatus;
import com.diego.bank_api.entity.enums.CardStatus;
import com.diego.bank_api.exception.BadRequestException;
import com.diego.bank_api.exception.ResourceNotFoundException;
import com.diego.bank_api.repository.*;
import com.diego.bank_api.entity.Account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.*;
import java.util.UUID;
import java.util.List;

@RequiredArgsConstructor
@Service
public class CardService {

    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public CardResponse createCard(CardCreateRequest request){

        Account account = accountRepository.findById(request.accountId())
                .orElseThrow(()-> new ResourceNotFoundException("Account not found."));

        if (account.getStatus() != AccountStatus.ACTIVE){
            throw  new BadRequestException("Account must be Active.");
        }

        Card card = new Card();
        card.setCardNumber(UUID.randomUUID().toString());
        card.setHolderName(account.getCustomer().getFullName());
        card.setExpirationDate(LocalDate.now().plusYears(5));
        card.setCardType(request.cardType());
        card.setStatus(CardStatus.ACTIVE);
        card.setCreatedAt(LocalDateTime.now());
        card.setAccount(account);

        Card savedCard = cardRepository.save(card);

        return toResponse(savedCard);
    }

    public CardResponse findCardById(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new ResourceNotFoundException("Card not found."));

        return toResponse(card);
    }

    public List<CardResponse> findAllCards(){
        return cardRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public CardResponse blockCard(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new ResourceNotFoundException("Card not found."));

        if (card.getStatus() == CardStatus.BLOCKED){
            throw new BadRequestException("This card is already blocked.");
        }

        card.setStatus(CardStatus.BLOCKED);
        Card savedCard = cardRepository.save(card);
        return toResponse(savedCard);
    }

    @Transactional
    public CardResponse activateCard(Long cardId){
        Card card = cardRepository.findById(cardId)
                .orElseThrow(()-> new ResourceNotFoundException("Card not found."));

        if (card.getStatus() == CardStatus.ACTIVE){
            throw new BadRequestException("This card is already active.");
        }

        card.setStatus(CardStatus.ACTIVE);
        Card savedCard = cardRepository.save(card);
        return toResponse(savedCard);
    }

    private CardResponse toResponse(Card card){
        return new CardResponse(
                card.getId(),
                card.getCardNumber(),
                card.getHolderName(),
                card.getExpirationDate(),
                card.getCardType(),
                card.getStatus(),
                card.getCreatedAt(),
                card.getAccount().getId()
        );
    }
}
