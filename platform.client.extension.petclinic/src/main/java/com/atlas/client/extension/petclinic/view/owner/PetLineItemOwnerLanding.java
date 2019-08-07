package com.atlas.client.extension.petclinic.view.owner;

import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.pet.Pet;

import lombok.Getter;
import lombok.Setter;
/**
 * @author Andrew Jo
 *
 */
@MapsTo.Type(Pet.class)
@Getter @Setter
public class PetLineItemOwnerLanding {
	
    @GridColumn(hidden = true)
    @Path
    private Long id;
 
    @Label("Pet's name")
    @GridColumn
    @Path
    private String name;
	
}
