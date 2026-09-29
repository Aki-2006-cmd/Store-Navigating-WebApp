import sqlite3

def view_db():
    db=sqlite3.connect("Database/database.db")
    cursor=db.cursor()

    data=list(cursor.execute('''SELECT * from main_table'''))

    db.commit()
    db.close()

    for i in data:
        print(i)
    return