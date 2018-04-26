package com.jay.service.mapper;

import com.jay.domain.*;
import com.jay.service.dto.HouseHoldDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity HouseHold and its DTO HouseHoldDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface HouseHoldMapper extends EntityMapper <HouseHoldDTO, HouseHold> {
    
    @Mapping(target = "houseHolds", ignore = true)
    HouseHold toEntity(HouseHoldDTO houseHoldDTO); 
    default HouseHold fromId(Long id) {
        if (id == null) {
            return null;
        }
        HouseHold houseHold = new HouseHold();
        houseHold.setId(id);
        return houseHold;
    }
}
