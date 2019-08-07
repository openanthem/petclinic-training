package com.atlas.client.extension.petclinic.scenariotests.pet;

import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import com.antheminc.oss.nimbus.domain.cmd.Action;
import com.antheminc.oss.nimbus.domain.cmd.exec.CommandExecution.MultiOutput;
import com.antheminc.oss.nimbus.domain.model.state.EntityState.Param;
import com.antheminc.oss.nimbus.support.Holder;
import com.antheminc.oss.nimbus.test.domain.support.utils.ExtractResponseOutputUtils;
import com.antheminc.oss.nimbus.test.domain.support.utils.MockHttpRequestBuilder;
import com.atlas.client.extension.petclinic.core.owner.Owner;
import com.atlas.client.extension.petclinic.core.pet.Pet;
import com.atlas.client.extension.petclinic.core.veterinarian.Veterinarian;
import com.atlas.client.extension.petclinic.scenariotests.AbstractPetclinicSpringTest;
import com.atlas.client.extension.petclinic.view.pet.VPAddEditPet.VFAddEditPet;
import com.atlas.client.extension.petclinic.view.pet.VRPet;
import com.fasterxml.jackson.core.JsonProcessingException;

public class AddEditPetUnitTest extends AbstractPetclinicSpringTest {
	
	public void vetSetup() {
		Veterinarian v1 = new Veterinarian();
		v1.setId(new Random().nextLong());
		v1.setFirstName("John");
		v1.setLastName("Doe");
		List<Long> assignedPets = new ArrayList<Long>();
		assignedPets.add(new Random().nextLong());
		assignedPets.add(new Random().nextLong());
		assignedPets.add(new Random().nextLong());
		v1.setSpeciality("Canine");
		
		Veterinarian v2 = new Veterinarian();
		v2.setId(new Random().nextLong());
		v2.setFirstName("William");
		v2.setLastName("Smith");
		v2.setSpeciality("Feline");
		
		mongo.insert(v1, CollectionNames.VET);
		mongo.insert(v2, CollectionNames.VET);		
	}
	
	private Owner ownerSetup() {
		Owner o1 = new Owner();
		o1.setFirstName("Martin");
		o1.setLastName("Byrde");
		o1.setId(new Random().nextLong());
		o1.setTelephone("2322322222");
		
		mongo.insert(o1, CollectionNames.OWNER);
		return o1;
	}
	
	public Pet petSetup(String type) {
		Pet p1 = new Pet();
		Owner o = ownerSetup();
		p1.setName("Mars");
		p1.setOwnerId(o.getId());
		p1.setOwnerName(o.getFirstName() + " " + o.getLastName());
		p1.setType(type);
		p1.setDob(LocalDate.now());
		String[] category = new String[]{"Hound"};
		p1.setCategory(category);
		p1.setId(new Random().nextLong());
		mongo.insert(p1, CollectionNames.PET);
		return p1;
	}
	
	@Before
	public void setup() {
		vetSetup();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void t0_petSubmit() throws JsonProcessingException {
		Pet p = petSetup("Cat");
		Long petId = p.getId();
		
		VFAddEditPet form = new VFAddEditPet();
		form.setName(p.getName());
		form.setDob(p.getDob());
		form.setType(p.getType());
		
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri("/hooli/box/p/")
				.addNested("petview")
				.addRefId(petId)
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/vbgAddPetButtonGrp/submit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.objectMapper.writeValueAsString(form));
		
		MockHttpServletRequest request2 = MockHttpRequestBuilder.withUri("/hooli/box/p/")
				.addNested("petview")
				.addRefId(petId)
				.addAction(Action._get)
				.getMock();
		Holder<MultiOutput> holder = (Holder<MultiOutput>)this.controller.handlePost(request2, null);
		Param<VRPet> petViewParam = ExtractResponseOutputUtils.extractOutput(holder);
		assertNotNull(petViewParam);
		assertNotNull(petViewParam.findParamByPath("/vpAddEditPet/vets"));
		assertNotNull(petViewParam.findParamByPath("/vpAddEditPet/vets").getLeafState());
		Pet newlyAddedPet = (Pet) petViewParam.findParamByPath("/.m").getState();
		assertNotNull(newlyAddedPet);
		
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void t1_assignPet_multipleVets_speciality() throws JsonProcessingException {
		
		Veterinarian v3 = new Veterinarian();
		v3.setId(new Random().nextLong());
		v3.setFirstName("Mike");
		v3.setLastName("Oscar");
		v3.setSpeciality("Canine");
		List<Long> assignedPets3 = new ArrayList<Long>();
		assignedPets3.add(new Random().nextLong());
		
		Veterinarian v4 = new Veterinarian();
		v4.setId(new Random().nextLong());
		v4.setFirstName("Michael");
		v4.setLastName("Scott");
		v4.setSpeciality("Canine");
		List<Long> assignedPets4 = new ArrayList<Long>();
		assignedPets4.add(new Random().nextLong());	
		assignedPets4.add(new Random().nextLong());	
		
		
		mongo.insert(v3, CollectionNames.VET);
		mongo.insert(v4, CollectionNames.VET);
		
		Pet p = petSetup("Dog");
		Long petId = p.getId();
		
		VFAddEditPet form = new VFAddEditPet();
		form.setName(p.getName());
		form.setDob(p.getDob());
		form.setType(p.getType());
		
		MockHttpServletRequest request = MockHttpRequestBuilder.withUri("/hooli/box/p/")
				.addNested("petview")
				.addRefId(petId)
				.addNested("/vpAddEditPet/vtAddEditPet/vsAddEditPet/vfAddEditPet/vbgAddPetButtonGrp/submit")
				.addAction(Action._get)
				.getMock();
		
		this.controller.handlePost(request, this.objectMapper.writeValueAsString(form));
		System.out.println("Submit complete");
		MockHttpServletRequest request2 = MockHttpRequestBuilder.withUri("/hooli/box/p/")
				.addNested("petview")
				.addRefId(petId)
				.addAction(Action._get)
				.getMock();
		Holder<MultiOutput> holder = (Holder<MultiOutput>)this.controller.handlePost(request2, null);
		Param<VRPet> petViewParam = ExtractResponseOutputUtils.extractOutput(holder);
		assertNotNull(petViewParam);
		assertNotNull(petViewParam.findParamByPath("/vpAddEditPet/vets"));
		assertNotNull(petViewParam.findParamByPath("/vpAddEditPet/vets").getLeafState());
		Pet p2 = (Pet) petViewParam.findParamByPath("/.m").getState();
		assertNotNull(p2);
	}
}
