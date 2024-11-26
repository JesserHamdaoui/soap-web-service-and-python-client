import zeep

SERVICE_URL = 'http://localhost:8080/soapService?wsdl'

client = zeep.Client(SERVICE_URL)

def create_user(name, email, password):
    try:
        response = client.service.createUser(name=name, email=email, password=password)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def update_user(user_id, name, email, password):
    try:
        response = client.service.updateUser(userId=user_id, name=name, email=email, password=password)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def delete_user(user_id):
    try:
        response = client.service.deleteUser(userId=user_id)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def get_all_users():
    try:
        users = client.service.getAllUsers()
        return users
    except Exception as e:
        return f"Error: {str(e)}"

def create_book(title, author, genre):
    try:
        response = client.service.createBook(title=title, author=author, genre=genre)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def update_book(book_id, title, author, genre):
    try:
        response = client.service.updateBook(bookId=book_id, title=title, author=author, genre=genre)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def delete_book(book_id):
    try:
        response = client.service.deleteBook(bookId=book_id)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def get_all_books():
    try:
        books = client.service.getAllBooks()
        return books
    except Exception as e:
        return f"Error: {str(e)}"

def assign_book_to_user(book_id, user_id):
    try:
        response = client.service.assignBookToUser(bookId=book_id, userId=user_id)
        return response
    except Exception as e:
        return f"Error: {str(e)}"

def get_books_by_user(user_id):
    try:
        books = client.service.getBooksByUser(userId=user_id)
        return books
    except Exception as e:
        return f"Error: {str(e)}"