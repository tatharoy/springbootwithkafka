package com.davita.pie.service;

import com.davita.pie.domain.DataEvent;
import com.davita.pie.domain.DataEvent_New;
import com.davita.pie.domain.Physician;
import com.davita.pie.kafka.DataProducer;
import com.davita.pie.kafka.TaskType;
import com.davita.pie.repository.PhysicianRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.Map;

/**
 * Created by taroy on 4/27/16.
 */
@Service
public class PhysicianService {

    @Autowired
    private DataProducer dataProducer;

    private PhysicianRepository physicianRepository;

    @Autowired
    public void setPhysicianRepository(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    public void add(Physician resource) {
    	DataEvent addEvent =  new DataEvent();
    	addEvent.setPhysician(resource);
    	addEvent.setType(TaskType.CREATE);
        dataProducer.sendMessage(addEvent);
    }


    public void update(String id,Physician resource) {
    	DataEvent addEvent =  new DataEvent();
    	resource.setId(Long.parseLong(id));
    	addEvent.setPhysician(resource);
    	addEvent.setType(TaskType.UPDATE);
        dataProducer.sendMessage(addEvent);
    }


    public void delete(String id) {
    	DataEvent addEvent =  new DataEvent();
    	Physician phy = new Physician();
    	phy.setId(Long.parseLong(id));
    	addEvent.setPhysician(phy);
    	addEvent.setType(TaskType.DELETE);
        dataProducer.sendMessage(addEvent);
    }
    
    public void process(DataEvent event){
    	if(event.getType().equals(TaskType.CREATE)){
    		physicianRepository.save((Physician)event.getPhysician());
    	}else if(event.getType().equals(TaskType.UPDATE)){
    		physicianRepository.save((Physician)event.getPhysician());
    	}if(event.getType().equals(TaskType.DELETE)){
    		physicianRepository.delete(((Physician)event.getPhysician()).getId());
    	}
    }


    public Physician get(String id) {

        return physicianRepository.findOne(Long.parseLong(id));

    }


}
