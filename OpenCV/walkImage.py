import os
import cv2 
from PIL import Image
import numpy as np
import pickle

#Trainer for Recognizer

#Useless if we conserv only the ROI and not the entire image
#face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_default.xml')

#We use Local Binary Pattern for recognition
recognizer = cv2.face.LBPHFaceRecognizer_create()

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
#Gallery that contains the templates
image_dir = os.path.join(BASE_DIR, "images")

y_labels = [] #Labels (target)
x_train = [] #Data

#ids for labels
current_id = 0

#dictionary for labels
label_ids= {}

for root, dirs, files in os.walk(image_dir):
    for file in files:
        if file.endswith("png") or file.endswith("jpg"):
            path = os.path.join(root, file)
            label = os.path.basename(root).replace(" ", "-").lower()

            if label in label_ids:
                pass
            else:
                label_ids[label] = current_id
                current_id += 1
            id_ = label_ids[label]

            pil_image = Image.open(path).convert("L") #grayscale
            #size = (550, 550)
            #finalImage = pil_image.resize(size, Image.ANTIALIAS )
            image_array = np.array(pil_image, "uint8")
            
            #we pass directly the roi so is useless to recompute it
            #faces = face_cascade.detectMultiScale(image_array, scaleFactor = 1.5, minNeighbors = 5)
            #for (x,y,w,h) in faces:
                #roi = image_array[y:y+h, x:x+w]
                #x_train.append(roi)
                #y_labels.append(id_)
            
            x_train.append(image_array)
            y_labels.append(id_)

with open("labels.pickle", 'wb') as f:
    pickle.dump(label_ids, f)

#train the recognizer, then save it in a file
recognizer.train(x_train, np.array(y_labels))
recognizer.save("trainer.yml")