**Confluent 5.2.2**
Start Kafka
`cd Workspace/Kafka/confluent-5.2.2`
```bash
bin/confluent start
```

**Confluent 5.3.1**
For Confluent Kafka version 5.3.1, download cli and Confluent Platform. Follow instructions from here.

confluent local start --path <path-to-confluent>

Watch output of Kafka topic myTopic
```bash
kafka-console-consumer --bootstrap-server 127.0.0.1:9092 describe --topic myTopic
```

Run Spring Boot Application