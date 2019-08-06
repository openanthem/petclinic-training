package com.atlas.client.extension.petclinic.view.pet;

import java.time.LocalDate;
import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Executions.Configs;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Type;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button.Style;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetail;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.pet.Pet;
import com.atlas.client.extension.petclinic.view.owner.VisitLineItem;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Swetha Vemuri
 *
 */
@Model
@Getter @Setter
public class VPPetInfo {
	
	@Tile(imgSrc="resources/icons/careplan.svg#Layer_1", size=Tile.Size.Large)
	private VTPetInfo vtPetInfo;
	
	@Model
	@Getter @Setter
	public static class VTPetInfo {
		
		@Section
		private VSPetInfo vsPetInfo;
		
		@Section(cssClass="contentBox bg-lightest")
		private VSVisits vsVisits;
		
	}
	
	@Model
	@Getter @Setter
	public static class VSPetInfo {
		
		@CardDetail(cssClass="contentBox right-gutter bg-alternate mt-0")
		private VCDPetInfo vcdPetInfo;
		
	}
	
	@Model
	@Getter @Setter
	public static class VCDPetInfo {
		
		@CardDetail.Body(cssClass = "threeColumn")
		private VCDBPet vcdbPet;
	}
	
	@Type(Pet.class)
	@Getter @Setter
	public static class VCDBPet {
		
		@Path @FieldValue
		@Label("Owner's name")
		private String ownerName;
		
		@Path("name") @FieldValue(cols="2") 
		@Label("Pet's Name")
		private String petName;	
		
		@Path @FieldValue
		@Label("Date Of Birth")
		private LocalDate dob;
		
		@Path @FieldValue 
		@Label("Type")
		private String type;
	}
	
	@Model
	@Getter @Setter
	public static class VSVisits {
		
		@Configs({
			@Config(url="/p/visitview/_new?fn=_initEntity&target=/.m/petId&json=\"<!/.m/id!>\""
					+ "&target=/.m/petName&json=\"<!/.m/name!>\""
					+ "&target=/.m/ownerName&json=\"<!/.m/ownerName!>\"")
	    })
	    @Button(style=Style.SECONDARY)
		@Label("Add Visit")
	    private String addVisit;
		
		@Path(linked=false)
		@Config(url="/vpPetInfo/vtPetInfo/vsVisits/visits.m/_process?fn=_set&url=/p/visit/_search?fn=query&where=visit.petId.eq('<!/.m/id!>')")
		@Grid(onLoad=true, pageSize = "7")
		@Label("Visits")
		private List<VisitLineItem> visits;
	}
	
}