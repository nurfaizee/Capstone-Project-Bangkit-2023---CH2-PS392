from flask import Flask, request, jsonify
from flask_cors import CORS  # Tambahkan import ini
import tensorboard as tf
from tensorflow.keras.models import load_model
from tensorflow.keras.preprocessing import image
import numpy as np
import io

app = Flask(__name__)
CORS(app)  # Tambahkan baris ini untuk mengizinkan CORS

# Function to load the model
def load_model_func():
    # Replace 'path/to/your/model.h5' with the correct path to your model file
    model_path = 'hasil_model_fix_jadi.h5'
    model = load_model(model_path)
    return model

# Route for predicting via API
@app.route('/api/predict', methods=['POST'])
def predict_api():
    # Getting the image from the POST request
    img_data = request.files['image'].read()
    img = image.img_to_array(image.load_img(io.BytesIO(img_data), target_size=(128, 128)))
    img_array = np.expand_dims(img, axis=0)
    img_array /= 255.0

    # Load the model
    model = load_model_func()

    # Making predictions
    predictions = model.predict(img_array)
    predicted_class = np.argmax(predictions[0])

    # Getting class labels from the generator
    class_labels = ['Apple', 'Banana', 'Grape', 'Mango', 'Strawberry'] # Ensure 'train_generator' is defined

    # Displaying the prediction result
    result = {
        'predicted_class': class_labels[predicted_class],
        # 'predicted_probabilities': predictions[0].tolist()
    }

    return jsonify(result)

# ... (rest of your code)

if __name__ == '__main__':
    app.run(debug=True)
