package com.bkjeon.codebase.domain.mapper;

import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.CreateAdResponse;
import com.bkjeon.codebase.adapter.in.web.v1.dto.ad.response.ReadAdResponse;
import com.bkjeon.codebase.adapter.out.persistence.entity.AdEntity;
import com.bkjeon.codebase.application.command.CreateAdCommand;
import com.bkjeon.codebase.domain.model.Ad;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AdMapper {

    AdMapper INSTANCE = Mappers.getMapper(AdMapper.class);

    CreateAdResponse toAdResponse(Ad ad);
    AdEntity toAdEntity(Ad ad);
    Ad toAd(AdEntity adEntity);
    Ad toAd(CreateAdCommand createAdCommand);
    List<Ad> toAdList(List<AdEntity> adEntityList);
    List<ReadAdResponse> toReadAdResponseList(List<Ad> adList);

}