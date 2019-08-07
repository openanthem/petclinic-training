package com.atlas.client.extension.petclinic.core.owner;

import java.time.LocalDate;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sandeep Mantha
 *
 */

@Domain(value="visit", includeListeners={ListenerType.persistence, ListenerType.update}) 
@Repo(value=Database.rep_mongodb, cache=Cache.rep_device)
@Getter @Setter @ToString(callSuper=true)
public class Visit extends AbstractEntity.IdLong{

	private static final long serialVersionUID = 1L;
	
	private String petId; //petId
	
	@Calendar
	@Label("Appointment")
	private LocalDate appointment;
	
	private String ownerName;
	
	private String petName;
	
	private String reasonForVisit;
	
	private String visitOutcome;
	
	private String visitNote;
	
	private String status; // change to Status enum. Find out what are the possible values.
	
	private String veterinarianId;
}
