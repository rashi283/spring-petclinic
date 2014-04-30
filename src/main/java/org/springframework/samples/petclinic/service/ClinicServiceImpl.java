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
package org.springframework.samples.petclinic.service;

import java.security.acl.Owner;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.DataAccessException;
import org.springframework.samples.petclinic.model.Hospital;
import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.samples.petclinic.model.DoctorType;
//import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.HospitalRepository;
import org.springframework.samples.petclinic.repository.DoctorRepository;
//import org.springframework.samples.petclinic.repository.OwnerRepository;
//import org.springframework.samples.petclinic.repository.PetRepository;
import org.springframework.samples.petclinic.repository.VetRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Mostly used as a facade for all Petclinic controllers
 * Also a placeholder for @Transactional and @Cacheable annotations
 *
 * @author Michael Isvy
 */
/*
@Service
public class ClinicServiceImpl implements ClinicService {

    private PetRepository petRepository;
    private VetRepository vetRepository;
    private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;

    @Autowired
    public ClinicServiceImpl(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        return ownerRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        return ownerRepository.findByLastName(lastName);
    }

    @Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save(owner);
    }


    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }


    @Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }


}*/

@Service
public class ClinicServiceImpl implements ClinicService {
	
	
	//private PetRepository petRepository;
    private VetRepository vetRepository;
    //private OwnerRepository ownerRepository;
    private VisitRepository visitRepository;
    
    private DoctorRepository doctorRepository;
    //private VetRepository vetRepository;
    private HospitalRepository hospitalRepository;
    //private VisitRepository visitRepository;


    /*@Autowired
    public ClinicServiceImpl(PetRepository petRepository, VetRepository vetRepository, OwnerRepository ownerRepository, VisitRepository visitRepository) {
        this.petRepository = petRepository;
        this.vetRepository = vetRepository;
        this.ownerRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }*/

    @Autowired
    public ClinicServiceImpl(DoctorRepository doctorRepository, VetRepository vetRepository, HospitalRepository hospitalRepository, VisitRepository visitRepository) {
        this.doctorRepository = doctorRepository;
        this.vetRepository = vetRepository;
        this.hospitalRepository = hospitalRepository;
        this.visitRepository = visitRepository;
    }
    
    /*@Override
    @Transactional(readOnly = true)
    public Collection<PetType> findPetTypes() throws DataAccessException {
        return petRepository.findPetTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Owner findOwnerById(int id) throws DataAccessException {
        return (Owner) ownerRepository.findById(id);
    }*/

   /* @Override
    @Transactional(readOnly = true)
    public Collection<Owner> findOwnerByLastName(String lastName) throws DataAccessException {
        //return ownerRepository.findByLastName(lastName);
    	return null;
    }*/

    /*@Override
    @Transactional
    public void saveOwner(Owner owner) throws DataAccessException {
        ownerRepository.save((org.springframework.samples.petclinic.model.Owner) owner);
    }*/


    @Override
    @Transactional
    public void saveVisit(Visit visit) throws DataAccessException {
        visitRepository.save(visit);
    }


    /*@Override
    @Transactional(readOnly = true)
    public Pet findPetById(int id) throws DataAccessException {
        return petRepository.findById(id);
    }

    @Override
    @Transactional
    public void savePet(Pet pet) throws DataAccessException {
        petRepository.save(pet);
    }*/

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "vets")
    public Collection<Vet> findVets() throws DataAccessException {
        return vetRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<DoctorType> findDoctorTypes() throws DataAccessException {
        return doctorRepository.findDoctorTypes();
    }

    @Override
    @Transactional(readOnly = true)
    public Hospital findHospitalById(int id) throws DataAccessException {
        return hospitalRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<Hospital> findHospitalByName(String name) throws DataAccessException {
        return hospitalRepository.findByName(name);
    }

    @Override
    @Transactional
    public void saveHospital(Hospital hospital) throws DataAccessException {
        hospitalRepository.save(hospital);
    }


//    @Override
//    @Transactional
//    public void saveVisit(Visit visit) throws DataAccessException {
//        visitRepository.save(visit);
//    }


    @Override
    @Transactional(readOnly = true)
    public Doctor findDoctorById(int id) throws DataAccessException {
        return doctorRepository.findById(id);
    }

    @Override
    @Transactional
    public void saveDoctor(Doctor doctor) throws DataAccessException {
        doctorRepository.save(doctor);
    }

//    @Override
//    @Transactional(readOnly = true)
//    @Cacheable(value = "vets")
//    public Collection<Vet> findVets() throws DataAccessException {
//        return vetRepository.findAll();
//    }


}