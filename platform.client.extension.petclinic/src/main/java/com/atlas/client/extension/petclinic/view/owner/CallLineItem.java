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
package com.atlas.client.extension.petclinic.view.owner;

import java.time.LocalDateTime;

import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn.FilterMode;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.OwnerCall;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Tony Lopez
 *
 */
@Model @Getter @Setter @ToString
@MapsTo.Type(OwnerCall.class)
public class CallLineItem {

	@GridColumn(sortable = false, filterMode = FilterMode.contains)
	@Label("Date of Call")
	@MapsTo.Path
	private LocalDateTime date;
	
	@GridColumn(sortable = false, filterMode = FilterMode.contains)
	@Label("Was call received?")
	@MapsTo.Path
	private boolean received;
	
	@GridColumn(sortable = false, filterMode = FilterMode.contains)
	@Label("Reason for Call")
	@MapsTo.Path
	private String reason;
}
