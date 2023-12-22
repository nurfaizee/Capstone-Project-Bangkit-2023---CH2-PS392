# import pandas as pd
# from sklearn.model_selection import train_test_split
# from sklearn.tree import DecisionTreeClassifier
# from sklearn.metrics import accuracy_score, classification_report
# import tensorflow as tf

# # Membuat DataFrame dari data yang diberikan
# data=pd.read_csv('DeteksiFIx/datasetfix2.csv')

# df = pd.DataFrame(data)

# # Mengubah data kategorikal (Jenis_Kelamin) menjadi numerik
# df['Jenis_Kelamin'] = df['Jenis_Kelamin'].map({'Laki-laki': 0, 'Perempuan': 1})
# # Memisahkan fitur dan label
# X = df.drop('Risiko_Stunting', axis=1)
# y = df['Risiko_Stunting']

# # Memisahkan data menjadi subset pelatihan dan pengujian
# X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

# # Membuat dan melatih model Decision Tree
# model = DecisionTreeClassifier(random_state=42)
# model.fit(X_train, y_train)

# # Melakukan prediksi pada subset pengujian
# y_pred = model.predict(X_test)

# # Evaluasi model
# accuracy = accuracy_score(y_test, y_pred)
# classification_rep = classification_report(y_test, y_pred)

# print(f'Accuracy: {accuracy:.2f}')
# print('Classification Report:\n', classification_rep)

import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.tree import DecisionTreeClassifier
from sklearn.metrics import accuracy_score, classification_report
import joblib

def train_and_evaluate_model():
    # Membuat DataFrame dari data yang diberikan
    data = pd.read_csv('datasetfix3.csv')
    df = pd.DataFrame(data)

    # Mengubah data kategorikal (Jenis_Kelamin) menjadi numerik
    df['Jenis_Kelamin'] = df['Jenis_Kelamin'].map({'Laki-laki': 0, 'Perempuan': 1})
    
    # Memisahkan fitur dan label
    X = df.drop('Risiko_Stunting', axis=1)
    y = df['Risiko_Stunting']

    # Memisahkan data menjadi subset pelatihan dan pengujian
    X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)

    # Membuat dan melatih model Decision Tree
    model = DecisionTreeClassifier(random_state=42)
    model.fit(X_train, y_train)

    # Melakukan prediksi pada subset pengujian
    y_pred = model.predict(X_test)

    # Evaluasi model
    accuracy = accuracy_score(y_test, y_pred)
    classification_rep = classification_report(y_test, y_pred)

    print(f'Accuracy: {accuracy:.2f}')
    print('Classification Report:\n', classification_rep)

    # Simpan model menggunakan joblib
    joblib.dump(model, 'decision_tree_modelfix.joblib')

if __name__ == "__main__":
    train_and_evaluate_model()
