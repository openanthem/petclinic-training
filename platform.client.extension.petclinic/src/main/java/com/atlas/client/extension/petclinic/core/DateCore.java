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
package com.atlas.client.extension.petclinic.core;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

import com.antheminc.oss.nimbus.domain.defn.Domain;
import com.antheminc.oss.nimbus.domain.defn.Domain.ListenerType;
import com.antheminc.oss.nimbus.domain.defn.ViewConfig.Calendar;
import com.antheminc.oss.nimbus.domain.defn.extension.Content.Label;
import com.antheminc.oss.nimbus.domain.defn.Repo;
import com.antheminc.oss.nimbus.entity.AbstractEntity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author Tony Lopez
 *
 */
@Domain(value = "date_core", includeListeners = { ListenerType.persistence, ListenerType.update })
@Repo(value = Repo.Database.rep_mongodb, cache = Repo.Cache.rep_device)
@Getter
@Setter
@ToString(callSuper = true)
public class DateCore extends AbstractEntity.IdLong {

	private static final long serialVersionUID = 1L;

	private LocalDate localDate;
	private LocalDateTime localDateTime;
	private LocalDateTime localDateTimeWithTime;
	private ZonedDateTime zonedDateTime;
	private ZonedDateTime zonedDateTimeWithTime;
}
