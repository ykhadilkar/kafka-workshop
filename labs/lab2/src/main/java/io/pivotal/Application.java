package io.pivotal;

import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;

@SpringBootApplication
public class Application {

	private final Logger logger = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * By default, records that fail are simply logged and we move on to the next one.
	 * We can, however, configure an error handler in the listener container to perform some other action.
	 * To do so, we override Spring Boot’s auto-configured container factory with our own:
	 * @param configurer
	 * @param kafkaConsumerFactory
	 * @param template
	 * @return
	 */
	@Bean
	public ConcurrentKafkaListenerContainerFactory<?, ?> kafkaListenerContainerFactory(
			ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
			ConsumerFactory<Object, Object> kafkaConsumerFactory,
			KafkaTemplate<Object, Object> template) {
		ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
		configurer.configure(factory, kafkaConsumerFactory);
		factory.setErrorHandler(new SeekToCurrentErrorHandler(
				new DeadLetterPublishingRecoverer(template), 3)); // dead-letter after 3 tries
		return factory;
	}

	@Bean
	public RecordMessageConverter converter() {
		return new StringJsonMessageConverter();
	}

	@KafkaListener(id = "userGroup", topics = "users")
	public void listen(User2 user2) {
		logger.info("Received: " + user2);
		if (user2.getName().startsWith("fail")) {
			throw new RuntimeException("failed");
		}
	}

	@KafkaListener(id = "dltGroup", topics = "users.DLT")
	public void dltListen(String in) {
		logger.info("Received from DLT: " + in);
	}

	@Bean
	public NewTopic topic() {
		return new NewTopic("users", 1, (short) 1);
	}

	@Bean
	public NewTopic dlt() {
		return new NewTopic("users.DLT", 1, (short) 1);
	}

}
