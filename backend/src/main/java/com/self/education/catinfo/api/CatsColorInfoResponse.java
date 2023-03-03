package com.self.education.catinfo.api;

import com.self.education.catinfo.domain.Colors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatsColorInfoResponse {

    private Colors catColor;
    private Integer count;
}
