package com.hrbatovic.springboot.master.payment.db.repositories;

import com.hrbatovic.springboot.master.payment.db.entities.PaymentMethodEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PaymentMethodRepository  extends MongoRepository<PaymentMethodEntity, UUID> {

    @Query(value = "{'selector':  ?0}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    Optional<PaymentMethodEntity> findBySelector(String selector);
}
