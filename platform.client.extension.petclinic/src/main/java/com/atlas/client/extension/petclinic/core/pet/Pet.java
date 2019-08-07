package com.atlas.client.extension.petclinic.core.pet;

import java.time.LocalDate;
import java.util.List;

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
@Domain(value="pet", includeListeners={ListenerType.persistence, ListenerType.update}) 
@Repo(value=Database.rep_mongodb, cache=Cache.rep_device)
@Getter @Setter @ToString(callSuper=true)
public class Pet extends AbstractEntity.IdLong {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	
	private LocalDate dob;
	
	private String type;

	private Long ownerId;
	
	private String ownerName;
	
	private String[] category;
	
	private String notes;

	private List<MealInstruction> mealInstructions;

}
