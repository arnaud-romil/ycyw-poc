# Your Car Your Way - POC Chat Client-Agent

## Objectif

Ce Proof of Concept (POC) a pour objectif de démontrer la faisabilité technique d'un chat entre un client et un agent du support.
Il repose sur un serveur WebSocket, intégré dans un backend Spring Boot, avec une interface Angular permettant d'échanger des messages instantanément.

## Technologies utilisées

- Backend: **Spring Boot 3.4.5**
- Frontend: **Angular 18**
- Base de données: **MySQL 8**

## Installer et lancer l'application

1. Clonez le dépôt

```git clone  https://github.com/arnaud-romil/ycyw-poc.git```

2. Installez la base de données

- Assurez-vous d'avoir installer MySQL sur votre machine.
- Créez la base de données: ``` CREATE DATABASE `ycyw_db` ```;
- Créez un utilisateur avec les privilèges nécessaires:
```
CREATE USER 'ycyw_user'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON ycyw_db.* TO 'ycyw_user'@'localhost';
FLUSH PRIVILEGES;
```
- Vérifiez les détails de connexion dans le fichier ```ycyw-poc/back/src/main/resources/application-local.properties```:
```
spring.datasource.url=jdbc:mysql://localhost:3306/ycyw_db
spring.datasource.username=ycyw_user
spring.datasource.password=password
```

3. Installez les dépendances Maven pour le backend: 
```
mvn clean install
```

4. Installez les dépendances npm pour le frontend:
```
npm install
```

5. Lancez le backend
```
mvn spring-boot:run
```

6. Lancez le frontend
```
ng serve
```

L'application est disponible à ```http://localhost:4200```.

Deux utilisateurs sont pré-configurés:
- un client qui a pour identifiants: ```customer@test.com:user1Password!```
- un agent qui a pour identifiants: ```agent1@ycyw.com:user2Password!```

## Notes

**Note importante - Clé privée dans le dépôt**

Ce dépôt contient une **clé privée utilisée uniquement pour ce POC**, à des fins de test local.

Il est **fortement déconseillé de procéder ainsi dans un environnement réel**.

En production, la clé privée JWT doit être gérée de manière sécurisée et ne doit pas être versionnée.


## Licence

Ce projet est la propriété de **Your Car Your Way**.











