/**
 *  Copyright 2016-2019 the original author or authors.
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
package com.atlas.client.extension.petclinic.view.demos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Accordion;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.AccordionTab;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.GridColumn;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Paragraph;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Tony Lopez
 *
 */
@Model
@Getter
@Setter
public class VPSampleForms {

	@Tile
	private VT vt;

	@Model
	@Getter
	@Setter
	public static class VT {

		@Section
		private VS vs;
	}

	@Model
	@Getter
	@Setter
	public static class VS {

		@Label("Forms with Date Components")
		@Accordion
		private Accordion1 accordion1;
	}

	@Model
	@Getter
	@Setter
	public static class Accordion1 {

		@Label("@Calendar")
		@AccordionTab
		private Accordion1Tab1 tab1;

		@Model
		@Getter
		@Setter
		public static class Accordion1Tab1 {

			@Form
			private VF vf;

			@Label("For testing only - TODO REMOVE ME")
			@Grid
			@Path(linked = false)
			private List<DateCoreLineItem> dates;

			@Model
			@Getter
			@Setter
			public static class DateCoreLineItem {

				@Label("LocalDate")
				@GridColumn
				private LocalDate localDate;

				@Label("LocalDateTime")
				@GridColumn
				private LocalDateTime localDateTime;

				@Label("LocalDateTime (with Time)")
				@GridColumn
				private LocalDateTime localDateTimeWithTime;

				@Label("ZonedDateTime")
				@GridColumn
				private ZonedDateTime zonedDateTime;

				@Label("ZonedDateTime (with Time)")
				@GridColumn
				private ZonedDateTime zonedDateTimeWithTime;
			}

			@Model
			@Getter
			@Setter
			public static class VF {

				@Label("This form shows a sample of how Nimbus supports the Java objects of LocalDate, LocalDateTime, and ZonedDateTime on the UI as well as what it sends to the server. Try inspecting your browser's network tab to see the resulting date (and time, if applicable) that will be sent to the server.")
				@Paragraph
				private String description;

				@Label("LocalDate")
				@Calendar(postEventOnChange = true)
				private LocalDate localDate;

				@Label("LocalDateTime")
				@Calendar(postEventOnChange = true)
				private LocalDateTime localDateTime;

				@Label("LocalDateTime (with Time)")
				@Calendar(postEventOnChange = true, showTime = true)
				private LocalDateTime localDateTimeWithTime;

				@Label("ZonedDateTime")
				@Calendar(postEventOnChange = true)
				private ZonedDateTime zonedDateTime;

				@Label("ZonedDateTime (with Time)")
				@Calendar(postEventOnChange = true, showTime = true)
				private ZonedDateTime zonedDateTimeWithTime;

				@ButtonGroup
				private VBG vbg;

				@Model
				@Getter
				@Setter
				public static class VBG {

					@Label("Clear")
					@Button(type = Button.Type.reset, style = Button.Style.SECONDARY)
					private String clear;

					@Label("Submit")
					@Button(type = Button.Type.submit, style = Button.Style.PRIMARY)
					@Config(url = "<!#this!>/../../../dates/.m/_update")
					private String submit;
				}
			}
		}
	}
}
