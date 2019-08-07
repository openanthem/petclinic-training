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

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Model.Param.Values;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextArea;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.home.Note;
import com.atlas.client.extension.petclinic.core.home.CodeValueTypes.NoteTypes;
import com.antheminc.oss.nimbus.domain.defn.extension.ParamContext;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model @Getter @Setter
public class VMAddNote {

	@Section
	private VSAddNote vsAddNote;
	
	@Model @Getter@Setter
	public static class VSAddNote{
		
		@Form
		@Path(linked = false)
		private VFAddNote vfAddNote;
		
	}
	
	@MapsTo.Type(Note.class)
	@Getter
	@Setter
	public static class VFAddNote{
		
		@Label("Note Type")
		@ComboBox
		@Values(NoteTypes.class)
		@ParamContext(enabled = false, visible = true)
		@Path
		private String noteType;
		
		@Label("Note Description")
		@NotNull
		@TextArea
		@Path
		private String noteDescription;
		
		@ButtonGroup
		private DefaultButtonGroup vbgDefault;
	}
	
	@Model @Getter @Setter
	public static class DefaultButtonGroup {
		
		@Label(value="Submit")
		@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Config(url = "<!#this!>/../../../vfAddNote/_update")
		@Config(url = "/p/notes/_new?fn=_initEntity&target=/noteDescription&json=\"<!/../noteDescription!>\"&target=/noteType&json=\"<!/../noteType!>\"")
		@Config(url = "<!#this!>/../../../../_process?fn=_setByRule&rule=togglemodal")
		private String submit;
		
		@Label(value = "Back")
		@Button(style = Button.Style.SECONDARY, type = Button.Type.reset)
		@Config(url = "<!#this!>/../../../../_process?fn=_setByRule&rule=togglemodal")
		private String back;
		
	}
}
