import sqlite3

def del_records():

    db=sqlite3.connect("Database/database.db")
    cursor=db.cursor()

    del_id=input("Enter ids to delete seperated by \",\" or to delete all enetr \"all\" : ")
    if del_id=="all":
        cursor.execute('''DELETE FROM main_table''')
    else:
        del_id=list(map(int, del_id.split(",")))
        for record_id in del_id:
            cursor.execute('''
                DELETE FROM main_table
                WHERE id=?
                ''',(record_id,))

    db.commit()
    db.close()
    return
