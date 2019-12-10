package com.atlas.client.extension.petclinic.view.home;

import java.time.ZonedDateTime;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Type;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.FileAttachment;

import lombok.Getter;
import lombok.Setter;

@Type(FileAttachment.class)
@Model
@Getter @Setter
public class FileLineItem {
	
	@Path
	private Long id;
	
	@Label("Name")
	@GridColumn
	@Path
	private String fileName;
	
	@Label("Type")
	@GridColumn
	@Path
	private String fileType;
	
	@Label("Comments")
	@GridColumn
	@Path
	private String comments;
	
	@Label("Description")
	@GridColumn
	@Path
	private String description;
	
	@Label("Uploaded Time")
	@GridColumn
	@Path
	private ZonedDateTime uploadedDateTime;
	
	@LinkMenu
	private VLMDefault vlmDefault;
	
	@Model @Getter @Setter
	public static class VLMDefault {
		
		@Label("View File")
		@Link(value= Link.Type.DOWNLOAD, target="_blank", url="/fileattachment/download?id={id}")
		@MapsTo.Path(linked = false)
		private String view;
		
		@Label("Delete File")
		@Link
		@Config(url = "/p/fileattachment:<!/../../.m/id!>/_delete")
		//TODO :Confirm if we delete the file or just the reference
		@Config(url = "/vpNotes/vtNotes/vsFiles/files/_get")
		private String delete;
	}
}
