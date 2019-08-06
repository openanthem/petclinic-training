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
package com.atlas.client.extension.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.atlas.client.extension.petclinic.core.owner.Owner;
import com.atlas.client.extension.petclinic.pageobject.model.OwnerLandingUnitTestPage;
import com.atlas.client.extension.petclinic.test.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.owner.OwnerLineItem;

/**
 * @author Tony Lopez
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class OwnerLandingPageTests extends AbstractPetclinicSpringTest {

	private OwnerLandingUnitTestPage ownerLandingPage;
	
	@Before
	public void t0_init() {
		super.t0_init();
		this.ownerLandingPage = new OwnerLandingUnitTestPage(this.beanResolver, CLIENT_ID, CLIENT_ORG, 1L);
	}
	
	@Test
	public void t01_ownersGrid_validateData() {
		
		Owner expected = this.mongo.findById(1L, Owner.class);
		
		// Validate owners
		List<OwnerLineItem> actual = this.ownerLandingPage.getOwners();
		assertThat(actual).isNotNull();
		assertThat(actual.size()).isEqualTo(1);
		assertThat(actual.get(0).getId()).isEqualTo(expected.getId());
		assertThat(actual.get(0).getFirstName()).isEqualTo(expected.getFirstName());
		assertThat(actual.get(0).getLastName()).isEqualTo(expected.getLastName());
		assertThat(actual.get(0).getStatus()).isEqualTo(expected.getStatus());
//		assertThat(actual.get(0).getShouldUseNickname()).isEqualTo(expected.getShouldUseNickname());
		assertThat(actual.get(0).getOwnerCity()).isEqualTo(expected.getCity());
		assertThat(actual.get(0).getTelephone()).isEqualTo(expected.getTelephone());

	}
}
