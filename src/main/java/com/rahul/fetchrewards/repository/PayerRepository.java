package com.rahul.fetchrewards.repository;

import com.rahul.fetchrewards.models.Payer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PayerRepository extends JpaRepository<Payer, Long> {
    Payer findByPayer(String payer);

    @Transactional
    @Modifying
    @Query("UPDATE Payer p SET p.points = p.points + :points WHERE p.payer = :payer")
    void updatePayersBalance(@Param("payer") String payer, @Param("points") int points);
}
