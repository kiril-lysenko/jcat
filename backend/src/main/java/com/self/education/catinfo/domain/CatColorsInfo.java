package com.self.education.catinfo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static javax.persistence.EnumType.STRING;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cat_colors_info")
public class CatColorsInfo implements Serializable {

    @Id
    @Enumerated(value = STRING)
    @Column(name = "cat_color")
    private Colors catColor;

    @Column(name = "count")
    private Integer count;
}
