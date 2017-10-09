package com.nishant.spring.boot.ldap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	public Repo repo;
	
	
	/// methods by regular way
	@GetMapping("/")
	public List<String> getAll() {
		return repo.getAll();
	}
	@GetMapping("/persons")
	public List<Person> getAllPersons() {
		return repo.getAllPerson();
	}
	@GetMapping("/getdn/{dn}")
	public Person getDN(@PathVariable String dn) {
		return repo.getPersonByDN(dn);
	}
	@GetMapping("/getlastname/{lastname}")
	public List<String> getlastname(@PathVariable String lastname) {
		return repo.getPersonByLastName(lastname);
	}
	@PostMapping("/add")
	public String add(@RequestBody Person p) {
		repo.create(p);
		return "success";
	}
	@PutMapping("/update")
	public String update(@RequestBody Person p) {
		repo.update(p);
		return "success";
	}
	@PutMapping("/updatedesc")
	public String updateDescription(@RequestBody Person p) {
		repo.updateDescription(p);
		return "success";
	}
	@PostMapping("/del")
	public String delete(@RequestBody Person p) {
		repo.delete(p);
		return "success";
	}
}