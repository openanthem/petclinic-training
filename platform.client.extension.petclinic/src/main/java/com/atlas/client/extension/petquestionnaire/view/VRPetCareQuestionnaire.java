package com.atlas.client.extension.petquestionnaire.view;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Nature;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Modal;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.antheminc.oss.nimbus.domain.defn.extension.ActivateConditional;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petquestionnaire.core.PetCareAssessment;
import com.atlas.client.extension.petquestionnaire.core.PetCareAssessment.PetCareForm;
import com.atlas.client.extension.petquestionnaire.core.PetCareAssessment.Questionnaire_Section4.QuestionnaireNoteLineItem;
import com.atlas.client.extension.petquestionnaire.core.QuestionnaireNote;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 
 * @author Tony Lopez
 *
 */
@Domain(value = "petcareassessmentview", includeListeners = { ListenerType.websocket })
@Repo(value = Database.rep_none, cache = Cache.rep_device)
@MapsTo.Type(PetCareAssessment.class)
@ViewRoot(layout = "home")
@Getter
@Setter
public class VRPetCareQuestionnaire {

	@Page(defaultPage = true)
	private VPMain vpMain;

	@Model
	@Getter
	@Setter
	public static class VPMain {

		@Tile(size = Tile.Size.Large)
		private VTMain vtMain;
	}

	@Model
	@Getter
	@Setter
	public static class VTMain {

		@Label("Add Custom Note")
		@Modal(closable = true)
		private VMCustomNote vmCustomNote;

		@Label("Add System Note")
		@Modal(closable = true)
		private VMSystemNote vmSystemNote;

		@Section
		private VSPetGeneralAssessment vsPetGeneralAssessment;

	}

	@MapsTo.Type(PetCareAssessment.class)
	@Getter
	@Setter
	public static class VSPetGeneralAssessment {

		@Form(cssClass = "questionGroup")
		@Path
		private PetCareForm petCareForm;

	}

	@Model
	@Getter
	@Setter
	public static class VMCustomNote {

		@Config(url = "<!#this!>/../_process?fn=_setByRule&rule=togglemodal")
		@Button(style = Button.Style.PLAIN, type = Button.Type.button)
		private String closeModal;

		@Section
		private VS section;

		@MapsTo.Type(PetCareAssessment.class)
		@Model
		@Getter
		@Setter
		public static class VS {

			@Form(cssClass = "questionGroup")
			@Path(value = "/petCareForm/petCareAssessmentQuestionnaire/questionnaire_Section4/questionnaireNotes", nature = Nature.TransientColElem)
			@ActivateConditional(when = "isAssigned()", targetPath = "/vbg/edit")
			@ActivateConditional(when = "!isAssigned()", targetPath = "/vbg/add")
			private VF form;
		}

		@MapsTo.Type(QuestionnaireNoteLineItem.class)
		@Model
		@Getter
		@Setter
		public static class VF {

			@Label("ID")
			@TextBox
			@Path
			private Long id;

			@Label("Content")
			@TextBox
			@Path
			private String content;

			@ButtonGroup(cssClass = "oneColumn")
			private VBG vbg;
		}

		@Model
		@Getter
		@Setter
		public static class VBG {

			@Label("Add")
			@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
			@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_update")
			@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_get?fn=param&expr=flush()")
			@Config(url = "/vpMain/vtMain/vmCustomNote/_process?fn=_setByRule&rule=togglemodal")
			private String add;

			@Label("Edit")
			@Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
			@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_update")
			@Config(url = "/vpMain/vtMain/vmCustomNote/section/form/_get?fn=param&expr=flush()")
			@Config(url = "/vpMain/vtMain/vmCustomNote/_process?fn=_setByRule&rule=togglemodal")
			private String edit;

			@Label("Cancel")
			@Button(style = Button.Style.SECONDARY, type = Button.Type.reset)
			@Config(url = "/vpMain/vtMain/vmCustomNote/_process?fn=_setByRule&rule=togglemodal")
			private String cancel;
		}
	}

	@Model
	@Getter
	@Setter
	public static class VMSystemNote {

		@Config(url = "<!#this!>/../_process?fn=_setByRule&rule=togglemodal")
		@Button(style = Button.Style.PLAIN, type = Button.Type.button)
		private String closeModal;

		@Section
		private VS vs;

		@Model
		@Getter
		@Setter
		public static class VS {

			private QuestionnaireNote systemNotesRequest;

			@Config(url = "/vpMain/vtMain/vmSystemNote/vs/notes/.m/_process?fn=_set&url=/p/questionnaire_note/_search?fn=example&rawPayload=<!json(/../systemNotesRequest)!>")
			@Grid(headerCheckboxToggleAllPages = true, pageSize = "7", onLoad = true, rowSelection = true, expandableRows = true, postButton = true, postButtonUri = "/petcareassessmentview/vpMain/vtMain/vmSystemNote/vs/add", postButtonTargetPath = "temp_ids", postButtonLabel = "Add Notes")
			@Label("Reserved System Notes")
			@MapsTo.Path(linked = false)
			private List<SystemNoteLineItem> notes;

			@Config(url = "/vpMain/vtMain/vmSystemNote/vs/tempIds/_replace")
			@Config(url = "/vpMain/vtMain/vsPetGeneralAssessment/petCareForm/petCareAssessmentQuestionnaire/questionnaire_Section4/questionnaireNotes/_process?fn=_systemNotesConversion")
			@Config(url = "/vpMain/vtMain/vmSystemNote/_process?fn=_setByRule&rule=togglemodal")
			private String add;

			@ButtonGroup(cssClass = "oneColumn right")
			private CancelButtonGroup buttonGroup;

			@MapsTo.Path(linked = false)
			private TempIds tempIds;
		}

		@Model
		@Getter
		@Setter
		public static class CancelButtonGroup {

			@Button
			@Label("Cancel")
			@Config(url = "/vpMain/vtMain/vmSystemNote/_process?fn=_setByRule&rule=togglemodal")
			private String cancel;
		}

		@MapsTo.Type(QuestionnaireNote.class)
		@Getter
		@Setter
		public static class TempIds {

			private String[] temp_ids;
		}

		@MapsTo.Type(QuestionnaireNote.class)
		@Getter
		@Setter
		@ToString
		public static class SystemNoteLineItem {

			@Label("Content")
			@GridColumn(placeholder = "--")
			@Path
			private String content;
		}
	}

}