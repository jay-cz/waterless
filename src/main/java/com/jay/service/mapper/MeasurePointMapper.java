package com.jay.service.mapper;

import com.jay.domain.*;
import com.jay.service.dto.MeasurePointDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MeasurePoint and its DTO MeasurePointDTO.
 */
@Mapper(componentModel = "spring", uses = {HouseHoldMapper.class, })
public interface MeasurePointMapper extends EntityMapper <MeasurePointDTO, MeasurePoint> {

    @Mapping(source = "houseHold.id", target = "houseHoldId")
    MeasurePointDTO toDto(MeasurePoint measurePoint); 

    @Mapping(source = "houseHoldId", target = "houseHold")
    MeasurePoint toEntity(MeasurePointDTO measurePointDTO); 
    default MeasurePoint fromId(Long id) {
        if (id == null) {
            return null;
        }
        MeasurePoint measurePoint = new MeasurePoint();
        measurePoint.setId(id);
        return measurePoint;
    }
}
