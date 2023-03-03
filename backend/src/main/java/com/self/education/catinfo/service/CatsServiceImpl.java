package com.self.education.catinfo.service;

import com.self.education.catinfo.api.Attribute;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.api.OffsetBasedPageRequest;
import com.self.education.catinfo.mapper.CatsMapper;
import com.self.education.catinfo.repository.CatsRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CatsServiceImpl implements CatsService {

    private final CatsRepository catsRepository;
    private final CatsMapper catsMapper;

    @Override
    public Page<CatsResponse> findAllCats(Integer offset, Integer limit, Sort.Direction order, Attribute attribute) {
        final Pageable pageable = new OffsetBasedPageRequest(offset, limit, order, attribute);
        return catsRepository.findAll(pageable).map(catsMapper::transformEntityToResponse);
    }

    @Override
    public void createCat(CatRequest request) {
        catsRepository.createNewCat(catsMapper.transformRequestToEntity(request));
    }
}
