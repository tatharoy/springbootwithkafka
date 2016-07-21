package com.davita.pie.repository;

import com.davita.pie.domain.Physician;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by taroy on 4/25/16.
 */
public interface PhysicianRepository extends CrudRepository<Physician,Long> {
}
