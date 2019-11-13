package com.atlas.client.extension.petclinic.core;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.domain.defn.Repo.Database;
import com.antheminc.oss.nimbus.entity.fileUpload.FileAttributes;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Domain(value="fileattachment", includeListeners={ListenerType.persistence})
@Repo(alias="fileattachment", value=Database.rep_mongodb)
@Getter @Setter @ToString
public class FileAttachment extends FileAttributes {

	
	
	private String comments;
	
	private String description;

	
	
}
