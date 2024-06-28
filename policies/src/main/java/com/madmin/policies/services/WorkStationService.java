package com.madmin.policies.services;

import com.madmin.policies.object.WorkStation;
import com.madmin.policies.repository.WorkStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkStationService {
    @Autowired
    private WorkStationRepository repository;

    public WorkStation saveWorkStation(WorkStation workStation) {
        return repository.save(workStation);
    }

    public List<WorkStation> getAllWorkStations() {
        return repository.findAll();
    }

    public void deleteWorkStation(Long id) {
        repository.deleteById(id);
    }

    public WorkStation getWorkStationById(Long id) {
        return repository.findById(id).orElse(null);
    }
}
