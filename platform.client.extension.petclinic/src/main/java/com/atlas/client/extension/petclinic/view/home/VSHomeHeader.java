/**
 * 
 */
package com.atlas.client.extension.petclinic.view.home;

import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Hints;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Hints.AlignOptions;
/* import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Label; DNE */
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.PageHeader;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.PageHeader.Property;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Paragraph;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Model @Getter @Setter
public class VSHomeHeader {
	
	@Link(imgSrc = "/images/anthem.png")
	@Hints(AlignOptions.Left)
	@PageHeader(Property.LOGO)
	@Label(value = "Anthem")
	private String linkHomeLogo;
	
	@PageHeader(Property.APPTITLE)
	@Paragraph
	@Label(value = "Pet Clinic")
	private String title;
	
}
