import _thread
import threading

from endpoints import Controller
import signal, os
import time

class Default(Controller):
    def GET(self):
        return "boom"

    def POST(self, **kwargs):
        return 'hello {}'.format(kwargs['name'])

class Foo(Controller):
    def GET(self):
        return '{ "name":"John", "age":30, "city":"New York"}'

    def POST(self, **kwargs):
        return 'hello {}'.format(kwargs['name'])

class Exit(Controller):

    def suicide_message(pidtokill):
        time.sleep(1)
        os.kill(os.getpid(), signal.SIGTERM)

    def GET(self):
        threading.Thread(target=self.suicide_message).start()
        txt = "server will be close in 1sec ! PID : {0}".format(os.getpid())
        return txt

    def POST(self, **kwargs):
        if kwargs['off'] == 1:
            return os.kill(os.getpid(), signal.SIGTERM)
        return 'hello {}'.format(kwargs['name'])


