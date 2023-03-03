package com.self.education.catinfo.repository;

import com.self.education.catinfo.domain.Cats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CatsRepository extends JpaRepository<Cats, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO cats (name, color, tail_length, whiskers_length) VALUES (:#{#cat.name}, cast(:#{#cat.color.name()} as colors), :#{#cat.tailLength}, :#{#cat.whiskersLength})", nativeQuery = true)
    void createNewCat(Cats cat);
}
