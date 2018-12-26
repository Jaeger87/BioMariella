import numpy as np
import cv2

#Load, show and save image

# cv2.IMREAD_COLOR : Loads a color image. Any transparency of image will be neglected. It is the default flag. 
# cv2.IMREAD_GRAYSCALE : Loads image in grayscale mode
# cv2.IMREAD_UNCHANGED : Loads image as such including alpha channel
# Come secondo argomento rispettivamente (1, 0 e -1)

# Load color images in grayscale
imgGray = cv2.imread('2.jpg',0)
imgDef = cv2.imread('2.jpg', 1)
imgAlpha = cv2.imread('2.jpg', -1)

# Show the images
cv2.imshow('imageG',imgGray)

cv2.imshow('imageD',imgDef)

cv2.imshow('imageA',imgAlpha)

# When 0 is clicked destroy all windows.
    #cv2.waitKey(0)
    #cv2.destroyAllWindows()

# To save an image
    #cv2.imwrite('EmiliaGrey.png',imgGray)

#Better in this way
k = cv2.waitKey(0)
if k == 27:         # wait for ESC key to exit
    cv2.destroyAllWindows()
elif k == ord('s'): # wait for 's' key to save and exit
    cv2.imwrite('EmiliaGray.png',imgGray)
    cv2.destroyAllWindows()

