package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hospital;
//import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.model.Doctor;
import org.springframework.samples.petclinic.model.DoctorType;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

@Controller
@SessionAttributes("doctor")
public class DoctorController {

    private final ClinicService clinicService;


    @Autowired
    public DoctorController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @ModelAttribute("types")
    public Collection<DoctorType> populateDoctorTypes() {
        return this.clinicService.findDoctorTypes();
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/hospitals/{hospitalId}/doctors/new", method = RequestMethod.GET)
    public String initCreationForm(@PathVariable("hospitalId") int hospitalId, Map<String, Object> model) {
        Hospital hospital = this.clinicService.findHospitalById(hospitalId);
        Doctor doctor = new Doctor();
        hospital.addDoctor(doctor);
        model.put("doctor", doctor);
        return "doctors/createOrUpdateDoctorForm";
    }

    @RequestMapping(value = "/hospitals/{hospitalId}/doctors/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute("doctor") Doctor doctor, BindingResult result, SessionStatus status) {
        new DoctorValidator().validate(doctor, result);
        if (result.hasErrors()) {
            return "doctors/createOrUpdateDoctorForm";
        } else {
            this.clinicService.saveDoctor(doctor);
            status.setComplete();
            return "redirect:/hospitals/{hospitalId}";
        }
    }

    @RequestMapping(value = "/hospitals/*/doctors/{doctorId}/edit", method = RequestMethod.GET)
    public String initUpdateForm(@PathVariable("doctorId") int doctorId, Map<String, Object> model) {
        Doctor doctor = this.clinicService.findDoctorById(doctorId);
        model.put("doctor", doctor);
        return "doctors/createOrUpdateDoctorForm";
    }

    @RequestMapping(value = "/hospitals/{hospitalId}/doctors/{doctorId}/edit", method = {RequestMethod.PUT, RequestMethod.POST})
    public String processUpdateForm(@ModelAttribute("doctor") Doctor doctor, BindingResult result, SessionStatus status) {
        // we're not using @Valid annotation here because it is easier to define such validation rule in Java
        new DoctorValidator().validate(doctor, result);
        if (result.hasErrors()) {
            return "doctorss/createOrUpdateDoctorForm";
        } else {
            this.clinicService.saveDoctor(doctor);
            status.setComplete();
            return "redirect:/hospitals/{hospitalId}";
        }
    }

}
