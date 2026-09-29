import Python_sources.add_data as add
import Python_sources.delete_records as delete
import Python_sources.View_DB as view

while True:
        print("" \
        "1. View DB \n" \
        "2. Add Data \n" \
        "3. Delete records \n" \
        "4. Exit"
        "")
        N=input("Enter number of the operation : \t")

        if N=="1": view.view_db()
        elif N=="2" : add.add_data()
        elif N=="3": delete.del_records()
        elif N=="4": break
