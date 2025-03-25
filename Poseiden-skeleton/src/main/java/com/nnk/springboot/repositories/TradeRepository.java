package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Repository interface for {@link Trade} entity.
 * Extends {@link JpaRepository} for basic CRUD operations and {@link JpaSpecificationExecutor} for advanced query capabilities.
 */
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
