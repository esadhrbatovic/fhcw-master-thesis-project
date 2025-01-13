package com.hrbatovic.micronaut.master.payment.db.repositories;


import com.hrbatovic.micronaut.master.payment.db.entities.PaymentMethodEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface PaymentMethodRepository extends PageableRepository<PaymentMethodEntity, UUID> {

    @MongoFindQuery("{'selector':  :selector}")
    Optional<PaymentMethodEntity> findBySelector(String selector);

}
