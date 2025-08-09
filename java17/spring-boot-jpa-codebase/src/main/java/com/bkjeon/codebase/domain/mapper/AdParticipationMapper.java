package com.bkjeon.codebase.domain.mapper;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.CreateAdParticipationResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.ReadAdParticipationResponse;
import com.bkjeon.codebase.adapter.out.persistence.entity.AdParticipationEntity;
import com.bkjeon.codebase.application.command.CreateAdParticipationCommand;
import com.bkjeon.codebase.domain.model.AdParticipation;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdParticipationMapper {

    AdParticipationMapper INSTANCE = Mappers.getMapper(AdParticipationMapper.class);
    
    CreateAdParticipationResponse toCreateAdParticipationResponse(AdParticipation adParticipation);
    AdParticipation toAdParticipation(CreateAdParticipationCommand createAdParticipationCommand);
    AdParticipation toAdParticipation(AdParticipationEntity adParticipationEntity);
    AdParticipationEntity toAdParticipationEntity(AdParticipation adParticipation);
    List<ReadAdParticipationResponse> toReadAdParticipationResponseList(List<AdParticipation> adParticipationList);

}