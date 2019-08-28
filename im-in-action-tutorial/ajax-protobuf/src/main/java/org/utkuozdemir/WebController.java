package org.utkuozdemir;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.utkuozdemir.sample1.protobuf.Address;
import org.utkuozdemir.sample1.protobuf.Gender;
import org.utkuozdemir.sample1.protobuf.Person;
import org.utkuozdemir.sample2.protobuf.Many;
import org.utkuozdemir.sample2.protobuf.One;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

@Controller
public class WebController {
	private final String protobufPerson;
	private final org.utkuozdemir.sample1.json.Person jsonPerson;
	private final String protobufOne;
	private final org.utkuozdemir.sample2.json.One jsonOne;

	public WebController() {
		byte[] protobufPersonData = Person.newBuilder()
				.setFirstName("Firstname")
				.setLastName("Lastname")
				.setAge(25)
				.setActive(true)
				.setGender(Gender.MALE)
				.addAddresses(Address.newBuilder()
						.setFirstLine("1st Address First Line")
						.setSecondLine("1st Adress Optional Second Line")
						.setPostalCode(12345))
				.addAddresses(Address.newBuilder()
								.setFirstLine("2nd Address First Line")
				).build().toByteArray();
		protobufPerson = new String(Base64.getEncoder().encode(protobufPersonData), Charset.forName("UTF-8"));
		jsonPerson = new org.utkuozdemir.sample1.json.Person("Firstname", "Lastname", 25, true,
				org.utkuozdemir.sample1.json.Gender.MALE,
				Arrays.asList(
						new org.utkuozdemir.sample1.json.Address("1st Address First Line",
								"1st Adress Optional Second Line", 12345),
						new org.utkuozdemir.sample1.json.Address("2nd Address First Line", null, null)
				));

		One.Builder oneBuilder = One.newBuilder().setName("one");
		for (int i = 1; i <= 1024; i++) {
			oneBuilder.addMany(Many.newBuilder()
					.setName("many-" + i).build());
		}
		protobufOne = new String(Base64.getEncoder()
				.encode(oneBuilder.build().toByteArray()), Charset.forName("UTF-8"));

		jsonOne = new org.utkuozdemir.sample2.json.One();
		jsonOne.setName("one");
		List<org.utkuozdemir.sample2.json.Many> manies = new ArrayList<>(1024);
		for (int i = 1; i <= 1024; i++) {
			manies.add(new org.utkuozdemir.sample2.json.Many("many-" + i));
		}
		jsonOne.setMany(manies);
	}

	@RequestMapping(value = "/personProtobuf", method = RequestMethod.GET)
	@ResponseBody
	public String personProtobuf() {
		return protobufPerson;
	}

	@RequestMapping(value = "/personJSON", method = RequestMethod.GET)
	@ResponseBody
	public org.utkuozdemir.sample1.json.Person personJSON() {
		return jsonPerson;
	}

	@RequestMapping(value = "/oneProtobuf", method = RequestMethod.GET)
	@ResponseBody
	public String oneProtobuf() {
		return protobufOne;
	}

	@RequestMapping(value = "/oneJSON", method = RequestMethod.GET)
	@ResponseBody
	public org.utkuozdemir.sample2.json.One oneJSON() {
		return jsonOne;
	}
}
