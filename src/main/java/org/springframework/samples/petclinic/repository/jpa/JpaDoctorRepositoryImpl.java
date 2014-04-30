
package org.springframework.samples.petclinic.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.samples.petclinic.model.DoctorType;
import org.springframework.samples.petclinic.repository.DoctorRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaDoctorRepositoryImpl implements DoctorRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("unchecked")
    public List<DoctorType> findDoctorTypes() {
        return this.em.createQuery("SELECT ptype FROM DoctorType ptype ORDER BY ptype.name").getResultList();
    }

    @Override
    public Doctor findById(int id) {
        return this.em.find(Doctor.class, id);
    }

    @Override
    public void save(Doctor doctor) {
    	if (doctor.getId() == null) {
    		this.em.persist(doctor);     		
    	}
    	else {
    		this.em.merge(doctor);    
    	}
    }

}
