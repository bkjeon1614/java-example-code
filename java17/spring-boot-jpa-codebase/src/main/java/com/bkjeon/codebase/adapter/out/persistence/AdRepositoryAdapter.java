package com.bkjeon.codebase.adapter.out.persistence;

import com.bkjeon.codebase.adapter.out.persistence.entity.AdEntity;
import com.bkjeon.codebase.adapter.out.persistence.repository.AdRepository;
import com.bkjeon.codebase.application.port.out.CreateAdPort;
import com.bkjeon.codebase.application.port.out.ExistAdPort;
import com.bkjeon.codebase.application.port.out.ReadAdPort;
import com.bkjeon.codebase.application.port.out.UpdateAdPort;
import com.bkjeon.codebase.domain.exception.AdEmptyException;
import com.bkjeon.codebase.domain.mapper.AdMapper;
import com.bkjeon.codebase.domain.model.Ad;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AdRepositoryAdapter implements CreateAdPort, ExistAdPort, ReadAdPort, UpdateAdPort {

    private final AdRepository adRepository;

    /**
     * 광고 생성
     * @param ad 생성할 광고 정보
     * @return 생성된 광고 정보
     */
    @Override
    public Ad createAd(Ad ad) {
        return AdMapper.INSTANCE.toAd(adRepository.save(AdMapper.INSTANCE.toAdEntity(ad)));
    }

    /**
     * 광고명으로 조회
     * @param name 광고명
     * @return boolean
     */
    @Override
    public boolean existsByName(String name) {
        return adRepository.existsByName(name);
    }

    /**
     * 광고 조회
     * @param size 총 조회할 개수
     * @return 광고 리스트
     */
    @Override
    public List<Ad> findTopAvailableAdsByLimit(int size) {
        return AdMapper.INSTANCE.toAdList(adRepository.findTopAvailableAdsByLimit(size));
    }

    /**
     * 광고 ID 로 광고정보 조회
     * @param id 광고 ID
     * @return 광고 정보
     */
    @Override
    public Ad findById(Long id) {
        AdEntity adEntity = adRepository.findById(id).orElseThrow(AdEmptyException::new);
        return AdMapper.INSTANCE.toAd(adEntity);
    }

    /**
     * 광고 데이터 업데이트
     * @param ad 업데이트할 광고 정보
     * @return 업데이트된 광고 정보
     */
    @Override
    public void updateAd(Ad ad) {
        adRepository.save(AdMapper.INSTANCE.toAdEntity(ad));
    }

}