/**
 * 
 */
package com.atlas.client.extension.petclinic.view.pet;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Accordion;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.AccordionTab;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetail;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValueGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Initialize;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.MenuLink;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.MenuPanel;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.PageHeader;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.PageHeader.Property;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section.Type;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TabInfo;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.Owner;
import com.atlas.client.extension.petclinic.core.pet.Pet;

import lombok.Getter;
import lombok.Setter;
@Domain(value = "petlayout", includeListeners = { ListenerType.websocket })
@MapsTo.Type(Pet.class)
@Repo(value = Database.rep_none, cache = Cache.rep_device)
@Getter @Setter
public class VLPet {

	@Page
	private VP vp;
	
	@Model
	@Getter @Setter
	public static class VP {
		
		@Section(Type.HEADER)
		@Initialize
		@Config(url = "/vp/vsHeader/vaBanner/vatBannerTab/vcdPetDetails/vcdbPetDetails/vfvgOwnerContact/.m/_process?fn=_set&url=/p/owner:<!/.m/ownerId!>/_get?b=$state")
		private VSHeader vsHeader;
		
		@MenuPanel
		private VSNavigationLinks vsNavigationLinks;
	}
	
	@Model
	@Getter @Setter
	public static class VSHeader {
		
		@Accordion
		private VABanner vaBanner;
		
		@Model
		@Getter @Setter
		public static class VABanner {
			
			@AccordionTab(selected = true)
			private VATBannerTab vatBannerTab;
		}
		
		@Model
		@Getter @Setter
		public static class VATBannerTab {
			
			@TabInfo(cssClass = "infoBarHeader")
			private String ownerName;
			
			@CardDetail
			private VCDPetDetails vcdPetDetails;
		}
		
		@Model
		@Getter @Setter
		public static class VCDPetDetails {
			
			@CardDetail.Body
			private VCDBPetDetails vcdbPetDetails;
		}
		
		@MapsTo.Type(Pet.class)
		@Getter @Setter
		public static class VCDBPetDetails {
			
			@Label("Owner Name")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@PageHeader(Property.SUBHEADER)
			@Path
			private String ownerName;
			
			@FieldValueGroup(cols = "5", cssClass = "label-left align-right")
			@Path(linked = false)
			private VFVGOwnerContact vfvgOwnerContact;
			
			@FieldValueGroup(cols = "5", cssClass = "label-left align-right")
			private VFVGTest vfvgSection2;
		}
		
		@MapsTo.Type(Pet.class)
		@Getter @Setter
		public static class VFVGTest {
			
			@Label("Type")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String type;
		}
		
		@MapsTo.Type(Owner.class)
		@Getter @Setter
		public static class VFVGOwnerContact {
			
			@Label("Address")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String address;
			
			@Label("City")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String city;
			
			@Label("State")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String state;
			
			@Label("Zip")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String zip;
			
			@Label("Telephone")
			@FieldValue(cols = "1", cssClass = "label-left align-right")
			@Path
			private String telephone;
		}
	}
	
	@Model
	@Getter @Setter
	public static class VSNavigationLinks {
		
		@Label("Sample Link 1")
		@MenuLink
		private String sampleLink1;
		
		@Label("Sample Link 2")
		@MenuPanel
		private SampleLink2 sampleLink2;
		
		@Model
		@Getter @Setter
		public static class SampleLink2 {
			
			@Label("Sample Link 2.1")
			@MenuLink
			private String sampleLink1;
			
			@Label("Sample Link 2.2")
			@MenuLink
			private String sampleLink2;
		}
	}
}
