.PHONY: start-lab stop-lab reset-lab format

start-lab:
	@echo "Starting Virtual Internship Platform Lab Environment..."
	@echo "-> Starting Container Track (Task 1: Alert Triage)..."
	cd labs/container-track && docker compose up -d
	@echo "-> Container Track is running. Access logs at http://localhost:8080/logs/suricata.json"
	@echo "-> Starting VM Track (Task 2: Endpoint Investigation)..."
	cd labs/vm-track && vagrant up
	@echo "-> VM Track is running. SSH into it via: cd labs/vm-track && vagrant ssh"

stop-lab:
	@echo "Stopping Virtual Internship Platform Lab Environment..."
	@echo "-> Stopping Container Track..."
	cd labs/container-track && docker compose stop
	@echo "-> Stopping VM Track..."
	cd labs/vm-track && vagrant halt
	@echo "-> Lab stopped."

reset-lab:
	@echo "Resetting Virtual Internship Platform Lab Environment..."
	@echo "-> Destroying Container Track..."
	cd labs/container-track && docker compose down -v
	@echo "-> Destroying VM Track..."
	cd labs/vm-track && vagrant destroy -f
	@echo "-> Lab reset complete."

format:
	./format.sh
