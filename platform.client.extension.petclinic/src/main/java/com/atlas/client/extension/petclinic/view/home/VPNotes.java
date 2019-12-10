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
package com.atlas.client.extension.petclinic.view.home;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Executions.Configs;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FileUpload;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FileUpload.Type;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FormElementGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Modal;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Paragraph;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.VisibleConditional;
import com.atlas.client.extension.petclinic.core.FileAttachment;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.YesTest;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model
@Getter @Setter
public class VPNotes {

	@Tile
	private VTNotes vtNotes;

	@Model
	@Getter @Setter
	public static class VTNotes {
		
		@Label("This is the Notes section of Pet Clinic.")
    	@Paragraph(cssClass="font-weight-bold")
    	private String headerCallSection;
		
		@Modal(closable = true, width = "large")
		private VMNotes vmNotes;
		
		@Section
		private VSNotes vsNotes;
		
		@Section
		private VSFiles vsFiles;
		
		@Section
		private VSSampleVisibilityTest vsSampleVisibilityTest;
		
		
		
		
	}
	
	
	@Model
	@Getter @Setter
	public static class VSNotes {
		
		@Label("Pet Care Questionnaire")
		@Button(imgSrc = "fa-plus-circle")
        @Config(url="/p/petcareassessmentview/_new")
        private String petCareQuestionnaire;
		
		
		@Button(imgSrc = "fa-plus-circle")
		@Label(value = "Hide File Section")
		@Config(url = "/vpNotes/vtNotes/vsFiles/_process?fn=_setByRule&rule=hidefilesection")
		private String hideFilesSection;
		
		@Label(value = "Add a Note")
		@Button(imgSrc = "fa-plus-circle")
		@Config(url = "/vpNotes/vtNotes/vmNotes/mode/_process?fn=_set&value=add")
		@Config(url = "/vpNotes/vtNotes/vmNotes/vsMain/vfAddNote/_replace?rawPayload=null")
		@Config(url = "/vpNotes/vtNotes/vmNotes/vsMain/vfAddNote/noteType/_process?fn=_set&value=general")
		@Config(url = "/vpNotes/vtNotes/vmNotes/_process?fn=_setByRule&rule=togglemodal")
		private String addNote;
		
		@Label("Notes")
		@Grid(onLoad = true, pageSize = "20")
		@Path(linked = false)
		@Config(url = "<!#this!>/.m/_process?fn=_set&url=/p/notes/_search?fn=example")
		private List<NoteLineItem> notes;
	}
	@Model
	@Getter @Setter	
	public static class VSFiles{
		@Form
		private VFFile vfFile;
		
	

		@Label("Files")
		@Grid(onLoad = true, pageSize = "20",isTransient=true)
		@Path(linked=false)
		private List<FileLineItem> gridFiles;
		
		private FileAttachment uploadedFiles;
		
		@Button(style = Button.Style.SECONDARY, type = Button.Type.submit)
		@Label(value = "Add All File Ids to Pet 7")
		@Config(url = "/p/pet:7/files/_update?rawPayload=<!json(/../uploadedFiles)!>")
		private String addFileIdsToPet;
	}
	@Model
	@Getter @Setter
	public static class VFFile{
		
		@Paragraph(cssClass="h3 oneColumn")
		@Label(value="Add Attachment")
		private String formTitle;
		
		
		@FileUpload(url="/fileattachment/upload", targetParam="/../../uploadedFiles",urlType=Type.INTERNAL,
		type= ".exe,.dot,.jpeg,.odt,.ini,.dotx,.docm,.onetoc2,.gif,.xlsb,.one,.docx,.bmp,.zip,.xml,.vbs,.csv,.mht,.xlsm,.PNG,.jpg,.htm,.url,.txt,.xls,.xps,.xlsx,.msg,.tiff,.docx,.rtf,.doc,.pdf,.TIF,"
				+ ".EXE,.DOT,.JPEG,.ODT,.INI,.DOTX,.DOCM,.ONETOC2,.GIF,.XLSB,.ONE,.DOCX,.BMP,.ZIP,.XML,.VBS,.CSV,.MHT,.XLSM,.png,.JPG,.HTM,.URL,.TXT,.XLS,.XPS,.XLSX,.MSG,.TIFF,.DOCX,.RTF,.DOC,.PDF,.tif")
		@NotNull
		private String fileControl;
		

		@TextBox
		@NotNull
		@Label("Description")
		private String description;
		
		@TextBox(postEventOnChange=true)
		@Label("Comments")
		@VisibleConditional(when="state != null && state !=''", targetPath="../submitBG")
		
		private String comments;
		
		@ButtonGroup(cssClass="oneColumn center")
		private VBGSubmit submitBG;
		
		
		
	}

	@Model
	@Getter
	@Setter
	public static class VBGSubmit{
		@Configs({
			@Config(url = "<!#this!>/../../_update"),
//			@Config(url = "/vpNotes/vtNotes/vsFiles/fileIds/_update?rawPayload=<!/../../internalId!>"),
			@Config(url = "/vpNotes/vtNotes/vsFiles/gridFiles/.m/_update?rawPayload=<!json(/../../../uploadedFiles)!>"),
			@Config(url = "<!#this!>/../../_delete"),
			
		})
		@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Label(value = "Add File")
		private String upload;
		
	

	
	}
	
	
}
