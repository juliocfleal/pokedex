FROM node:16 AS react-build
WORKDIR /app
COPY pokemon-front/pokedex/package.json pokemon-front/pokedex/package-lock.json ./
RUN npm install
COPY pokemon-front/pokedex/ ./
RUN npm run build

FROM maven:3.8.5-openjdk-17 AS spring-build
WORKDIR /app
COPY pokemon/pom.xml ./
RUN mvn dependency:go-offline
COPY pokemon/ ./
RUN mvn package -DskipTests

FROM openjdk:17-jdk-slim
WORKDIR /app
COPY --from=react-build /app/build/ /app/static/
COPY --from=spring-build /app/target/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
