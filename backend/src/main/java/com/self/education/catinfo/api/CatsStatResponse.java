package com.self.education.catinfo.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatsStatResponse {

    private Double tailLengthMean;
    private Double tailLengthMedian;
    private Integer[] tailLengthMode;
    private Double whiskersLengthMean;
    private Double whiskersLengthMedian;
    private Integer[] whiskersLengthMode;
}
