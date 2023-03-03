package com.self.education.catinfo.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "cats_stat")
public class CatsStat implements Serializable {

    @Id
    @Column(name = "lock")
    private Boolean lock;

    @Column(name = "tail_length_mean")
    private Double tailLengthMean;

    @Column(name = "tail_length_median")
    private Double tailLengthMedian;

    @Type(type = "com.self.education.catinfo.converter.CustomIntegerArrayType")
    @Column(name = "tail_length_mode", columnDefinition = "int[]")
    private Integer[] tailLengthMode;

    @Column(name = "whiskers_length_mean")
    private Double whiskersLengthMean;

    @Column(name = "whiskers_length_median")
    private Double whiskersLengthMedian;

    @Type(type = "com.self.education.catinfo.converter.CustomIntegerArrayType")
    @Column(name = "whiskers_length_mode", columnDefinition = "int[]")
    private Integer[] whiskersLengthMode;
}
