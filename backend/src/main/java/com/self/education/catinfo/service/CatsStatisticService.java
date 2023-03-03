package com.self.education.catinfo.service;

import com.self.education.catinfo.api.CatsColorInfoResponse;
import com.self.education.catinfo.api.CatsStatResponse;

import java.util.List;

public interface CatsStatisticService {

    List<CatsColorInfoResponse> createCatColorsInfo();

    CatsStatResponse createOrUpdateCatsStat();
}
