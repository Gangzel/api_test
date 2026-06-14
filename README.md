# Movies API

A REST API for managing a movie database, built with Spring Boot. This was made as a school project for a film society that wanted to move away from using spreadsheets to keep track of their movies, genres and actors.

---

## What this project does

The API lets you:
- Add, update, delete and view movies, genres and actors
- Link movies to genres and actors
- Filter movies by genre, release year or actor
- Search movies by title
- Get paginated results on all list endpoints

---

## Tech used

- Java 21
- Spring Boot 4
- Spring Data JPA
- SQLite (database)
- Maven (build tool)

---

## Project structure

I used a package-by-feature structure instead of the more traditional package-by-layer. This means all files related to movies (entity, repository, service, controller, DTOs) live together in one package, same for actors and genres. I found this easier to navigate.

```
src/main/java/com/filmSociety/movies_api/
├── DemoApplication.java
├── errors/xs
│   └── GlobalExceptionHandler.java
├── movie/
│   ├── MovieEntity.java
│   ├── MovieRepository.java
│   ├── MovieService.java
│   ├── MoviesController.java
│   └── dto/
│       ├── CreateMovieRequest.java
│       ├── UpdateMovieRequest.java
│       └── MovieSummaryResponse.java
├── actor/
│   ├── ActorEntity.java
│   ├── ActorsRepository.java
│   ├── ActorsService.java
│   ├── ActorsController.java
│   └── dto/
│       ├── CreateActorRequest.java
│       ├── UpdateActorRequest.java
│       └── ActorSummaryResponse.java
└── genre/
    ├── GenreEntity.java
    ├── GenresRepository.java
    ├── GenresService.java
    ├── GenresController.java
    └── dto/
        ├── CreateGenreRequest.java
        ├── UpdateGenreRequest.java
        └── GenreSummaryResponse.java
```

---

## How to run it

### You'll need:
- Java 21
- Maven (or just use the `./mvnw` wrapper that comes with the project)

### Steps:

1. Clone the repo
```bash
git clone <your-repo-url>
cd movies-api
```

2. Run it
```bash
./mvnw spring-boot:run
```

The API will start at **http://localhost:8080**

The SQLite database file (`database.db`) gets created automatically in the project root when you run the app for the first time, so you don't need to set anything up.

---

## Configuration

Settings are in `src/main/resources/application.properties`:

```properties
spring.application.name=demo
spring.datasource.url=jdbc:sqlite:database.db
spring.datasource.driver-class-name=org.sqlite.JDBC

spring.jpa.database-platform=org.hibernate.community.dialect.SQLiteDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
server.error.include-message=always
```

---

## API Endpoints

### Movies

| Method | URL | What it does |
|--------|-----|-------------|
| GET | `/movies` | Get all movies |
| GET | `/movies?genre={id}` | Filter movies by genre |
| GET | `/movies?year={year}` | Filter movies by release year |
| GET | `/movies?actor={id}` | Filter movies by actor |
| GET | `/movies/search?title={text}` | Search movies by title |
| GET | `/movies/{id}` | Get one movie |
| POST | `/movies` | Create a movie |
| PATCH | `/movies/{id}` | Update a movie |
| DELETE | `/movies/{id}` | Delete a movie |
| GET | `/movies/{id}/actors` | Get all actors in a movie |
| POST | `/movies/{id}/actors/{actorId}` | Add actor to movie |
| DELETE | `/movies/{id}/actors/{actorId}` | Remove actor from movie |
| POST | `/movies/{id}/genres/{genreId}` | Add genre to movie |
| DELETE | `/movies/{id}/genres/{genreId}` | Remove genre from movie |

All GET list endpoints support `?page=0&size=10` for pagination.

---

### Genres

| Method | URL | What it does |
|--------|-----|-------------|
| GET | `/genres` | Get all genres |
| GET | `/genres/{id}` | Get one genre |
| GET | `/genres/{id}/movies` | Get all movies in a genre |
| POST | `/genres` | Create a genre |
| PATCH | `/genres/{id}` | Update a genre |
| DELETE | `/genres/{id}` | Delete a genre |
| DELETE | `/genres/{id}?force=true` | Force delete a genre |

---

### Actors

| Method | URL | What it does |
|--------|-----|-------------|
| GET | `/actors` | Get all actors |
| GET | `/actors?name={text}` | Search actors by name |
| GET | `/actors/{id}` | Get one actor |
| GET | `/actors/{id}/movies` | Get all movies of an actor |
| POST | `/actors` | Create an actor |
| PATCH | `/actors/{id}` | Update an actor |
| DELETE | `/actors/{id}` | Delete an actor |
| DELETE | `/actors/{id}?force=true` | Force delete an actor |
| POST | `/actors/{id}/movies/{movieId}` | Add movie to actor |
| DELETE | `/actors/{id}/movies/{movieId}` | Remove movie from actor |

---

## Example requests

### Create a movie
```json
POST /movies
{
  "title": "Inception",
  "releaseYear": 2010,
  "duration": 148
}
```

### Update a movie (only send the fields you want to change)
```json
PATCH /movies/1
{
  "duration": 152
}
```

### Create an actor
```json
POST /actors
{
  "name": "Leonardo DiCaprio",
  "birthDate": "1974-11-11"
}
```

> Note: birthDate must be in YYYY-MM-DD format

### Create a genre
```json
POST /genres
{
  "name": "Sci-Fi"
}
```

---

## Example responses

### Movie
```json
{
  "id": 1,
  "title": "Inception",
  "releaseYear": 2010,
  "duration": 148,
  "actors": [1, 2, 3],
  "genres": [1, 2]
}
```
> actors and genres are returned as lists of IDs

### Actor
```json
{
  "id": 1,
  "name": "Leonardo DiCaprio",
  "birthDate": "1974-11-11",
  "movies": [1, 4, 7]
}
```

### Genre
```json
{
  "id": 1,
  "name": "Sci-Fi"
}
```

---

## Deleting with relationships

By default you can't delete a genre or actor that is still linked to movies. You'll get an error like:

```json
{
  "status": 409,
  "message": "Genre is used by 5 movies"
}
```

If you want to delete it anyway and remove all its associations, add `?force=true`:

```
DELETE /genres/1?force=true
DELETE /actors/1?force=true
```

Movies can always be deleted without any extra steps.

---

## Error responses

Errors always come back as JSON:

```json
{
  "status": 404,
  "message": "Movie not found"
}
```

If you send invalid data to a POST endpoint:
```json
{
  "status": 400,
  "errors": ["title: must not be blank", "releaseYear: must not be null"]
}
```

