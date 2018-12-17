FROM openjdk:12-alpine
RUN apk add gettext curl
COPY /maven/scripts/* /app/
COPY /maven/app/${project.artifactId}.${project.packaging}.jar /app/${project.artifactId}.${project.packaging}
RUN chmod u+x /app/run.sh
CMD ["/app/run.sh"]