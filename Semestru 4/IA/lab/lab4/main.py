import tensorflow as tf
import tensorflow_hub as hub

import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from PIL import ImageColor
from PIL import ImageDraw
from PIL import ImageFont
from PIL import ImageOps


module_handle = "https://tfhub.dev/google/faster_rcnn/openimages_v4/inception_resnet_v2/1"

detector = hub.load(module_handle).signatures['default']
min_score = .1

def display_image(image):
    fig = plt.figure(figsize=(20, 15))
    plt.grid(False)
    plt.imshow(image)

def load_image(image_path):
    img = tf.io.read_file(image_path)
    img = tf.image.decode_jpeg(img, channels=3)
    return img

def draw_boxes(image, detected_data):
    boxes, names, scores = (detected_data["detection_boxes"], detected_data["detection_class_entities"],
                          detected_data["detection_scores"])

    other_objects = ""

    for i, box in boxes:
        if names[i].to_lower() in ["bike", "bicycle"]:
            if scores[i] > min_score:
                y1, x1, y2, x2 = tuple(boxes[i])
                draw = ImageDraw.Draw(Image.fromarray(np.uint8(image)).convert("RGB"))
                width, height = image.size






def detect(detector, path):
    img = load_image(path)
    converted = tf.image.convert_image_dtype(img, tf.float32)[tf.newaxis, ...]
    result = detector(converted)
    result = {key:value.numpy() for key, value in result.items()}
    boxed_image = draw_boxes(img.numpy(), result)



