import time
from soap_utils import *
    
user_response1 = create_user("Alice", "alice@example.com", "password123")
print(user_response1)
user_response2 = create_user("Bob", "bob@example.com", "password456")
print(user_response2)

time.sleep(2)

users = get_all_users()
for user in users:
    print(user.name, user.email)

update_response = update_user(user_id=1, name="Alice Johnson", email="alice.johnson@example.com", password="newpassword123")
print(update_response)

book_response1 = create_book("Java Programming", "John Smith", "Programming")
print(book_response1)
book_response2 = create_book("Data Science with Python", "Jane Doe", "Data Science")
print(book_response2)

time.sleep(2)

books = get_all_books()
for book in books:
    print(book.title, book.author)

assign_response1 = assign_book_to_user(book_id=1, user_id=1)
print(assign_response1)
assign_response2 = assign_book_to_user(book_id=2, user_id=2)
print(assign_response2)

alice_books = get_books_by_user(user_id=1)
for book in alice_books:
    print(book.title)
bob_books = get_books_by_user(user_id=2)
for book in bob_books:
    print(book.title)

time.sleep(2)

delete_book_response1 = delete_book(book_id=1)
print(delete_book_response1)
delete_book_response2 = delete_book(book_id=2)
print(delete_book_response2)

delete_user_response1 = delete_user(user_id=1)
print(delete_user_response1)
delete_user_response2 = delete_user(user_id=2)
print(delete_user_response2)