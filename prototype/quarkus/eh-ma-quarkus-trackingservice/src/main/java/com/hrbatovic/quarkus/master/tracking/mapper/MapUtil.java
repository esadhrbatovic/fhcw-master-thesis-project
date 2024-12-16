package com.hrbatovic.quarkus.master.tracking.mapper;

import com.hrbatovic.master.quarkus.tracking.model.Event;
import com.hrbatovic.quarkus.master.tracking.db.entities.EventEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract List<Event> map(List<EventEntity> eventEntities);

    @Mapping(target="eventId", source="id")
    public abstract Event map(EventEntity eventEntity);

}
