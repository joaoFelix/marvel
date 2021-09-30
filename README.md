# Marvel API Demo Project

So you stumbled upon my humble API demo project. Good for you :)

This little project was my opportunity to code using Spring Boot and it did not disapoint!
I really enjoyed messing around, I hope you have fun with it too!

## Instalation

I used Maven to pack things up so you just need to run `mvn clean install`.


## Usage

You should have a `.env` file with the 3 properties inside:
* `marvel.publicKey`: a Marvel Developer Public Key
* `marvel.privateKey`: a Marvel Developer Private Key
* `translation.key`: the path for a JSON file containing a Google Cloud key

You can check [Marvel](https://developer.marvel.com/documentation/getting_started) and [Google Cloud](https://cloud.google.com/docs/authentication/production) for more information about these keys.

There properties may also be passed to the `JAR` file or to Maven through parameters.

There are two ways you can run the server:

  * Run the JAR file `target/marvel-0.0.1-SNAPSHOT.jar` with `java -jar target/marvel-0.0.1-SNAPSHOT.jar`
  * Run the command `mvn spring-boot:run`
  
The server runs on port `8080` of you machine (`http://localhost:8080/...`)
  
  
## API 

The server exposes 2 Rest endpoints:

* `http://localhost:8080/characters`: get and array with Marvel Characters ids
  * By default it gets ll Characters but you can use the `size` query parameter to say how many you want to retrieve
  
* `http://localhost:8080/characters/{characterId}`: get info on a particular Character
  * You can also use the `language` query parameter to translate the characters' description
  
There is also an OpenAPI page available on `http://localhost:8080/swagger-ui.html`, where you can check all information on these endpoints.
It is also available as JSON on `http://localhost:8080/v3/api-docs` and even in the file `target/openapi.json`
