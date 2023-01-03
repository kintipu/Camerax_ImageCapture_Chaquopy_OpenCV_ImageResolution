import cv2
import numpy as np


def main(imageBytes):
    np_data = np.asarray(imageBytes,np.uint8)
    img = cv2.imdecode(np_data,cv2.IMREAD_UNCHANGED)


    if img.shape[0] > img.shape[1]:
        img = cv2.transpose(img)
       
    #img = cv2.resize(img, (1600, 1200))
    
    return str(str(img.shape[0]) + "_"  + str(img.shape[1])) 
