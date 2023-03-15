package com.project.mct.recipe.management.system.service;

import com.project.mct.recipe.management.system.dao.StatusRepository;
import com.project.mct.recipe.management.system.model.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusService {
        @Autowired
        StatusRepository statusRepository;


        public int saveStatus(Status status){
            Status statusObj=statusRepository.save(status);
            return statusObj.getStatusId();
        }

}
