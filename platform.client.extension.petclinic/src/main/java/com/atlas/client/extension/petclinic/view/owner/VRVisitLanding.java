/**
 * 
 */
package com.atlas.client.extension.petclinic.view.owner;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button.Style;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.ParamContext;
import com.atlas.client.extension.petclinic.core.owner.Visit;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rakesh Patel
 *
 */
@Domain(value = "visitlandingview", includeListeners = {ListenerType.websocket})
@Repo(value=Repo.Database.rep_none,cache=Repo.Cache.rep_device)
@MapsTo.Type(Visit.class)
@Getter @Setter
@ViewRoot(layout = "")
public class VRVisitLanding {

	@Label("Visits - Client Side")
	@Page(defaultPage = true)
	private VPVisits vpVisits;
	
	@Label("Visits - Server side")
	@Page
	private VPVisitsServer vpVisitsServer;
	
	@Label("Visits - Bulk Actions")
	@Page
	private VPVisitsBulkAction vpVisitsBulkAction;
	
	@Model
	@Getter @Setter
	public static class VPVisits  {

		@Tile(imgSrc = "resources/icons/task.svg#Layer_1")
        private VTMyVisits vtMyVisits;
		
    }
	
	@Model
	@Getter @Setter
	public static class VTMyVisits  {

		@Section
		private VSMyVisits vsMyVisits;
		
    }
	
	@Model
	@Getter @Setter
	public static class VSMyVisits  {
		
//		@Label("Visits - Bulk Action")
//		@Button(style = Style.SECONDARY)
//		@Config(url = "/.d/_nav?pageId=vpVisitsBulkAction")
//		private String goToVisitsBulkAction;
		
		@ParamContext(enabled=false, visible=false)
		@Label("Owners")
		@Button(style = Style.SECONDARY)
		@Config(url = "/p/ownerlandingview/_new")
		private String goToOwners;
		
		@Label("Visits - Client Side")
		@MapsTo.Path(linked = false)       
		@Grid(onLoad = true, pageSize = "3")
		@Config(url = "/vpVisits/vtMyVisits/vsMyVisits/myVisits.m/_process?fn=_set&url=/p/visit/_search?fn=example")
		private List<VisitLineItem> myVisits;
		
    }
}
