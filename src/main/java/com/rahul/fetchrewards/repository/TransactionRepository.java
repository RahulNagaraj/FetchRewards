package com.rahul.fetchrewards.repository;

import com.rahul.fetchrewards.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findAllByOrderByTimestampAsc();

    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.points = :points WHERE t.transactionId = :id")
    void updateTransactionsPoints(@Param("id") Long id,
                                  @Param("points") Integer points);
}
