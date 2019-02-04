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
package com.atlas.client.extension.petclinic.view.demos;

import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Chart;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Chart.Type;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Initialize;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Paragraph;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.entity.aggregate.chart.DataGroup;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */
@Model
@Getter @Setter
public class VPChartDemo {
	
	private static final String request = 
			"url=/p/changelogdomain/_search?fn=query&where={\n" + 
			" aggregate: \"changelog\",\n" + 
			" pipeline: [\n" + 
			"	{\n" + 
			"	$match: {\n" + 
			" \"action\": {$in: [\"_new\",\"_search\"]}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$group: {\n" + 
			" _id: {\"action\": \"$action\", \"root\":\"$root\"},\n" + 
			"	count: {$sum: 1}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$group: {\n" + 
			"	_id: \"$_id.action\",\n" + 
			"	dataPoints: {$addToSet: {x: \"$_id.root\", y: \"$count\"}}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$project: {\n" + 
			"	_class: {$literal:\"com.antheminc.oss.nimbus.entity.aggregate.chart.DataGroup\"},\n" + 
			"	legend: {$concat:[\"Action \", \"$_id\"]},\n" + 
			"	dataPoints:1,\n" + 
			"	_id: 0\n" + 
			"	}\n" + 
			"	}\n" + 
			"\n" + 
			"	]\n" + 
			"}";
	
	
	private static final String request2 = 
			"url=/p/changelogdomain/_search?fn=query&where={\n" + 
			" aggregate: \"changelog\",\n" + 
			" pipeline: [\n" + 
			"	{\n" + 
			"	$match: {\n" + 
			" \"action\": {$in: [\"_new\"]}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$group: {\n" + 
			" _id: {\"action\": \"$action\", \"root\":\"$root\"},\n" + 
			"	count: {$sum: 1}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$group: {\n" + 
			"	_id: \"$_id.action\",\n" + 
			"	dataPoints: {$addToSet: {x: \"$_id.root\", y: \"$count\"}}\n" + 
			"	}\n" + 
			"	},\n" + 
			" {\n" + 
			"	$project: {\n" + 
			"	_class: {$literal:\"com.antheminc.oss.nimbus.entity.aggregate.chart.DataGroup\"},\n" + 
			"	legend: {$concat:[\"Action \", \"$_id\"]},\n" + 
			"	dataPoints:1,\n" + 
			"	_id: 0\n" + 
			"	}\n" + 
			"	}\n" + 
			"\n" + 
			"	]\n" + 
			"}";
	@Tile
	private VT vtMain;
	
	@Model
	@Getter @Setter
	public static class VT {
		
		@Label("This is Charts demo of Pet Clinic.")
    	@Paragraph(cssClass="font-weight-bold")
    	private String headerCallSection;
		
		@Section
		@Initialize
		@Config(url = "/vpChartDemo/vtMain/vsPetGeneralAssessment/barGraph/_process?fn=_set&"+request)
		@Config(url = "/vpChartDemo/vtMain/vsPetGeneralAssessment/lineGraph/_process?fn=_set&"+request)
		@Config(url = "/vpChartDemo/vtMain/vsPetGeneralAssessment/pieGraph/_process?fn=_set&"+request2)
		@Config(url = "/vpChartDemo/vtMain/vsPetGeneralAssessment/doughnutGraph/_process?fn=_set&"+request2)
		private VS vsPetGeneralAssessment;
	}
	
	@Model
	@Getter @Setter
	public static class VS {
		
		@Label("Bar Graph")
		@Chart(value=Type.BAR, xAxisLabel="Domain", yAxisLabel="Count")
		private List<DataGroup> barGraph;
		
		@Label("Line Graph")
		@Chart(value=Type.LINE, xAxisLabel="Domain", yAxisLabel="Count", stepSize="30")
		private List<DataGroup> lineGraph;
		
		@Label("Pie Graph")
		@Chart(value=Type.PIE)
		private List<DataGroup> pieGraph;
			
		@Label("Doughnut Chart")
		@Chart(value=Type.DOUGHNUT)
		private List<DataGroup> doughnutGraph;
	
	}
}
