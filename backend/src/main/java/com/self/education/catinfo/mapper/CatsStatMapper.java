package com.self.education.catinfo.mapper;

import org.mapstruct.Mapper;
import com.self.education.catinfo.api.CatsStatResponse;
import com.self.education.catinfo.domain.CatsStat;

@Mapper(componentModel = "spring")
public interface CatsStatMapper {

    CatsStatResponse transform(CatsStat catsStat);
}
