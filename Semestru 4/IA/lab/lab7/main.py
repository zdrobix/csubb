from PIL import Image
import os

def apply_sepia(input_image_path, output_image_path):
    image = Image.open(input_image_path)
    pixels = image.load()

    for i in range(image.width):
        for j in range(image.height):
            r, g, b = image.getpixel((i, j))

            tr = int(0.393 * r + 0.769 * g + 0.189 * b)
            tg = int(0.349 * r + 0.686 * g + 0.168 * b)
            tb = int(0.272 * r + 0.534 * g + 0.131 * b)

            if tr > 255:
                tr = 255

            if tg > 255:
                tg = 255

            if tb > 255:
                tb = 255

            pixels[i, j] = (tr, tg, tb)

    print(image.filename)
    image.save(output_image_path)

def run():
    input_folder = 'Q:/Personal/poze/majorat'
    output_folder_sepia = 'Q:/Personal/poze/ml_datra/sepia'
    output_folder_no_sepia = 'Q:/Personal/poze/ml_datra/wo_sepia'

    os.makedirs(output_folder_sepia, exist_ok=True)
    os.makedirs(output_folder_no_sepia, exist_ok=True)

    for image_name in os.listdir(input_folder):
        if image_name.endswith(('.jpg', '.jpeg', '.png')):
            input_path = os.path.join(input_folder, image_name)

            output_no_sepia_path = os.path.join(output_folder_no_sepia, image_name)
            image = Image.open(input_path)
            image.save(output_no_sepia_path)

            output_sepia_path = os.path.join(output_folder_sepia, image_name)
            apply_sepia(input_path, output_sepia_path)


def rename_files(folder_path, str):
    if not os.path.exists(folder_path):
        return

    for filename in os.listdir(folder_path):
        file_path = os.path.join(folder_path, filename)

        if os.path.isfile(file_path):
            new_filename = str + '_' + filename

            new_file_path = os.path.join(folder_path, new_filename)

            os.rename(file_path, new_file_path)
            print(f"file {filename} has been renamed {new_filename}")


output_folder_sepia = 'Q:/Personal/poze/ml_datra/sepia'
output_folder_no_sepia = 'Q:/Personal/poze/ml_datra/wo_sepia'

rename_files(output_folder_sepia, "sepia")
