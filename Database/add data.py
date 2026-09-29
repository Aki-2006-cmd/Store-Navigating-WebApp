import sqlite3

db=sqlite3.connect('Database/database.db')

cursor=db.cursor()

print("Enter exit for name to stop")

while True:
    name=input("Name : ")
    if name.lower()=="exit": break
    image_url=input("Image URL : ")
    X=int(input("X coordinate : "))
    Y=int(input("Y coordinate : "))
    Z=int(input("Z coordinate : "))
    shelf=int(input("Shelf number : "))
    price=float(input("Price : "))
    remaining=int(input("Remaining quentity : "))
    catagory=input("Catagoty : ").lower()
    brand=input("Brand : ")
    offer=float(input("Offer : "))
    cursor.execute('''INSERT INTO main_table (Product_name, Product_image,X_cordinate,Y_cordinate,Z_cordinate,Shelf_number,Price,Remaining_quantity,Catagory,Brand,Offer)
        VALUES (?,?,?,?,?,?,?,?,?,?,?)''',
        (name,image_url,X,Y,Z,shelf,price,remaining,catagory,brand,offer))

    db.commit()
    print("Data added succefully")

db.close()