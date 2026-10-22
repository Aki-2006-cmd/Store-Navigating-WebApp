# AisleWise Backend

Spring Boot (Java) REST API for AisleWise. It receives a shopper's product list, calculates an **optimal route** through the supermarket, and returns the route as ordered waypoints.

**In charge:** G.D.P.A. Gamage · T.M. Hasthimuni · A. Thivaharan

Back to the [main README](../README.md).

---

## Tech Stack

- Java
- Spring Boot
- Maven
- REST API (JSON)

## Architecture

```
Frontend ──REST──▶ Controllers ──▶ Services ──▶ Repository ──▶ Database
Frontend ◀─JSON─── Controllers ◀── Services ◀── Repository ◀──
```

| Layer | Class | Responsibility |
|-------|-------|----------------|
| Controller | `ProductCatalogController` | Products, stock and price |
| Controller | `NavigationRouteController` | Optimal route and waypoints |
| Controller | `CartValidationController` | Checks the product list |
| Controller | `SessionSyncController` | Links the cart screen to a phone via QR code |
| Service | `ProductCatalogService` | Product catalog logic |
| Service | `StoreNavigationService` | Coordinates navigation requests |
| Service | `RouteSolverService` | Optimal route calculation (Dijkstra + TSP) |
| Service | `QRCodeSessionService` | QR code sessions for mobile navigation |
| Model | `GridPoint`, `PathNode`, `Product`, `RouteResponsedto` | Data objects |
| Repository | `ProductRepository` | Data access for products |
| Entry point | `StoreNavigatingBackendApplication` | Starts the Spring Boot app |

## Optimal Route Calculation

Implemented in `RouteSolverService` using a **Dijkstra + TSP approach**:

1. **Input:** the product list is converted to grid coordinates.
2. **Dijkstra:** computes the travel cost between every pair of stops on the walkable grid, going around obstacles (aisles and shelves).
3. **TSP:** chooses the best order in which to visit all the products.
4. **Route:** entrance, products and checkout are returned as ordered waypoints.

Dijkstra alone is greedy; adding TSP makes the whole route efficient and accurate.

## Project Structure

```
Backend/
├── .mvn/
└── src/main/
    ├── java/com/example/storenav.../
    │   ├── controller/
    │   ├── model/
    │   ├── repository/
    │   ├── service/
    │   └── StoreNavigatingBackendApplication.java
    └── resources/
```

## Getting Started

> Adjust these steps to your configuration.

**Prerequisites:** JDK and Maven (or the included wrapper), plus a configured database (see [Database](../Database/README.md)).

```bash
cd Backend
./mvnw spring-boot:run
```

Set your database connection in `src/main/resources/application.properties`. Spring Boot runs on port `8080` unless you change it there.

## API Endpoints

<!-- TODO: fill in the real paths, parameters and example responses -->

| Controller | Method | Path | Description |
|------------|--------|------|-------------|
| Product catalog | GET | `[path]` | List products with stock and price |
| Navigation route | POST | `[path]` | Optimal route for a product list |
| Cart validation | POST | `[path]` | Validate a product list |
| Session sync | GET / POST | `[path]` | Create or join a QR code session |

**Example route response**

```json
{
  "waypoints": [ { "x": 0, "y": 0 }, { "x": 4, "y": 3 } ]
}
```

*(Illustrative only. Replace it with the real response shape.)*

## Challenges

- Dijkstra alone is greedy, so we combined it with TSP.
- Mapping grid coordinates from the database to the route algorithm, while keeping aisles out of the walkable path.