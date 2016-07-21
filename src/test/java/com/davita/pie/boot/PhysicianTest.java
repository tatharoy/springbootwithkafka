package com.davita.pie.boot;

import com.davita.pie.domain.Physician;
import com.davita.pie.repository.PhysicianRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by taroy on 4/25/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = {RepositoryConfiguration.class})
public class PhysicianTest {


    private PhysicianRepository physicianRepository;

    @Autowired
    public void setPhysicianRepository(PhysicianRepository physicianRepository) {
        this.physicianRepository = physicianRepository;
    }

    @Test
    public void testAddPhysician() throws Exception {

        Physician physician  =  new Physician();
        physician.setFirstName("John");
        physician.setLastName("Doe");
        Assert.assertNull(physician.getId());
        Physician savedPhysician = physicianRepository.save(physician);
        Assert.assertNotNull(savedPhysician.getId());
        savedPhysician = physicianRepository.findOne(new Long(1));
        Assert.assertNotNull(savedPhysician);

    }

    
}
