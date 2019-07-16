/**
 *  Copyright 2016-2019 the original author or authors.
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
package com.atlas.petclinic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.atlas.client.extension.petclinic.core.MealInstruction;
import com.atlas.client.extension.petquestionnaire.core.NoteOwner;
import com.atlas.client.extension.petquestionnaire.core.QuestionnaireNote;

/**
 * @author Tony Lopez
 *
 */
@RestController
public class QuestionnaireNoteController {

	@PostMapping(value = "/**/questionnaire_note/_search")
	public List<QuestionnaireNote> getNotes(@RequestBody(required = false) MealInstruction payload) {

		// if (null == payload) {
		// return new ArrayList<>();
		// }

		return this.getMockNotes();
	}

	private List<QuestionnaireNote> getMockNotes() {
		List<QuestionnaireNote> response = new ArrayList<>();
		response.add(new QuestionnaireNote());
		response.get(0).setId(1L);
		response.get(0).setContent("Note 1 (2 owners)");
		response.get(0).setOwners(new ArrayList<>());
		response.get(0).getOwners().add(new NoteOwner());
		response.get(0).getOwners().get(0).setName("Owner 1");
		response.get(0).getOwners().add(new NoteOwner());
		response.get(0).getOwners().get(1).setName("Owner 2");
		response.add(new QuestionnaireNote());
		response.get(1).setId(2L);
		response.get(1).setContent("Note 2 (1 owner)");
		response.get(1).setOwners(new ArrayList<>());
		response.get(1).getOwners().add(new NoteOwner());
		response.get(1).getOwners().get(0).setName("Owner 1");
		response.add(new QuestionnaireNote());
		response.get(2).setId(3L);
		response.get(2).setContent("Note 3 (0 owners)");
		response.get(2).setOwners(new ArrayList<>());
		return response;
	}
}
