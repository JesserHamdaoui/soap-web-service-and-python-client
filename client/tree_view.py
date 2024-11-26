import tkinter as tk
from tkinter import ttk, messagebox
from tkinter import Toplevel
from soap_utils import (
    create_user, update_user, delete_user, get_all_users,
    create_book, update_book, delete_book, get_all_books,
    assign_book_to_user, get_books_by_user
)

root = tk.Tk()
root.title("SOAP Service Test")

tree = ttk.Treeview(root, columns=("ID", "Name", "Email"), show="headings")
tree.heading("ID", text="ID")
tree.heading("Name", text="Name")
tree.heading("Email", text="Email")
tree.pack(fill=tk.BOTH, expand=True)

def on_right_click(event):
    selected_item = tree.selection()
    if selected_item:
        user_id = tree.item(selected_item[0], "values")[0]
        menu = tk.Menu(root, tearoff=0)
        menu.add_command(label="Show Books", command=lambda: show_books(user_id))
        menu.add_command(label="Edit User", command=lambda: edit_user(user_id))
        menu.add_command(label="Delete User", command=lambda: delete_user_confirm(user_id))
        menu.post(event.x_root, event.y_root)

tree.bind("<Button-3>", on_right_click)

def show_books(user_id):
    window = Toplevel(root)
    window.title(f"Books of User {user_id}")
    window.geometry("400x300")

    books = get_books_by_user(int(user_id))
    book_tree = ttk.Treeview(window, columns=("ID", "Title", "Author"), show="headings")
    book_tree.heading("ID", text="ID")
    book_tree.heading("Title", text="Title")
    book_tree.heading("Author", text="Author")
    book_tree.pack(fill=tk.BOTH, expand=True)

    if isinstance(books, list):
        for book in books:
            book_tree.insert("", "end", values=(book.id, book.title, book.author))

def edit_user(user_id):
    window = Toplevel(root)
    window.title("Edit User")
    window.geometry("300x250")

    user = list(filter(lambda x: x.id == int(user_id), get_all_users()))[0]
    if user:
        tk.Label(window, text="Name:").pack()
        name_entry = tk.Entry(window)
        name_entry.insert(0, user.name)
        name_entry.pack()

        test_label = tk.Label(window, text="Email:")
        test_label.pack()
        email_entry = tk.Entry(window)


        tk.Label(window, text="Email:").pack()
        email_entry = tk.Entry(window)
        email_entry.insert(0, user.email)
        email_entry.pack()

        def save_user():
            name = name_entry.get()
            email = email_entry.get()
            response = update_user(user_id, name, email, list(filter(lambda x: x.id == int(user_id), get_all_users()))[0].password)
            tk.Label(window, text=f"Response: {response}").pack()
            load_users()
            window.destroy()

        tk.Button(window, text="Save Changes", command=save_user).pack()

def delete_user_confirm(user_id):
    response = messagebox.askyesno("Delete User", "Are you sure you want to delete this user?")
    if response:
        delete_user(user_id)
        load_users()

def load_users():
    users = get_all_users()
    for item in tree.get_children():
        tree.delete(item)
    if isinstance(users, list):
        for user in users:
            tree.insert("", "end", values=(user.id, user.name, user.email))

def add_user_window():
    window = Toplevel(root)
    window.title("Add User")
    window.geometry("300x250")

    tk.Label(window, text="Name:").pack()
    name_entry = tk.Entry(window)
    name_entry.pack()

    tk.Label(window, text="Email:").pack()
    email_entry = tk.Entry(window)
    email_entry.pack()

    tk.Label(window, text="Password:").pack()
    password_entry = tk.Entry(window, show="*")
    password_entry.pack()

    def add_user():
        name = name_entry.get()
        email = email_entry.get()
        password = password_entry.get()
        response = create_user(name, email, password)
        tk.Label(window, text=f"Response: {response}").pack()
        name_entry.delete(0, tk.END)
        email_entry.delete(0, tk.END)
        password_entry.delete(0, tk.END)
        load_users()

    tk.Button(window, text="Add User", command=add_user).pack()

def assign_book_window():
    window = Toplevel(root)
    window.title("Assign Book to User")
    window.geometry("300x250")

    tk.Label(window, text="User ID:").pack()
    user_id_entry = tk.Entry(window)
    user_id_entry.pack()

    tk.Label(window, text="Book ID:").pack()
    book_id_entry = tk.Entry(window)
    book_id_entry.pack()

    def assign_book():
        user_id = user_id_entry.get()
        book_id = book_id_entry.get()
        response = assign_book_to_user(int(user_id), int(book_id))
        tk.Label(window, text=f"Response: {response}").pack()
        user_id_entry.delete(0, tk.END)
        book_id_entry.delete(0, tk.END)

    tk.Button(window, text="Assign Book", command=assign_book).pack()

def add_book_window():
    window = Toplevel(root)
    window.title("Add Book")
    window.geometry("300x250")

    tk.Label(window, text="Book Title:").pack()
    book_title_entry = tk.Entry(window)
    book_title_entry.pack()

    tk.Label(window, text="Book Author:").pack()
    book_author_entry = tk.Entry(window)
    book_author_entry.pack()

    tk.Label(window, text="Book Genre:").pack()
    book_genre_entry = tk.Entry(window)
    book_genre_entry.pack()

    def add_book():
        title = book_title_entry.get()
        author = book_author_entry.get()
        genre = book_genre_entry.get()
        response = create_book(title, author, genre)
        tk.Label(window, text=f"Response: {response}").pack()
        book_title_entry.delete(0, tk.END)
        book_author_entry.delete(0, tk.END)
        book_genre_entry.delete(0, tk.END)

    tk.Button(window, text="Add Book", command=add_book).pack()

def show_unassigned_books():
    window = Toplevel(root)
    window.title("Unassigned Books")
    window.geometry("400x300")

    unassigned_books = get_all_books()
    book_tree = ttk.Treeview(window, columns=("ID", "Title", "Author"), show="headings")
    book_tree.heading("ID", text="ID")
    book_tree.heading("Title", text="Title")
    book_tree.heading("Author", text="Author")
    book_tree.pack(fill=tk.BOTH, expand=True)

    if isinstance(unassigned_books, list):
        for book in unassigned_books:
            if book.user is None:
                book_tree.insert("", "end", values=(book.id, book.title, book.author))

button_frame = tk.Frame(root)
button_frame.pack()

add_user_button = tk.Button(button_frame, text="Add User", command=add_user_window)
add_user_button.grid(row=0, column=0, padx=10, pady=10)

add_book_button = tk.Button(button_frame, text="Add Book", command=add_book_window)
add_book_button.grid(row=0, column=1, padx=10, pady=10)

assign_book_button = tk.Button(button_frame, text="Assign Book to User", command=assign_book_window)
assign_book_button.grid(row=0, column=2, padx=10, pady=10)

unassigned_books_button = tk.Button(button_frame, text="Unassigned Books", command=show_unassigned_books)
unassigned_books_button.grid(row=0, column=3, padx=10, pady=10)

load_users_button = tk.Button(root, text="Load Users", command=load_users)
load_users_button.pack(pady=10)

load_users()

root.mainloop()
