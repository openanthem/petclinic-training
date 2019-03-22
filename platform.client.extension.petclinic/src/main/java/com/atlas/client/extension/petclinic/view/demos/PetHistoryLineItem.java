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

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TreeGridChild;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.demos.PetHistory;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Tony Lopez
 *
 */
@MapsTo.Type(PetHistory.class)
@Getter @Setter @ToString
public class PetHistoryLineItem {

	@Label("Name")
	@GridColumn
	@Path
	private String name;
	
	@Label("Date of Birth")
	@GridColumn(placeholder = "--")
	@Path
	private LocalDate dob;
	
	@Label("Sex")
	@GridColumn
	@Path
	private String sex;
	
	@TreeGridChild
	@Path
	private List<PetHistoryLineItem> children;
	
	@LinkMenu
	private VLM vlm;
	
	@Model
	@Getter @Setter
	public static class VLM {
		
		@Label("View Details")
		@Link
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/mode/_process?fn=_set&value=read")
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
		private String read;
		
		@Label("Add Pet")
		@Link
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_get?fn=param&expr=unassignMapsTo()")
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/mode/_process?fn=_set&value=create")
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
		private String create;
		
		@Label("Edit Pet")
		@Link
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/vf/_get?fn=param&expr=assignMapsTo('/.d/<!#this!>/../../.m')")
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/vs/mode/_process?fn=_set&value=update")
		@Config(url = "/vpTreegridDemo/vt/vmPetHistory/_process?fn=_setByRule&rule=togglemodal")
		private String update;
		
		@Label("Remove Pet")
		@Link
		@Config(url = "<!#this!>/../../_delete")
		private String delete;
	}
}
