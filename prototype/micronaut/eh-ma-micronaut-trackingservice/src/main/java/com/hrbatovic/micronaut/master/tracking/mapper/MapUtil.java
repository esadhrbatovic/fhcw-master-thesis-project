package com.hrbatovic.micronaut.master.tracking.mapper;

import com.hrbatovic.micronaut.master.tracking.db.entities.EventEntity;
import com.hrbatovic.micronaut.master.tracking.model.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "jsr330")
public abstract class MapUtil {

    public static MapUtil INSTANCE = Mappers.getMapper(MapUtil.class);

    public abstract List<Event> map(List<EventEntity> eventEntities);

    @Mapping(target="eventId", source="id")
    public abstract Event map(EventEntity eventEntity);

}
