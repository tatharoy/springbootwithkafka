package com.davita.pie.controller;

import com.davita.pie.domain.Physician;
import com.davita.pie.service.PhysicianService;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by taroy on 4/27/16.
 */
@Controller
@RequestMapping(value = "/physicians")
public class PhysicianController {

    public static final String HEADER_STATUS_CODE = "status";

    public static final String BODY = "body";

    /** The logger. */
    private final Logger log = LoggerFactory.getLogger(PhysicianController.class);

    @Autowired
    private PhysicianService physicianService;

    @RequestMapping(method = RequestMethod.POST, value = "", produces = "application/json")
    public Map<String, Object> add(ModelMap requestModel, @RequestBody Physician resource) {
        Map<String, Object> output = new HashMap<String, Object>();
        log.info("physician being saved {}",resource);
        physicianService.add(resource);
        output.put(HEADER_STATUS_CODE,new Integer(201));
        return output;
    }

    /**
     * {@inheritDoc}
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/{id}")
    public Map<String, Object> update(ModelMap requestModel, @PathVariable String id,
                                       @RequestBody  Physician resource) {
        Map<String, Object> output = new HashMap<String, Object>();
        physicianService.update(id, resource);
        output.put(HEADER_STATUS_CODE,new Integer(201));
        return output;
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
    public Map<String, Object> remove( ModelMap requestModel, @PathVariable  String id) {
        Map<String, Object> output = new HashMap<String, Object>();
        physicianService.delete(id);
        output.put(HEADER_STATUS_CODE,new Integer(204));
        return output;
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public Map<String, Object> getById( ModelMap requestModel, @PathVariable  String id) {
        Map<String, Object> output = new HashMap<String, Object>();
        Physician physician = physicianService.get(id);
        if(physician !=null){
            output.put(HEADER_STATUS_CODE,new Integer(200));
            output.put(BODY,physician);
        }else{
            output.put(HEADER_STATUS_CODE,new Integer(404));
        }

        return output;
    }

   /* @RequestMapping(method = RequestMethod.GET, value = "")
    public Map<String, Object> search(@NonNull ModelMap requestModel,
                                      @ModelAttribute("params") Map<String, String> queryParameters) {

        final Map<String, Object> responseModel = ModelSupport.createModel();
        // TODO validate criteria, instanceId and applicationId or appName should be there
        List<SortField> sortableParams = new ArrayList<SortField>();
        if (sortCriteria != null) {
            sortableParams = sortCriteria.getSortFields();
        }
        final Instance instance = getResourceService().getById(id);
        if (instance == null) {
            throw new ResourceNotFoundException(id);
        }

        final Multimap<String, String> clonedQueryParameters = HashMultimap.create(queryParameters);
        // Need to remove first in a Multimap if you only want 1 object
        clonedQueryParameters.removeAll("realmId");
        clonedQueryParameters.put("realmId", instance.getRealmId());
        final List<Application> applications = getResourceService().searchApplications(id, clonedQueryParameters,
                sortableParams, page);
        ModelSupport.setBody(responseModel, applications);
        ModelSupport.setStatusCode(responseModel, HttpStatus.OK.value());

        return responseModel;
    }*/




}
