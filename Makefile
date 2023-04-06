dev:
	mvn spring-boot:run

deploy:
	mvn clean package wildfly:deploy

install:
	mvn clean install

clean:
	mvn clean package

verify:
	mvn clean verify

idea:
	 mvn -U idea:idea

compile:
	mvn compile

pkg:
	mvn package