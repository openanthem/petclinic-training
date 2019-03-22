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

import com.antheminc.oss.nimbus.test.domain.support.pageobject.UnitTestPage;

import lombok.Getter;

/**
 * @author Tony Lopez
 *
 */
@Getter
public class ChildUnitTestPage<P extends UnitTestPage> extends UnitTestPage {

	private P parent;
	
	public ChildUnitTestPage(P parent, String coreDomainAlias, String viewDomainAlias, String pageId, Long refId) {
		super(parent.getBeanResolver(), parent.getClientId(), parent.getClientApp(), coreDomainAlias, viewDomainAlias, pageId, refId);
		this.parent = parent;
	}

}
