#!/bin/sh

cd /app
applicationYaml=$(RABBITMQ_PASSWORD=$(cat ${RABBITMQ_PASSWORD_FILE}) /usr/bin/envsubst < application.yaml)
echo "${applicationYaml}" > application.yaml
echo "Waiting 60sec for RabbitMq to be up"
sleep 60
java -Djava.security.egd=file:/dev/./urandom -jar /app/IndexServer.jar