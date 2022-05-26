from flask import Flask, request

app = Flask(__name__)

@app.post("/jj")
def jj():
    content = request.json
    print(type(content).__name__)
    print(content)
    return content

@app.post("/ff")
def ff():
    return str(request.form)

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=8888, debug=True)