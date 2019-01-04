Petclinic setup instructions
=======================
 

## Steps to build
* Build platform.extension.client.petclinic: mvn clean install (optionally add -DskipTests=true)
* Build petclinic.web: mvn clean install (optionally add -DskipTests=true)

## To start the spring boot applications
* Run petclinic-web as a spring boot application

---
### Steps to run the Application in Kubernetes

1. Start Minikube

```bash
minikube start
```

2. Build the application (optionally add -DskipTests=true)

```bash
cd platform.client.extension.petclinic/
mvn clean install -DskipTests=True

cd petclinic-web
mvn clean install -DskipTests=True

mvn package spring-boot:repackage
```

3. Build and publish the container image (replace *zsaliou* with your docker registry)

```bash
docker login
docker build -t zsaliou/petclinic .
docker push zsaliou/petclinic
```

4. Deploy the application to Minikube

```bash
kubectl create -f mongo-pv.yml

kubectl create -f mongo-pvc.yml

kubectl create -f mongo-controller.yml

kubectl create -f mongo-service.yml

kubectl create -f petclinic-deploy.yml

kubectl create -f petclinic-service.yml

minikube addons enable ingress

kubectl create -f ingress.yml
```

5. Retrieve the Minikube IP Address

```bash
minikube ip
```

6. Add the Minikube IP Address to the host file /etc/hosts

```bash
192.168.99.100 petclinic.com
```

7. The application will be exposed at the url: <http://petclinic.com/petclinic/#/h/petclinicdashboard/vpNotes>

8. Scale the application as needed 

```bash

kubectl scale --replicas=2 deployment/petclinic
```

---
