# System Blueprint: Virtual Internship Platform (Project "Forage-MD")

## 1. Project Objective
To build and deploy a localized, B2B virtual internship platform for the Malagasy market (targeting enterprises like Axian Group, Telma, and local banks). The platform bridges the gap between academic theory and corporate needs by providing pre-vetted tech talent through hands-on, automated cybersecurity and development simulations.

**Primary Engineering Goal:** Build the system using an enterprise-grade stack so the platform itself serves as a high-tier DevOps/Cloud/Cybersecurity portfolio.

---

## 2. Technology Stack
* **Backend Core:** Java 21+ with Spring Boot (RESTful API, User Management, Progress Tracking).
* **Database:** PostgreSQL.
* **Infrastructure as Code (IaC):** Terraform (Cloud provisioning) & Ansible (Configuration management).
* **Hybrid Virtualization:** Docker Compose (Lightweight containers) & Vagrant (Full Virtual Machines).
* **Cloud Target (Phase 3):** AWS (EC2, ECS, RDS).
* **CI/CD:** Git, GitHub Actions / GitLab CI.

---

## 3. Architecture Overview: The Hybrid Sandbox Model
The defining feature of this platform is the dynamically provisioned hands-on environment. The system intelligently routes simulation requirements based on the required depth of analysis.

### 3.1. The Container Track (Default / Lightweight)
**Triggered when:** The task involves log analysis, network traffic monitoring (PCAP), web application pentesting, or interacting with a specific service (e.g., SIEM dashboard).
* **Mechanism:** Docker Compose.
* **Advantages:** Millisecond spin-up time, negligible resource overhead.
* **Example Stack:** ELK stack + Nginx + Mock Log Generator.

### 3.2. The Virtual Machine Track (Heavyweight)
**Triggered when:** The task requires kernel-level interaction, endpoint memory forensics, malware execution/reversing, or specific Windows OS features (Active Directory, Registry analysis).
* **Mechanism:** Vagrant provisioning an isolated hypervisor environment (VirtualBox/KVM).
* **Advantages:** Complete isolation, realistic OS-level behavior.
* **Example Stack:** Windows 10 Endpoint + Sysmon + Splunk Universal Forwarder.

---

## 4. Phased Execution Roadmap

### Phase 1: Local Minimum Viable Product (The "Playbook")
*Zero cloud costs. Validates the technical curriculum and automation.*
1.  **Repository Setup:** Create `md-virtual-internship-core`.
2.  **Curriculum Design:** Draft the "SOC Analyst Tier 1" 3-task simulation.
3.  **Ansible Automation:** Write playbooks that inject mock firewall logs and misconfigurations into standard baseline images.
4.  **Local Provisioning:** Create a `docker-compose.yml` (for the SIEM/Log parsing task) and a `Vagrantfile` (for the endpoint forensics task). 
5.  **Output:** A downloadable package. The user runs `make start-lab`, which triggers the hybrid provisioning locally.

### Phase 2: Core Engine Development (Spring Boot API)
*Building the brain of the platform.*
1.  **Domain Driven Design:** Implement entities for `Student`, `Company`, `InternshipTrack`, `Module`, and `Task`.
2.  **Authentication:** Implement JWT-based auth and Role-Based Access Control (Admin, Employer, Student).
3.  **Progress Tracking Engine:** Create endpoints to track task starts, submissions, and completion verification.
4.  **Certificate Generation:** Automate PDF generation upon successful completion of an `InternshipTrack`.

### Phase 3: Cloud Migration & Orchestration (AWS + Terraform)
*Moving from local automation to cloud-hosted SaaS.*
1.  **Terraform States:** Write Terraform scripts to provision an AWS VPC, EC2 instances, and RDS.
2.  **API Integration:** Modify the Spring Boot API to programmatically execute Terraform/Ansible commands or interact with the AWS SDK to spin up the required Docker/VM environments on demand.
3.  **Reverse Proxy & Routing:** Ensure each spun-up student environment is accessible via a unique, temporary secure subdomain (e.g., using a wildcard SSL and Nginx routing).

---

## 5. First Module Specification: SOC Tier 1 Analyst

**Sponsor Target:** Telecommunications/Banking Sector.
**Objective:** Identify, contain, and report a simulated data exfiltration event.

* **Task 1: Alert Triage (Container Track)**
    * *Setup:* Dockerized Kibana dashboard pre-loaded with mock Suricata/Zeek logs via Ansible.
    * *Action:* Student parses logs to identify an anomalous outbound connection (C2 beaconing).
* **Task 2: Endpoint Investigation (VM Track)**
    * *Setup:* Vagrant-provisioned Linux/Windows VM with injected persistence mechanisms (e.g., cronjob/registry key).
    * *Action:* Student accesses the VM, locates the malicious binary, and extracts its hash.
* **Task 3: Incident Reporting (Platform Track)**
    * *Setup:* Web application form via the Spring Boot frontend.
    * *Action:* Student writes an executive summary and submits IOCs (Indicators of Compromise) to complete the internship and earn the certificate.

---
## Agent Directives
1.  Prioritize idempotency in all Ansible scripts. The environment must build identically every time.
2.  Maintain strict separation of concerns between the Java API and the IaC provisioning layer.
3.  Ensure all generated mock data (logs, IP addresses) uses private IP space and non-attributable dummy data to prevent accidental real-world targeting.
