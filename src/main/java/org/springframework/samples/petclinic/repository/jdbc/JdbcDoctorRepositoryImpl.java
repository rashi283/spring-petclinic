package org.springframework.samples.petclinic.repository.jdbc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.DoctorRepository;
import org.springframework.samples.petclinic.repository.HospitalRepository;
import org.springframework.samples.petclinic.repository.OwnerRepository;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.samples.petclinic.util.EntityUtils;

public class JdbcDoctorRepositoryImpl implements DoctorRepository{
	
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private SimpleJdbcInsert insertPet;

    private HospitalRepository hospitalRepository;

    private VisitRepository visitRepository;


    @Autowired
    public JdbcDoctorRepositoryImpl(DataSource dataSource, HospitalRepository ownerRepository, VisitRepository visitRepository) {
        this.namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);

        this.insertPet = new SimpleJdbcInsert(dataSource)
                .withTableName("pets")
                .usingGeneratedKeyColumns("id");

        this.hospitalRepository = ownerRepository;
        this.visitRepository = visitRepository;
    }

    @Override
    public List<Doctor> findDoctorTypes() throws DataAccessException {
        Map<String, Object> params = new HashMap<String, Object>();
        return this.namedParameterJdbcTemplate.query(
                "SELECT id, name FROM types ORDER BY name",
                params,
                ParameterizedBeanPropertyRowMapper.newInstance(PetType.class));
    }

    @Override
    public Doctor findById(int id) throws DataAccessException {
        JdbcDoctor doctor;
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("id", id);
            doctor = this.namedParameterJdbcTemplate.queryForObject(
                    "SELECT id, name, birth_date, type_id, owner_id FROM pets WHERE id=:id",
                    params,
                    new JdbcPetRowMapper());
        } catch (EmptyResultDataAccessException ex) {
            throw new ObjectRetrievalFailureException(Pet.class, new Integer(id));
        }
        Owner owner = this.hospitalRepository.findById(doctor.getHospitalId());
        owner.addDoctor(doctor);
        doctor.setType(EntityUtils.getById(findDoctorTypes(), PetType.class, doctor.getTypeId()));

        /*List<Visit> visits = this.visitRepository.findByPetId(pet.getId());
        for (Visit visit : visits) {
            pet.addVisit(visit);
        }*/
        return pet;
    }

    public void save(Doctor doctor) throws DataAccessException {
        if (doctor.isNew()) {
            Number newKey = this.insertDoctor.executeAndReturnKey(
                    createDoctorParameterSource(doctor));
            doctor.setId(newKey.intValue());
        } else {
            this.namedParameterJdbcTemplate.update(
                    "UPDATE pets SET name=:name, birth_date=:birth_date, type_id=:type_id, " +
                            "owner_id=:owner_id WHERE id=:id",
                    createDoctorParameterSource(doctor));
        }
    }

    /**
     * Creates a {@link MapSqlParameterSource} based on data values from the supplied {@link Pet} instance.
     */
    private MapSqlParameterSource createPetParameterSource(Pet pet) {
        return new MapSqlParameterSource()
                .addValue("id", pet.getId())
                .addValue("name", pet.getName())
                .addValue("birth_date", pet.getBirthDate().toDate())
                .addValue("type_id", pet.getType().getId())
                .addValue("owner_id", pet.getOwner().getId());
    }


}
