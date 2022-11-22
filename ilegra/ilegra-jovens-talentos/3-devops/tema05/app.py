from flask import Flask

import service

app = Flask(__name__)


@app.route('/')
def hi():
    return 'Hello!'


@app.route("/conf/env")
def send_var():
    service.get_all_env_var()


@app.route("/conf/software")
def send_software():
    service.send(service.get_all_running_software())


@app.route("/conf/env/<key>/<value>")
def create_var(key, value):
    return service.create_new_env_var(key, value)


if __name__ == '__main__':
    app.run(host='localhost', port=8080)
