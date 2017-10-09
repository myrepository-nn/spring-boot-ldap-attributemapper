package com.nishant.spring.boot.ldap;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.springframework.ldap.core.AttributesMapper;

public class PersonMapper implements AttributesMapper<Person> {
	
	@Override
	public Person mapFromAttributes(Attributes attributes) throws NamingException {
		Person p=new Person();
		p.setFullname(attributes.get("cn").get().toString());
		p.setLastname(attributes.get("sn").get().toString());
		p.setDescription(attributes.get("description").get().toString());
		p.setGivenname(attributes.get("givenname").get().toString());
		p.setMail(attributes.get("mail").get().toString());
		//p.setManager(attributes.get("manager").toString());
		p.setUid(attributes.get("uid").get().toString());
		//p.setUserpassword();
		return p;
	}

}
