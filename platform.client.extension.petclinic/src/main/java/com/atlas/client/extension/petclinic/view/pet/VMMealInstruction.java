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
package com.atlas.client.extension.petclinic.view.pet;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Nature;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.pet.MealInstruction;
import com.atlas.client.extension.petclinic.core.pet.Pet;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model
@Getter @Setter
public class VMMealInstruction {
	
    @Config(url = "<!#this!>/../_process?fn=_setByRule&rule=togglemodal")
	@Button(style = Button.Style.PLAIN, type = Button.Type.button)
	private String closeModal;
	
	@Section
	private VS section;
	
	@MapsTo.Type(Pet.class)
	@Model @Getter @Setter
	public static class VS {
		
		@Form
		@Path(value = "/mealInstructions", nature = Nature.TransientColElem)
		@ActivateConditional(when = "isAssigned()", targetPath = "/vbg/edit")
		@ActivateConditional(when = "!isAssigned()", targetPath = "/vbg/add")
		private VF form;
	}
	
	@MapsTo.Type(MealInstruction.class)
	@Model @Getter @Setter
	public static class VF {
		
		@Path
		private Long id;
		
		@Label("Food Name")
		@TextBox
		@Path
		private String name;
		
		@Label("Amount")
		@TextBox
		@Path
		private String amount;
		
		@Label("Time of Day")
		@TextBox
		@Path
		private String timeOfDay;
		
		@Label("How long has pet eaten this food (in months)?")
 		@TextBox
 		@Path
 		private String lengthOfTimeEaten;
		
		@ButtonGroup
		private VBG vbg;
	}
	
	@Model
	@Getter @Setter
	public static class VBG {
		
		@Label("Add")
		@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/_update")
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/_get?fn=param&expr=flush()")
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/_process?fn=_setByRule&rule=togglemodal")
		private String add;
		
		@Label("Edit")
		@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/_update")
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/_get?fn=param&expr=flush()")
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/_process?fn=_setByRule&rule=togglemodal")
		private String edit;
		
		@Label("Cancel")
		@Button(style = Button.Style.SECONDARY, type = Button.Type.reset)
		@Config(url = "/vpAddEditPet/vtAddEditPet/vmMealInstruction/_process?fn=_setByRule&rule=togglemodal")
		private String cancel;
	}
}
