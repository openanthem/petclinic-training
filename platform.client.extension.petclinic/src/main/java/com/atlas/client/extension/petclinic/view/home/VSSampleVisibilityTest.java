package com.atlas.client.extension.petclinic.view.home;

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FormElementGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.extension.VisibleConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.YesTest;

import lombok.Getter;
import lombok.Setter;

@Model
@Getter
@Setter
public class VSSampleVisibilityTest {

	@Form
	private VFSampleVisibilityForm vfSampleVisibilityForm;

	@Model
	@Getter
	@Setter
	public static class VFSampleVisibilityForm {
		@TextBox
		@NotNull
		@Label("Text Box 1")
		private String textBox1;

		@ComboBox(postEventOnChange = true)
		@VisibleConditional(when = "state == 'Yes'", targetPath = { "/../vformGroupSample" })
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question with activate conditional (activates vformGroupSample)")
		private String question3;

		@FormElementGroup
		private VFormGroupSample vformGroupSample;

		@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
		@Label("Submit Form")
		private String submitSample;

	}

	@Model
	@Getter
	@Setter
	public static class VFormGroupSample {
		@TextBox
		@NotNull
		@Label("Text Box 2")
		private String textBox2;

		@TextBox
		@NotNull
		@Label("Text Box 3")
		private String textBox3;
	}
}
