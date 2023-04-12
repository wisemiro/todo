run:
	mvn clean compile package

deploy:
	mvn clean compile package wildfly:deploy

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

wire:
	docker exec -it wildfly /bin/bash

migrateup:
	mvn clean flyway:migrate -Dflyway.configFiles=flyway.conf