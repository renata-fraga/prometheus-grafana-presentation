up-grafana:
	docker compose up -d grafana

up:
	docker compose up -d && docker compose -f docker-compose-io.yml up -d

up-api:
	docker compose up -d api.prometheus.grafana.presentation --build

down:
	docker compose down && docker compose -f docker-compose-io.yml down

install:
	gradle build