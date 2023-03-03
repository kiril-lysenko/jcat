package com.self.education.catinfo.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import com.self.education.catinfo.domain.CatColorsInfo;
import com.self.education.catinfo.domain.Colors;

@Repository
public interface CatColorsInfoRepository extends JpaRepository<CatColorsInfo, Colors> {

    @Query(value = "SELECT c.color AS cat_color, COUNT(c.color) AS count FROM cats c GROUP BY c.color", nativeQuery = true)
    List<CatColorsInfo> countCatsByColor();

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cat_colors_info (cat_color, count) VALUES (CAST(:color AS colors), :count)", nativeQuery = true)
    void save(String color, int count);

    @Query(value = "SELECT * FROM cat_colors_info WHERE cat_color = CAST(:color AS colors)", nativeQuery = true)
    Optional<CatColorsInfo> findByColor(String color);

    @Modifying
    @Transactional
    @Query(value = "UPDATE cat_colors_info SET count = :count WHERE cat_color = CAST(:color AS colors)", nativeQuery = true)
    void update(String color, int count);
}
