package com.nishant.spring.boot.ldap;

import java.util.List;

import javax.naming.Name;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.support.LdapNameBuilder;

public class Repo {

	@Autowired
	private LdapTemplate ldapTemplate;
	@Autowired
	private PersonMapper personMapper;

	public List<String> getAll(){
		LdapQuery lq=LdapQueryBuilder.query().where("objectclass").is("person"); 
		AttributesMapper<String> am=attributes -> attributes.get("cn").get().toString();
		return ldapTemplate.search(lq, am);
	}
	public List<Person> getAllPerson(){
		LdapQuery lq=LdapQueryBuilder.query().where("objectclass").is("person"); 
		return ldapTemplate.search(lq, personMapper);
	}
	public Person getPersonByDN(String dn){
		return ldapTemplate.lookup(dn, personMapper);
	}
	public List<String> getPersonByLastName(String lastname){
		LdapQuery lq=LdapQueryBuilder.query().base("ou=people").attributes("cn","sn").where("objectclass").is("person").and("sn").is(lastname); 
		AttributesMapper<String> am=attributes -> attributes.get("cn").get().toString();
		return ldapTemplate.search(lq, am);
	}


	protected Name buildDN(Person p) {
		return LdapNameBuilder.newInstance()
				.add("ou", "people")
				.add("cn", p.getFullname())
				.build();
	}

	public void create(Person p) {
		ldapTemplate.bind(buildDN(p), null, buildAttributes(p));
	}
	public void delete(Person p) {
		ldapTemplate.unbind(buildDN(p));
	}
	public void update(Person p) {
		ldapTemplate.rebind(buildDN(p), null, buildAttributes(p));
	}
	public void updateDescription(Person p) {
		Attribute attr = new BasicAttribute("description", p.getDescription());
		ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);
		ldapTemplate.rebind(buildDN(p), null, buildAttributes(p));
	}
	public Attributes buildAttributes(Person p) {
		Attributes attr=new BasicAttributes();
		BasicAttribute battr=new BasicAttribute("objectclass");
		battr.add("top");
		battr.add("person");
		battr.add("inetOrgPerson");
		battr.add("organizationalPerson");
		attr.put(battr);
		attr.put("cn", p.getFullname());
		attr.put("sn",p.getLastname());
		attr.put("description", p.getDescription());
		attr.put("givenname",p.getGivenname());
		attr.put("mail", p.getMail());
		//attr.put("manager",p.getLastname());
		attr.put("uid",p.getUid());
		return attr;
	}

}
