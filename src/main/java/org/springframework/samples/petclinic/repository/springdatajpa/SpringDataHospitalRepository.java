
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.samples.petclinic.model.Hospital;
import org.springframework.samples.petclinic.repository.HospitalRepository;


public interface SpringDataHospitalRepository extends HospitalRepository, Repository<Hospital, Integer> {
		
		@Override
	    @Query("SELECT DISTINCT owner FROM Owner owner left join fetch owner.pets WHERE owner.lastName LIKE :lastName%")
	    public Collection<Hospital> findByName(@Param("lastName") String lastName);
		
		@Override
		@Query("SELECT owner FROM Owner owner left join fetch owner.pets WHERE owner.id =:id")
	    public Hospital findById(@Param("id") int id);
}
