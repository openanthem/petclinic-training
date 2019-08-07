package com.atlas.client.extension.petclinic.view.veterinarian;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetail;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetailsGrid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.veterinarian.Veterinarian;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


/**
 * @author Sandeep Mantha
 * @author Rakesh Patel
 *
 */
@Domain(value = "veterinarianview", includeListeners = {ListenerType.websocket})
@Repo(value = Repo.Database.rep_none,cache = Repo.Cache.rep_device)
@Getter @Setter @ToString(callSuper = true)
//@MapsTo.Type(Veterinarian.class)
@ViewRoot(layout = "")
public class VRVeterinarian {
	
	@Label("Veterinarians")
	@Page(defaultPage=true)
	private VPVeterinarians vpVeterinarians;

	@Model @Getter @Setter
	public static class VPVeterinarians  {

		@Tile(imgSrc = "resources/icons/task.svg#Layer_1", size = Tile.Size.Large)
        private VTVeterinarians vtVeterinarians;
		
    }
	
	@Model @Getter @Setter
	public static class VTVeterinarians  {
		
		@Section
		private VSVeterinarians vsVeterinarians;
		
		@Label("Card details grid section")
		@Section
		private VeterinarianLineItemDetailsSection veterinarianLineItemDetailsSection;

    }

	@Model @Getter @Setter
	public static class VSVeterinarians  {
		
		@Label("Add Veterinarian")
	    @Button (style = Button.Style.SECONDARY)
	    @Config(url="/p/veterinarianlandingview/_new")
	    private String addVeterinarian;
		
		
		/*@Label("Veterinarians")
		@Path(linked=false)
		//@Config(url="/p/veterinarianlandingview/vpVeterinarians/vtVeterinarians/vsVeterinarians/veterinarians.m/_process?fn=_set&url=/p/veterinarian/_search?fn=example")  
		//@Config(url="<!#this!>/.m/_process?fn=_set&url=/p/veterinarian/_search?fn=example")
		@Grid(onLoad = true,pageSize = "7")
		private List<VeterinarianLineItem> veterinarians;*/
		
    }
	
	@Model
	//@MapsTo.Type(Veterinarian.class)
	@Getter
	@Setter
	public static class VeterinarianLineItemDetailsSection {
		
		@Label("Veterinarians")
		@CardDetailsGrid(onLoad = true)
		@Path(linked = false)
		@Config(url = "/vpVeterinarians/vtVeterinarians/veterinarianLineItemDetailsSection/veterinarianLineItemDetailsList.m/_process?fn=_set&url=/p/veterinarian/_search?fn=example")
		private List<VeterinarianLineItemDetails> veterinarianLineItemDetailsList;

	}

	@Model
	@MapsTo.Type(Veterinarian.class)
	@Getter
	@Setter
	public static class VeterinarianLineItemDetails {
		
		@CardDetail(expandable=true)
		private VeterinarianLineItemSummary veterinarianLineItemSummary;

	}

	@Model
	@Getter
	@Setter
	@MapsTo.Type(Veterinarian.class)
	public static class VeterinarianLineItemSummary {

		@CardDetail.Header
		private VeterinarianCardHeader veterinarianCardHeader;

		@CardDetail.Body(cssClass = "threeColumn")
		private VeterinarianLineItem veterinarianLineItem;
	}
}