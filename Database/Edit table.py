import sqlite3

connection = sqlite3.connect('Database/database.db')
cursor=connection.cursor()

cursor.execute('''ALTER TABLE main_table ADD COLUMN Brand TEXT''')
cursor.execute('''ALTER TABLE main_table ADD COLUMN Offer REAL''')

connection.commit()
connection.close()