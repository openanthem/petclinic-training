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
import com.atlas.client.extension.petclinic.view.owner.CallLineItem;
import com.atlas.client.extension.petclinic.view.owner.VPOwnerInfo.VSOwnerInfo.VCDOwnerInfo;
import com.atlas.client.extension.petclinic.view.pet.PetLineItem;

/**
 * @author Tony Lopez
 *
 */
public class OwnerInfoUnitTestPage extends UnitTestPage {

	/**
	 * @param beanResolver
	 * @param clientId
	 * @param clientApp
	 * @param refId
	 */
	public OwnerInfoUnitTestPage(BeanResolverStrategy beanResolver, String clientId, String clientApp,
			Long refId) {
		super(beanResolver, clientId, clientApp, "owner", "ownerview", "vpOwnerInfo", refId);
		
		// invoke on load calls
		this.getPets();
		this.getCalls();
	}
	
	public List<CallLineItem> getCalls() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/gridWrapper/calls")
				.addAction(Action._get)
				.getMock();
		
		Param<List<CallLineItem>> response = ParamUtils.extractResponseByParamPath(this.controller.handlePost(request, null), "/calls");
		return response.getLeafState();
	}
	
	public List<PetLineItem> getPets() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpOwnerInfo/vtOwnerInfo/vsPets/pets")
				.addAction(Action._get)
				.getMock();
		
		Param<List<PetLineItem>> response = ParamUtils.extractResponseByParamPath(this.controller.handlePost(request, null), "/pets");
		return response.getLeafState();
	}
	
	public Param<List<CallLineItem>> getParam_calls() {
		return this.getParam("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/gridWrapper/calls");
	}
	
	public Param<String> getParam_hideCallHistory() {
		return this.getParam("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/hideHistory");
	}
	
	public Param<String> getParam_showCallHistory() {
		return this.getParam("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/showHistory");
	}
	
	public OwnerInfoUnitTestPage clickHideCallHistory() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/hideHistory")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return this;
	}

	public OwnerInfoUnitTestPage clickShowCallHistory() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/callSection/showHistory")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, null);
		return this;
	}

	public Param<VCDOwnerInfo> getOwnerCardDetailParam() {
		return this.getParam("/vpOwnerInfo/vtOwnerInfo/vsOwnerInfo/vaBanner/vatBannerTab/vcdOwnerInfo");
	}
	
	

	public String getCallHistoryLabelText() {
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri(this.getViewRootDomainURI())
				.addRefId(this.getRefId())
				.addNested("/vpOwnerInfo/vtOwnerInfo/vsHistory/vfForm/headerCallSection")
				.addAction(Action._get)
				.getMock();
		
		Object response = this.controller.handlePost(request, null);
		Param<String> pCallHistoryLabelText = ParamUtils.extractResponseByParamPath(response, "/headerCallSection");
		return pCallHistoryLabelText.getDefaultLabel().getText();
	}
}
