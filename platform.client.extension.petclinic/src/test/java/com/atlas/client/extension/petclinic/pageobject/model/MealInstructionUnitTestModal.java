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

import org.springframework.mock.web.MockHttpServletRequest;

import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.test.domain.support.pageobject.UnitTestModal;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.atlas.client.extension.petclinic.view.pet.VMMealInstruction.VF;

/**
 * @author Tony Lopez
 *
 */
public class MealInstructionUnitTestModal extends UnitTestModal<AddEditPetUnitTestPage> {

	private VF form;
	
	public MealInstructionUnitTestModal(AddEditPetUnitTestPage parent) {
		super(parent, "/vpAddEditPet/vtAddEditPet/vmMealInstruction");
	}

	public MealInstructionUnitTestModal fillForm(VF form) {
		this.form = form;
		return this;
	}
	public AddEditPetUnitTestPage clickAdd() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/vbg/add")
				.addAction(Action._get)
				.getMock();
		
		Object response = this.controller.handlePost(request, this.convertToStringPayload(this.form));
		return this.getParent();
	}
	
	public AddEditPetUnitTestPage clickEdit() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/vbg/edit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.convertToStringPayload(this.form));
		return this.getParent();
	}
	
	@Override
	public AddEditPetUnitTestPage click_cancel() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vmMealInstruction/section/form/vbg/cancel")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return this.getParent();
	}

}
