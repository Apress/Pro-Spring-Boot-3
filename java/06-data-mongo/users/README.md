# Users - MongoDB


```shell
docker run --name mongodb --rm -d -p 27017:27017 \
-e MONGO_INITDB_ROOT_USERNAME=retroadmin \
-e MONGO_INITDB_ROOT_PASSWORD=aw2s0me \
-e MONGO_INITDB_DATABASE=retrodb mongo
```

```shell
mongosh mongodb://retroadmin:aw2s0me@127.0.0.1:27017/retrodb?directConnection=true&serverSelectionTimeoutMS=2000&authSource=admin&appName=mongosh+1.7.1
```

```shell
retrodb> show collections
user
retrodb> db.user.find()
[
  {
    _id: 'ximena@email.com',
    name: 'Ximena',
    gravatarUrl: 'https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar',
    password: 'aw2s0me',
    userRole: [ 'USER' ],
    active: true,
    _class: 'com.apress.users.User'
  },
  {
    _id: 'dummy@email.com',
    name: 'Dummy',
    gravatarUrl: 'https://www.gravatar.com/avatar/23bb62a7d0ca63c9a804908e57bf6bd4?d=wavatar',
    password: 'aw2s0me',
    userRole: [ 'USER' ],
    active: true,
    _class: 'com.apress.users.User'
  }
]


retrodb> exit
```

## Stop

```shell
docker stop mongodb
```