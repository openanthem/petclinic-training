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
package com.atlas.client.extension.petclinic.scenariotests;

import org.junit.After;
import org.junit.Before;

import com.atlas.client.extension.petclinic.pageobject.model.VisitLandingUnitTestPage;

/**
 * @author Tony Lopez
 *
 */
public abstract class AbstractPetclinicSpringTest extends BaseSpringTest {
	
	public final static String CLIENT_ID = "petclinic";
	public final static String CLIENT_ORG = "org";
	
	public static final class CollectionNames {
		public static final String OWNER = "owner";
		public static final String PET = "pet";
		public static final String VET = "veterinarian";
		public static final String VISIT = "visit";
	}
	
	protected VisitLandingUnitTestPage homepage;
	
	@Before
	public void init() {
		Long refId = 1L; // TODO Invoke from f/w call
		this.homepage = new VisitLandingUnitTestPage(beanResolver, CLIENT_ID, CLIENT_ORG, refId);
	}
	
	@After
	public void teardown() {
		this.mongo.getDb().drop();
		this.sessionProvider.clear();
	}
}
