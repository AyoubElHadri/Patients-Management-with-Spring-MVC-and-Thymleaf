package org.sid.springmvc.web;
import java.util.List;

import javax.validation.Valid;

import org.sid.springmvc.dao.PatientRepository;
import org.sid.springmvc.entities.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PatientController {
	@Autowired
	private PatientRepository patientRepository;
@GetMapping(path= "/index")
		public String index(){
			return "index";
		}
@GetMapping(path = "/patients")
public String list(Model model,
		@RequestParam(name="page" , defaultValue="0") int page , 
		@RequestParam(name="size" , defaultValue="5") int size,
		@RequestParam(name="keyword" , defaultValue="") String mc){
	Page<Patient> pagePatients=patientRepository.findByNameContains(mc, PageRequest.of(page, size));
	model.addAttribute("patients", pagePatients.getContent());
	model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
	model.addAttribute("currentPage", page);
	model.addAttribute("size", size);
	model.addAttribute("keyword", mc);
	
	return "patients";
}
@GetMapping(path= "/deletePatient")
public String delete(long id, String keyword, int page, int size){
	patientRepository.deleteById(id);
	return "redirect:/patients?page="+page+"&size="+size+"&keyword="+keyword;
}
@GetMapping(path= "/formPatient")
	public String formPatient(Model model) {
	model.addAttribute("patient", new Patient());
	model.addAttribute("mode" , "new");
		return "formPatient";
	}
@PostMapping(path= "/savePatient")
public String savePatient(Model model,@Valid Patient patient, BindingResult bindingResult) {
	if(bindingResult.hasErrors()) return "formPatient";
	patientRepository.save(patient);
	model.addAttribute("patient", patient);
	return "confirmation";
}
@GetMapping(path= "/editPatient")
	public String editPatient(Model model, Long id) {
	Patient p=patientRepository.findById(id).get();
	model.addAttribute("mode" , "edit");
	model.addAttribute("patient", p);
	return "formPatient";
}
}