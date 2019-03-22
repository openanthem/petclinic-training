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
package com.atlas.client.extension.petclinic.pageobject.model;

import java.util.List;

import org.springframework.mock.web.MockHttpServletRequest;

import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.antheminc.oss.nimbus.test.domain.support.utils.ParamUtils;
import com.atlas.client.extension.petclinic.view.pet.MealInstructionLineItem;
import com.atlas.client.extension.petclinic.view.pet.PetLineItem;
import com.atlas.client.extension.petclinic.view.pet.VPAddEditPet.VFAddEditPet;

import lombok.Getter;

/**
 * @author Tony Lopez
 *
 */
@Getter
public class AddEditPetUnitTestPage extends ChildUnitTestPage<PetsUnitTestPage> {

	private final Object response;
	private VFAddEditPet form;
	
	public AddEditPetUnitTestPage(PetsUnitTestPage parent, Long refId) {
		this(parent, refId, null);
	}
	
	public AddEditPetUnitTestPage(PetsUnitTestPage parent, Long refId, Object response) {
		super(parent, "pet", "petview", "vpAddEditPet", refId);
		this.response = response;
	}
	
	public AddEditPetUnitTestPage fillForm(VFAddEditPet form) {
		this.form = form;
		return this;
	}

	public PetsUnitTestPage clickSubmit() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/vbgAddPetButtonGrp/submit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.convertToStringPayload(this.form));
		return this.getParent();
	}
	
	public PetsUnitTestPage clickCancel() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/vbgAddPetButtonGrp/cancel")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.convertToStringPayload(this.form));
		return this.getParent();
	}
	
	public MealInstructionUnitTestModal clickAddMealInstruction() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/addMealInstruction")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return new MealInstructionUnitTestModal(this);
	}
	
	public MealInstructionUnitTestModal clickEditMealInstruction(int index) {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/mealInstructions/" + index + "/vlm/edit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return new MealInstructionUnitTestModal(this);
	}
	
	public AddEditPetUnitTestPage clickRemoveMealInstruction(int index) {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/mealInstructions/" + index + "/vlm/remove")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return this;
	}

	public List<MealInstructionLineItem> getMealInstructions() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/mealInstructions")
				.addAction(Action._get)
				.getMock();
		
		Object response = this.controller.handlePost(request, null);
		Param<List<MealInstructionLineItem>> param = ParamUtils.extractResponseByParamPath(response, "/mealInstructions");
		return param.getLeafState();
	}
}
