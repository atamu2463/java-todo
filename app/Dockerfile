FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

# フォルダの中身を指定してコピー
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src

# ここでもう一度確認（これが通れば成功！）
RUN ls -la .mvn/wrapper/maven-wrapper.properties

COPY src/ src/

# 改行コード問題対策（WSL上で作ったなら不要なはずですが念のため）
RUN chmod +x mvnw

RUN ./mvnw install -DskipTests

ENTRYPOINT ["java","-jar","target/java-todo.jar"]