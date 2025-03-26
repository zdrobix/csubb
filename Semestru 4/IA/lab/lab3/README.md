# OCR lab assignment

## Posibilitati de imbunatatire a recunoasterii textului

- ### I. Input
  1. Scris mai lizibil
  2. Scris cu un brush mai subtire
  3. Scris de tipar
  4. Scris drept
- ### II. Prelucrare input
  1. Binarizarea imaginilor (alb-negru)
  ```python
  _, image = cv2.threshold(image, 128, 255, cv2.THRESH_BINARY | cv2.THRESH_OTSU)
  ```
    
- ### III. Functii Pytesseract
  ```python
  # Setari implicite
  text = pytesseract.image_to_string(image, lang=language).strip()
  ```
  1. Specificam ca imaginea contine doar un cuvant
  ```python
  text = pytesseract.image_to_string(image, lang=language, config=r'--psm 7').strip()
  ```
  2. Se foloseste cel mai avansat model de recunoastere
  ```python
  text = pytesseract.image_to_string(image, lang=language, config=r'-oem 3').strip()
  ```
  3. Se foloseste functia image_to_data 
  ```python
  # Rezultatul va fi mapat intr-un dictionar
  results = pytesseract.image_to_data(image, lang=language, config=r'--psm 7 --oem 3', output_type=Output.DICT)
  ```