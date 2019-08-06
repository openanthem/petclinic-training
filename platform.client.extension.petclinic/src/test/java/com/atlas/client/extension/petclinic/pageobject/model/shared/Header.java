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
package com.atlas.client.extension.petclinic.pageobject.model.shared;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import org.springframework.mock.web.MockHttpServletRequest;

import com.antheminc.oss.nimbus.channel.web.WebActionController;
import com.antheminc.oss.nimbus.context.BeanResolverStrategy;
import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.domain.cmd.Behavior;
import com.antheminc.oss.nimbus.test.domain.support.pageobject.UnitTestPage;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.atlas.client.extension.petclinic.pageobject.model.OwnerLandingUnitTestPage;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Tony Lopez
 *
 */
@RequiredArgsConstructor
@Getter
public class Header {

	private final String clientId;
	private final String clientApp;
	private final BeanResolverStrategy beanResolver;
	private final WebActionController controller;

	private Set<String> visitedPageCache = new HashSet<>();
	
	public UnitTestPage clickHome() {
		return null;
	}
	
	public UnitTestPage clickVisits() {
		return null;
	}
	
	public UnitTestPage clickVeterinarians() {
		return null;
	}
	
	public OwnerLandingUnitTestPage clickOwners() {
		return this.clickNavLink("ownerlandingview", "vpOwners", () -> {
			MockHttpServletRequest request = MockHttpRequestBuilder.withUri("/" + clientId + "/" + clientApp + "/p/ownerlandingview")
					.addNested("/vpOwners/vtOwners/vsOwners/owners")
					.addAction(Action._get)
					.addBehavior(Behavior.$execute)
					.addParam("pageSize", "5")
					.addParam("page", "0")
					.getMock();

			Object response = this.controller.handlePost(request, null);
			return new OwnerLandingUnitTestPage(this.beanResolver, getClientId(), getClientApp(), response);
		});
	}
	
	public UnitTestPage clickPets() {
		return null;
	}
	
	public UnitTestPage clickNotes() {
		return null;
	}
	
	public UnitTestPage clickTreegridDemo() {
		return null;
	}
	
	public UnitTestPage clickChartsDemo() {
		return null;
	}
	
	private <T extends UnitTestPage> T clickNavLink(String alias, String pageId, Supplier<T> onVisit) {
		// if the page has been previously visited, the UI shouldn't invoke a _new call. Just return the onVisit result.
		final String uniquePageId = alias + "/" + pageId;
		if (isPageVisited(uniquePageId)) {
			return onVisit.get();
		}
		
		// simulate first visit (_new)
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri("/" + clientId + "/" + clientApp + "/p/" + alias)
				.addAction(Action._new)
				.getMock();
		this.controller.handlePost(request, null);
		
		// handle any server-side calls initiated by the UI and store the page object in cache
		setPageVisited(uniquePageId);
		return onVisit.get();
	}
	
	private boolean isPageVisited(String pageId) {
		return this.visitedPageCache.contains(pageId);
	}
	
	private boolean setPageVisited(String pageId) {
		return this.visitedPageCache.add(pageId);
	}
}
