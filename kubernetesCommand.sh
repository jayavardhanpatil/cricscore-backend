#!/bin/sh

kubectl create -f specs/postgres.yml

kubectl create configmap hostname-config --from-literal=postgres_host=$(kubectl get svc postgres -o jsonpath="{.spec.clusterIP}")

./mvnw -DskipTests package

docker build -t jayavardhanpatil/cricscore-springboot:v8 .

docker push jayavardhanpatil/cricscore-springboot:v8

kubectl create -f specs/spring-boot-app.yml

kubectl expose deployment cricscore-springboot --type=LoadBalancer --port=5050

kubectl get svc cricscore-springboot

kubectl scale deployment cricscore-springboot --replicas=2

#kubectl set image deployment/cricscore-springboot cricscore-springboot=jayavardhanpatil/cricscore-springboot:v7
