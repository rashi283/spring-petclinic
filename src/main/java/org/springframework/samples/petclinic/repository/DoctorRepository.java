package org.springframework.samples.petclinic.repository;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;

public interface DoctorRepository
{
	 List<Doctor> findDoctorTypes() throws DataAccessException;
	 Doctor findById(int id) throws DataAccessException;
	 void save(Doctor doctor) throws DataAccessException;

}
