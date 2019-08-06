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

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Type;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.home.Note;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Type(Note.class)
@Model
@Getter @Setter
public class NoteLineItem {

	@Label("Note Type")
	@GridColumn
	@Path
	private String noteType;
	
	@Label("Note Description")
	@GridColumn
	@Path
	private String noteDescription;
	
	@LinkMenu
	private VLMDefault vlmDefault;
	
	@Model @Getter @Setter
	public static class VLMDefault {
		
		@Label("View Note")
		@Link
		@Config(url = "/vpNotes/vtNotes/vmNotes/vsMain/vfAddNote/checker/_process?fn=_set&value=HIDE")
		@Config(url = "/vpNotes/vtNotes/vmNotes/mode/_process?fn=_set&value=readonly")
		@Config(url = "/vpNotes/vtNotes/vmNotes/vsMain/vfViewNote/noteType/_process?fn=_set&value=general")
		@Config(url = "/vpNotes/vtNotes/vmNotes/vsMain/vfViewNote/_replace?rawPayload=<!json(/../../.m)!>")
		@Config(url = "/vpNotes/vtNotes/vmNotes/_process?fn=_setByRule&rule=togglemodal")
		private String view;
		
		@Label("Delete Note")
		@Link
		@Config(url = "/p/notes:<!/../../.m/id!>/_delete")
		@Config(url = "/vpNotes/vtNotes/vsNotes/notes/_get")
		private String delete;
	}
}
