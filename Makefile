up-grafana:
	docker compose up -d grafana

up:
	docker compose up -d && docker compose -f docker-compose-io.yml up -d

up-api:
	docker compose  -f docker-compose-io.yml up -d --build

down:
	docker compose -f docker-compose-io.yml down

down-grafana:
	docker compose down

install:
	gradle build