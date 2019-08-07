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

import com.antheminc.oss.nimbus.context.BeanResolverStrategy;
import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.test.domain.support.pageobject.UnitTestPage;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.atlas.client.extension.petclinic.view.owner.VPAddEditOwner.SectionName;
import com.atlas.client.extension.petclinic.view.owner.VPAddEditOwner.VFAddEditOwner;

/**
 * @author Tony Lopez
 *
 */
public class AddEditOwnerUnitTestPage extends UnitTestPage {

	private OwnerLandingUnitTestPage parent;
	private SectionName form;
	
	public AddEditOwnerUnitTestPage(BeanResolverStrategy beanResolver, String clientId, String clientApp, Long refId) {
		super(beanResolver, clientId, clientApp, "owner", "ownerview", "vpAddEditOwner", refId);
	}
	
	public AddEditOwnerUnitTestPage(OwnerLandingUnitTestPage parent, Long refId) {
		this(parent.getBeanResolver(), parent.getClientId(), parent.getClientApp(), refId);
		this.parent = parent;
	}

	public AddEditOwnerUnitTestPage fillForm(SectionName owner1) {
		this.form = owner1;
		return this;
	}
	
	public OwnerLandingUnitTestPage clickSubmit() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpAddEditOwner/vtAddEditOwner/vsAddEditOwner/vfAddEditOwner/vbgAddOwnerButtonGrp/submit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.convertToStringPayload(this.form));
		return this.parent;
	}
}
