# kubernetes-demo

Пример проекта из нескольких сервисов и соответствующей конфигурации для Kubernetes. Для локального запуска используется [minikube](https://kubernetes.io/ru/docs/tasks/tools/install-minikube/).

### Запуск

Создание локального кластера через minikube:
```
minikube start
minikube addons enable ingress
```

Предварительное создание Docker-образов и их загрузка в minikube:
```
for app in ui todo-service user-service
do
    (cd "$app" && ./mvnw -DskipTests clean package spring-boot:build-image && minikube image load "${app}:0.0.1-SNAPSHOT") || break
done
```

Развёртывание всех сервисов:
```
kubectl apply -f kubernetes.yaml
```

Для доступа через localhost:
```
kubectl port-forward service/ingress-nginx-controller -n ingress-nginx 8080:80
```

### Полезные ссылки

- https://kubernetes.io/docs/concepts/workloads/controllers/deployment/
- https://kubernetes.io/docs/tutorials/services/connect-applications-service/
- https://kubernetes.io/ru/docs/tasks/tools/install-minikube/
- https://docs.spring.io/spring-boot/maven-plugin/build-image.html
- https://spring.io/blog/2020/03/25/liveness-and-readiness-probes-with-spring-boot
