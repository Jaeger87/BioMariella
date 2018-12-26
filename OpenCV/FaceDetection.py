import numpy as np
import cv2
import time
import os

#Face detection with Haar Cascades (for more info usefull for documentation see 
# https://opencv-python-tutroals.readthedocs.io/en/latest/py_tutorials/py_objdetect/py_face_detection/py_face_detection.html#face-detection)


# OpenCV already contains a pre-trained classifier for face and eyes, so we'll use it

#Load the required classifiers
face_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_frontalface_default.xml')
eye_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_eye.xml')
smile_cascade = cv2.CascadeClassifier('cascades/data/haarcascade_smile.xml')

#Capture of camera
cap = cv2.VideoCapture(0)
i = 0
flag = False

BASE_DIR = os.path.dirname(os.path.abspath(__file__))
#Gallery that contains the templates
image_dir = os.path.join(BASE_DIR, "images")

UserName = raw_input('What is your user name?')

#directory for the new user
user_dir = image_dir + '/' + UserName

#si crea la nuova cartella per l'utente <- circondare il tutto con try/catch
try:
    os.mkdir(user_dir, 0755)
except:
    print "Name already used"


os.chdir(user_dir)


while(True):
    # Capture frame-by-frame
    ret, frame = cap.read()

    # Our operations on the frame come here
    img = frame
    # Load our video in grayscale mode
    gray = cv2.cvtColor(frame, cv2.COLOR_BGR2GRAY)

    #Find the faces in the image
    faces = face_cascade.detectMultiScale(gray, 1.3, 5)

    #For each face found, consider the region of interest (ROI) and look for eyes and mouth 
    for (x,y,w,h) in faces:
        flagEyes = 0
        flagSmile = 0

        img = cv2.rectangle(img,(x,y),(x+w,y+h),(255,0,0),2)
        roi_gray = gray[y:y+h, x:x+w]
        roi_color = img[y:y+h, x:x+w]
        roi_copy = roi_color

        #looking for eyes
        eyes = eye_cascade.detectMultiScale(roi_gray)
        for (ex,ey,ew,eh) in eyes:
            flagEyes += 1
            #cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)

        #looking for smile <---poco preciso   
        smile = smile_cascade.detectMultiScale(roi_gray)
    	for (ex,ey,ew,eh) in smile:
            flagSmile = flagSmile + 1
            #cv2.rectangle(roi_color,(ex,ey),(ex+ew,ey+eh),(0,255,0),2)
        
        if flagEyes >= 2 & flagSmile >= 1:
            i += 1
            fileName = str(i) + '.jpg'
            cv2.imwrite(fileName, roi_color)
            time.sleep(1)
            if i>=5:
                flag = True
            

    # Display the resulting frame
    cv2.imshow('frame', img)
    if (cv2.waitKey(20) & 0xFF == ord('q') or flag):
        break

# When everything done, release the capture
cap.release()
cv2.destroyAllWindows()