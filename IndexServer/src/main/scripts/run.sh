#!/bin/sh
/app/waitForRabbitMQ.sh
/app/waitForElastic.sh
cd /app
applicationYaml=$(RABBITMQ_PASSWORD=$(cat ${RABBITMQ_PASSWORD_FILE}) /usr/bin/envsubst < application.yaml)
echo "${applicationYaml}" > application.yaml
echo "Waiting 5sec for RabbitMq to be setup"
sleep 5
java -Djava.security.egd=file:/dev/./urandom -jar /app/IndexServer.jar