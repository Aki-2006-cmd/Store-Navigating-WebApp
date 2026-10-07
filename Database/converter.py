import sqlite3

connection=sqlite3.connect("Database/database.db")

with open("Database/database.sql","w") as file:
    for line in connection.iterdump():
        file.write(f"{line}\n")

connection.close()