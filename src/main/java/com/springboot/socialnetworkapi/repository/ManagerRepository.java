package com.springboot.socialnetworkapi.repository;

import com.springboot.socialnetworkapi.entity.Manager;
import org.springframework.data.repository.CrudRepository;

public interface ManagerRepository extends CrudRepository<Manager, Long> {
}
