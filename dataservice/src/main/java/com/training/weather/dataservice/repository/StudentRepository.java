package com.training.weather.dataservice.repository;

import com.training.weather.dataservice.model.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    List<Student> findByLastName(String lastName);
    List<Student> findByMajor(String major);
}
