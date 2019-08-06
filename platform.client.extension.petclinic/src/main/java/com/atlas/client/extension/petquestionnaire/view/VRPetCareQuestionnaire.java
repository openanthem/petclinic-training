package com.atlas.client.extension.petquestionnaire.view;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.atlas.client.extension.petquestionnaire.core.PetAssessment;
import com.atlas.client.extension.petquestionnaire.core.PetCareAssessment;
import com.atlas.client.extension.petquestionnaire.core.PetCareAssessment.PetCareForm;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */

@Domain(value = "petcareassessmentview", includeListeners = { ListenerType.websocket })
@Repo(value = Database.rep_none, cache = Cache.rep_device)
@MapsTo.Type(PetCareAssessment.class)
@ViewRoot(layout = "home")
@Getter
@Setter
public class VRPetCareQuestionnaire {
	
	@Page(defaultPage = true)
	private VPMain vpMain;


	@Model
	@Getter
	@Setter
	public static class VPMain {

		@Tile( size = Tile.Size.Large)
		private VTMain vtMain;
	}

	@Model
	@Getter
	@Setter
	public static class VTMain {
		
		@Section
		private VSPetGeneralAssessment vsPetGeneralAssessment;
	
	}
	
	@MapsTo.Type(PetAssessment.class)
	@Getter
	@Setter
	public static class VSPetGeneralAssessment{
			
		@Form(cssClass = "questionGroup")
		private PetCareForm petCareForm;

	}

	

}