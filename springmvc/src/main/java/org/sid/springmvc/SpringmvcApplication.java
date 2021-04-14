package org.sid.springmvc;

import java.util.Date;

import org.sid.springmvc.dao.PatientRepository;
import org.sid.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringmvcApplication implements CommandLineRunner{
	@Autowired
	private PatientRepository patientRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringmvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null, "Hassan", new Date(), false,5));		
		patientRepository.save(new Patient(null, "samira", new Date(), false,2));		
		patientRepository.save(new Patient(null, "mohamed", new Date(), true,6));		
		
		patientRepository.findAll().forEach(p->{System.out.println(p.getName());
		});
		}
	
	}
	


