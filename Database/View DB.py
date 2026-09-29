import sqlite3

db=sqlite3.connect("Database/database.db")
cursor=db.cursor()

data=list(cursor.execute('''SELECT * from main_table'''))

db.commit()
db.close()

print(data)