package com.hrbatovic.springboot.master.license.db.repositories;

import com.hrbatovic.springboot.master.license.db.entities.LicenseTemplateEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface LicenseTemplateRepository extends MongoRepository<LicenseTemplateEntity, UUID> {

    @Query(value = "{'productId':  ?0}", collation = "{ 'locale': 'en_US', 'numericOrdering': true }")
    Optional<LicenseTemplateEntity> findByProductId(UUID productId);

}
