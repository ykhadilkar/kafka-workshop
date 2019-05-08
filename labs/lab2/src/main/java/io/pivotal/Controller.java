package io.pivotal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@Autowired
	private KafkaTemplate<Object, Object> template;

	@PostMapping(path = "/send/user/{name}")
	public void sendUser(@PathVariable String name) {
		this.template.send("topic1", new User1(name));

	}

}
