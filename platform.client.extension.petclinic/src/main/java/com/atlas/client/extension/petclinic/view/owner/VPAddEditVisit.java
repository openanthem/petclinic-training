package com.atlas.client.extension.petclinic.view.owner;

import java.time.LocalDate;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Executions.Configs;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.Visit;
import com.antheminc.oss.nimbus.domain.defn.extension.ParamContext;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@MapsTo.Type(Visit.class)
@Getter @Setter
public class VPAddEditVisit {
	
	@Tile(imgSrc="resources/icons/autocase.svg#Layer_1", size=Tile.Size.Large) 
	private VTAddEditVisit vtAddEditVisit;

	@Model
	@Getter @Setter
	public static class VTAddEditVisit {
		
		@Section
		private VSAddEditVisit vsAddEditVisit;
	}

	@MapsTo.Type(Visit.class)
	@Getter @Setter
	public static class VSAddEditVisit {
		
		@Form(cssClass="twoColumn")
		private VFAddEditVisit vfAddEditVisit;
	}
	

	@MapsTo.Type(Visit.class)
	@Getter @Setter
	public static class VFAddEditVisit {
		
		@ButtonGroup
		private VBGAddEditVisit buttonGroup;
		
		@TextBox(readOnly=true) @Path
		@Label("Pet Id")
		private String petId;
		
		@TextBox
		@ParamContext(enabled= false, visible = true)
		@Path
		@Label("Owner's name")
		private String ownerName;
		
		@TextBox
		@ParamContext(enabled= false, visible = true)
		@Path
		@Label("Pet's name")
		private String petName;
		
		@TextBox @Path
		@Label("Reason For Visit")
		private String reasonForVisit;
		
		@TextBox @Path
		@Label("Visit Outcome")
		private String visitOutcome;
		
		@TextBox @Path
		@Label("Visit Note")
		private String visitNote;
		
		@Calendar @Path
		@Label("Appointment")
		private LocalDate appointment;
		
		@TextBox @Path
		@Label("Status")
		private String status;
	}
	
	@Model
	@Getter @Setter
	public static class VBGAddEditVisit {
		
		@Button(browserBack=true)
		private String back;
		
		@Configs({
			@Config(url="/vPAddEditVisit/vtAddEditVisit/vsAddEditVisit/vfAddEditVisit/_update"),
			@Config(url="/p/petclinicdashboard/_nav?pageId=vpDashboard")	
		})
		@Button(style=Button.Style.PRIMARY, type = Button.Type.submit)
		private String Submit;
	}
}
