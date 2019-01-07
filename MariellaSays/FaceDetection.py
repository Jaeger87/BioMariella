import numpy as np
import cv2
import time
import os
import sys

#Face detection with Haar Cascades (for more info usefull for documentation see 
# https://opencv-python-tutroals.readthedocs.io/en/latest/py_tutorials/py_objdetect/py_face_detection/py_face_detection.html#face-detection)

# OpenCV already contains pre-trained classifiers for face and eyes, so we'll use them

#Load the required classifiers
face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')


BASE_DIR = os.path.dirname(os.path.abspath(__file__))

image_dir = os.path.join(BASE_DIR, "photos")

#Check if argumnt is passed to script
if len(sys.argv) ==1:
    raise ValueError('No arguments passed')

#Name of user to enroll
user = sys.argv[1]

DirName = os.path.join(image_dir, user)

i=0

for root, dirs, files in os.walk(DirName):
    for file in files:
        if file.endswith("png") or file.endswith("jpg"):
            path = os.path.join(root, file)

            img= cv2.imread(path, 1)
            imgGray = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)

            #Find the faces in the image
            faces = face_cascade.detectMultiScale(imgGray, 1.3, 5)

            #For each face found, consider the region of interest (ROI) and look for eyes and mouth 
            for (x,y,w,h) in faces:
                flagEyes = 0

                roi_gray = imgGray[y:y+h, x:x+w]
                roi_color = img[y:y+h, x:x+w]
                roi_copy = roi_color

                #looking for eyes
                eyes = eye_cascade.detectMultiScale(roi_gray)
                for (ex,ey,ew,eh) in eyes:
                    flagEyes += 1

                #A detected face must have at least 2 eyes
                if flagEyes >= 2:
                    i += 1
                    fileName = str(i) + '.jpg'
                    cv2.imwrite(fileName, roi_color)
            os.remove(path)
    
os.rmdir("photos/"+user)