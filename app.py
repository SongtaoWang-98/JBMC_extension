from flask import Flask, render_template, request
import subprocess, sys

app = Flask(__name__)

@app.route('/index')
def inxdex():
    return render_template('index.html')
    
@app.route('/result', methods=['post'])
def result():
    name = request.files['fileUpload'].filename
    options = request.form.get('options')
    subprocess.call(["python3", "run.py", "sv-benchmarks/java/" + name, options])
    return render_template('result.html')

if __name__ == '__main__':
    app.run(host="0.0.0.0", port=5000)
