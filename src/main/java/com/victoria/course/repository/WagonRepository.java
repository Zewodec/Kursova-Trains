package com.victoria.course.repository;

import com.victoria.course.transport.TrainWagon;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WagonRepository extends MongoRepository<TrainWagon, String> {
}
