package com.hrbatovic.springboot.master.payment.db.repositories;

import com.hrbatovic.springboot.master.payment.db.entities.UserEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository  extends MongoRepository<UserEntity, UUID> {
}
