 # TaskFlow API

 TaskFlow est une API REST développée avec **Spring Boot** permettant de gérer ses tâches au quotidien.  
 L’application permet de créer, organiser et suivre des tâches avec des catégories, des tags, des priorités et des statuts.  

 L’environnement est entièrement **dockerisé** avec **MySQL**, **phpMyAdmin** et un serveur **SMTP de test**.

 ---

 ## ✨ Fonctionnalités principales
 - Authentification JWT (register, login, refresh token).
 - Gestion des utilisateurs.
 - CRUD des tâches (créer, lister, modifier, supprimer).
 - Gestion des catégories et des tags.
 - Attribution de priorité et statut aux tâches.
 - Notifications email simulées via **smtp4dev**.
 - Déploiement rapide grâce à **Docker**.

 ---

 ## 🛠️ Prérequis
 - [Docker](https://docs.docker.com/get-docker/) & [Docker Compose](https://docs.docker.com/compose/) installés.
 - [Java 17+](https://adoptium.net/) ou [OpenJDK](https://openjdk.org/).
 - [Maven](https://maven.apache.org/) pour builder le projet.

 ---

 ## 🚀 Installation & Lancement

 ### 1. Cloner le projet
 git clone git@github.com:NAJPRO/TaskFlow-API.git
 cd TaskFlow-API

 ### 2. Lancer l’infrastructure Docker
 docker compose up -d

 Cela démarre :
 - **MySQL 8.0** (port `3307`)
 - **phpMyAdmin** (port `8081`)
 - **smtp4dev** (port `8082` pour UI et `9025` pour SMTP)

 ### 3. Configurer l’application
 Le fichier `application.yml` est déjà configuré pour se connecter au conteneur MySQL et au serveur mail local.

 ### 4. Lancer l’API Spring Boot
 ./mvnw spring-boot:run

 L’API sera disponible sur :  
 👉 http://localhost:8080/api

 ---

 ## 📌 Endpoints principaux

 ### 🔑 Authentification
 - POST /api/auth/register → Créer un compte.
 - POST /api/auth/login → Se connecter et recevoir un JWT.
 - POST /api/auth/refresh-token → Rafraîchir un JWT expiré.

 ### ✅ Tâches
 - POST /api/tasks → Créer une tâche.
 - GET /api/tasks → Lister mes tâches.
 - GET /api/tasks/{id} → Consulter une tâche.
 - PUT /api/tasks/{id} → Modifier une tâche.
 - DELETE /api/tasks/{id} → Supprimer une tâche.

 ### 📂 Catégories & Tags
 - POST /api/categories → Créer une catégorie.
 - GET /api/categories → Lister mes catégories.
 - POST /api/tags → Créer un tag.
 - GET /api/tags → Lister mes tags.

 ---

 ## 🗄️ Base de données
 L’API utilise **MySQL**. Par défaut :
 - Database : TaskFlow
 - User : junior
 - Password : TaskFlow
 - Port : 3307

 Accès phpMyAdmin :  
 👉 http://localhost:8081

 ---

 ## 📧 Emails
 Les emails sortants sont capturés par **smtp4dev** (aucun envoi réel).  
 Interface web :  
 👉 http://localhost:8082

 ---

 ## 🔧 Stack technique
 - Backend : Spring Boot 3, Spring Security, Spring Data JPA, Hibernate.
 - DB : MySQL 8.0.
 - Outils : Docker, Docker Compose, phpMyAdmin, smtp4dev.
 - Auth : JWT (Json Web Token).

 ---

 ## 🤝 Contribution
 Les contributions sont les bienvenues !  
 1. Forkez le projet.  
 2. Créez une branche (`feature/ma-fonctionnalite`).  
 3. Committez vos changements.  
 4. Poussez la branche.  
 5. Créez une Pull Request.  

 ---

 ## 📜 Licence
 Ce projet est distribué sous licence **MIT**.
