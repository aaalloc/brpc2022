from endpoints import Controller
from endpoints.interface import BaseServer
import sys
import atexit
import signal, os

class Default(Controller):
    def GET(self):
        return "boom"

    def POST(self, **kwargs):
        return 'hello {}'.format(kwargs['name'])

class Foo(Controller):
    def GET(self):
        return "banzg"

class Exit(Controller):

    def GET(self):
        return os.kill(os.getpid(), signal.SIGTERM)