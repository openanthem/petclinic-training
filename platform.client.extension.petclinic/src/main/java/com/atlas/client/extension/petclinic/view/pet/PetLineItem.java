package com.atlas.client.extension.petclinic.view.pet;

import java.time.LocalDate;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.pet.Pet;

import lombok.Getter;
import lombok.Setter;

@MapsTo.Type(Pet.class)
@Getter @Setter
public class PetLineItem {
	
    @GridColumn(hidden = true)
    @Path
    private Long id;
 
    @Label("Name")
    @GridColumn
    @Path
    private String name;
 
    @Label("Type")
    @GridColumn(filter = true)
    @Path("type")
    private String petType;
    
    @Label("Owner Name")
    @GridColumn(filter = true)
    @Path
	private String ownerName;
    
    @Label("Date of Birth")
    @GridColumn(filter = true)
    @Path
    private LocalDate dob;    
    
    @LinkMenu
    private VLMCaseItemLinks vlmCaseItemLinks;
   
    @Model @Getter @Setter
    public static class VLMCaseItemLinks {
    	
    	@Label("Edit Pet")
        @Link
        @Config(url = "/p/petlayout:<!/../id!>/_get?b=$execute")
        @Config(url = "/p/petview:<!/../id!>/_get")
    	@Config(url = "/p/petview:<!../id!>/_nav?pageId=vpAddEditPet")
        private String editPet;
     
    	@Label("View Visits")
        @Link
        @Config(url = "/p/petlayout:<!/../id!>/_get?b=$execute")
        @Config(url = "/p/petview:<!/../id!>/_get")
        @Config(url = "/p/petview:<!../id!>/_nav?pageId=vpPetInfo")
        private String viewVisits;
    	
    }
	
}
