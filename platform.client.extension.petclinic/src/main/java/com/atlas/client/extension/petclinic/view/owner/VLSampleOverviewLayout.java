package com.atlas.client.extension.petclinic.view.owner;


import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.Executions.Configs;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Initialize;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.MenuPanel;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section.Type;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Sandeep Mantha
 *
 */

@Domain(value = "sampleoverviewlayout", includeListeners = { ListenerType.websocket })
@Repo(value = Database.rep_none, cache = Cache.rep_device)
@Getter
@Setter
public class VLSampleOverviewLayout {

	@Model
	@Getter
	@Setter
	public static class PageSampleOverviewLayout {

		
		@Section(Type.HEADER)
		private VSsampleHeader vsSampleHeader;

	}

	@Page
	private PageSampleOverviewLayout pageCaseOverviewLayout;
}
