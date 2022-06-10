import json
import logging
import socket
import sys


class Client:
    socket = None
    HOST = "localhost"
    PORT = 8080

    def __init__(self, host, port):
        self.HOST = host
        self.PORT = port

    def open(self):
        try:
            # SOCK_STREAM is for TCP, secure the packet but maybe slow the transmission
            self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        except OSError as msg:
            print(msg)
            self.socket = None
        try:
            self.socket.connect((self.HOST, self.PORT))
        except OSError as msg:
            print(msg)
            self.socket.close()
            self.socket = None
        if self.socket is None:
            sys.exit(1)

    def send_packet(self, data):
        self.socket.sendall(json.dumps(data).encode('utf-8') + "\n".encode())
        logging.debug("packet sent")

    def stop_connection(self):
        self.socket.close()
