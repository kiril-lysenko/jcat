package com.self.education.catinfo.api;

import com.self.education.catinfo.domain.Colors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatRequest {

    @NotBlank(message = "Cat name can't blank")
    private String name;
    @NotNull(message = "Color can't be null")
    private Colors color;
    @Min(value = 0, message = "Tail length must be equal or greater than 0")
    @Max(value = 100, message = "Tail length must be equal or less than 100")
    private Integer tailLength;
    @Min(value = 0, message = "Whiskers length must be equal or greater than 0")
    @Max(value = 50, message = "Whiskers length must be equal or less than 50")
    private Integer whiskersLength;
}
