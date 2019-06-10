# JPA DAO Integration Tests With TestContainers, Liquibase and Oracle XE

## Why
The project shows how to create integration tests for a JPA-based DAO using a real database and scheme managed by by Liquibase.

Typical approach to JPA tests in the JavaEE environment is based on in-memory databases and automatic creation of the database schema based on annonations. That's fine, except that's not how application actually works when deployed in production.

Native SQL is [magic wand](https://vladmihalcea.com/the-jpa-entitymanager-createnativequery-is-a-magic-wand/) in JPA, but it also dramatically increases the value of integration tests executed against production database.


## Building and running tests

Init Git submodules
```
git submodule update --init --recursive
```

Build Oracle XE Docker image

```
docker build -t wnameless/docker-oracle-xe-11g docker-images/wnameless/docker-oracle-xe-11g
```

Run tests
```
mvn clean install
```
