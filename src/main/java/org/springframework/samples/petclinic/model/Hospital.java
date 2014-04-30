package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.core.style.ToStringCreator;

public class Hospital extends Person
{
	@Entity
	@Table(name = "hospitals")
	public class Owner extends Person {
	    @Column(name = "address")
	    @NotEmpty
	    private String address;

	    @Column(name = "city")
	    @NotEmpty
	    private String city;

	    @Column(name = "telephone")
	    @NotEmpty
	    @Digits(fraction = 0, integer = 10)
	    private String telephone;

	    @OneToMany(cascade = CascadeType.ALL, mappedBy = "hospital")
	    private Set<Doctor> doctors;


	    public String getAddress() {
	        return this.address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getCity() {
	        return this.city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getTelephone() {
	        return this.telephone;
	    }

	    public void setTelephone(String telephone) {
	        this.telephone = telephone;
	    }

	    protected void setDoctorsInternal(Set<Doctor> doctors) {
	        this.doctors = doctors;
	    }

	    protected Set<Doctor> getDoctorsInternal() {
	        if (this.doctors == null) {
	            this.doctors = new HashSet<Doctor>();
	        }
	        return this.doctors;
	    }

	    public List<Doctor> getDoctors() {
	        List<Doctor> sortedDoctors = new ArrayList<Doctor>(getDoctorsInternal());
	        PropertyComparator.sort(sortedDoctors, new MutableSortDefinition("name", true, true));
	        return Collections.unmodifiableList(sortedDoctors);
	    }

	    public void addDoctor(Doctor doctor) {
	        getDoctorsInternal().add(doctor);
	        doctor.setHospital(this);
	    }

	    /**
	     * Return the Pet with the given name, or null if none found for this Owner.
	     *
	     * @param name to test
	     * @return true if pet name is already in use
	     */
	    public Doctor getDoctor(String name) {
	        return getDoctor(name, false);
	    }

	    /**
	     * Return the Pet with the given name, or null if none found for this Owner.
	     *
	     * @param name to test
	     * @return true if pet name is already in use
	     */
	    public Doctor getDoctor(String name, boolean ignoreNew) {
	        name = name.toLowerCase();
	        for (Doctor doctor : getDoctorsInternal()) {
	            if (!ignoreNew || !doctor.isNew()) {
	                String compName = doctor.getName();
	                compName = compName.toLowerCase();
	                if (compName.equals(name)) {
	                    return doctor;
	                }
	            }
	        }
	        return null;
	    }

	    @Override
	    public String toString() {
	        return new ToStringCreator(this)

	                .append("id", this.getId())
	                .append("new", this.isNew())
	                .append("lastName", this.getLastName())
	                .append("firstName", this.getFirstName())
	                .append("address", this.address)
	                .append("city", this.city)
	                .append("telephone", this.telephone)
	                .toString();
	    }
	}
}

