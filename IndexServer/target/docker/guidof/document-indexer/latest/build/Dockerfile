FROM openjdk:12-alpine
RUN apk add gettext
COPY /maven/resources/* /app/
COPY /maven/app/*.jar /app/IndexServer.jar
RUN chmod u+x /app/run.sh
CMD ["/app/run.sh"]
