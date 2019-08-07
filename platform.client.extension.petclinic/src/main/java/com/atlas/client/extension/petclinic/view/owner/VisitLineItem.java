package com.atlas.client.extension.petclinic.view.owner;

import java.time.LocalDate;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.Visit;

import lombok.Getter;
import lombok.Setter;


/**
 * @author Sandeep Mantha
 *
 */

@MapsTo.Type(Visit.class)
@Getter @Setter
public class VisitLineItem {
	
	@Path @GridColumn(hidden = true)
	private Long id;
	
	// Add Pet Name and Owner columns
	
	@Label("Appointment Date")
	@GridColumn(filter=true)
	@Path 
	private LocalDate appointment;
	
	@Label("Owner's name")
	@GridColumn(filter=true)
	@Path
	private String ownerName;
	
	@Label("Pet's name")
	@GridColumn(filter=true)
	@Path
	private String petName;
	
	@Label("Reason For Visit")
	@GridColumn(filter=true)
	@Path 
	private String reasonForVisit;
	
	@Label("Status")
	@GridColumn(filter=true)
	@Path
	private String status;
	
    @LinkMenu
    private VLMVisitItemLinks vlmVisitItemLinks;
   
    @Model @Getter @Setter
    public static class VLMVisitItemLinks {
        
    	@Label("Pet Questionnaire")
        @Link(imgSrc="edit.png")
        @Config(url="/p/petassessmentview/_new")
        private String petQuestionnaire;
    	
    	@Label("Pet Care Questionnaire")
        @Link(imgSrc="edit.png")
        @Config(url="/p/petcareassessmentview/_new")
        private String petCareQuestionnaire;
    }

}
