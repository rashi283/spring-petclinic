package org.springframework.samples.petclinic.repository.jdbc;

import org.springframework.samples.petclinic.model.Doctor;

public class JdbcDoctor extends Doctor{
	
	private int typeId;

    private int hospitalId;

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public int getHospitalId() {
		return hospitalId;
	}

	public void setHospitalId(int hospitalId) {
		this.hospitalId = hospitalId;
	}
    
    

}
