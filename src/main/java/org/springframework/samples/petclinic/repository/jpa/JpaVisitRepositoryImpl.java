
package org.springframework.samples.petclinic.repository.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Repository;


@Repository
public class JpaVisitRepositoryImpl implements VisitRepository {

    @PersistenceContext
    private EntityManager em;


    @Override
    public void save(Visit visit) {
    	if (visit.getId() == null) {
    		this.em.persist(visit);     		
    	}
    	else {
    		this.em.merge(visit);    
    	}
    }


    @Override
    @SuppressWarnings("unchecked")
    public List<Visit> findByDoctorId(Integer doctorId) {
        Query query = this.em.createQuery("SELECT visit FROM Visit v where v.doctors.id= :id");
        query.setParameter("id",doctorId);
        return query.getResultList();
    }

}
