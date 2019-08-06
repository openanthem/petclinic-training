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
package com.atlas.client.extension.petclinic.scenariotests.owner;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.atlas.client.extension.petclinic.core.owner.Owner;
import com.atlas.client.extension.petclinic.core.owner.OwnerCall;
import com.atlas.client.extension.petclinic.core.pet.Pet;
import com.atlas.client.extension.petclinic.pageobject.model.OwnerInfoUnitTestPage;
import com.atlas.client.extension.petclinic.scenariotests.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.owner.CallLineItem;
import com.atlas.client.extension.petclinic.view.owner.VPOwnerInfo.VSOwnerInfo.VCDBOwner;
import com.atlas.client.extension.petclinic.view.owner.VPOwnerInfo.VSOwnerInfo.VCDOwnerInfo;
import com.atlas.client.extension.petclinic.view.pet.PetLineItem;

/**
 * @author Tony Lopez
 *
 */
public class OwnerInfoPageTests extends AbstractPetclinicSpringTest {
	
	private OwnerInfoUnitTestPage ownerInfoPage;
	
	@Before
	public void init() {
		super.init();
		
		// Insert a test owner into the db.
		Owner owner1 = new Owner();
		owner1.setId(1L);
		owner1.setFirstName("Oscar");
		owner1.setLastName("Grouch");
		owner1.setAddress("123 Sesame Street");
		owner1.setCity("New York City");
		owner1.setState("NY");
		owner1.setZip("10128");
		owner1.setTelephone("1-234-567-8910");
		owner1.setEmail("thelonelytrashcan@sesamest.com");
		
		// Insert a test pet into the db for the owner.
		Pet expectedPet = new Pet();
		expectedPet.setId(1L);
		expectedPet.setName("Fido");
		expectedPet.setType("Dog - Chihuahua");
		expectedPet.setOwnerId(1L);
		expectedPet.setOwnerName("Oscar Grouch");
		expectedPet.setDob(LocalDate.of(2001, 01, 21));
		this.mongo.insert(expectedPet, CollectionNames.PET);
		
		// Insert test calls into the db for the owner.
		OwnerCall call1 = new OwnerCall();
		call1.setReceived(true);
		call1.setReason("Schedule pet haircut");
		OwnerCall call2 = new OwnerCall();
		call2.setReceived(false);
		call2.setReason("N/A");
		owner1.setCalls(Arrays.asList(call1, call2));
		
		this.mongo.insert(owner1, CollectionNames.OWNER);
		
		// Build the owner info page to work with in the test cases.
		this.ownerInfoPage = this.homepage.getHeader().clickOwners().clickOwnerInfo(0);
	}
	
	@Test
	public void testCallsGridDataValidation() {
		
		// Validate pets data
		List<PetLineItem> pets = this.ownerInfoPage.getPets();
		assertThat(pets).isNotNull();
		assertThat(pets.size()).isEqualTo(1);
		assertThat(pets.get(0).getName()).isEqualTo("Fido");
		assertThat(pets.get(0).getPetType()).isEqualTo("Dog - Chihuahua");
		assertThat(pets.get(0).getOwnerName()).isEqualTo("Oscar Grouch");
		assertThat(pets.get(0).getDob()).isEqualTo(LocalDate.of(2001, 01, 21));
		
		// Validate the call data
		List<CallLineItem> calls = this.ownerInfoPage.getCalls();
		assertThat(calls).isNotNull();
		assertThat(calls.size()).isEqualTo(2);
		assertThat(calls.get(0).isReceived()).isTrue();
		assertThat(calls.get(0).getReason()).isEqualTo("Schedule pet haircut");
		assertThat(calls.get(1).isReceived()).isFalse();
		assertThat(calls.get(1).getReason()).isEqualTo("N/A");
	}
	
	@Test
	public void testCallsGridToggleActive() {
		
		// invoke a get call on grid to initialize the data.
		this.ownerInfoPage.getCalls();
		
		// Validate calls grid and toggle buttons are appropriately active/inactive.
		assertThat(this.ownerInfoPage.getParam_calls().isActive()).isFalse();
		assertThat(this.ownerInfoPage.getParam_hideCallHistory().isActive()).isFalse();
		assertThat(this.ownerInfoPage.getParam_showCallHistory().isActive()).isTrue();
		
		// click show history. calls grid and hide button should be active. show button should be inactive.
		this.ownerInfoPage.clickShowCallHistory();
		assertThat(this.ownerInfoPage.getParam_calls().isActive()).isTrue();
		assertThat(this.ownerInfoPage.getParam_hideCallHistory().isActive()).isTrue();
		assertThat(this.ownerInfoPage.getParam_showCallHistory().isActive()).isFalse();
		
		// click hide history. show button should be active. calls grid and hide button should be inactive.
		this.ownerInfoPage.clickHideCallHistory();
		assertThat(this.ownerInfoPage.getParam_calls().isActive()).isFalse();
		assertThat(this.ownerInfoPage.getParam_hideCallHistory().isActive()).isFalse();
		assertThat(this.ownerInfoPage.getParam_showCallHistory().isActive()).isTrue();
	}
	
	@Test
	public void testOwnerInfoCardDisplay() {
		Param<VCDOwnerInfo> vcdParam = this.ownerInfoPage.getOwnerCardDetailParam();
		Param<VCDBOwner> vcdbParam = vcdParam.findParamByPath("/vcdbOwner");
		assertThat(vcdbParam.findParamByPath("/firstName").getLeafState()).isEqualTo("Oscar");
		assertThat(vcdbParam.findParamByPath("/lastName").getLeafState()).isEqualTo("Grouch");
//		assertThat(vcdbParam.findParamByPath("/addressGroup/address").getLeafState()).isEqualTo("123 Sesame Street");
//		assertThat(vcdbParam.findParamByPath("/addressGroup/city").getLeafState()).isEqualTo("New York City");
//		assertThat(vcdbParam.findParamByPath("/addressGroup/state").getLeafState()).isEqualTo("NY");
//		assertThat(vcdbParam.findParamByPath("/addressGroup/zip").getLeafState()).isEqualTo("10128");
		assertThat(vcdbParam.findParamByPath("/telephone").getLeafState()).isEqualTo("1-234-567-8910");
	}
	
	@Test
	public void testCallHistoryLabel() {
		assertThat(this.ownerInfoPage.getCallHistoryLabelText()).isEqualTo("Hello Oscar Grouch !! Welcome to PugsAndPaws. Below is your call history.");
	}
}