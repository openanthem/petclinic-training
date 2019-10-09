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
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
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
import com.atlas.client.extension.petclinic.core.CodeValueTypes;
import com.atlas.client.extension.petclinic.core.demos.PetHistory;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Model
@Getter @Setter
public class VPGridInlineEdit {
	
	@Tile
	private VT vt;
	
	@Model
	@Getter @Setter
	public static class VT {
		
		@Label("Demo for grid inline edit")
    	@Paragraph(cssClass="font-weight-bold")
    	private String headerCallSection;

		@Section
		private VS vs;
	}
	
	@Model
	@Getter @Setter
	public static class VS {
		
		@Label("Inline Edit")
        @Grid(onLoad = true, pageSize = "2",  rowSelection = false, expandableRows = true, editRow = true, addRow = true)
		@Path(linked = false)
		@Config(url = "<!#this!>/.m/_process?fn=_set&url=/p/vetlocation/_search?fn=example")
		private List<VetLocationLineItem> inlinegrid;
		
		@Config(url = "/p/vetlocation/_new")
    	@Config(url="<!#this!>/../inlinegrid/.m/_process?fn=_set&url=/p/vetlocation/_search?fn=query")
    	private String _action_onAdd;
	}
}
