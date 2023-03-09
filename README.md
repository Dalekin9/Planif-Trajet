# PlanifCarbon

Projet de génie logiciel du groupe Carbon (M1 2023)

# Configure project
```sh
# Java
sudo apt install openjdk-17-jdk openjdk-17-jre
# Node.js
curl -sL https://deb.nodesource.com/setup_14.x | sudo bash -
sudo apt -y install nodejs
npm install
```

# Launch backend
```sh
cd backend/
./mvnw compile exec:java
```

# Launch frontend
```sh
cd frontend/
npm run-script start
```

# Launch test
```sh
cd backend/
./mvnw compile test
```