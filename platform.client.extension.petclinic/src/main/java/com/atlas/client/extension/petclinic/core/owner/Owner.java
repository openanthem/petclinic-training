package com.atlas.client.extension.petclinic.core.owner;


 
import java.util.List;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Domain(value="owner", includeListeners={ListenerType.persistence, ListenerType.update})
@Repo(value=Repo.Database.rep_mongodb, cache=Repo.Cache.rep_device)
@Getter @Setter @ToString(callSuper=true)
public class Owner extends AbstractEntity.IdLong {
 
    private static final long serialVersionUID = 1L;
 
    private String firstName;
 
    private String lastName;
    
    private String status;
    
    private String notificationPreference;
    
    private String address;
    
    private String address2;
 
    private String city;
    
    private String state;
    
    private String zip;
 
    private String telephone;
    
    private String email;
    
    private boolean shouldUseNickname;
    
    private String nickname;
    
//    private String petName;
 
    List<OwnerCall> calls;
}
