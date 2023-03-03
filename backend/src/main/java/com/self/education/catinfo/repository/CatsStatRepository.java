package com.self.education.catinfo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.self.education.catinfo.domain.CatsStat;

@Repository
public interface CatsStatRepository extends JpaRepository<CatsStat, Boolean> {
}
