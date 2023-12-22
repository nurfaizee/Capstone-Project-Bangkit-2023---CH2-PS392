from flask import Flask, request, jsonify
from flask_cors import CORS  # Tambahkan import ini
import pandas as pd
from sklearn.tree import DecisionTreeClassifier
import joblib

app = Flask(__name__)
CORS(app)  # Tambahkan baris ini untuk mengizinkan CORS

# Load the pre-trained model
model = joblib.load('modelzuama/DeteksiFIx/decision_tree_model.joblib')

@app.route('/')
def welcome():
    return 'Welcome to the model prediction API!'

@app.route('/predict', methods=['POST', 'OPTIONS'])  # Tambahkan 'OPTIONS' di sini
def predict():
    try:
        # Get data from the request
        data = request.get_json()

        # Ensure that the expected features are present in the input data
        expected_features = ['Jenis_Kelamin', 'Tinggi_Badan', 'Berat_Badan', 'Usia']
        if not all(feature in data for feature in expected_features):
            raise ValueError('Missing required features in input data')

        # Convert 'Jenis_Kelamin' to numeric (0 for 'Laki-laki', 1 for 'Perempuan')
        data['Jenis_Kelamin'] = 0 if data['Jenis_Kelamin'].lower() == 'laki-laki' else 1

        # Create a DataFrame from the input data
        input_data = pd.DataFrame([data])

        # Make predictions
        prediction = model.predict(input_data)[0]  # Assuming a single prediction is made

        # Prepare response
        response = {'prediction': prediction}  # Convert prediction to int if necessary

        return jsonify(response)

    except Exception as e:
        return jsonify({'error': str(e)})

if __name__ == '__main__':
    app.run(debug=True)
