# SpookyScarySkeletons
Componentware Project

Prerequesites:
Make sure to install the following
1. Flutter -> https://flutter.dev/docs/get-started/install
2. Docker -> https://docs.docker.com/install/
3. Wildfly -> https://wildfly.org/downloads/
4. Maven -> http://maven.apache.org/install.html

How to setup:
1. Go into the root directory and execute "mvn clean package"
2. Start the PostgreSQL Database using docker "docker run --rm   --name pg-docker -e POSTGRES_PASSWORD=docker -d -p 5432:5432 postgres"
2. Start the Wildfly Server
3. Go into the Wildfly Admin console and upload a new Deployment from the folder "EAR/target/SpookyScarySkeletons.ear"
4. Go into the "app" folder and execute "flutter run -d <deviceId>"

