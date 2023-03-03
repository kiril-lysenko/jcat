package com.self.education.catinfo.mapper;

import com.self.education.catinfo.api.CatRequest;
import com.self.education.catinfo.api.CatsResponse;
import com.self.education.catinfo.domain.Cats;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatsMapper {

    CatsResponse transformEntityToResponse(Cats cats);

    Cats transformRequestToEntity(CatRequest request);
}
