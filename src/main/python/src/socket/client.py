import socket

HOST = "localhost"
PORT = 8080

# SOCK_STREAM is for TCP, secure the packet but maybe slow the transmission
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
sock.connect((HOST, PORT))

info = "Hello\n"
sock.sendall(info.encode())
data = sock.recv(1024)
print ("1)", data.decode())

if (data == "received!\n"):
    sock.close()
    print("Socket closed")
