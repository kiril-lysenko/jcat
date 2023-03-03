package com.self.education.catinfo.service;

import com.self.education.catinfo.api.Attribute;
import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

public interface CatsService {

    Page<CatsResponse> findAllCats(Integer offset, Integer limit, Sort.Direction order, Attribute attribute);

    void createCat(CatRequest request);
}
