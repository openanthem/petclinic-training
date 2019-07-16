package com.atlas.client.extension.petquestionnaire.core;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Accordion;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.AccordionTab;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CardDetail;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CheckBoxGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FieldValue;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FormElementGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridRowBody;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Link;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.LinkMenu;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Radio;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.MessageConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.GROUP_1;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.ValidationScope;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditional.Condition;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditionals;
import com.antheminc.oss.nimbus.entity.AbstractEntity.IdLong;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.PositiveSatisfactionType;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.SatisfactionType;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.YesTest;

import groovy.transform.ToString;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Domain(value = "petcareassessment", includeListeners = { ListenerType.persistence })
@Repo(alias = "petcareassessment", value = Database.rep_mongodb, cache = Cache.rep_device)
@Getter
@Setter
public class PetCareAssessment extends IdLong {

	private static final long serialVersionUID = 1L;
	
	private PetCareForm petCareForm;
	
	@Model
	@Getter @Setter
	public static class PetCareForm {
		
		@Accordion(showExpandAll=true, showMessages=true)
		private PetCareAssessmentQuestionnaire petCareAssessmentQuestionnaire;
				
		@ButtonGroup
		private  FormButtonGroup buttonGroup;
	}
	
	@Model
	@Getter @Setter
	public static class PetCareAssessmentQuestionnaire {
		
		@AccordionTab
		@Label("Section 1")
		private Quetionnaire_Section1 quetionnaire_Section1;
		
		@AccordionTab
		@Label("Section 2")
		private Quetionnaire_Section2 quetionnaire_Section2;
		
		@AccordionTab
		@Label("Section 3")
		private Quetionnaire_Section3 quetionnaire_Section3;

		@AccordionTab
		@Label("Section 4")
		private Questionnaire_Section4 questionnaire_Section4;
	}
	
	@Model
	@Getter @Setter
	public static class Quetionnaire_Section1 {
				
		@TextBox
		@NotNull
		@Label(value = "Question 1")
		private String question1;

		@Calendar(timeOnly=true)
		@NotNull
		@Label(value = "Calendar with time only")
		private LocalDate question2;

		@ComboBox(postEventOnChange=true)
		@ActivateConditional(when = "state == 'Yes'", targetPath = {
	    		"/../section12"
	    	})
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question with activate conditional (activates Section 12)")
		private String question3;
		
		@ComboBox(postEventOnChange=true, cssClass= "questionGroup form-inline")
		@NotNull
		@ValuesConditionals({
			@ValuesConditional(condition = { @Condition(when = "state=='Yes'", then = @Model.Param.Values(value = PositiveSatisfactionType.class)), }, targetPath = "/../question22"),
			@ValuesConditional(condition = { @Condition(when = "state=='No'", then = @Model.Param.Values(value = SatisfactionType.class)), }, targetPath = "/../question22")
		})
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question 19")
		private String question19;
		
		@ComboBox(postEventOnChange=true, cssClass= "questionGroup form-inline")
		@Model.Param.Values(value = YesTest.class)
		@MessageConditional(when="state =='Yes'", targetPath="/../question22", message="'Message is set'")
		@Label(value = "MessageConditional example - select Yes for message")
		private String question21;
		
		
		@CheckBoxGroup(cssClass= "questionGroup form-inline")
		@Size(min=2)
		@NotNull
		@Model.Param.Values(value = SatisfactionType.class)
		@Label(value = "Question 22")
		private String question22;
		
		@TextBox
		@Pattern(regexp = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", message = "Please enter phone number in xxx-xxx-xxxx format")
		@Label(value = "Question 23 with regex pattern")
		private String question23;
		
		@TextBox
		@NotNull
		@Size(min=3)
		@Pattern(regexp = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", message = "Please enter phone number in xxx-xxx-xxxx format")
		@Label(value = "Question 24 with regex pattern and mandatory")
		private String question24;
		
		@FormElementGroup
		@Label("Section 12")
		private Section12 section12;
	}
	
	@Model
	@Getter @Setter
	public static class Section12 {
		
		@TextBox
		@NotNull
		@Label(value = "Question 13")
		private String question13;

		@Calendar()
		@NotNull
		@Label(value = "Calendar with date only")
		private LocalDate question14;

		@Radio(cssClass="questionGroup form-inline")
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Radio with inline syles")
		private String question15;
		
		@FormElementGroup
		private Section16 section16;
	}
	
	@Model
	@Getter @Setter
	public static class Section16 {
		
		@TextBox
		@NotNull
		@Label(value = "Question 16")
		private String question16;

		@Calendar()
		@NotNull
		@Label(value = "Question 17")
		private LocalDate question17;

		@ComboBox()
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question 18")
		private String question18;
		
	}
	
	@Model
	@Getter @Setter
	public static class Quetionnaire_Section2 {
		
		@TextBox
		@NotNull
		@Label(value = "Question 4")
		private String question4;

		@Calendar(showTime=true)
		@NotNull
		@Label(value = "Calendar with Date and Time")
		private LocalDate question5;

		@ComboBox()
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question 6")
		private String question6;
		
		@TextBox
		@NotNull
		@Label(value = "Question 11")
		private String question11;
	}
	
	@Model
	@Getter @Setter
	public static class Quetionnaire_Section3 {

		@TextBox
		@NotNull
		@Label(value = "Question 7")
		private String question7;

		@Calendar()
		@NotNull
		@Label(value = "Question 8")
		private LocalDate question8;

		@ComboBox(postEventOnChange=true)
		@ValidateConditional(when = "state == 'Yes'", scope = ValidationScope.SIBLING, targetGroup = GROUP_1.class)
		@Model.Param.Values(value = YesTest.class)
		@Label(value = "Question with validate conditional")
		private String question9;
		
		@TextBox(postEventOnChange=true)
		@NotNull(groups = { GROUP_1.class })
		@Label(value = "Question with activate conditional (shows question 11 on section 2 when value = activate)")
		@ActivateConditional(when = "state == 'activate'", targetPath = {
	    		"/../../quetionnaire_Section2/question11"
	    	})
		private String question10;
	
		@Calendar()
		@NotNull
		@Label(value = "Question 8")
		private LocalDate question1;
	}
	
	@Model
	@Getter @Setter
	public static class FormButtonGroup {
		
		@Button(style = Button.Style.PRIMARY, cssClass = "btn btn-primary mb-1", type = Button.Type.submit, formReset = true)
		@Label(value = "Submit")
		private String submit;
	
		@Button(style = Button.Style.VALIDATION, cssClass = "btn btn-primary mb-1", type = Button.Type.button)
		@Label(value = "Validate Form")
		private String validate;
		
		@Button(style = Button.Style.SECONDARY, cssClass = "btn btn-primary mb-1", type = Button.Type.submit, formReset = true)
		@Label("Clear")
		private String Clear;
			
	
	}
	
	@Model
	@Getter @Setter
	public static class Questionnaire_Section4 {
		
		@ButtonGroup(cssClass = "text-sm-right")
		private VBG vbg;

		@Label("Notes")
		@Grid(expandableRows = true)
		private List<QuestionnaireNoteLineItem> questionnaireNotes;
		
		@Model
		@Getter
		@Setter
		public static class VBG {
			
			@Label("Add Custom")
			@Button
			@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_get?fn=param&expr=unassignMapsTo()")
			@Config(url = "/vpMain/vtMain/vmCustomNote/_process?fn=_setByRule&rule=togglemodal")
			private String addCustomNote;
			
			@Label("Add System Note")
			@Button
			@Config(url = "/vpMain/vtMain/vmSystemNote/_process?fn=_setByRule&rule=togglemodal")
			private String addSystemNote;
		}
		
		@Model
		@Getter @Setter @ToString
		public static class QuestionnaireNoteLineItem {
			
			@Label("ID")
			@GridColumn(placeholder = "--")
			private Long id;
			
			@Label("Content")
			@GridColumn(placeholder = "--")
			private String content;
			
			@LinkMenu
			private VLM vlm;
			
			@GridRowBody
			private RowBody rowBody;
			
			@GridColumn(hidden = true)
			private List<NoteOwnerLineItem> owners;
			
			@Model @Getter @Setter
			public static class VLM {

				@Label("Edit")
				@Link
				@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_get?fn=param&expr=assignMapsTo('/.d/<!#this!>/../../.m')")
				@Config(url = "/vpMain/vtMain/vmCustomNote/_process?fn=_setByRule&rule=togglemodal")
				private String edit;

				@Label("Remove")
				@Link
				@Config(url = "<!#this!>/../../.m/_delete")
				private String remove;
			}
			
			@Model @Getter @Setter
			public static class RowBody {
				
				@CardDetail
				private VS section;
				
				@Model @Getter @Setter
				public static class VS {
					
					@CardDetail.Body
					private CardBody cardBody;
					
					@Model @Getter @Setter
					public static class CardBody {
						
						@Link
						@Label("View Owners")
						@Config(url = "/vpMain/vtMain/viewNoteOwnersModal/vsBody/authNotes/_replace?rawPayload=<!json(/../../../../owners)!>")
						@Config(url = "/vpMain/vtMain/viewNoteOwnersModal/_process?fn=_setByRule&rule=togglemodal")
						private String viewNotes;
					}
				}
			}
		}
	}
	
	@Model
	@Getter
	@Setter
	@ToString
	public static class NoteOwnerLineItem {
		
		@CardDetail
		private Card card;
		
		@Model
		@Getter @Setter @ToString
		public static class Card {
			
			@CardDetail.Body
			private Body body;
		}
		
		@Model
		@Getter @Setter @ToString
		public static class Body {
			
			@FieldValue(placeholder = "--")
			@Label("Owner name")
			private String name;
		}
	}
}