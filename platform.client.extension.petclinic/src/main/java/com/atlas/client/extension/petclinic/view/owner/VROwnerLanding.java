package com.atlas.client.extension.petclinic.view.owner;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Execution.Config;
import com.antheminc.oss.nimbus.domain.defn.MapsTo;
import com.antheminc.oss.nimbus.domain.defn.MapsTo.Path;
import com.antheminc.oss.nimbus.domain.defn.Model;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Autocomplete;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Button;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ButtonGroup;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Form;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Grid;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Page;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Section;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.TextBox;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Tile;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.ViewRoot;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.atlas.client.extension.petclinic.core.owner.Owner;

import lombok.Getter;
import lombok.Setter;
 
 
@Domain(value = "ownerlandingview", includeListeners = {ListenerType.websocket})
@Repo(value = Repo.Database.rep_none, cache=Repo.Cache.rep_device)
@Getter @Setter
@ViewRoot(layout = "")
public class VROwnerLanding {
 
	@Label("Owners")
	@Page(defaultPage=true)
    private VPOwners vpOwners;
 
    @Model @Getter @Setter
    public static class VPOwners  {
 
        @Tile(imgSrc = "resources/icons/task.svg#Layer_1")
        private VTOwners vtOwners;
    }
 
    @Model @Getter @Setter
    public static class VTOwners  {
 
        @Section
        private VSSearchOwnerCriteria vsSearchOwnerCriteria;
 
        @Section
        private VSOwners vsOwners;
    }
 
    @Model
    @Getter @Setter
    public static class VSSearchOwnerCriteria  {
 
        @Form(cssClass="twoColumn")
        @Path(linked=false)
        private VFSearchOwnerCriteria vfSearchOwnerCriteria;
    }
 
    @Model
    @MapsTo.Type(Owner.class)
    @Getter @Setter
    public static class VFSearchOwnerCriteria  {
 
    	@Label("First Name")
    	@NotNull
    	@Autocomplete(display="label")
    	@Config(url = "/p/owner/_search?fn=lookup&where=owner.firstName.containsIgnoreCase('<!autocompletesearchvalue!>')&projection.mapsTo=code:id,label:firstName")
        @Path
        private String firstName;
           
        @Label("Last Name")
        @TextBox
        @Path
        private String lastName;       
        
        @ButtonGroup
        private VBGSearchOwner vbgSearchOwner;
    }
 
    @Model @Getter @Setter
    public static class VBGSearchOwner {

    	@Label("Search")
        @Button(style = Button.Style.PRIMARY, type = Button.Type.submit)
        @Config(url = "/vpOwners/vtOwners/vsSearchOwnerCriteria/vfSearchOwnerCriteria/_update")
        @Config(url = "/vpOwners/vtOwners/vsOwners/owners.m/_process?fn=_set&url=/p/owner/_search?fn=example")
        private String search;
 
        @Label("Add Owner")
        @Button(style = Button.Style.SECONDARY)
        @Config(url = "/p/ownerview/_new")
        private String addOwner;
    }
 
    @Model @Getter @Setter
    public static class VSOwners  {
 
    	@Label("Owners")
        @Grid(onLoad = true, pageSize = "5",  rowSelection = false, expandableRows = true)
    	@Path(linked = false)
    	@Config(url="<!#this!>/.m/_process?fn=_set&url=/p/owner/_search?fn=example")
        private List<OwnerLineItem> owners;
    }
}