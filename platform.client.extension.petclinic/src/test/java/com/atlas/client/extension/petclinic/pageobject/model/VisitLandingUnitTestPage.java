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

import com.antheminc.oss.nimbus.context.BeanResolverStrategy;
import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.test.domain.support.pageobject.UnitTestPage;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.antheminc.oss.nimbus.test.domain.support.utils.ParamUtils;
import com.atlas.client.extension.petclinic.pageobject.model.shared.Header;
import com.atlas.client.extension.petclinic.view.owner.VisitLineItem;

import lombok.Getter;

/**
 * @author Tony Lopez
 *
 */
public class VisitLandingUnitTestPage extends UnitTestPage {

	@Getter
	private final Header header;
	
	public VisitLandingUnitTestPage(BeanResolverStrategy beanResolver, String clientId, String clientApp, Long refId) {
		super(beanResolver, clientId, clientApp, null, "visitlandingview", "vpVisits", refId);
		this.header = new Header(clientId, clientApp, beanResolver, this.controller);
		
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addAction(Action._new)
				.getMock();
		this.controller.handlePost(request, null);
	}

	public List<VisitLineItem> getVisits() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpVisits/vtMyVisits/vsMyVisits/myVisits")
				.addAction(Action._get)
				.getMock();
		
		Param<List<VisitLineItem>> response = ParamUtils.extractResponseByParamPath(this.controller.handlePost(request, null), "/myVisits");
		return response.getLeafState();
	}

	public PetsUnitTestPage clickGoToPets() {
		// client side redirect
		return new PetsUnitTestPage(this, this.getRefId());
	}
}
