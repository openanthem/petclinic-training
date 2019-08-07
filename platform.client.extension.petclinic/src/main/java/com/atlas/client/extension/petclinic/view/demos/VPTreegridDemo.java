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
package com.atlas.client.extension.petclinic.view.demos;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Nature;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Model.Param.Values;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button.Style;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button.Type;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Modal;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Paragraph;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TreeGrid;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.EnableConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.LabelConditional;
import com.atlas.client.extension.petclinic.core.demos.PetHistory;
import com.atlas.client.extension.petclinic.core.home.CodeValueTypes;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model
@Getter @Setter
public class VPTreegridDemo {
	
	@Tile
	private VT vt;
	
	@Model
	@Getter @Setter
	public static class VT {
		
		@Label("This is Treegrid Demo section of Pet Clinic.")
    	@Paragraph(cssClass="font-weight-bold")
    	private String headerCallSection;

		@Modal(closable = true)
		private VMPetHistory vmPetHistory;
		
		@Section
		private VS vs;
	}
	
	@Model
	@Getter @Setter
	public static class VS {

		@Label("Add New Pet")
		@Button
		@Config(url = "<!#this!>/../_process?fn=_setByRule&rule=rules/treegrid-demo/addChild")
		@Config(url = "<!#this!>/../treegrid/_get")
		private String addChild;
		
		@Label("Pet History Tree Grid")
		@TreeGrid
		@Path(linked = false)
		@Config(url = "<!#this!>/.m/_process?fn=_set&url=/p/pethistory/_search?fn=example")
		private List<PetHistoryLineItem> treegrid;
	}
	
	@Model
	@Getter @Setter
	public static class VMPetHistory {
		
		@Config(url = "<!#this!>/../_process?fn=_setByRule&rule=togglemodal")
		@Button(style = Button.Style.PLAIN, type = Button.Type.button)
		private String closeModal;
		
		@Section
		@Path(linked = false)
		private VS vs;
		
		@MapsTo.Type(PetHistory.class)
		@Model @Getter @Setter
		public static class VS {
			
			@LabelConditional(targetPath = "/../..", condition = {
				@LabelConditional.Condition(when = "state == 'create'", then = @Label(value = "Add Pet", helpText = "This is a modal for adding!")),
				@LabelConditional.Condition(when = "state == 'read'", then = @Label(value = "View Pet", helpText = "This is a modal for viewing!")),
				@LabelConditional.Condition(when = "state == 'update'", then = @Label(value = "Edit Pet", helpText = "This is a modal for editing!"))
			})
			private String mode;
			
			@Form
			@Path(value = "/children", nature = Nature.TransientColElem)
			@ActivateConditional(when = "findStateByPath('/../mode') == 'create'", targetPath = "/vbg/add")
			@ActivateConditional(when = "findStateByPath('/../mode') == 'update'", targetPath = "/vbg/save")
			@ActivateConditional(when = "findStateByPath('/../mode') == 'read'", targetPath = "/vbg/ok")
			@EnableConditional(when = "findStateByPath('/../mode') != 'read'", targetPath = "/fields")
			private VF vf;
		}
		
		@MapsTo.Type(PetHistory.class)
		@Model @Getter @Setter
		public static class VF {
			
			private FormFields fields;
			
			@ButtonGroup
			private VBG vbg;
		}
		
		@MapsTo.Type(PetHistory.class)
		@Model @Getter @Setter
		public static class FormFields {
			
			@Label(value = "Name", helpText = "Use a name... any name you want!")
			@TextBox
			@Path
			@NotNull
			private String name;

			@Label("Date of Birth")
			@Calendar
			@Path
			private LocalDate dob;
			
			@Label("Sex")
			@ComboBox
			@Values(CodeValueTypes.Sex.class)
			@NotNull
			@Path
			private String sex;
		}
		
		@Model
		@Getter @Setter
		public static class VBG {
			
			@Label("Cancel")
			@Button(style = Style.SECONDARY, type = Type.reset)
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
			private String cancel;
			
			@Label("Ok")
			@Button(style = Style.SECONDARY, type = Type.reset)
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
			private String ok;
			
			@Label("Add")
			@Button(style = Style.PRIMARY, type = Type.submit)
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_update")
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_get?fn=param&expr=flush()")
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
			private String add;
			
			@Label("Save")
			@Button(style = Style.PRIMARY, type = Type.submit)
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_update")
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_get?fn=param&expr=flush()")
			@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
			private String save;
		}
	}
}
