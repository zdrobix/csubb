import tensorflow as tf
import tensorflow_hub as hub

import matplotlib

matplotlib.use("TkAgg")
import matplotlib.pyplot as plt
import numpy as np
from PIL import Image
from PIL import ImageColor
from PIL import ImageDraw

from shapely.geometry import box

import datetime
import os

module_handle = "https://tfhub.dev/google/faster_rcnn/openimages_v4/inception_resnet_v2/1"

detector = hub.load(module_handle).signatures['default']
min_score = .1

bike_coords = {
    "./images/bikes/bike1.jpg": [(8, 20, 400, 408)],
    "./images/bikes/bike02.jpg": [(30, 83, 387, 323)],
    "./images/bikes/bike03.jpg": [(76, 144, 198, 388), (167, 151, 342, 394)],
    "./images/bikes/bike04.jpg": [(5, 4, 412, 412)],
    "./images/bikes/bike05.jpg": [(74, 57, 353, 342)],
    "./images/bikes/bike06.jpg": [(76, 144, 198, 388), (167, 151, 342, 394)],
    "./images/bikes/bike07.jpg": [(64, 217, 295, 414)],
    "./images/bikes/bike08.jpg": [(55, 39, 380, 350)],
    "./images/bikes/bike09.jpg": [(18, 14, 376, 387)],
    "./images/bikes/bike10.jpg": [(149, 138, 371, 405)],
}

# 15 minute pentru a localiza bicicletele manual

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


def get_iou(rectangle1, rectangle2):
    inter = rectangle1.intersection(rectangle2).area
    union = rectangle1.union(rectangle2).area
    if union == 0:
        return 0
    return inter / union


def convert_coords(image, y1, x1, y2, x2):
    image_width, image_height = image.size
    return y1 * image_height, x1 * image_width, y2 * image_height, x2 * image_width

def draw_on_image(image, y1, x1, y2, x2, color, convert):
    draw = ImageDraw.Draw(image)
    left, right, top, bottom = x1, x2, y1, y2
    if convert:
        (top, left, bottom, right) = convert_coords(image, y1, x1, y2, x2)
    draw.line([(left, top), (left, bottom), (right, bottom), (right, top),
               (left, top)],
              width=1,
              fill=ImageColor.getrgb(color))


def draw_boxes(image, detected_data):
    boxes, names, scores = (detected_data["detection_boxes"], detected_data["detection_class_entities"],
                            detected_data["detection_scores"])

    other_objects = []
    image_pil = Image.fromarray(np.uint8(image)).convert("RGB")
    actual_bike_coords = []

    for i in range(min(boxes.shape[0], 5)):
        if names[i].decode("utf-8") in ["Bicycle", "bicycle", "Bike", "bike"]:
            if scores[i] > min_score:
                y1, x1, y2, x2 = tuple(boxes[i])
                actual_bike_coords += [convert_coords(image_pil, y1, x1, y2, x2)]
                print("Score: " + f"{int(100 * scores[i])}%")
                draw_on_image(
                    image_pil,
                    y1, x1, y2, x2, "red", True)
                np.copyto(image, np.array(image_pil, dtype=image.dtype))

        else:
            other_objects += [f"{names[i].decode('utf-8')}"]

    for boxx in bike_coords["./images/bikes/bike1.jpg"]:
        y1, x1, y2, x2 = tuple(boxx)
        draw_on_image(image_pil, y1, x1, y2, x2, "green", False)
        for actual_boxx in actual_bike_coords:
            iou = get_iou(box(*boxx), box(*actual_boxx))
            print(f"Iou: {iou}")
        np.copyto(image, np.array(image_pil, dtype=image.dtype))
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
    print(f"Model time/Human time: {end_time - start_time} / 00:01:30.00000")


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

detect(detector, "./images/bikes/bike1.jpg")
