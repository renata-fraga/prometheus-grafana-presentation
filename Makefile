up-grafana:
	cd docker-local && docker compose up -d grafana

up:
	cd docker-local && docker compose up -d

up-api:
	cd docker-local && docker compose up -d api.prometheus.grafana.presentation --build

down:
	cd docker-local && docker compose down

install:
	gradle build