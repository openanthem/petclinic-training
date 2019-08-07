/**
 *  Copyright 2016-2018 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.atlas.client.extension.petclinic.view.owner;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Type;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Model.Param.Values;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CheckBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FormElementGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Header;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.GROUP_1;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.GROUP_3;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.ValidationScope;
import com.atlas.client.extension.petclinic.core.home.CodeValueTypes.OwnerNotificationPreferences;
import com.atlas.client.extension.petclinic.core.owner.Owner;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@MapsTo.Type(Owner.class)
@Getter @Setter
public class VPAddEditOwner {
	
	@Tile
    private VTAddEditOwner vtAddEditOwner;

	@Model @Getter @Setter
	public static class VTAddEditOwner {
	
	    @Header(size=Header.Size.H3)
	    private String addOwnerHeader;
	
	    @Header(size=Header.Size.H3)
	    private String editOwnerHeader;
	
	    @Section
	    private VSAddEditOwner vsAddEditOwner;
	}
	
	@Model @Getter @Setter
	public static class VSAddEditOwner {
		
	    @Form
	    private VFAddEditOwner vfAddEditOwner;
	}
	
	@Type(Owner.class)
	@Getter @Setter
	public static class VFAddEditOwner {
	    
	    // TODO id is not sent with UI payload using @ParamContext(enabled = false, visible = true)
	    // Should we offer a way of supporting this in favor of deprecating readOnly?
	    @Label("Owner ID")
	    @TextBox(readOnly = true, cssClass="oneColumn")
	    @Path
	    private Long id;
	    
	    @Label("Name")
	    @FormElementGroup(cssClass="oneColumn")
		private SectionName sectionName;
	    
	    @Label("Address")
	    @FormElementGroup(cssClass="oneColumn")
		private SectionAddress sectionAddresss;
	    
	    @FormElementGroup(cssClass="oneColumn")
	    private SectionNickname sectionNickname;
	    
	    @FormElementGroup(cssClass="fourColumn")
	    private SectionNotificationPreference sectionNotificationPreference;
	    
	    @FormElementGroup(cssClass="oneColumn")
		private SectionContact sectionContact;
	    
	    @ButtonGroup(cssClass="text-sm-left pt-2 pb-2 oneColumn")
	    private VBGAddOwnerButtonGrp vbgAddOwnerButtonGrp;
	}
	
	@Type(Owner.class)
	@Model
	@Getter @Setter
	public static class SectionName {
		
		@Label("First Name")
	    @TextBox(cssClass="inline")
	    @NotNull
	    @Path
	    private String firstName;
		
		@Label("Last Name")
		@TextBox(cssClass="inline")
	    @NotNull
	    @Path
	    private String lastName;
		
	}
	
	@Type(Owner.class)
	@Model
	@Getter @Setter
	public static class SectionAddress {
		
		@Path 
		@TextBox(cssClass="inline")
		@Label("Address 1")
		@NotNull(groups = { GROUP_1.class })
		private String address;
		
		@Path 
		@TextBox(cssClass="inline")
		@Label("Address 2 (Optional)")
		@NotNull(groups = { GROUP_1.class })
		private String address2;
		 
		@Path 
	    @TextBox(cssClass="sixColumn") 
	    @NotNull(groups = { GROUP_1.class })
	    @Label("City")
	    private String city;
		
		@Path 
		@TextBox(cssClass ="inline")
	    @NotNull(groups = { GROUP_1.class })
	    @Label("State")
	    private String state;
		
		@Path 
		@TextBox(cssClass="inline")
	    @NotNull(groups = { GROUP_1.class })
	    @Label("Zip Code")	
	    private String zip;
		
	}
	
	@Type(Owner.class)
	@Model
	@Getter @Setter
	public static class SectionNickname {
		
		@Label("Nickname")
	    @NotNull(groups = { GROUP_1.class })
		@TextBox(cssClass="inline")
	    private String nickname;
	    
	    @Label("Does user prefer nickname?")
	    @CheckBox(postEventOnChange = true, cssClass="inline")
	    @ValidateConditional(when = "state == true", scope = ValidationScope.SIBLING, targetGroup = GROUP_1.class)
	    private boolean shouldUseNickname;
		
	}

	
	@Type(Owner.class)
	@Model
	@Getter @Setter
	public static class SectionNotificationPreference {
		
	    @Label("Notification Preference")
	    @NotNull
	    @ComboBox(postEventOnChange = true)
	    @Values(OwnerNotificationPreferences.class)
	    @ValidateConditional(when = "state == 'Physical_mail'", scope = ValidationScope.CHILDREN, targetGroup = GROUP_1.class, targetPath = { 
	    		"../../sectionAddresss/address",
	    		"../../sectionAddresss/city",
	    		"../../sectionAddresss/state",
	    		"../../sectionAddresss/zip",
	    		"../../sectionContact/telephone",
	    		"../../sectionContact/email"
	    })
	    @ValidateConditional(when = "state == 'Email'", scope = ValidationScope.CHILDREN, targetGroup = GROUP_3.class, targetPath = {
	    		"../../sectionAddresss/address",
	    		"../../sectionAddresss/city",
	    		"../../sectionAddresss/state",
	    		"../../sectionAddresss/zip",
	    		"../../sectionContact/telephone",
	    		"../../sectionContact/email"
	    })
	    private String notificationPreference;
		
	}
	
	@Type(Owner.class)
	@Model
	@Getter @Setter
	public static class SectionContact {
		
	    @Path 
	    @TextBox(cssClass="inline")
	    @NotNull
	    @Label("Telephone") 
	    private String telephone;
	    
	    @Path 
	    @TextBox(cssClass="inline")
	    @NotNull(groups = { GROUP_3.class })
	    @Label("Email Address") 
	    private String email;
		
	}
	
	@Model @Getter @Setter
	public static class VBGAddOwnerButtonGrp {
	
		@Label("Submit")
	    @Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Config(url = "/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/sectionName/_update")
		@Config(url = "/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/sectionAddresss/_update")
		@Config(url = "/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/sectionNickname/_update")
		@Config(url = "/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/sectionNotificationPreference/_update")
		@Config(url = "/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/sectionContact/_update")
	    @Config(url = "/p/ownerlandingview/_nav?pageId=vpOwners")
	    private String submit;
	
		@Label("Cancel")
	    @Button(style = Button.Style.PLAIN, type = Button.Type.reset)
	    @Config(url = "/p/ownerlandingview/_nav?pageId=vpOwners")
	    private String cancel;
	}
}
