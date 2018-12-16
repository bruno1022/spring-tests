package com.javaee.bruno.springtests.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.bruno.springtests.domain.Vendor;

@Repository
public interface VendorRepository extends MongoRepository<Vendor, String>{

}
