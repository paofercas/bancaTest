FROM eclipse-temurin:17.0.5_8-jre-alpine
ENV DEPLOY_DIR=/opt/app
RUN mkdir ${DEPLOY_DIR}
COPY build/libs/banca-cliente*.jar ${DEPLOY_DIR}/japp.jar
RUN ls -la ${DEPLOY_DIR}
ENV JAVA_TOOL_OPTIONS="-Dfile.encoding=UTF-8 -Dsun.jnu.encoding=UTF-8"
ENV LANG=C.UTF-8
ENV LC_ALL=C.UTF-8
CMD ["java", "-jar", "/opt/app/japp.jar"]