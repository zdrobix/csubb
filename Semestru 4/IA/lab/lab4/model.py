import numpy as np
import os
import cv2

from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import accuracy_score

vectorizer = TfidfVectorizer(max_features=1000)


def load_images(folder, image_size=(100, 100)):
    images = []
    labels = []
    for filename in os.listdir(folder):
        img = cv2.imread(os.path.join(folder, filename))
        if img is not None:
            resized = cv2.resize(img, image_size)
            images.append(resized.flatten())
            labels += ['bike' in filename]
    return np.array(images), np.array(labels)


image_data, labels = load_images("./images/bikes")

X_train, X_test, y_train, y_test = train_test_split(image_data, labels, test_size=0.3)

model = LogisticRegression(max_iter=1000)
model.fit(X_train, y_train)

y_predictions = model.predict(X_test)
print("Accuracy:", accuracy_score(y_test, y_predictions))
