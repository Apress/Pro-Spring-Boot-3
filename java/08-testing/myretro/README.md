# MyRetro - MongoDB

## Example when using authentication

```shell
docker run --name mongodb --rm -d -p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=retroadmin \
-e MONGO_INITDB_ROOT_PASSWORD=aw2s0me \
-e MONGO_INITDB_DATABASE=retrodb mongo
```

```shell
mongosh mongodb://retroadmin:aw2s0me@127.0.0.1:27017/retrodb?directConnection=true&serverSelectionTimeoutMS=2000&authSource=admin&appName=mongosh+1.7.1
```
## Mongo Commands

```shell
retrodb> show collections
retroBoard
retrodb> db.retroBoard.find({});
[
  {
    _id: new UUID("9dc9b71b-a07e-418b-b972-40225449aff2"),
    name: 'Spring Boot Conference',
    cards: [
      {
        _id: new UUID("bb2a80a5-a0f5-4180-a6dc-80c84bc014c9"),
        comment: 'Spring Boot Rocks!',
        cardType: 'HAPPY'
      },
      {
        _id: new UUID("7d894291-e055-4bbd-a361-8620beaabdc7"),
        comment: 'Meet everyone in person',
        cardType: 'HAPPY'
      },
      {
        _id: new UUID("92086012-33e6-4372-9213-4f30edac5d3b"),
        comment: 'When is the next one?',
        cardType: 'MEH'
      },
      {
        _id: new UUID("0b775f4a-7032-462a-862c-2496ab92e0c9"),
        comment: 'Not enough time to talk to everyone',
        cardType: 'SAD'
      }
    ],
    _class: 'com.apress.myretro.board.RetroBoard'
  }
]


retrodb> exit
```

## No Authentication when running with `spring-boot-docker-compose`

```shell
docker run -it --rm --network myretro_default mongo mongosh --host mongo retrodb
```



## Stop

```shell
docker stop mongodb
```
