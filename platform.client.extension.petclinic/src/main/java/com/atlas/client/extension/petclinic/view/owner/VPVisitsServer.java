package com.atlas.client.extension.petclinic.view.owner;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

@Model
@Getter
@Setter
public class VPVisitsServer {
	


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
		
		@Label("Visits - Server side")
		@MapsTo.Path(linked = false)       
		@Grid(onLoad = true, pageSize = "3",lazyLoad=true)
		@Config(url = "/vpVisitsServer/vtVisits/vsVisits/visits.m/_process?fn=_set&url=/p/visit/_search?fn=example&<!page=y!>")
		private List<VisitLineItem> visits;
		
    }

}
