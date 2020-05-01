FROM openjdk:8-alpine
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /rkots/lib
COPY ${DEPENDENCY}/META-INF /rkots/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /rkots
ENTRYPOINT ["java","-cp","rkots:rkots/lib/*","com.ad3bay0.rkots.Rkots"]