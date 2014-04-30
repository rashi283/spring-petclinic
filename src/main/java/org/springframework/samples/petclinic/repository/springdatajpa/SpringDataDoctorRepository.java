
package org.springframework.samples.petclinic.repository.springdatajpa;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.samples.petclinic.model.DoctorType;
import org.springframework.samples.petclinic.repository.DoctorRepository;

/**
 * Spring Data JPA specialization of the {@link PetRepository} interface
 *
 * @author Michael Isvy
 * @since 15.1.2013
 */
public interface SpringDataDoctorRepository extends DoctorRepository, Repository<Doctor, Integer> 
{

    @Override
    @Query("SELECT doctortype FROM DoctorType doctortype ORDER BY doctortype.name")
    List<DoctorType> findDoctorTypes() throws DataAccessException;
}
