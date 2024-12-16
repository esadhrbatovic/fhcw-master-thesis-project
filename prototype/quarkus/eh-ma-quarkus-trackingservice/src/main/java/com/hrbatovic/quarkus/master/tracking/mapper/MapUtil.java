package com.hrbatovic.quarkus.master.tracking.mapper;

import com.hrbatovic.master.quarkus.tracking.model.Event;
import com.hrbatovic.quarkus.master.tracking.db.entities.EventEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public abstract class MapUtil {

    public abstract List<Event> map(List<EventEntity> eventEntities);
}
