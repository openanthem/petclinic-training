package com.atlas.client.extension.petquestionnaire.core;

import java.time.LocalDate;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Accordion;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.AccordionTab;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CheckBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.CheckBoxGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ComboBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.FormElementGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.InputSwitch;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Radio;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextArea;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.extension.MessageConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.GROUP_1;
import com.antheminc.oss.nimbus.domain.defn.extension.ValidateConditional.ValidationScope;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditionals;
import com.antheminc.oss.nimbus.domain.defn.extension.ValuesConditional.Condition;
import com.antheminc.oss.nimbus.entity.AbstractEntity.IdLong;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.PositiveSatisfactionType;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.SatisfactionType;
import com.atlas.client.extension.petquestionnaire.core.CodeValueTypes.YesTest;

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
	
	@Getter
	@Setter
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
		
		@TextArea
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
		@MessageConditional(when="state ==null", targetPath="/../p1", message="'Message is set for p1'")
		@Label(value = "Question 11")
		private String question11;

		@Label("Textbox 1")
		@Pattern(regexp = "^[2-9]\\d{2}-\\d{3}-\\d{4}$", message = "Please enter phone number in xxx-xxx-xxxx format")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p1;

		@Label("Textbox 2")
		@MessageConditional(when="state =='Yes'", targetPath="/../p3", message="'Message is set for p3'")
		@MessageConditional(when="state =='Yes'", targetPath="/../p4", message="'Message is set for p4'")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p2;

		@Label("Textbox 3")
		@Radio(postEventOnChange = true)
		@Model.Param.Values(value = YesTest.class)
		@NotNull
		private String p3;

		@Label("Textbox 4")
		@InputSwitch(postEventOnChange = true)
		@NotNull
		private String p4;

		@Label("Textbox 5")
		@CheckBox(postEventOnChange = true)
		@NotNull
		private Boolean p5;

		@Label("Textbox 6")
		@Max(value=10)
		@TextArea(postEventOnChange = true)
		@NotNull
		private String p6;

		@Label("Textbox 7")
		@Max(value=10)
		@TextArea(postEventOnChange = true)
		@NotNull
		private String p7;

		@Label("Textbox 8")
		@Max(value=10)
		@TextArea(postEventOnChange = true)
		@NotNull
		private String p8;

		@Label("Textbox 9")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p9;

		@Label("Textbox 10")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p10;

		@Label("Textbox 11")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p11;

		@Label("Textbox 12")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p12;

		@Label("Textbox 13")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p13;

		@Label("Textbox 14")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p14;

		@Label("Textbox 15")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p15;

		@Label("Textbox 16")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p16;

		@Label("Textbox 17")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p17;

		@Label("Textbox 18")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p18;

		@Label("Textbox 19")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p19;

		@Label("Textbox 20")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p20;

		@Label("Textbox 21")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p21;

		@Label("Textbox 22")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p22;

		@Label("Textbox 23")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p23;

		@Label("Textbox 24")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p24;

		@Label("Textbox 25")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p25;

		@Label("Textbox 26")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p26;

		@Label("Textbox 27")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p27;

		@Label("Textbox 28")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p28;

		@Label("Textbox 29")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p29;

		@Label("Textbox 30")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p30;

		@Label("Textbox 31")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p31;

		@Label("Textbox 32")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p32;

		@Label("Textbox 33")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p33;

		@Label("Textbox 34")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p34;

		@Label("Textbox 35")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p35;

		@Label("Textbox 36")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p36;

		@Label("Textbox 37")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p37;

		@Label("Textbox 38")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p38;

		@Label("Textbox 39")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p39;

		@Label("Textbox 40")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p40;

		@Label("Textbox 41")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p41;

		@Label("Textbox 42")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p42;

		@Label("Textbox 43")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p43;

		@Label("Textbox 44")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p44;

		@Label("Textbox 45")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p45;

		@Label("Textbox 46")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p46;

		@Label("Textbox 47")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p47;

		@Label("Textbox 48")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p48;

		@Label("Textbox 49")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p49;

		@Label("Textbox 50")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p50;

		@Label("Textbox 51")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p51;

		@Label("Textbox 52")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p52;

		@Label("Textbox 53")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p53;

		@Label("Textbox 54")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p54;

		@Label("Textbox 55")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p55;

		@Label("Textbox 56")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p56;

		@Label("Textbox 57")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p57;

		@Label("Textbox 58")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p58;

		@Label("Textbox 59")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p59;

		@Label("Textbox 60")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p60;

		@Label("Textbox 61")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p61;

		@Label("Textbox 62")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p62;

		@Label("Textbox 63")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p63;

		@Label("Textbox 64")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p64;

		@Label("Textbox 65")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p65;

		@Label("Textbox 66")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p66;

		@Label("Textbox 67")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p67;

		@Label("Textbox 68")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p68;

		@Label("Textbox 69")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p69;

		@Label("Textbox 70")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p70;

		@Label("Textbox 71")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p71;

		@Label("Textbox 72")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p72;

		@Label("Textbox 73")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p73;

		@Label("Textbox 74")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p74;

		@Label("Textbox 75")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p75;

		@Label("Textbox 76")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p76;

		@Label("Textbox 77")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p77;

		@Label("Textbox 78")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p78;

		@Label("Textbox 79")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p79;

		@Label("Textbox 80")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p80;

		@Label("Textbox 81")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p81;

		@Label("Textbox 82")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p82;

		@Label("Textbox 83")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p83;

		@Label("Textbox 84")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p84;

		@Label("Textbox 85")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p85;

		@Label("Textbox 86")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p86;

		@Label("Textbox 87")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p87;

		@Label("Textbox 88")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p88;

		@Label("Textbox 89")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p89;

		@Label("Textbox 90")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p90;

		@Label("Textbox 91")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p91;

		@Label("Textbox 92")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p92;

		@Label("Textbox 93")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p93;

		@Label("Textbox 94")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p94;

		@Label("Textbox 95")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p95;

		@Label("Textbox 96")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p96;

		@Label("Textbox 97")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p97;

		@Label("Textbox 98")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p98;

		@Label("Textbox 99")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p99;

		@Label("Textbox 100")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p100;

		@Label("Textbox 101")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p101;

		@Label("Textbox 102")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p102;

		@Label("Textbox 103")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p103;

		@Label("Textbox 104")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p104;

		@Label("Textbox 105")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p105;

		@Label("Textbox 106")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p106;

		@Label("Textbox 107")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p107;

		@Label("Textbox 108")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p108;

		@Label("Textbox 109")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p109;

		@Label("Textbox 110")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p110;

		@Label("Textbox 111")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p111;

		@Label("Textbox 112")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p112;

		@Label("Textbox 113")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p113;

		@Label("Textbox 114")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p114;

		@Label("Textbox 115")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p115;

		@Label("Textbox 116")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p116;

		@Label("Textbox 117")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p117;

		@Label("Textbox 118")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p118;

		@Label("Textbox 119")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p119;

		@Label("Textbox 120")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p120;

		@Label("Textbox 121")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p121;

		@Label("Textbox 122")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p122;

		@Label("Textbox 123")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p123;

		@Label("Textbox 124")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p124;

		@Label("Textbox 125")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p125;

		@Label("Textbox 126")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p126;

		@Label("Textbox 127")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p127;

		@Label("Textbox 128")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p128;

		@Label("Textbox 129")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p129;

		@Label("Textbox 130")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p130;

		@Label("Textbox 131")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p131;

		@Label("Textbox 132")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p132;

		@Label("Textbox 133")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p133;

		@Label("Textbox 134")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p134;

		@Label("Textbox 135")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p135;

		@Label("Textbox 136")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p136;

		@Label("Textbox 137")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p137;

		@Label("Textbox 138")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p138;

		@Label("Textbox 139")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p139;

		@Label("Textbox 140")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p140;

		@Label("Textbox 141")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p141;

		@Label("Textbox 142")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p142;

		@Label("Textbox 143")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p143;

		@Label("Textbox 144")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p144;

		@Label("Textbox 145")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p145;

		@Label("Textbox 146")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p146;

		@Label("Textbox 147")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p147;

		@Label("Textbox 148")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p148;

		@Label("Textbox 149")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p149;

		@Label("Textbox 150")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p150;

		@Label("Textbox 151")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p151;

		@Label("Textbox 152")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p152;

		@Label("Textbox 153")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p153;

		@Label("Textbox 154")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p154;

		@Label("Textbox 155")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p155;

		@Label("Textbox 156")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p156;

		@Label("Textbox 157")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p157;

		@Label("Textbox 158")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p158;

		@Label("Textbox 159")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p159;

		@Label("Textbox 160")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p160;

		@Label("Textbox 161")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p161;

		@Label("Textbox 162")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p162;

		@Label("Textbox 163")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p163;

		@Label("Textbox 164")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p164;

		@Label("Textbox 165")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p165;

		@Label("Textbox 166")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p166;

		@Label("Textbox 167")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p167;

		@Label("Textbox 168")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p168;

		@Label("Textbox 169")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p169;

		@Label("Textbox 170")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p170;

		@Label("Textbox 171")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p171;

		@Label("Textbox 172")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p172;

		@Label("Textbox 173")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p173;

		@Label("Textbox 174")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p174;

		@Label("Textbox 175")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p175;

		@Label("Textbox 176")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p176;

		@Label("Textbox 177")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p177;

		@Label("Textbox 178")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p178;

		@Label("Textbox 179")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p179;

		@Label("Textbox 180")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p180;

		@Label("Textbox 181")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p181;

		@Label("Textbox 182")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p182;

		@Label("Textbox 183")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p183;

		@Label("Textbox 184")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p184;

		@Label("Textbox 185")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p185;

		@Label("Textbox 186")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p186;

		@Label("Textbox 187")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p187;

		@Label("Textbox 188")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p188;

		@Label("Textbox 189")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p189;

		@Label("Textbox 190")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p190;

		@Label("Textbox 191")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p191;

		@Label("Textbox 192")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p192;

		@Label("Textbox 193")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p193;

		@Label("Textbox 194")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p194;

		@Label("Textbox 195")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p195;

		@Label("Textbox 196")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p196;

		@Label("Textbox 197")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p197;

		@Label("Textbox 198")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p198;

		@Label("Textbox 199")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p199;

		@Label("Textbox 200")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p200;

		@Label("Textbox 201")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p201;

		@Label("Textbox 202")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p202;

		@Label("Textbox 203")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p203;

		@Label("Textbox 204")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p204;

		@Label("Textbox 205")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p205;

		@Label("Textbox 206")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p206;

		@Label("Textbox 207")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p207;

		@Label("Textbox 208")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p208;

		@Label("Textbox 209")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p209;

		@Label("Textbox 210")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p210;

		@Label("Textbox 211")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p211;

		@Label("Textbox 212")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p212;

		@Label("Textbox 213")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p213;

		@Label("Textbox 214")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p214;

		@Label("Textbox 215")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p215;

		@Label("Textbox 216")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p216;

		@Label("Textbox 217")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p217;

		@Label("Textbox 218")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p218;

		@Label("Textbox 219")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p219;

		@Label("Textbox 220")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p220;

		@Label("Textbox 221")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p221;

		@Label("Textbox 222")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p222;

		@Label("Textbox 223")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p223;

		@Label("Textbox 224")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p224;

		@Label("Textbox 225")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p225;

		@Label("Textbox 226")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p226;

		@Label("Textbox 227")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p227;

		@Label("Textbox 228")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p228;

		@Label("Textbox 229")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p229;

		@Label("Textbox 230")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p230;

		@Label("Textbox 231")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p231;

		@Label("Textbox 232")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p232;

		@Label("Textbox 233")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p233;

		@Label("Textbox 234")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p234;

		@Label("Textbox 235")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p235;

		@Label("Textbox 236")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p236;

		@Label("Textbox 237")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p237;

		@Label("Textbox 238")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p238;

		@Label("Textbox 239")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p239;

		@Label("Textbox 240")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p240;

		@Label("Textbox 241")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p241;

		@Label("Textbox 242")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p242;

		@Label("Textbox 243")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p243;

		@Label("Textbox 244")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p244;

		@Label("Textbox 245")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p245;

		@Label("Textbox 246")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p246;

		@Label("Textbox 247")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p247;

		@Label("Textbox 248")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p248;

		@Label("Textbox 249")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p249;

		@Label("Textbox 250")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p250;

		@Label("Textbox 251")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p251;

		@Label("Textbox 252")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p252;

		@Label("Textbox 253")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p253;

		@Label("Textbox 254")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p254;

		@Label("Textbox 255")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p255;

		@Label("Textbox 256")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p256;

		@Label("Textbox 257")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p257;

		@Label("Textbox 258")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p258;

		@Label("Textbox 259")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p259;

		@Label("Textbox 260")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p260;

		@Label("Textbox 261")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p261;

		@Label("Textbox 262")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p262;

		@Label("Textbox 263")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p263;

		@Label("Textbox 264")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p264;

		@Label("Textbox 265")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p265;

		@Label("Textbox 266")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p266;

		@Label("Textbox 267")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p267;

		@Label("Textbox 268")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p268;

		@Label("Textbox 269")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p269;

		@Label("Textbox 270")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p270;

		@Label("Textbox 271")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p271;

		@Label("Textbox 272")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p272;

		@Label("Textbox 273")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p273;

		@Label("Textbox 274")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p274;

		@Label("Textbox 275")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p275;

		@Label("Textbox 276")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p276;

		@Label("Textbox 277")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p277;

		@Label("Textbox 278")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p278;

		@Label("Textbox 279")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p279;

		@Label("Textbox 280")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p280;

		@Label("Textbox 281")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p281;

		@Label("Textbox 282")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p282;

		@Label("Textbox 283")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p283;

		@Label("Textbox 284")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p284;

		@Label("Textbox 285")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p285;

		@Label("Textbox 286")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p286;

		@Label("Textbox 287")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p287;

		@Label("Textbox 288")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p288;

		@Label("Textbox 289")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p289;

		@Label("Textbox 290")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p290;

		@Label("Textbox 291")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p291;

		@Label("Textbox 292")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p292;

		@Label("Textbox 293")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p293;

		@Label("Textbox 294")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p294;

		@Label("Textbox 295")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p295;

		@Label("Textbox 296")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p296;

		@Label("Textbox 297")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p297;

		@Label("Textbox 298")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p298;

		@Label("Textbox 299")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p299;

		@Label("Textbox 300")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p300;

		@Label("Textbox 301")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p301;

		@Label("Textbox 302")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p302;

		@Label("Textbox 303")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p303;

		@Label("Textbox 304")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p304;

		@Label("Textbox 305")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p305;

		@Label("Textbox 306")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p306;

		@Label("Textbox 307")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p307;

		@Label("Textbox 308")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p308;

		@Label("Textbox 309")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p309;

		@Label("Textbox 310")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p310;

		@Label("Textbox 311")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p311;

		@Label("Textbox 312")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p312;

		@Label("Textbox 313")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p313;

		@Label("Textbox 314")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p314;

		@Label("Textbox 315")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p315;

		@Label("Textbox 316")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p316;

		@Label("Textbox 317")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p317;

		@Label("Textbox 318")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p318;

		@Label("Textbox 319")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p319;

		@Label("Textbox 320")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p320;

		@Label("Textbox 321")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p321;

		@Label("Textbox 322")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p322;

		@Label("Textbox 323")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p323;

		@Label("Textbox 324")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p324;

		@Label("Textbox 325")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p325;

		@Label("Textbox 326")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p326;

		@Label("Textbox 327")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p327;

		@Label("Textbox 328")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p328;

		@Label("Textbox 329")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p329;

		@Label("Textbox 330")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p330;

		@Label("Textbox 331")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p331;

		@Label("Textbox 332")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p332;

		@Label("Textbox 333")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p333;

		@Label("Textbox 334")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p334;

		@Label("Textbox 335")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p335;

		@Label("Textbox 336")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p336;

		@Label("Textbox 337")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p337;

		@Label("Textbox 338")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p338;

		@Label("Textbox 339")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p339;

		@Label("Textbox 340")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p340;

		@Label("Textbox 341")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p341;

		@Label("Textbox 342")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p342;

		@Label("Textbox 343")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p343;

		@Label("Textbox 344")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p344;

		@Label("Textbox 345")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p345;

		@Label("Textbox 346")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p346;

		@Label("Textbox 347")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p347;

		@Label("Textbox 348")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p348;

		@Label("Textbox 349")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p349;

		@Label("Textbox 350")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p350;

		@Label("Textbox 351")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p351;

		@Label("Textbox 352")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p352;

		@Label("Textbox 353")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p353;

		@Label("Textbox 354")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p354;

		@Label("Textbox 355")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p355;

		@Label("Textbox 356")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p356;

		@Label("Textbox 357")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p357;

		@Label("Textbox 358")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p358;

		@Label("Textbox 359")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p359;

		@Label("Textbox 360")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p360;

		@Label("Textbox 361")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p361;

		@Label("Textbox 362")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p362;

		@Label("Textbox 363")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p363;

		@Label("Textbox 364")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p364;

		@Label("Textbox 365")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p365;

		@Label("Textbox 366")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p366;

		@Label("Textbox 367")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p367;

		@Label("Textbox 368")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p368;

		@Label("Textbox 369")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p369;

		@Label("Textbox 370")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p370;

		@Label("Textbox 371")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p371;

		@Label("Textbox 372")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p372;

		@Label("Textbox 373")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p373;

		@Label("Textbox 374")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p374;

		@Label("Textbox 375")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p375;

		@Label("Textbox 376")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p376;

		@Label("Textbox 377")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p377;

		@Label("Textbox 378")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p378;

		@Label("Textbox 379")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p379;

		@Label("Textbox 380")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p380;

		@Label("Textbox 381")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p381;

		@Label("Textbox 382")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p382;

		@Label("Textbox 383")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p383;

		@Label("Textbox 384")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p384;

		@Label("Textbox 385")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p385;

		@Label("Textbox 386")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p386;

		@Label("Textbox 387")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p387;

		@Label("Textbox 388")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p388;

		@Label("Textbox 389")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p389;

		@Label("Textbox 390")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p390;

		@Label("Textbox 391")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p391;

		@Label("Textbox 392")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p392;

		@Label("Textbox 393")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p393;

		@Label("Textbox 394")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p394;

		@Label("Textbox 395")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p395;

		@Label("Textbox 396")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p396;

		@Label("Textbox 397")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p397;

		@Label("Textbox 398")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p398;

		@Label("Textbox 399")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p399;

		@Label("Textbox 400")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p400;

		@Label("Textbox 401")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p401;

		@Label("Textbox 402")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p402;

		@Label("Textbox 403")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p403;

		@Label("Textbox 404")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p404;

		@Label("Textbox 405")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p405;

		@Label("Textbox 406")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p406;

		@Label("Textbox 407")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p407;

		@Label("Textbox 408")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p408;

		@Label("Textbox 409")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p409;

		@Label("Textbox 410")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p410;

		@Label("Textbox 411")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p411;

		@Label("Textbox 412")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p412;

		@Label("Textbox 413")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p413;

		@Label("Textbox 414")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p414;

		@Label("Textbox 415")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p415;

		@Label("Textbox 416")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p416;

		@Label("Textbox 417")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p417;

		@Label("Textbox 418")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p418;

		@Label("Textbox 419")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p419;

		@Label("Textbox 420")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p420;

		@Label("Textbox 421")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p421;

		@Label("Textbox 422")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p422;

		@Label("Textbox 423")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p423;

		@Label("Textbox 424")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p424;

		@Label("Textbox 425")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p425;

		@Label("Textbox 426")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p426;

		@Label("Textbox 427")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p427;

		@Label("Textbox 428")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p428;

		@Label("Textbox 429")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p429;

		@Label("Textbox 430")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p430;

		@Label("Textbox 431")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p431;

		@Label("Textbox 432")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p432;

		@Label("Textbox 433")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p433;

		@Label("Textbox 434")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p434;

		@Label("Textbox 435")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p435;

		@Label("Textbox 436")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p436;

		@Label("Textbox 437")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p437;

		@Label("Textbox 438")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p438;

		@Label("Textbox 439")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p439;

		@Label("Textbox 440")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p440;

		@Label("Textbox 441")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p441;

		@Label("Textbox 442")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p442;

		@Label("Textbox 443")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p443;

		@Label("Textbox 444")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p444;

		@Label("Textbox 445")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p445;

		@Label("Textbox 446")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p446;

		@Label("Textbox 447")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p447;

		@Label("Textbox 448")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p448;

		@Label("Textbox 449")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p449;

		@Label("Textbox 450")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p450;

		@Label("Textbox 451")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p451;

		@Label("Textbox 452")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p452;

		@Label("Textbox 453")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p453;

		@Label("Textbox 454")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p454;

		@Label("Textbox 455")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p455;

		@Label("Textbox 456")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p456;

		@Label("Textbox 457")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p457;

		@Label("Textbox 458")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p458;

		@Label("Textbox 459")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p459;

		@Label("Textbox 460")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p460;

		@Label("Textbox 461")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p461;

		@Label("Textbox 462")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p462;

		@Label("Textbox 463")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p463;

		@Label("Textbox 464")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p464;

		@Label("Textbox 465")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p465;

		@Label("Textbox 466")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p466;

		@Label("Textbox 467")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p467;

		@Label("Textbox 468")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p468;

		@Label("Textbox 469")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p469;

		@Label("Textbox 470")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p470;

		@Label("Textbox 471")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p471;

		@Label("Textbox 472")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p472;

		@Label("Textbox 473")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p473;

		@Label("Textbox 474")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p474;

		@Label("Textbox 475")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p475;

		@Label("Textbox 476")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p476;

		@Label("Textbox 477")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p477;

		@Label("Textbox 478")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p478;

		@Label("Textbox 479")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p479;

		@Label("Textbox 480")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p480;

		@Label("Textbox 481")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p481;

		@Label("Textbox 482")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p482;

		@Label("Textbox 483")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p483;

		@Label("Textbox 484")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p484;

		@Label("Textbox 485")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p485;

		@Label("Textbox 486")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p486;

		@Label("Textbox 487")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p487;

		@Label("Textbox 488")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p488;

		@Label("Textbox 489")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p489;

		@Label("Textbox 490")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p490;

		@Label("Textbox 491")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p491;

		@Label("Textbox 492")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p492;

		@Label("Textbox 493")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p493;

		@Label("Textbox 494")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p494;

		@Label("Textbox 495")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p495;

		@Label("Textbox 496")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p496;

		@Label("Textbox 497")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p497;

		@Label("Textbox 498")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p498;

		@Label("Textbox 499")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p499;

		@Label("Textbox 500")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p500;

		@Label("Textbox 501")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p501;

		@Label("Textbox 502")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p502;

		@Label("Textbox 503")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p503;

		@Label("Textbox 504")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p504;

		@Label("Textbox 505")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p505;

		@Label("Textbox 506")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p506;

		@Label("Textbox 507")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p507;

		@Label("Textbox 508")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p508;

		@Label("Textbox 509")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p509;

		@Label("Textbox 510")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p510;

		@Label("Textbox 511")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p511;

		@Label("Textbox 512")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p512;

		@Label("Textbox 513")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p513;

		@Label("Textbox 514")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p514;

		@Label("Textbox 515")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p515;

		@Label("Textbox 516")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p516;

		@Label("Textbox 517")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p517;

		@Label("Textbox 518")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p518;

		@Label("Textbox 519")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p519;

		@Label("Textbox 520")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p520;

		@Label("Textbox 521")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p521;

		@Label("Textbox 522")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p522;

		@Label("Textbox 523")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p523;

		@Label("Textbox 524")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p524;

		@Label("Textbox 525")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p525;

		@Label("Textbox 526")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p526;

		@Label("Textbox 527")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p527;

		@Label("Textbox 528")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p528;

		@Label("Textbox 529")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p529;

		@Label("Textbox 530")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p530;

		@Label("Textbox 531")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p531;

		@Label("Textbox 532")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p532;

		@Label("Textbox 533")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p533;

		@Label("Textbox 534")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p534;

		@Label("Textbox 535")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p535;

		@Label("Textbox 536")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p536;

		@Label("Textbox 537")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p537;

		@Label("Textbox 538")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p538;

		@Label("Textbox 539")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p539;

		@Label("Textbox 540")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p540;

		@Label("Textbox 541")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p541;

		@Label("Textbox 542")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p542;

		@Label("Textbox 543")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p543;

		@Label("Textbox 544")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p544;

		@Label("Textbox 545")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p545;

		@Label("Textbox 546")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p546;

		@Label("Textbox 547")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p547;

		@Label("Textbox 548")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p548;

		@Label("Textbox 549")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p549;

		@Label("Textbox 550")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p550;

		@Label("Textbox 551")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p551;

		@Label("Textbox 552")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p552;

		@Label("Textbox 553")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p553;

		@Label("Textbox 554")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p554;

		@Label("Textbox 555")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p555;

		@Label("Textbox 556")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p556;

		@Label("Textbox 557")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p557;

		@Label("Textbox 558")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p558;

		@Label("Textbox 559")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p559;

		@Label("Textbox 560")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p560;

		@Label("Textbox 561")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p561;

		@Label("Textbox 562")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p562;

		@Label("Textbox 563")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p563;

		@Label("Textbox 564")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p564;

		@Label("Textbox 565")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p565;

		@Label("Textbox 566")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p566;

		@Label("Textbox 567")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p567;

		@Label("Textbox 568")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p568;

		@Label("Textbox 569")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p569;

		@Label("Textbox 570")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p570;

		@Label("Textbox 571")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p571;

		@Label("Textbox 572")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p572;

		@Label("Textbox 573")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p573;

		@Label("Textbox 574")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p574;

		@Label("Textbox 575")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p575;

		@Label("Textbox 576")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p576;

		@Label("Textbox 577")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p577;

		@Label("Textbox 578")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p578;

		@Label("Textbox 579")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p579;

		@Label("Textbox 580")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p580;

		@Label("Textbox 581")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p581;

		@Label("Textbox 582")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p582;

		@Label("Textbox 583")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p583;

		@Label("Textbox 584")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p584;

		@Label("Textbox 585")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p585;

		@Label("Textbox 586")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p586;

		@Label("Textbox 587")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p587;

		@Label("Textbox 588")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p588;

		@Label("Textbox 589")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p589;

		@Label("Textbox 590")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p590;

		@Label("Textbox 591")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p591;

		@Label("Textbox 592")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p592;

		@Label("Textbox 593")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p593;

		@Label("Textbox 594")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p594;

		@Label("Textbox 595")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p595;

		@Label("Textbox 596")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p596;

		@Label("Textbox 597")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p597;

		@Label("Textbox 598")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p598;

		@Label("Textbox 599")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p599;

		@Label("Textbox 600")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p600;

		@Label("Textbox 601")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p601;

		@Label("Textbox 602")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p602;

		@Label("Textbox 603")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p603;

		@Label("Textbox 604")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p604;

		@Label("Textbox 605")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p605;

		@Label("Textbox 606")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p606;

		@Label("Textbox 607")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p607;

		@Label("Textbox 608")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p608;

		@Label("Textbox 609")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p609;

		@Label("Textbox 610")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p610;

		@Label("Textbox 611")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p611;

		@Label("Textbox 612")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p612;

		@Label("Textbox 613")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p613;

		@Label("Textbox 614")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p614;

		@Label("Textbox 615")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p615;

		@Label("Textbox 616")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p616;

		@Label("Textbox 617")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p617;

		@Label("Textbox 618")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p618;

		@Label("Textbox 619")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p619;

		@Label("Textbox 620")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p620;

		@Label("Textbox 621")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p621;

		@Label("Textbox 622")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p622;

		@Label("Textbox 623")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p623;

		@Label("Textbox 624")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p624;

		@Label("Textbox 625")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p625;

		@Label("Textbox 626")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p626;

		@Label("Textbox 627")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p627;

		@Label("Textbox 628")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p628;

		@Label("Textbox 629")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p629;

		@Label("Textbox 630")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p630;

		@Label("Textbox 631")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p631;

		@Label("Textbox 632")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p632;

		@Label("Textbox 633")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p633;

		@Label("Textbox 634")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p634;

		@Label("Textbox 635")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p635;

		@Label("Textbox 636")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p636;

		@Label("Textbox 637")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p637;

		@Label("Textbox 638")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p638;

		@Label("Textbox 639")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p639;

		@Label("Textbox 640")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p640;

		@Label("Textbox 641")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p641;

		@Label("Textbox 642")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p642;

		@Label("Textbox 643")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p643;

		@Label("Textbox 644")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p644;

		@Label("Textbox 645")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p645;

		@Label("Textbox 646")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p646;

		@Label("Textbox 647")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p647;

		@Label("Textbox 648")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p648;

		@Label("Textbox 649")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p649;

		@Label("Textbox 650")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p650;

		@Label("Textbox 651")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p651;

		@Label("Textbox 652")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p652;

		@Label("Textbox 653")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p653;

		@Label("Textbox 654")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p654;

		@Label("Textbox 655")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p655;

		@Label("Textbox 656")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p656;

		@Label("Textbox 657")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p657;

		@Label("Textbox 658")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p658;

		@Label("Textbox 659")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p659;

		@Label("Textbox 660")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p660;

		@Label("Textbox 661")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p661;

		@Label("Textbox 662")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p662;

		@Label("Textbox 663")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p663;

		@Label("Textbox 664")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p664;

		@Label("Textbox 665")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p665;

		@Label("Textbox 666")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p666;

		@Label("Textbox 667")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p667;

		@Label("Textbox 668")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p668;

		@Label("Textbox 669")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p669;

		@Label("Textbox 670")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p670;

		@Label("Textbox 671")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p671;

		@Label("Textbox 672")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p672;

		@Label("Textbox 673")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p673;

		@Label("Textbox 674")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p674;

		@Label("Textbox 675")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p675;

		@Label("Textbox 676")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p676;

		@Label("Textbox 677")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p677;

		@Label("Textbox 678")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p678;

		@Label("Textbox 679")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p679;

		@Label("Textbox 680")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p680;

		@Label("Textbox 681")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p681;

		@Label("Textbox 682")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p682;

		@Label("Textbox 683")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p683;

		@Label("Textbox 684")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p684;

		@Label("Textbox 685")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p685;

		@Label("Textbox 686")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p686;

		@Label("Textbox 687")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p687;

		@Label("Textbox 688")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p688;

		@Label("Textbox 689")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p689;

		@Label("Textbox 690")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p690;

		@Label("Textbox 691")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p691;

		@Label("Textbox 692")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p692;

		@Label("Textbox 693")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p693;

		@Label("Textbox 694")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p694;

		@Label("Textbox 695")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p695;

		@Label("Textbox 696")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p696;

		@Label("Textbox 697")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p697;

		@Label("Textbox 698")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p698;

		@Label("Textbox 699")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p699;

		@Label("Textbox 700")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p700;

		@Label("Textbox 701")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p701;

		@Label("Textbox 702")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p702;

		@Label("Textbox 703")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p703;

		@Label("Textbox 704")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p704;

		@Label("Textbox 705")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p705;

		@Label("Textbox 706")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p706;

		@Label("Textbox 707")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p707;

		@Label("Textbox 708")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p708;

		@Label("Textbox 709")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p709;

		@Label("Textbox 710")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p710;

		@Label("Textbox 711")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p711;

		@Label("Textbox 712")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p712;

		@Label("Textbox 713")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p713;

		@Label("Textbox 714")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p714;

		@Label("Textbox 715")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p715;

		@Label("Textbox 716")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p716;

		@Label("Textbox 717")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p717;

		@Label("Textbox 718")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p718;

		@Label("Textbox 719")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p719;

		@Label("Textbox 720")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p720;

		@Label("Textbox 721")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p721;

		@Label("Textbox 722")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p722;

		@Label("Textbox 723")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p723;

		@Label("Textbox 724")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p724;

		@Label("Textbox 725")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p725;

		@Label("Textbox 726")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p726;

		@Label("Textbox 727")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p727;

		@Label("Textbox 728")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p728;

		@Label("Textbox 729")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p729;

		@Label("Textbox 730")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p730;

		@Label("Textbox 731")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p731;

		@Label("Textbox 732")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p732;

		@Label("Textbox 733")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p733;

		@Label("Textbox 734")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p734;

		@Label("Textbox 735")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p735;

		@Label("Textbox 736")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p736;

		@Label("Textbox 737")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p737;

		@Label("Textbox 738")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p738;

		@Label("Textbox 739")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p739;

		@Label("Textbox 740")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p740;

		@Label("Textbox 741")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p741;

		@Label("Textbox 742")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p742;

		@Label("Textbox 743")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p743;

		@Label("Textbox 744")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p744;

		@Label("Textbox 745")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p745;

		@Label("Textbox 746")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p746;

		@Label("Textbox 747")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p747;

		@Label("Textbox 748")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p748;

		@Label("Textbox 749")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p749;

		@Label("Textbox 750")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p750;

		@Label("Textbox 751")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p751;

		@Label("Textbox 752")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p752;

		@Label("Textbox 753")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p753;

		@Label("Textbox 754")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p754;

		@Label("Textbox 755")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p755;

		@Label("Textbox 756")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p756;

		@Label("Textbox 757")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p757;

		@Label("Textbox 758")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p758;

		@Label("Textbox 759")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p759;

		@Label("Textbox 760")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p760;

		@Label("Textbox 761")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p761;

		@Label("Textbox 762")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p762;

		@Label("Textbox 763")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p763;

		@Label("Textbox 764")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p764;

		@Label("Textbox 765")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p765;

		@Label("Textbox 766")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p766;

		@Label("Textbox 767")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p767;

		@Label("Textbox 768")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p768;

		@Label("Textbox 769")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p769;

		@Label("Textbox 770")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p770;

		@Label("Textbox 771")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p771;

		@Label("Textbox 772")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p772;

		@Label("Textbox 773")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p773;

		@Label("Textbox 774")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p774;

		@Label("Textbox 775")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p775;

		@Label("Textbox 776")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p776;

		@Label("Textbox 777")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p777;

		@Label("Textbox 778")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p778;

		@Label("Textbox 779")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p779;

		@Label("Textbox 780")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p780;

		@Label("Textbox 781")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p781;

		@Label("Textbox 782")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p782;

		@Label("Textbox 783")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p783;

		@Label("Textbox 784")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p784;

		@Label("Textbox 785")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p785;

		@Label("Textbox 786")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p786;

		@Label("Textbox 787")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p787;

		@Label("Textbox 788")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p788;

		@Label("Textbox 789")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p789;

		@Label("Textbox 790")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p790;

		@Label("Textbox 791")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p791;

		@Label("Textbox 792")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p792;

		@Label("Textbox 793")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p793;

		@Label("Textbox 794")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p794;

		@Label("Textbox 795")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p795;

		@Label("Textbox 796")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p796;

		@Label("Textbox 797")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p797;

		@Label("Textbox 798")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p798;

		@Label("Textbox 799")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p799;

		@Label("Textbox 800")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p800;

		@Label("Textbox 801")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p801;

		@Label("Textbox 802")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p802;

		@Label("Textbox 803")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p803;

		@Label("Textbox 804")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p804;

		@Label("Textbox 805")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p805;

		@Label("Textbox 806")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p806;

		@Label("Textbox 807")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p807;

		@Label("Textbox 808")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p808;

		@Label("Textbox 809")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p809;

		@Label("Textbox 810")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p810;

		@Label("Textbox 811")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p811;

		@Label("Textbox 812")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p812;

		@Label("Textbox 813")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p813;

		@Label("Textbox 814")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p814;

		@Label("Textbox 815")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p815;

		@Label("Textbox 816")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p816;

		@Label("Textbox 817")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p817;

		@Label("Textbox 818")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p818;

		@Label("Textbox 819")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p819;

		@Label("Textbox 820")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p820;

		@Label("Textbox 821")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p821;

		@Label("Textbox 822")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p822;

		@Label("Textbox 823")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p823;

		@Label("Textbox 824")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p824;

		@Label("Textbox 825")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p825;

		@Label("Textbox 826")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p826;

		@Label("Textbox 827")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p827;

		@Label("Textbox 828")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p828;

		@Label("Textbox 829")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p829;

		@Label("Textbox 830")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p830;

		@Label("Textbox 831")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p831;

		@Label("Textbox 832")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p832;

		@Label("Textbox 833")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p833;

		@Label("Textbox 834")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p834;

		@Label("Textbox 835")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p835;

		@Label("Textbox 836")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p836;

		@Label("Textbox 837")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p837;

		@Label("Textbox 838")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p838;

		@Label("Textbox 839")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p839;

		@Label("Textbox 840")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p840;

		@Label("Textbox 841")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p841;

		@Label("Textbox 842")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p842;

		@Label("Textbox 843")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p843;

		@Label("Textbox 844")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p844;

		@Label("Textbox 845")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p845;

		@Label("Textbox 846")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p846;

		@Label("Textbox 847")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p847;

		@Label("Textbox 848")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p848;

		@Label("Textbox 849")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p849;

		@Label("Textbox 850")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p850;

		@Label("Textbox 851")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p851;

		@Label("Textbox 852")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p852;

		@Label("Textbox 853")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p853;

		@Label("Textbox 854")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p854;

		@Label("Textbox 855")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p855;

		@Label("Textbox 856")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p856;

		@Label("Textbox 857")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p857;

		@Label("Textbox 858")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p858;

		@Label("Textbox 859")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p859;

		@Label("Textbox 860")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p860;

		@Label("Textbox 861")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p861;

		@Label("Textbox 862")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p862;

		@Label("Textbox 863")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p863;

		@Label("Textbox 864")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p864;

		@Label("Textbox 865")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p865;

		@Label("Textbox 866")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p866;

		@Label("Textbox 867")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p867;

		@Label("Textbox 868")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p868;

		@Label("Textbox 869")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p869;

		@Label("Textbox 870")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p870;

		@Label("Textbox 871")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p871;

		@Label("Textbox 872")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p872;

		@Label("Textbox 873")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p873;

		@Label("Textbox 874")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p874;

		@Label("Textbox 875")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p875;

		@Label("Textbox 876")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p876;

		@Label("Textbox 877")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p877;

		@Label("Textbox 878")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p878;

		@Label("Textbox 879")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p879;

		@Label("Textbox 880")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p880;

		@Label("Textbox 881")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p881;

		@Label("Textbox 882")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p882;

		@Label("Textbox 883")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p883;

		@Label("Textbox 884")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p884;

		@Label("Textbox 885")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p885;

		@Label("Textbox 886")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p886;

		@Label("Textbox 887")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p887;

		@Label("Textbox 888")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p888;

		@Label("Textbox 889")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p889;

		@Label("Textbox 890")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p890;

		@Label("Textbox 891")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p891;

		@Label("Textbox 892")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p892;

		@Label("Textbox 893")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p893;

		@Label("Textbox 894")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p894;

		@Label("Textbox 895")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p895;

		@Label("Textbox 896")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p896;

		@Label("Textbox 897")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p897;

		@Label("Textbox 898")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p898;

		@Label("Textbox 899")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p899;

		@Label("Textbox 900")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p900;

		@Label("Textbox 901")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p901;

		@Label("Textbox 902")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p902;

		@Label("Textbox 903")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p903;

		@Label("Textbox 904")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p904;

		@Label("Textbox 905")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p905;

		@Label("Textbox 906")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p906;

		@Label("Textbox 907")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p907;

		@Label("Textbox 908")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p908;

		@Label("Textbox 909")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p909;

		@Label("Textbox 910")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p910;

		@Label("Textbox 911")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p911;

		@Label("Textbox 912")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p912;

		@Label("Textbox 913")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p913;

		@Label("Textbox 914")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p914;

		@Label("Textbox 915")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p915;

		@Label("Textbox 916")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p916;

		@Label("Textbox 917")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p917;

		@Label("Textbox 918")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p918;

		@Label("Textbox 919")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p919;

		@Label("Textbox 920")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p920;

		@Label("Textbox 921")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p921;

		@Label("Textbox 922")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p922;

		@Label("Textbox 923")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p923;

		@Label("Textbox 924")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p924;

		@Label("Textbox 925")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p925;

		@Label("Textbox 926")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p926;

		@Label("Textbox 927")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p927;

		@Label("Textbox 928")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p928;

		@Label("Textbox 929")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p929;

		@Label("Textbox 930")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p930;

		@Label("Textbox 931")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p931;

		@Label("Textbox 932")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p932;

		@Label("Textbox 933")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p933;

		@Label("Textbox 934")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p934;

		@Label("Textbox 935")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p935;

		@Label("Textbox 936")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p936;

		@Label("Textbox 937")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p937;

		@Label("Textbox 938")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p938;

		@Label("Textbox 939")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p939;

		@Label("Textbox 940")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p940;

		@Label("Textbox 941")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p941;

		@Label("Textbox 942")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p942;

		@Label("Textbox 943")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p943;

		@Label("Textbox 944")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p944;

		@Label("Textbox 945")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p945;

		@Label("Textbox 946")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p946;

		@Label("Textbox 947")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p947;

		@Label("Textbox 948")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p948;

		@Label("Textbox 949")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p949;

		@Label("Textbox 950")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p950;

		@Label("Textbox 951")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p951;

		@Label("Textbox 952")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p952;

		@Label("Textbox 953")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p953;

		@Label("Textbox 954")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p954;

		@Label("Textbox 955")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p955;

		@Label("Textbox 956")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p956;

		@Label("Textbox 957")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p957;

		@Label("Textbox 958")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p958;

		@Label("Textbox 959")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p959;

		@Label("Textbox 960")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p960;

		@Label("Textbox 961")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p961;

		@Label("Textbox 962")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p962;

		@Label("Textbox 963")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p963;

		@Label("Textbox 964")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p964;

		@Label("Textbox 965")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p965;

		@Label("Textbox 966")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p966;

		@Label("Textbox 967")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p967;

		@Label("Textbox 968")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p968;

		@Label("Textbox 969")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p969;

		@Label("Textbox 970")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p970;

		@Label("Textbox 971")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p971;

		@Label("Textbox 972")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p972;

		@Label("Textbox 973")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p973;

		@Label("Textbox 974")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p974;

		@Label("Textbox 975")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p975;

		@Label("Textbox 976")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p976;

		@Label("Textbox 977")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p977;

		@Label("Textbox 978")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p978;

		@Label("Textbox 979")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p979;

		@Label("Textbox 980")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p980;

		@Label("Textbox 981")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p981;

		@Label("Textbox 982")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p982;

		@Label("Textbox 983")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p983;

		@Label("Textbox 984")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p984;

		@Label("Textbox 985")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p985;

		@Label("Textbox 986")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p986;

		@Label("Textbox 987")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p987;

		@Label("Textbox 988")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p988;

		@Label("Textbox 989")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p989;

		@Label("Textbox 990")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p990;

		@Label("Textbox 991")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p991;

		@Label("Textbox 992")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p992;

		@Label("Textbox 993")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p993;

		@Label("Textbox 994")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p994;

		@Label("Textbox 995")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p995;

		@Label("Textbox 996")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p996;

		@Label("Textbox 997")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p997;

		@Label("Textbox 998")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p998;

		@Label("Textbox 999")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p999;

		@Label("Textbox 1000")
		@TextBox(postEventOnChange = true)
		@NotNull
		private String p1000;

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
	
}