package org.springframework.samples.petclinic.web;

import org.springframework.samples.petclinic.model.Pet;
import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;

public class DoctorValidator {

    public void validate(Doctor doctor, Errors errors) {
        String name = doctor.getName();
        // name validaation
        if (!StringUtils.hasLength(name)) {
            errors.rejectValue("name", "required", "required");
        } else if (doctor.isNew() && doctor.getHospital().getDoctor(name, true) != null) {
            errors.rejectValue("name", "duplicate", "already exists");
        }
        
        // type valication
        if (doctor.isNew() && doctor.getType() == null) {
            errors.rejectValue("type", "required", "required");
        }
        
     // type valication
        if (doctor.getBirthDate()==null) {
            errors.rejectValue("birthDate", "required", "required");
        }
    }

}
