package org.springframework.samples.petclinic.repository.jpa;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.samples.petclinic.model.Hospital;
import org.springframework.samples.petclinic.repository.HospitalRepository;
import org.springframework.stereotype.Repository;

@Repository
public class JpaHospitalRepositoryImpl implements HospitalRepository 
{

    @PersistenceContext
    private EntityManager em;


    /**
     * Important: in the current version of this method, we load Owners with all their Pets and Visits while 
     * we do not need Visits at all and we only need one property from the Pet objects (the 'name' property).
     * There are some ways to improve it such as:
     * - creating a Ligtweight class (example here: https://community.jboss.org/wiki/LightweightClass)
     * - Turning on lazy-loading and using {@link OpenSessionInViewFilter}
     */
    @SuppressWarnings("unchecked")
    public Collection<Hospital> findByName(String nameVal) 
    {
        // using 'join fetch' because a single query should load both owners and pets
        // using 'left join fetch' because it might happen that an owner does not have pets yet
        Query query = this.em.createQuery("SELECT DISTINCT hospital FROM hospital Hospital left join fetch hospital.doctors WHERE hospital.name LIKE :name");
        query.setParameter("name", nameVal + "%");
        return query.getResultList();
    }

    @Override
    public Hospital findById(int id) 
    {
        // using 'join fetch' because a single query should load both owners and pets
        // using 'left join fetch' because it might happen that an owner does not have pets yet
        Query query = this.em.createQuery("SELECT hospital FROM Hospital hospital left join fetch hospital.doctors WHERE hospital.id =:id");
        query.setParameter("id", id);
        return (Hospital) query.getSingleResult();
    }


    @Override
    public void save(hospital) 
    {
    	if (hospital.getId() == null) {
    		this.em.persist(hospital);     		
    	}
    	else {
    		this.em.merge(hospital);    
    	}
    }

}
