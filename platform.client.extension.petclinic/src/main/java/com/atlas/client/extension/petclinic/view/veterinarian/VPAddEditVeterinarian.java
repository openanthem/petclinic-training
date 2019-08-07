package com.atlas.client.extension.petclinic.view.veterinarian;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Type;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Model.Param.Values;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.ParamContext;
import com.atlas.client.extension.petclinic.core.home.CodeValueTypes.veterinarianSpecialityType;
import com.atlas.client.extension.petclinic.core.veterinarian.Veterinarian;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Rakesh Patel
 *
 */
@MapsTo.Type(Veterinarian.class)
@Getter @Setter
public class VPAddEditVeterinarian {
	
	@Tile
    private VTAddEditVeterinarian vtAddEditVeterinarian;

	
	@Model @Getter @Setter
	public static class VTAddEditVeterinarian {
	
	    @Section
	    private VSAddEditVeterinarian vsAddEditVeterinarian;
	}
	
	@Model @Getter @Setter
	public static class VSAddEditVeterinarian {
		
	    @Form(cssClass="twoColumn")
	    private VFAddEditVeterinarian vfAddEditVeterinarian;
	}
	
	
	@Type(Veterinarian.class)
	@Getter @Setter
	public static class VFAddEditVeterinarian {
		
		@TextBox @MapsTo.Path
		@Label("First Name")
		private String firstName;
		
		@TextBox @MapsTo.Path
		@Label("Last Name")
		private String lastName;
		
		
//		@InputMask(mask="(999) 999-9999")
//		@MapsTo.Path
//		@Label("Phone Number")
//		
//		private String phoneNumber ;
		
		
//		@TextBox(hidden=true) @MapsTo.Path
//		private String fullName;
		
		@Label("Speciality")
		@ComboBox
		@Values(veterinarianSpecialityType.class)
		@ParamContext(visible = true, enabled = true)
		@Path
		private String speciality;
		
		@TextBox @MapsTo.Path
		@Label("Number of Pets")
		private String numberOfPets;
		
		@ButtonGroup
		private VBGAddEditVeterinarian buttonGroup;
		
	}
	
	
	@Model
	@Getter @Setter
	public static class VBGAddEditVeterinarian {
		
		@Label("Submit")
	    @Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Config(url="/vpAddEditVeterinarian/vtAddEditVeterinarian/vsAddEditVeterinarian/vfAddEditVeterinarian/_update")
		@Config(url="/p/veterinarianview/vpVeterinarians/vtVeterinarians/vsVeterinarians/_nav?pageId=vpVeterinarians")
	    private String submit;
	
		@Label("Cancel")
	    @Button(style = Button.Style.PLAIN, type = Button.Type.reset)
		@Config(url="/p/veterinarianview/vpVeterinarians/vtVeterinarians/vsVeterinarians/_nav?pageId=vpVeterinarians")
	    private String cancel;
		
		
	}
}