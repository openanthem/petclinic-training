package com.atlas.client.extension.petclinic.core.veterinarian;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Rakesh Patel
 *
 */
@Domain(value="veterinarian", includeListeners={ListenerType.persistence, ListenerType.update}) 
@Repo(value=Database.rep_mongodb, cache=Cache.rep_device)
@Getter @Setter @ToString(callSuper=true)
public class Veterinarian extends AbstractEntity.IdLong {
	
	private static final long serialVersionUID = 1L;

	private String firstName;
	
	private String lastName;
	
	private String fullName;
	
	public String getFullName() {
		return firstName + ' ' + lastName;		
	}
	
	private String veterinarian;
	
//	private String phoneNumber;
	
	private String speciality;
	
	private String numberOfPets;
	
	
	
}