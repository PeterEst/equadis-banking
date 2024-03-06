
# Equadis - Banking

This project implements a banking system using Spring Boot for the backend and Angular for the frontend. The system provides APIs for managing customers, accounts, and transactions.


## Expectations

- API to create customer ✅
- API to create an account with initial balance ✅
- APIs to deposit and withdraw certain amounts ✅
- APIs to query transactions ✅
- Public Github ✅
## Bonuses

- Containerization working perfectly with Docker ✅
- Needed scripts to automate CI ✅
## Additionals

- Pagination: Enhances user experience by dividing large datasets into manageable pages, ensuring quicker loading times and efficient navigation through transaction histories or customer data in the banking system.

- Debounced Search Query (Accounts Listing): Optimized the search functionality with a debounced search query, providing users with a smoother and more responsive experience while reducing unnecessary API calls and enhancing overall performance.

- Swagger Endpoint: Explore and test APIs effortlessly using Swagger UI by navigating to the /swagger-ui/index.html endpoint using spring boot's port.

- Unit Testing & Github Actions: Implemented and executed simple unit tests for transaction deposit and withdrawal functionality. Ensured thorough testing and validation before any merge or push to the main branch for robust code quality.

- Postman Collection: Suite of requests to interact with and verify the functionality of the APIs.
## Run

1- **Clone the project & go to the project directory**

```bash
  git clone https://github.com/PeterEst/equadis-banking
```

```bash
  cd equadis-banking
```

2- **Create a `.env` file in the project root and add the necessary environment variables. You can use the provided `.env.example` file as a template.**

```bash
  cp .env.example .env
```

Update the values in the `.env` file as needed. If `SPRING_BOOT_DOCKER_PORT` is changed other than `8080`, make sure to update the NGINX configuration `nginx.conf` in the `frontend` folder to match the Docker network backend port.

```bash
  location /api {
        proxy_pass http://spring-boot-app:8080;
   }
```

3- **Execute the following command in the root folder to build and run the project:**
```bash
  docker-compose up
```
This command will start three services - **Microsoft SQL Server**, **Spring Boot** backend, and **Angular** frontend - creating a fully functional Equadis Banking System.

**Important Note**: Ensure the database is created. Spring Boot will keep failing on initial startup due to the `restart: on-failure` configuration in the Docker Compose file. Once a database with the name specified in the `MSSQL_DATABASE_NAME` environment variable is created, Spring Boot will work automatically.

There are multiple ways to access the SQL Server and create the database:

    Make sure MSSQL / SQL Server container having name of `ms-sql-server`
    is working with a status of UP using: `docker ps` command

- Accessing the Docker container directly:
    - `docker exec -it ms-sql-server sh`
    - `/opt/mssql-tools/bin/sqlcmd -U <SA_USERNAME> -P <SA_PASSWORD>` (Set in .env)
    - `create database <MSSQL_DATABASE_NAME>`
    - `exit` (exits sqlcmd)
    - `exit` (exits shell)

- Installing a CLI tool and accessing the database (e.g., using sql-cli npm package)
    - `npm i -g sql-cli`
    - `mssql -u <SA_USERNAME> -p <SA_PASSWORD>`
    - `create database <MSSQL_DATABASE_NAME>`

- Using any GUI database tool.


4-  **The system should be up and running!**
- http://localhost:{FRONTEND_APP_PORT} or http://localhost if the frontend is port forwarded to the default port of `80` as set in the `.env.example`.
![Angular Frontend](https://i.ibb.co/CWvBjQT/Screenshot-2024-03-07-at-12-21-06-AM.png)
- http://localhost:{SPRING_BOOT_APP_PORT}/swagger-ui/index.html to access Swagger.
![Swagger UI](https://i.ibb.co/zhh1Twh/Screenshot-2024-03-07-at-12-21-15-AM.png)
