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
import com.antheminc.oss.nimbus.domain.cmd.exec.CommandExecution.MultiOutput;
import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.support.Holder;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.antheminc.oss.nimbus.test.domain.support.utils.ParamUtils;
import com.atlas.client.extension.petclinic.view.pet.PetLineItem;

/**
 * @author Tony Lopez
 *
 */
public class PetsUnitTestPage extends ChildUnitTestPage<VisitLandingUnitTestPage> {
	
	public PetsUnitTestPage(VisitLandingUnitTestPage parent, Long refId) {
		super(parent, "pet", "petclinicdashboard", "vpAllPets", refId);
	}
	
	public VisitLandingUnitTestPage clickCancel() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/vbgAddPetButtonGrp/cancel")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return this.getParent();
	}

	@SuppressWarnings("unchecked")
	public AddEditPetUnitTestPage clickEditPet(int index) {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAllPets/vtMain/vsMain/pets/" + index + "/vlmCaseItemLinks/editPet")
				.addAction(Action._get)
				.getMock();
		
		Object response = this.controller.handlePost(request, null);
		Long refId = ((Holder<MultiOutput>) response).getState().getOutputs().get(0).getRootDomainId();
		return new AddEditPetUnitTestPage(this, refId, response);
	}
	
	public List<PetLineItem> getPets() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAllPets/vtMain/vsMain/pets")
				.addAction(Action._get)
				.getMock();
		
		Object response = this.controller.handlePost(request, null);
		Param<List<PetLineItem>> param = ParamUtils.extractResponseByParamPath(response, "/pets");
		return param.getLeafState();
	}
}
