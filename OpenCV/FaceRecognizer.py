import numpy as np
import cv2
import pickle
import os
import sys

face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_alt2.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')

BASE_DIR = os.path.dirname(os.path.abspath(__file__))

#Check if argumnt is passed to script
if len(sys.argv) ==1:
    raise ValueError('No arguments passed')

#Name of image to matching 
imgName = sys.argv[1]

path = os.path.join(BASE_DIR, imgName) 

#we use the recognizer trained with walkImage.py
recognizer = cv2.face.LBPHFaceRecognizer_create()
recognizer.read("trainer.yml")

#dictionary for the labels
labels = {"person_name": 1}
with open("labels.pickle", 'rb') as f:
	og_labels = pickle.load(f)
	labels = {v:k for k,v in og_labels.items()}

frame = cv2.imread(path, 1)

gray  = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY) # for template matching we need grayscale
faces = face_cascade.detectMultiScale(gray, scaleFactor=1.5, minNeighbors=5)
for (x, y, w, h) in faces:
	roi_gray = gray[y:y+h, x:x+w] 
	roi_color = frame[y:y+h, x:x+w]
	flagEyes = 0

	#looking for eyes
	eyes = eye_cascade.detectMultiScale(roi_gray)
	for (ex,ey,ew,eh) in eyes:
		flagEyes += 1

	#a detected face must have at least 2 eyes
	if flagEyes >= 2:
		id_, theta = recognizer.predict(roi_gray)

		#chosen threshold
		if theta<= 60:
			name = labels[id_]
			print(name)