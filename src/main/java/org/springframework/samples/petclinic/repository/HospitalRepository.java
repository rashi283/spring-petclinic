package org.springframework.samples.petclinic.repository;

import java.util.Collection;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Owner;

public interface HospitalRepository
{

	Collection<Hospital> findByName(String lastName) throws DataAccessException;
	Hospital findById(int id) throws DataAccessException;
	void save(Hospital hospital) throws DataAccessException;

}
