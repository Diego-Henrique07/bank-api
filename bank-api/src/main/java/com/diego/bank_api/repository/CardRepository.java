package com.diego.bank_api.repository;
import com.diego.bank_api.entity.Card;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface CardRepository extends JpaRepository<Card,Long>{}
