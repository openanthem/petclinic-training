package com.atlas.client.extension.petclinic.view.owner;


import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.Owner;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Domain(value = "ownerview", includeListeners = {ListenerType.websocket})
@MapsTo.Type(Owner.class)
@Repo(value = Repo.Database.rep_none, cache = Repo.Cache.rep_device)
@Getter @Setter @ToString(callSuper=true)
@ViewRoot(layout = "")

public class VROwner {
 
	@Label("Add Owner")
    @Page(defaultPage=true)
    private VPAddEditOwner vpAddEditOwner;
 
	@Label("Owner Info")
    @Page
    private VPOwnerInfo vpOwnerInfo;
 
}
