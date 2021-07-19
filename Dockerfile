FROM java:8-jdk
ENV ARTIFACT_NAME=knight_board.jar
ENV APP_HOME=/usr/app/

COPY *.gradle gradle.* gradlew $APP_HOME
COPY gradle $APP_HOME/gradle
WORKDIR $APP_HOME
RUN ./gradlew clean build

COPY /build/libs/$ARTIFACT_NAME .

ENTRYPOINT exec java -jar ${ARTIFACT_NAME}