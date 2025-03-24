import pytesseract
import cv2
import os

from pytesseract import Output

pytesseract.pytesseract.tesseract_cmd = r"Q:\kit\Tesseract-OCR\tesseract.exe"

image_cursive_folder = './images_cursive_letters'
image_normal_folder = './images_normal_letters'
image_words_thin_ro_folder = './images_words_thin_ro'
image_words_solid_ro_folder = './images_words_solid_ro'
image_words_thin_eng_folder = './images_words_thin_eng'
image_words_solid_eng_folder = './images_words_solid_eng'


def run_folder(folder_name, language):
    for image_filename in os.listdir(folder_name):
        image = cv2.imread(f"{folder_name}/{image_filename}", cv2.IMREAD_GRAYSCALE)

        _, image = cv2.threshold(image, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)

        detected_text1 = pytesseract.image_to_string(image, lang=language).strip()
        detected_text2 = pytesseract.image_to_string(image, lang=language, config=r'--psm 7 --oem 3').strip()
        expected_text = image_filename.split('.')[0]

        print(
            f"Detected1: {detected_text1:<20} | Detected2: {detected_text2:<15} | Should've been: {expected_text:<20}")


def run_folder2(folder_name, language):
    for image_filename in os.listdir(folder_name):
        image = cv2.imread(f"{folder_name}/{image_filename}")
        image = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
        results = pytesseract.image_to_data(image, lang=language, output_type=Output.DICT)
        for i in range(0, len(results["text"])):
            left = results["left"][i]
            top = results["top"][i]
            width = results["width"][i]
            height = results["height"][i]
            detected_text = results["text"][i]
            conf = int(results["conf"][i])

            print("Confidence: {}".format(conf))
            print("Text: {}\n".format(detected_text))

            cv2.rectangle(image, (left, top), (left + width, top + height), (106, 13, 173), 2)
            cv2.putText(image, detected_text, (left, top - 10), cv2.FONT_HERSHEY_SIMPLEX, 2, (255, 0, 0), 2)

        cv2.imshow("Detected Text", image)
        cv2.waitKey(0)

    cv2.destroyAllWindows()


run_folder2(image_cursive_folder, "eng")
run_folder2(image_normal_folder, "eng")
run_folder2(image_words_thin_ro_folder, "ron")
run_folder2(image_words_solid_ro_folder, "ron")
run_folder2(image_words_thin_eng_folder, "eng")
run_folder2(image_words_solid_eng_folder, "eng")
