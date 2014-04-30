package org.springframework.samples.petclinic.web;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.model.DoctorType;
import org.springframework.samples.petclinic.service.ClinicService;

public class DoctorTypeFormatter implements Formatter<DoctorType>{
	
	 private final ClinicService clinicService;


	    @Autowired
	    public DoctorTypeFormatter(ClinicService clinicService) {
	        this.clinicService = clinicService;
	    }

	    @Override
	    public String print(DoctorType doctorType, Locale locale) {
	        return doctorType.getName();
	    }

	    @Override
	    public DoctorType parse(String text, Locale locale) throws ParseException {
	        Collection<DoctorType> findDoctorTypes = this.clinicService.findDoctorTypes();
	        for (DoctorType type : findDoctorTypes) {
	            if (type.getName().equals(text)) {
	                return type;
	            }
	        }
	        throw new ParseException("type not found: " + text, 0);
	    }

}
