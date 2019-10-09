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
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TreeGridChild;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.demos.PetHistory;
import com.atlas.client.extension.petclinic.core.demos.VetLocation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Sandeep Mantha
 *
 */
@MapsTo.Type(VetLocation.class)
@Getter @Setter @ToString
public class VetLocationLineItem {

	@Config(url = "/p/vetlocation:<!/.m/id!>/_update")
	@Config(url="<!#this!>/../../.m/_process?fn=_set&url=/p/vetlocation/_search?fn=query")
	private String _action_onEdit;
	
	@Label("Name")
	@TextBox
	@Path
	private String name;
	
	@Label("Address1")
	@TextBox
	@Path
	private String address1;
	
	@Label("City")
	@TextBox
	@Path
	private String city;
	
	@Label("State")
	@TextBox
	@Path
	private String state;
	
	@Label("Country")
	@TextBox
	@Path
	private String country;
	
	@LinkMenu
	private VLM vlm;
	
	@Model
	@Getter @Setter
	public static class VLM {
		
		@Label("View Details")
		@Link
		private String read;
		
		@Label("Edit Location")
		@Link
		private String update;
		
		@Label("Remove Location")
		@Link
		@Config(url = "<!#this!>/../../_delete")
		private String delete;
	}
}
