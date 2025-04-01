import tensorflow as tf
import tensorflow_hub as hub

import matplotlib

matplotlib.use("TkAgg")
import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from PIL import ImageColor
from PIL import ImageDraw

import datetime
import os

module_handle = "https://tfhub.dev/google/faster_rcnn/openimages_v4/inception_resnet_v2/1"

detector = hub.load(module_handle).signatures['default']
min_score = .1


def display_image(image):
    fig = plt.figure(figsize=(20, 15))
    plt.grid(False)
    plt.imshow(image)
    plt.axis("off")
    plt.show()


def load_image(image_path):
    img = tf.io.read_file(image_path)
    img = tf.image.decode_jpeg(img, channels=3)
    return img


def draw_on_image(image, y1, x1, y2, x2):
    draw = ImageDraw.Draw(image)
    image_width, image_height = image.size
    (left, right, top, bottom) = (x1 * image_width, x2 * image_width,
                                  y1 * image_height, y2 * image_height)
    draw.line([(left, top), (left, bottom), (right, bottom), (right, top),
               (left, top)],
              width=1,
              fill=ImageColor.getrgb("red"))


def draw_boxes(image, detected_data):
    boxes, names, scores = (detected_data["detection_boxes"], detected_data["detection_class_entities"],
                            detected_data["detection_scores"])

    other_objects = []
    time_difference = 0

    for i in range(min(boxes.shape[0], 5)):
        if names[i].decode("utf-8") in ["Bicycle", "bicycle", "Bike", "bike"]:
            if scores[i] > min_score:
                y1, x1, y2, x2 = tuple(boxes[i])
                image_pil = Image.fromarray(np.uint8(image)).convert("RGB")
                print("Score: " + f"{int(100 * scores[i])}%")
                draw_on_image(
                    image_pil,
                    y1, x1, y2, x2)
                np.copyto(image, np.array(image_pil, dtype=image.dtype))
        else:
            other_objects += [f"{names[i].decode('utf-8')}"]
    print("Other objects detected:  " + ", ".join(other_objects))
    return image


def detect(detector_, path):
    img = load_image(path)
    converted = tf.image.convert_image_dtype(img, tf.float32)[tf.newaxis, ...]
    start_time = datetime.datetime.now()
    result = detector_(converted)
    end_time = datetime.datetime.now()
    result = {key: value.numpy() for key, value in result.items()}
    boxed_image = draw_boxes(img.numpy(), result)
    display_image(boxed_image)
    print(f"Time for finding the bike: {end_time - start_time}")

def detect_if_bike(detector_, path):
    img = load_image(path)
    converted = tf.image.convert_image_dtype(img, tf.float32)[tf.newaxis, ...]
    result = detector_(converted)
    result = {key: value.numpy() for key, value in result.items()}
    for class_name in result["detection_class_entities"]:
        if class_name.decode("utf-8").lower() in ["bicycle", "bike"]:
            return True
    return False


os.environ["TF_ENABLE_ONEDNN_OPTS"] = "0"
# print(detect_if_bike(detector, "./images/animals.png"))
# print(detect_if_bike(detector, "./images/bikes/bike1.jpg"))
detect(detector, "./images/bikes/bike05.jpg")

