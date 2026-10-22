# AisleWise Database

Stores everything the route calculation needs: the store layout grid, the obstacles (aisles and shelves) and the product data.

**In charge:** H.M.D.T. Samarasena · W.S.D. Tissera

Back to the [main README](../README.md).

---

## Technologies

- MySQL
- SQLite
- Python

## Store Layout Grid

The supermarket floor plan is divided into an **x-y coordinate grid**.

- `x` runs across the store, from 0 to 23.
- The entrance, cart and checkout area is `y = 0`.
- Shelf rows are counted upward from `y = 1`.
- Carts and the machine start at the entrance.
- Walkable corridors are the only cells the route may use.

## Obstacles

Aisles and shelves are named and marked as **non-walkable** cells, so the route never cuts through shelving.

| Aisle | Contents |
|-------|----------|
| Aisle 1 | Dairy & Milk |
| Aisle 2 | Bakery & Buns |
| Aisle 3 | Produce & Veg |
| Aisle 4 | Snacks & Sweets |
| Aisle 5 | Meat & Poultry |
| Aisle 6 | Seafood & Fish |

## Products

- **10 categories**, 22 items each: vegetables, fruits, dairy, meat, seafood, bakery, pantry and baking, cereals, snacks and beverages.
- Each product stores its name, category, price and stock.
- Each product is placed at an `(x, y)` cell with its aisle, shelf and column, so the app can pinpoint where it is.

## Schema

<!-- TODO: replace with your real table and column names -->

| Table | Key fields |
|-------|------------|
| `products` | id, name, category, price, stock, x, y, aisle, shelf, column |
| `obstacles` | id, name, x, y |

## Setup

> Adjust to your scripts and file names.

1. Create the database (MySQL, or an SQLite file).
2. Run the schema script to create the tables.
3. Load the layout grid, obstacles and products (for example with the Python script).
4. Set the connection details in the backend's `application.properties`.

```bash
# example only
python load_data.py
```

## Notes

- Keep grid coordinates consistent with the route algorithm in the [backend](../Backend/README.md).
- Adding a new product means giving it a valid walkable `(x, y)` position.