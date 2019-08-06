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

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model @Getter @Setter
public class VPAllPets {

	@Tile
	private VTMain vtMain;
	
	@Model @Getter @Setter
	public static class VTMain {
		
		@Section
		private VSMain vsMain;
	}
	
	@Model @Getter @Setter
	public static class VSMain {
		
		@Label("All Pets")
        @Grid(onLoad=true, pageSize = "7")
		@Path(linked=false)
		@Config(url="/vpAllPets/vtMain/vsMain/pets.m/_process?fn=_set&url=/p/pet/_search?fn=example")
        private List<PetLineItem> pets;
	}
}
