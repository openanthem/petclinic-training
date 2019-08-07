/**
 * 
 */
package com.atlas.client.extension.petclinic.view.home;

import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Image;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.MenuLink;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.MenuPanel;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Model @Getter @Setter
public class VSGlobalNav {

	@Label("Home")
	@MenuLink(url="petclinicdashboard/vpDashboard", imgSrc="notesIcon") 
	private String home;
	
	@MenuPanel(imgType= Image.Type.SVG, imgSrc="notesIcon")
	@Label("Visits")
	private VMPVisits vmpVisits;
	
	@Label("Veterinarians")
	@MenuLink(url="veterinarianview/vpVeterinarians", imgSrc="notesIcon")
	private String vets;
	
	@Label("Owners")
	@MenuLink(url="ownerlandingview/vpOwners", imgSrc="notesIcon")
	private String owners;
	
	@Label("Pets")
	@MenuLink(url="petclinicdashboard/vpAllPets", imgSrc="notesIcon")
	private String pets;
	
	@Label("Notes")
	@MenuLink(url="petclinicdashboard/vpNotes", imgSrc="notesIcon")
	private String notes;
	
	@Label("Treegrid Demo")
	@MenuLink(url="petclinicdashboard/vpTreegridDemo")
	private String treegrid;
	
	@Label("Charts Demo")
	@MenuLink(url="petclinicdashboard/vpChartDemo")
	private String chartsdemo;
	
	
	@Model
	@Getter
	@Setter
	public class VMPVisits {
		
		@Label("Visits - Client Side Link label")
		@MenuLink(url="visitlandingview/vpVisits", imgSrc="notesIcon")
		private String visits;
		
		@Label("Visits - Server Side Link label")
		@MenuLink(url="visitlandingview/vpVisitsServer", imgSrc="notesIcon")
		private String visitsServer;
		
		@Label("Visits - Bulk Action Link label")
		@MenuLink(url="visitlandingview/vpVisitsBulkAction", imgSrc="notesIcon")
		private String visitsBulkAction;

	}
	
}