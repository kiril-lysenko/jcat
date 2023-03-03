package com.self.education.catinfo.mapper;

import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.domain.CatColorsInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CatColorInfoMapper {
    
    CatsColorInfoResponse transform(CatColorsInfo catColorInfo);
}
