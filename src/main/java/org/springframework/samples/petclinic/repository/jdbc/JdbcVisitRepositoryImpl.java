
package org.springframework.samples.petclinic.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.samples.petclinic.model.Visit;
import org.springframework.samples.petclinic.repository.VisitRepository;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD

=======
/**
 * A simple JDBC-based implementation of the {@link VisitRepository} interface.
 *
 */
>>>>>>> 1bf5c8812950245cd18d952bf208904ee6788128
@Repository
public class JdbcVisitRepositoryImpl implements VisitRepository {

    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert insertVisit;

    @Autowired
    public JdbcVisitRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);

        this.insertVisit = new SimpleJdbcInsert(dataSource)
                .withTableName("visits")
                .usingGeneratedKeyColumns("id");
    }


    @Override
    public void save(Visit visit) throws DataAccessException {
        if (visit.isNew()) {
            Number newKey = this.insertVisit.executeAndReturnKey(
                    createVisitParameterSource(visit));
            visit.setId(newKey.intValue());
        } else {
            throw new UnsupportedOperationException("Visit update not supported");
        }
    }

<<<<<<< HEAD
    public void deleteDoctor(int id) throws DataAccessException {
        this.jdbcTemplate.update("DELETE FROM doctors WHERE id=?", id);
    }
=======
//    public void deletePet(int id) throws DataAccessException {
//        this.jdbcTemplate.update("DELETE FROM pets WHERE id=?", id);
//    }
>>>>>>> 1bf5c8812950245cd18d952bf208904ee6788128

    public void deleteDoctor(int idVal) throws DataAccessException {
        this.jdbcTemplate.update("DELETE FROM doctors WHERE id=?", idVal);
    }

    /**
     * Creates a {@link MapSqlParameterSource} based on data values from the supplied {@link Visit} instance.
     */
    private MapSqlParameterSource createVisitParameterSource(Visit visit) {
        return new MapSqlParameterSource()
                .addValue("id", visit.getId())
                .addValue("visit_date", visit.getDate().toDate())
                .addValue("description", visit.getDescription())
<<<<<<< HEAD
=======
                //.addValue("pet_id", visit.getPet().getId());
>>>>>>> 1bf5c8812950245cd18d952bf208904ee6788128
                .addValue("doctor_id", visit.getDoctor().getId());
    }

//    @Override
//    public List<Visit> findByPetId(Integer petId) {
//        final List<Visit> visits = this.jdbcTemplate.query(
//                "SELECT id, visit_date, description FROM visits WHERE pet_id=?",
//                new ParameterizedRowMapper<Visit>() {
//                    @Override
//                    public Visit mapRow(ResultSet rs, int row) throws SQLException {
//                        Visit visit = new Visit();
//                        visit.setId(rs.getInt("id"));
//                        Date visitDate = rs.getDate("visit_date");
//                        visit.setDate(new DateTime(visitDate));
//                        visit.setDescription(rs.getString("description"));
//                        return visit;
//                    }
//                },
//                petId);
//        return visits;
//    }
    @Override
<<<<<<< HEAD
    public List<Visit> findByDoctorId(Integer doctorId) {
=======
    public List<Visit> findByDoctorId(Integer doctorIdVal) {
>>>>>>> 1bf5c8812950245cd18d952bf208904ee6788128
        final List<Visit> visits = this.jdbcTemplate.query(
                "SELECT id, visit_date, description FROM visits WHERE doctor_id=?",
                new ParameterizedRowMapper<Visit>() {
                    @Override
                    public Visit mapRow(ResultSet rs, int row) throws SQLException {
                        Visit visit = new Visit();
                        visit.setId(rs.getInt("id"));
                        Date visitDate = rs.getDate("visit_date");
                        visit.setDate(new DateTime(visitDate));
                        visit.setDescription(rs.getString("description"));
                        return visit;
                    }
                },
<<<<<<< HEAD
                DoctorId);
=======
                doctorIdVal);
>>>>>>> 1bf5c8812950245cd18d952bf208904ee6788128
        return visits;
    }

}
