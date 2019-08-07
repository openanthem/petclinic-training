package com.atlas.client.extension.petclinic.view.veterinarian;

import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.atlas.client.extension.petclinic.core.veterinarian.Veterinarian;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Rakesh Patel
 *
 */


@MapsTo.Type(Veterinarian.class)
@Getter @Setter
public class VeterinarianCardHeader {
	
	@FieldValue
	@Path
	private String fullName;
	
}