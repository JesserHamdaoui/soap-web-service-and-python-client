import zeep

# URL of the SOAP service
SERVICE_URL = 'http://localhost:8080/soapService?wsdl'

# Create a client to interact with the SOAP service
client = zeep.Client(SERVICE_URL)

def add_user(id, name, books):
    response = client.service.addUser({'id': id, 'name': name, 'books': books})
    print(f"User added: {response}")

def get_user(id):
    response = client.service.getUser(id)
    print(f"User retrieved: {response}")

def get_all_users():
    response = client.service.getAllUsers()
    print(f"All Users: {response}")

def update_user(id, name, books):
    response = client.service.updateUser({'id': id, 'name': name, 'books': books})
    print(f"User updated: {response}")

def delete_user(id):
    client.service.deleteUser(id)
    print(f"User with id {id} deleted.")

if __name__ == "__main__":
    # Test the SOAP operations
    add_user(2, 'Alice', [{'id': 102, 'title': 'Python for Beginners'}])
    get_user(1)
    get_all_users()
    update_user(2, 'Alice Cooper', [{'id': 103, 'title': 'Advanced Python'}])
    delete_user(2)
