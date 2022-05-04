import socket
import sys

HOST = "localhost"
PORT = 8080

# SOCK_STREAM is for TCP, secure the packet but maybe slow the transmission
s = None
try:
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
except OSError as msg:
    print(msg)
    s = None
try:
    s.connect((HOST, PORT))
except OSError as msg:
    print(msg)
    s.close()
    s = None
if s is None:
    sys.exit(1)
with s:
    while True:
        value = input("Enter your value: ") + "\n"
        s.sendall(value.encode())
        data = s.recv(1024)
        if data == b'closed\r\n':
            print("Server is closed")
            s.close()
            sys.exit(0)
