/**
 * 
 */
package com.atlas.client.extension.petclinic.view.home;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Cache;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
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
@Domain(value="home", includeListeners={ListenerType.websocket}) 
@Repo(value = Database.rep_none, cache = Cache.rep_device)
@Getter @Setter
public class VLHome {

	@Page
	private VPHome vpHome;
	
	@Model @Getter @Setter
	public static class VPHome {
		
		@Section(Type.HEADER) 
		private VSHomeHeader vsHomeHeader;
		
		@MenuPanel
		private VSGlobalNav vsGlobalNav;
	}
}
