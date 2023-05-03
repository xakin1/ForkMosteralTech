# monsteral-tech

## Deployment

- create a new PostgreSQL database
- run `create extension postgis;` on the new database (PostGIS needs to be installed) 
- set `application.yml` with database data
- configure the mail sender in `application.yml`
- running: `./gradlew[.bat] bootRun`
- changing the default port: `./gradlew[.bat] bootRun --args='--server.port=9000'`
- running using the development database: `gradlew bootRun --args='--spring.profiles.active=dev'`


## Configuration

### application.yml

* `properties.clientHost: http://localhost:1234` is the protocol://hostname:port of the web client application
* `server.port: 8080` is the port to deploy the web server

## Launch using HTTPS - certificate generation

To launch the server using HTTPS, we need a certificate (.p12). That of the server will be generated from 
those of the client, so if they are not created, it is necessary to generate them following the README 
of the client before continuing.

If you already have the client's certificates, you only need to run the following command:

```bash
openssl pkcs12 -export -in fullchain.pem -inkey privkey.pem -out keystore.p12 -name server -CAfile chain.pem -caname root
```

Once executed, it will create a *keystore.p12* file that must be moved to the path */server/gradle/keys/*.

In case the certificate path or its name is changed, it is necessary to modify the property *server.ssl.key-store*
in the file *build.gradle* and indicating the new path or the new name.


