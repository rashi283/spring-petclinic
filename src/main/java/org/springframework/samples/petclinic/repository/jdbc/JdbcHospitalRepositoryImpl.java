/*
 * Copyright 2002-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.repository.jdbc;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.DoctorType;
import org.springframework.samples.petclinic.model.Hospital;
//import org.springframework.samples.petclinic.model.Owner;
//import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.HospitalRepository;
//import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.util.EntityUtils;
import org.springframework.stereotype.Repository;

/**
 * A simple JDBC-based implementation of the {@link HospitalRepository} interface.
 */

@Repository
public class JdbcHospitalRepositoryImpl implements HospitalRepository {

    private VisitRepository visitRepository;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    //private SimpleJdbcInsert insertOwner;  
    private SimpleJdbcInsert insertHospital;

    @Autowired
    public JdbcHospitalRepositoryImpl(DataSource dataSource, NamedParameterJdbcTemplate namedParameterJdbcTemplate,
                                   VisitRepository visitRepository) {

//        this.insertOwner = new SimpleJdbcInsert(dataSource)
//                .withTableName("owners")
//                .usingGeneratedKeyColumns("id");
    	this.insertHospital = new SimpleJdbcInsert(dataSource).withTableName("hospital")
    								.usingGeneratedKeyColumns("id");

        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.visitRepository = visitRepository;
    }


//    /**
//     * Loads {@link Owner Owners} from the data store by last name, returning all owners whose last name <i>starts</i> with
//     * the given name; also loads the {@link Pet Pets} and {@link Visit Visits} for the corresponding owners, if not
//     * already loaded.
//     */
//    @Override
//    public Collection<Owner> findByLastName(String lastName) throws DataAccessException {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("lastName", lastName + "%");
//        List<Owner> owners = this.namedParameterJdbcTemplate.query(
//                "SELECT id, first_name, last_name, address, city, telephone FROM owners WHERE last_name like :lastName",
//                params,
//                ParameterizedBeanPropertyRowMapper.newInstance(Owner.class)
//        );
//        loadOwnersPetsAndVisits(owners);
//        return owners;
//    }
    
    /**
     * Loads {@link Hospital Hospitals} from the data store by name, returning all 
     * hospitals whose name <i>starts</i> with
     * Also loads the {@link Patient Patient} and {@link Visit Visits} for the 
     * corresponding Hospitals, if not already loaded.
     */
    @Override
    public Collection<Hospital> findByName(String nameVal) throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("name", nameVal + "%");
        List<Hospital> hospitals = this.namedParameterJdbcTemplate.query(
                "SELECT id, name, address, city, telephone FROM hospitals WHERE name like :nameVal",
                params,
                ParameterizedBeanPropertyRowMapper.newInstance(Hospital.class)
        );
        loadHospitalsDoctorsAndVisits(hospitals);
        return hospitals;
    }
    
    
    /**
     * Loads the {@link Hospital} with the supplied <code>id</code>; 
     * also loads the {@link Doctor Doctors} and {@link Visit Visits}
     * for the corresponding hospital, if not already loaded.
     */
    
    @Override
    public Hospital findById(int idVal) throws DataAccessException {
        Hospital hospital;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", idVal);
            hospital = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, address, city, telephone FROM hospitals WHERE id= :idVal",
                    params,
                    ParameterizedBeanPropertyRowMapper.newInstance(Hospital.class)
            );
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Hospital.class, idVal);
        }
        loadDoctorsAndVisits(hospital);
        return hospital;
    }

//    public void loadPetsAndVisits(final Owner owner) {
//        Map<String, Object> params = new HashMap<String, Object>();
//        params.put("id", owner.getId().intValue());
//        final List<JdbcPet> pets = this.namedParameterJdbcTemplate.query(
//                "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE owner_id=:id",
//                params,
//                new JdbcPetRowMapper()
//        );
//        for (JdbcPet pet : pets) {
//            owner.addPet(pet);
//            pet.setType(EntityUtils.getById(getPetTypes(), PetType.class, pet.getTypeId()));
//            List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
//            for (Visit visit : visits) {
//                pet.addVisit(visit);
//            }
//        }
//    }

    public void loadDoctorsAndVisits(final Hospital hospitalVal) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("id", hospitalVal.getId().intValue());
        final List<JdbcDoctor> doctors = this.namedParameterJdbcTemplate.query(
                "SELECT id, name, birth_date, type_id, hospital_id FROM doctors WHERE hospital_id=:id",
                params,
                new JdbcDoctorRowMapper()
        );
//        for (JdbcPet pet : pets) {
//            hospitalVal.addPet(pet);
//            pet.setType(EntityUtils.getById(getPetTypes(), PetType.class, pet.getTypeId()));
//            List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
//            for (Visit visit : visits) {
//                pet.addVisit(visit);
//            }
//        }
        for (JdbcDoctor doctor : doctors) {
            hospitalVal.addDoctor(doctor);
            doctor.setType(EntityUtils.getById(getDoctorTypes(), DoctorType.class, doctor.getTypeId()));
            List<Visit> visits = this.visitRepository.findByDoctorId(doctor.getId());
            for (Visit visit : visits) {
                doctor.addVisit(visit);
            }
        }

    }
    
    @Override
    public void save(Hospital hospitalVal) throws DataAccessException {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(hospitalVal);
        if (hospitalVal.isNew()) {
            Number newKey = this.insertHospital.executeAndReturnKey(parameterSource);
            hospitalVal.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE hospitals SET name=:Name, address=:address, " +
                            "city=:city, telephone=:telephone WHERE id=:id",
                    parameterSource);
        }
    }

    public Collection<DoctorType> getDoctorTypes() throws DataAccessException {
        return this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM types ORDER BY name", new HashMap<String, Object>(),
                ParameterizedBeanPropertyRowMapper.newInstance(DoctorType.class));
    }

//    /**
//     * Loads the {@link Pet} and {@link Visit} data for the supplied {@link List} of {@link Owner Owners}.
//     *
//     * @param owners the list of owners for whom the pet and visit data should be loaded
//     * @see #loadPetsAndVisits(Owner)
//     */
//    private void loadOwnersPetsAndVisits(List<Owner> owners) {
//        for (Owner owner : owners) {
//            loadPetsAndVisits(owner);
//        }
//    }
    
    /**
     * Loads the {@link Doctor} and {@link Visit} data for the supplied {@link List} of {@link Hospital hospitals}.
     *
     * @param hospitals the list of hospitals for whom the doctor and visit data should be loaded
     * @see #loadDoctorsAndVisits(Hospital)
     */
    private void loadHospitalsDoctorsAndVisits(List<Hospital> hospitals) {
        for (Hospital hospital : hospitals) {
            loadDoctorsAndVisits(hospital);
        }
    }


}
