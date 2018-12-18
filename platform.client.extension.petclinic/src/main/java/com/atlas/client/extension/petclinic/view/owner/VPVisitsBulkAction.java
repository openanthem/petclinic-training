package com.atlas.client.extension.petclinic.view.owner;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.Visit;

import lombok.Getter;
import lombok.Setter;

@Model
@Getter
@Setter
public class VPVisitsBulkAction {
	
	@Tile(imgSrc = "resources/icons/task.svg#Layer_1")
    private VTVisits vtVisits;
	
	@Model
	@Getter @Setter
	public static class VTVisits  {

		@Section
		private VSVisits vsVisits;
		
    }
	
	@Model
	@Getter @Setter
	public static class VSVisits  {
		
		@Label("Visits - Bulk Action")
		@MapsTo.Path(linked = false)       
		@Grid(onLoad = true, pageSize = "3", rowSelection = true, postButton = true, postButtonTargetPath = "ids",
			postButtonUri = "../actionCancelVisits", postButtonLabel="Cancel")
		@Config(url = "/vpVisitsBulkAction/vtVisits/vsVisits/visits.m/_process?fn=_set&url=/p/visit/_search?fn=example")
		private List<VisitLineItem> visits;
		
		
		@Config(url = "/vpVisitsBulkAction/vtVisits/vsVisits/tempCancelVisitList/_replace")
		@Config(url = "/p/visit:<!/../visits/<!col!>/id!>/status/_replace?rawPayload=\"Cancelled\"", col = "<!/../tempCancelVisitList/ids!>")
		@Config(url = "/vpVisitsBulkAction/vtVisits/vsVisits/visits/_get")
		private String actionCancelVisits;
		
		@MapsTo.Path(linked = false) 
		private TempCancelVisitList tempCancelVisitList;
		
    }
	
	@MapsTo.Type(Visit.class)
	@Getter @Setter
	public static class TempCancelVisitList {

		private String[] ids;
	}

}
