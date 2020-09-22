package com.gltknbtn.samplebootapp;

import com.gltknbtn.model.Person;
import com.gltknbtn.repository.PersonRepository;
import com.gltknbtn.service.GreetingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SampleBootApplicationTests {

	@SpyBean
	GreetingService greetingServiceSpy;

	@Autowired
	PersonRepository personRepository;

	@Test
	public void testSavePerson() {
		greetingServiceSpy.savePerson("Gültekin");
		Mockito.verify(greetingServiceSpy, times(1)).savePerson(isA(String.class));
		Person fetchPerson = personRepository.findByName("Gültekin");
		assert  fetchPerson != null;
	}

}

