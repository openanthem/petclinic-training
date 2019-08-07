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
package com.atlas.client.extension.petclinic.scenariotests.home;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.test.domain.support.utils.ParamUtils;
import com.atlas.client.extension.petclinic.core.owner.Visit;
import com.atlas.client.extension.petclinic.pageobject.model.OwnerLandingUnitTestPage;
import com.atlas.client.extension.petclinic.scenariotests.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.owner.VROwnerLanding;
import com.atlas.client.extension.petclinic.view.owner.VisitLineItem;

/**
 * @author Tony Lopez
 *
 */
public class VisitLandingPageTest extends AbstractPetclinicSpringTest {

	@Test
	public void testMyVisitsGridDisplay() {
		// Create and add an owner to the db.
		Visit expected = new Visit();
		expected.setId(1L);
		expected.setAppointment(LocalDate.of(1988, 10, 13));
		expected.setOwnerName("Colby Jack");
		expected.setPetName("Fido");
		expected.setReasonForVisit("Illness");
		expected.setStatus("In Progress");
		this.mongo.insert(expected, CollectionNames.VISIT);
		
		// Validate visits are being displayed in the grid.
		List<VisitLineItem> actual = this.homepage.getVisits();
		assertThat(actual).isNotNull();
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual.get(0).getId()).isEqualTo(expected.getId());
		assertThat(actual.get(0).getAppointment()).isEqualTo(expected.getAppointment());
		assertThat(actual.get(0).getOwnerName()).isEqualTo(expected.getOwnerName());
		assertThat(actual.get(0).getPetName()).isEqualTo(expected.getPetName());
		assertThat(actual.get(0).getReasonForVisit()).isEqualTo(expected.getReasonForVisit());
		assertThat(actual.get(0).getStatus()).isEqualTo(expected.getStatus());
	}
}
