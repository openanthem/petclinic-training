package com.atlas.client.extension.petclinic.view.owner;

import java.time.LocalDate;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Accordion;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.AccordionTab;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetail;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValueGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TabInfo;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Model
@Getter
@Setter
public class VSsampleHeader {

	@Accordion
	private CaseBanner caseBanner;
	
	@Getter @Setter @Model
	public static class CaseBanner {
		@AccordionTab(selected = true)
		@Label(" ")
		private BannerTab bannerTab;
	}
	
	@Model
	@Getter
	@Setter
	public static class BannerTab {
		
		@TabInfo(cssClass="infoBarHeader")
		private String name;
		
		@CardDetail(cssClass="oneColumn")
		Card_Details cardDetails;
	}
	
	@Model
	@Getter
	@Setter
	public static class Card_Details {
		@CardDetail.Body(cssClass="fourColumn")
		SubHeader_Details cardBody;
	}
	
	@Model
	@Getter
	@Setter
	public static class SubHeader_Details {
		
		@FieldValueGroup()
		private FieldGroup_Details fgDetails;
		
		@FieldValueGroup()
		private FieldGroup_Details2 fgDetails2;

		@FieldValueGroup()
		private FieldGroup_Details3 fgDetails3;

		@FieldValueGroup()
		private FieldGroup_Details4 fgDetails4;

	}
	
	@Model
	@Getter
	@Setter
	public static class FieldGroup_Details {

		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "ID: ")
		private String id;

		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "DOB: ") 
		private LocalDate dob; 

		@FieldValue(cssClass="oneColumn")
		@Label(value = "Age: ")
		private Long age;
	}

	@Model
	@Getter
	@Setter
	public static class FieldGroup_Details2 {

		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "Start Date: ") 
		private LocalDate startDate;
		
		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "End Date: ") 
		private LocalDate endDate;

		@FieldValue(cssClass="oneColumn")
		@Label(value = "Last Visited Date: ")
		private LocalDate lastDate; 

	}
	
	@Model
	@Getter
	@Setter
	public static class FieldGroup_Details3 {
		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "Gender: ")
		private String gender;

		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "Language: ")
		private String language;

		@FieldValue(cssClass="oneColumn")
		@Label(value = "Status:")
		private String status;
	}
	
	@Model
	@Getter
	@Setter
	public static class FieldGroup_Details4 {
		
		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "Address: ")
		private String address;

		@FieldValue(cssClass="oneColumn mb-3")
		@Label(value = "Cell Phone: ")
		private String cell;

		@FieldValue(cssClass="oneColumn")
		@Label(value = "Email: ")
		private String email;

	}
	
}
