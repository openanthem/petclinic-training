package com.atlas.client.extension.petclinic.view.veterinarian;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.extension.Rule;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.veterinarian.Veterinarian;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Rakesh Patel
 *
 */


@MapsTo.Type(Veterinarian.class)
@Getter @Setter
public class VeterinarianLineItem {
	
	@Label("Speciality")
	@FieldValue
	@Path
	private String speciality;
	
	
	@Label("Number of pets")
	@FieldValue
	@Path
	private String numberOfPets;
	
//	@Label("Phone Number")
//	@FieldValue
//	@Path
//	private String phoneNumber;
	
	@Label("Edit Vet")
    @Link
    @Config(url = "/p/veterinarianlandingview:<!/../id!>/_get?b=$execute")
    @Config(url = "/p/veterinarianlandingview:<!/../id!>/_get")
	@Config(url = "/p/veterinarianlandingview:<!../id!>/_nav?pageId=vpAddEditVeterinarian")
    private String editVet;
	
    @GridColumn(hidden = true)
    @Path
    private Long id;
}