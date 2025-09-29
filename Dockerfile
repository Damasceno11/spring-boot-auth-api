# Estágio 1: Build - Usamos uma imagem Maven para compilar nosso projeto
# Esta imagem já vem com Maven e Java (JDK 17) instalados.
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia o código do seu projeto para dentro do container
COPY . .

# Executa o comando de build do Maven para gerar o arquivo .jar
RUN mvn clean install -DskipTests

# ---

# Estágio 2: Run - Usamos uma imagem Java leve para apenas rodar a aplicação
# A imagem 'slim' é menor e mais segura para produção.
FROM openjdk:17-jdk-slim

# Define o diretório de trabalho
WORKDIR /app

# Copia APENAS o arquivo .jar compilado do estágio de build para a nossa imagem final
# Isso torna a imagem final muito menor e mais eficiente.
COPY --from=build /app/target/crud-usuario-postgress-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta em que a aplicação vai rodar dentro do container
EXPOSE 10000

# Comando final para iniciar a aplicação quando o container for executado
ENTRYPOINT ["java", "-jar", "app.jar"]