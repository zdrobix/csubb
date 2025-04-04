import pytesseract
import cv2
import os
import difflib
import Levenshtein

from pytesseract import Output
from shapely.geometry import box

pytesseract.pytesseract.tesseract_cmd = r"Q:\kit\Tesseract-OCR\tesseract.exe"

image_cursive_folder = './images_cursive_letters'
image_normal_folder = './images_normal_letters'
image_words_thin_ro_folder = './images_words_thin_ro'
image_words_solid_ro_folder = './images_words_solid_ro'
image_words_thin_eng_folder = './images_words_thin_eng'
image_words_solid_eng_folder = './images_words_solid_eng'

def hamming_distance(word1, word2):
    error_count = 0
    for l1, l2 in zip(word1, word2):
        error_count += (l1 != l2)
    return error_count


def jaccard_similarity(word1, word2):
    return len(set(word1).intersection(set(word2))) / len(set(word1).union(set(word2)))


def get_iou(rectangle1, rectangle2):
    inter = rectangle1.intersection(rectangle2).area
    union = rectangle1.union(rectangle2).area
    if union == 0:
        return 0
    return inter / union


def run_folder(folder_name, language):
    for image_filename in os.listdir(folder_name):
        image = cv2.imread(f"{folder_name}/{image_filename}", cv2.IMREAD_GRAYSCALE)

        _, image = cv2.threshold(image, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)

        detected_text1 = pytesseract.image_to_string(image, lang=language).strip()
        detected_text2 = pytesseract.image_to_string(image, lang=language, config=r'--psm 7 --oem 3').strip()
        expected_text = image_filename.split('.')[0]

        print(
            f"Detected1: {detected_text1:<20} | Detected2: {detected_text2:<15} | Actual: {expected_text:<20}")


def run_folder2(folder_name, language):
    for image_filename in os.listdir(folder_name):
        image = cv2.imread(f"{folder_name}/{image_filename}")
        actual_text = image_filename.split('.')[0]
        x1, y1, y2, x2 = 0, 0, 0, 0
        if image_filename in word_coord:
            x1, y1, y2, x2 = word_coord[image_filename]
        results = pytesseract.image_to_data(image, lang=language, config=r'--psm 7 --oem 3', output_type=Output.DICT)
        for i in range(len(results["text"])):
            left = results["left"][i]
            top = results["top"][i]
            width = results["width"][i]
            height = results["height"][i]
            detected_text = results["text"][i]
            conf = int(results["conf"][i])

            if conf == -1:
                continue

            cv2.rectangle(image, (x1, y1), (y2, x2), (0, 255, 0), 2)
            cv2.rectangle(image, (left, top), (left + width, top + height), (106, 13, 173), 2)
            cv2.putText(image, detected_text, (left, top - 10), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 0, 0), 2)

            print(f"Confidence: {conf}")
            print(f"Detected: {detected_text}")
            print(f"Actual: {actual_text}")

            if len(detected_text) == len(actual_text):
                print(f"Hamming distance: {hamming_distance(detected_text, actual_text)}")
            else:
                print(f"Jaccard similarity: {jaccard_similarity(detected_text, actual_text)}")
            print(f"Difference: {', '.join(difflib.ndiff(detected_text, actual_text))}")
            print(f"Levenshtein distance: {Levenshtein.distance(detected_text, actual_text)}")
            print(f"CER: {Levenshtein.distance(detected_text, actual_text) / len(actual_text)}")
            print(f"IoU: {get_iou(box(left, top, left+width, top+ height), box(x1, y1, y2, x2))}\n")

        cv2.imshow("Detected Text", image)
        cv2.waitKey(0)

    cv2.destroyAllWindows()


# run_folder(image_words_thin_ro_folder, "ron")
# run_folder(image_words_solid_ro_folder, "ron")
# run_folder(image_words_thin_eng_folder, "eng")
# run_folder(image_words_solid_eng_folder, "eng")

word_coord = {
    "Blue.jpg": (10, 35, 348, 193),
    "Chauffer.jpg": (24, 36, 309, 225),
    "Drive.jpg": (45, 59, 313, 193),
    "Frequency.jpg": (22, 43, 345, 232)
}


# run_folder2(image_cursive_folder, "eng")
# run_folder2(image_normal_folder, "eng")
# run_folder2(image_words_thin_ro_folder, "ron")
# run_folder2(image_words_solid_ro_folder, "ron")
run_folder2(image_words_thin_eng_folder, "eng")
run_folder2(image_words_solid_eng_folder, "eng")
