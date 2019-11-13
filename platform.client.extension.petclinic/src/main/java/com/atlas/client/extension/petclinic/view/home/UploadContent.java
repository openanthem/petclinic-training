package com.atlas.client.extension.petclinic.view.home;



import java.time.ZonedDateTime;

import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;

import lombok.Data;

@Model @Data
public class UploadContent {
	@TextBox(hidden = true, postEventOnChange = true)
	private String fileId;

	@TextBox(hidden = true, postEventOnChange = true)
	private String name;

	private String attachedTo;

	private ZonedDateTime dateTime;

	private String addedBy;
}