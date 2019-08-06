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
package com.atlas.client.extension.petclinic.scenariotests.pet;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import com.atlas.client.extension.petclinic.core.pet.MealInstruction;
import com.atlas.client.extension.petclinic.core.pet.Pet;
import com.atlas.client.extension.petclinic.pageobject.model.AddEditPetUnitTestPage;
import com.atlas.client.extension.petclinic.pageobject.model.PetsUnitTestPage;
import com.atlas.client.extension.petclinic.scenariotests.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.pet.MealInstructionLineItem;
import com.atlas.client.extension.petclinic.view.pet.PetLineItem;
import com.atlas.client.extension.petclinic.view.pet.VMMealInstruction.VF;

/**
 * @author Tony Lopez
 *
 */
public class AddEditPetPageTests extends AbstractPetclinicSpringTest {

	private PetsUnitTestPage petsPage;
	
	@Before
	public void init() {
		super.init();
		
		// Add pet data to db
		// Insert a test pet into the db for the owner.
		Pet expectedPet = new Pet();
		expectedPet.setId(1L);
		expectedPet.setName("Fido");
		expectedPet.setType("Dog - Chihuahua");
		expectedPet.setOwnerId(1L);
		expectedPet.setOwnerName("Oscar Grouch");
		expectedPet.setDob(LocalDate.of(2001, 01, 21));
		this.mongo.insert(expectedPet, CollectionNames.PET);
		
		this.petsPage = this.homepage.clickGoToPets();
	}
	
	private AddEditPetUnitTestPage clickEditFirstPet() {
		List<PetLineItem> response = this.petsPage.getPets();
		return this.petsPage.clickEditPet(0);
	}
	
	/**
	 * Add the meal instruction to the pet as an "existing" item
	 * @param existingMealInstruction the meal instruction to add
	 */
	private void addMealInstructionToPet(MealInstruction existingMealInstruction) {
		Pet pet = this.mongo.findById(1L, Pet.class, CollectionNames.PET);
		pet.setMealInstructions(new ArrayList<>());
		pet.getMealInstructions().add(existingMealInstruction);
		this.mongo.save(pet);
	}
	
	/**
	 * Performs assertions that the meal instruction form is matching the line item.
	 * @param expected The form object
	 * @param actual The line item object
	 */
	private void compareMealInstructionFormToLineItem(VF expected, MealInstructionLineItem actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getAmount(), actual.getAmount());
		Assert.assertEquals(expected.getTimeOfDay(), actual.getTimeOfDay());
		Assert.assertEquals(expected.getLengthOfTimeEaten(), actual.getRowBody().getVcd1().getVcdBody().getLengthOfTimeEaten());
	}
	
	/**
	 * Performs assertions that the meal instruction core object is matching the line item.
	 * @param expected The form object
	 * @param actual The line item object
	 */
	private void compareMealInstructionToLineItem(MealInstruction expected, MealInstructionLineItem actual) {
		Assert.assertEquals(expected.getName(), actual.getName());
		Assert.assertEquals(expected.getAmount(), actual.getAmount());
		Assert.assertEquals(expected.getTimeOfDay(), actual.getTimeOfDay());
		Assert.assertEquals(expected.getLengthOfTimeEaten(), actual.getRowBody().getVcd1().getVcdBody().getLengthOfTimeEaten());
	}
	
	@Test
	public void testClickEditPet() {
		AddEditPetUnitTestPage addEditPetPage = clickEditFirstPet();
		Assert.assertNotNull(addEditPetPage.getResponse());
		// TODO Better validations
	}
	
	@Test
	public void testAddMealInstruction() {
		// Create a meal instruction object to add
		VF expected = new VF();
		expected.setName("Kibble");
		expected.setAmount("10");
		expected.setTimeOfDay("Morning");
		expected.setLengthOfTimeEaten("36");
		
		// Add the meal instruction object
		AddEditPetUnitTestPage addEditPetPage = clickEditFirstPet();
		addEditPetPage
			.clickAddMealInstruction()
			.fillForm(expected)
			.clickAdd();
		
		// Validate the meal instructions are as expected in the Grid.
		List<MealInstructionLineItem> actual = addEditPetPage.getMealInstructions();
		Assert.assertEquals(1, actual.size());
		compareMealInstructionFormToLineItem(expected, actual.get(0));
	}
	
	@Test
	public void testExistingMealInstructionAddedToGrid() {
		// Create a meal instruction item and add it to the db.
		MealInstruction existingMealInstruction = new MealInstruction();
		existingMealInstruction.setName("Kibble");
		existingMealInstruction.setAmount("10");
		existingMealInstruction.setTimeOfDay("Morning");
		existingMealInstruction.setLengthOfTimeEaten("36");
		addMealInstructionToPet(existingMealInstruction);
		
		// Load the edit pet page
		AddEditPetUnitTestPage addEditPetPage = clickEditFirstPet();
		
		// Validate the meal instructions are as expected in the Grid.
		List<MealInstructionLineItem> actual = addEditPetPage.getMealInstructions();
		Assert.assertEquals(1, actual.size());
		this.compareMealInstructionToLineItem(existingMealInstruction, actual.get(0));
	}
	
	@Test
	public void testEditMealInstruction() {
		// Create a meal instruction item and add it to the db.
		MealInstruction existingMealInstruction = new MealInstruction();
		existingMealInstruction.setName("Kibble");
		existingMealInstruction.setAmount("10");
		existingMealInstruction.setTimeOfDay("Morning");
		existingMealInstruction.setLengthOfTimeEaten("36");
		addMealInstructionToPet(existingMealInstruction);
		
		// Create the edited copy of the form the post
		VF expected = new VF();
		BeanUtils.copyProperties(existingMealInstruction, expected);
		
		// Load the edit pet page
		AddEditPetUnitTestPage addEditPetPage = clickEditFirstPet();
		
		// Validate the meal instruction is present in the Grid.
		Assert.assertEquals(1, addEditPetPage.getMealInstructions().size());
				
		// Make the edit
		expected.setAmount("42");
		addEditPetPage
			.clickEditMealInstruction(0)
			.fillForm(expected)
			.clickEdit();
		
		// Validate the meal instructions are as expected in the Grid.
		List<MealInstructionLineItem> actual = addEditPetPage.getMealInstructions();
		Assert.assertEquals(1, actual.size());
		this.compareMealInstructionFormToLineItem(expected, actual.get(0));
	}
	
	@Test
	public void testRemoveMealInstruction() {
		// Create a meal instruction item and add it to the db.
		MealInstruction existingMealInstruction = new MealInstruction();
		existingMealInstruction.setName("Kibble");
		existingMealInstruction.setAmount("10");
		existingMealInstruction.setTimeOfDay("Morning");
		existingMealInstruction.setLengthOfTimeEaten("36");
		addMealInstructionToPet(existingMealInstruction);
		
		// Load the edit pet page
		AddEditPetUnitTestPage addEditPetPage = clickEditFirstPet();
		
		// Validate the meal instruction is present in the Grid.
		Assert.assertEquals(1, addEditPetPage.getMealInstructions().size());
		
		// Remove the meal instruction.
		addEditPetPage.clickRemoveMealInstruction(0);
		
		// Validate the meal instructions are as expected in the Grid.
		List<MealInstructionLineItem> actual = addEditPetPage.getMealInstructions();
		Assert.assertTrue(actual.isEmpty());
	}
}
