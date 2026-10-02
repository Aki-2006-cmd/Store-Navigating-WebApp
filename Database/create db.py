import sqlite3

connection = sqlite3.connect('Database/database.db')
cursor=connection.cursor()

cursor.execute('''
    CREATE TABLE IF NOT EXISTS main_table
        (
        ID INTEGER PRIMARY KEY AUTOINCREMENT,
        Product_name TEXT NOT NULL,
        Product_image TEXT,
        X_cordinate INTEGER NOT NULL,
        Y_cordinate INTEGER NOT NULL,
        Z_cordinate INTEGER NOT NULL,
        Shelf_number INTEGER NOT NULL,
        Price REAL,
        Remaining_quantity INTEGER,
        Catagory TEXT,
        Brand TEXT,
        Offer REAL
        )
''')

connection.commit()
connection.close()


'''
def edit():
    import sqlite3

    connection = sqlite3.connect('Database/database.db')
    cursor=connection.cursor()

    cursor.execute(\'\'\'ALTER TABLE main_table ADD COLUMN Brand TEXT\'\'\')
    cursor.execute(\'''ALTER TABLE main_table ADD COLUMN Offer REAL\''')

    connection.commit()
    connection.close()

    return
'''