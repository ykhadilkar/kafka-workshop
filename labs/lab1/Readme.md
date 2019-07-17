Start Kafka
`cd Workspace/Kafka/confluent-5.2.2`
```bash
bin/confluent start
```

Watch output of Kafka topic myTopic
```bash
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 describe --topic myTopic
```

Run Spring Boot Application