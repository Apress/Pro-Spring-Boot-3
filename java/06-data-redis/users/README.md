# Redis

## Docker
```shell
docker run -d --name redis --rm \
-p 6379:6379 redis:alpine redis-server --save 60 1 --loglevel warning
```

## Docker Compose

The `docker-compose.yaml` file is provided. To run the redis server using docker compose, run the following command.

```shell
docker compose up -d
``` 

### Redis CLI - works only if redis is running using docker compose.

```shell
docker run -it --rm --network users_default redis:alpine redis-cli -h redis 
```

## Useful Commands

- Get all keys
  ```shell
  redis:6379> KEYS *
  ```
  
- Get the Type of the Key
    ```shell
    redis:6379> TYPE "USERS:norma@email.com"
    ```
  
- Get values from a Hash
    ```shell
    redis:6379> HVALS "USERS:norma@email.com"
    ```

- Get all the hash keys
    ```shell
    redis:6379> HKEYS "USERS:norma@email.com" 
    ```

- Get the Hash field value
    ```shell
    redis:6379> HGET "USERS:norma@email.com" name
    ```

- Delete a Key
    ```shell
    redis:6379> DEL "USERS"
    ```