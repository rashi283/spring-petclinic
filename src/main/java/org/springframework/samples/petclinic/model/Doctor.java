package org.springframework.samples.petclinic.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.joda.time.DateTime;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PropertyComparator;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "doctors")
public class Doctor extends NamedEntity 
{

    @Column(name = "birth_date")
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    private DateTime birthDate;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private DoctorType type;

    @ManyToOne
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "doctor", fetch = FetchType.EAGER)
    private Set<Visit> visits;


    public void setType(DoctorType type) {
        this.type = type;
    }

    public DoctorType getType() {
        return this.type;
    }

    protected void setHospital(Hospital hospital) {
        this.hospital = hospital;
    }

    public Hospital getHospital() {
        return this.hospital;
    }

    protected void setVisitsInternal(Set<Visit> visits) {
        this.visits = visits;
    }

    protected Set<Visit> getVisitsInternal() {
        if (this.visits == null) {
            this.visits = new HashSet<Visit>();
        }
        return this.visits;
    }

    public List<Visit> getVisits() {
        List<Visit> sortedVisits = new ArrayList<Visit>(getVisitsInternal());
        PropertyComparator.sort(sortedVisits, new MutableSortDefinition("date", false, false));
        return Collections.unmodifiableList(sortedVisits);
    }

    public void addVisit(Visit visit) {
        getVisitsInternal().add(visit);
        visit.setDoctor(this);
    }

}
