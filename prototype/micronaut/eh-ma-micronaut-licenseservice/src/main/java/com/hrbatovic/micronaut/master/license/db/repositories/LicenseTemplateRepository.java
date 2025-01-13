package com.hrbatovic.micronaut.master.license.db.repositories;

import com.hrbatovic.micronaut.master.license.db.entities.LicenseTemplateEntity;
import io.micronaut.data.mongodb.annotation.MongoCollation;
import io.micronaut.data.mongodb.annotation.MongoFindQuery;
import io.micronaut.data.mongodb.annotation.MongoRepository;
import io.micronaut.data.repository.PageableRepository;

import java.util.Optional;
import java.util.UUID;

@MongoRepository
@MongoCollation("{ locale: 'en_US', numericOrdering: true}")
public interface LicenseTemplateRepository extends PageableRepository<LicenseTemplateEntity, UUID> {

    @MongoFindQuery("{'productId':  :productId}")
    Optional<LicenseTemplateEntity> findByProductId(UUID productId);
}
