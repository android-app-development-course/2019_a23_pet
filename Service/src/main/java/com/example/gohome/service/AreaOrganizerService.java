package com.example.gohome.service;

import com.example.gohome.entity.AreaOrganizer;
import org.springframework.stereotype.Service;

@Service
public interface AreaOrganizerService {

    AreaOrganizer areaOrganizerByAreaId(Integer areaId);

    AreaOrganizer areaOrganizerByUserId(Integer userId);
}
