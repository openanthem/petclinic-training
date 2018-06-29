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
package com.atlas.client.extension.petclinic.test;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;

import com.atlas.client.extension.petclinic.core.Owner;
import com.atlas.client.extension.petclinic.core.OwnerCall;

/**
 * @author Tony Lopez
 *
 */
public class AbstractPetclinicSpringTest extends BaseSpringTest {
	
	public final static String CLIENT_ID = "petclinic";
	public final static String CLIENT_ORG = "org";
	
	public static final class CollectionNames {
		public static final String OWNER = "owner";
	}
	
	@Before
	public void t0_init() {		
		Owner owner = new Owner();
		owner.setId(1L);
		owner.setFirstName("Jane");
		owner.setLastName("Doe");
		owner.setAddress("742 Evergreen Terrace");
		owner.setCity("Springfield");
		owner.setTelephone("867-5309");
		
		owner.setCalls(new ArrayList<>());
		owner.getCalls().add(new OwnerCall());
		owner.getCalls().add(new OwnerCall());
		owner.getCalls().get(0).setReceived(true);
		owner.getCalls().get(0).setReason("Schedule pet haircut");
		owner.getCalls().get(1).setReceived(false);
		owner.getCalls().get(1).setReason("N/A");
		
		this.mongo.insert(owner, CollectionNames.OWNER);
	}
	
	@After
	public void t0_teardown() {
		this.mongo.getDb().dropDatabase();
		this.sessionProvider.clear();
	}
}
