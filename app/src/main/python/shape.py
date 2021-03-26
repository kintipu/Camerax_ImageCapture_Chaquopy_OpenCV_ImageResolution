import cv2
import numpy as np
import base64

def main(imgString):
    decoded_data = base64.b64decode(imgString)
    np_data = np.fromstring(decoded_data,np.uint8)
    img = cv2.imdecode(np_data,cv2.IMREAD_UNCHANGED)

    if img.shape[0] > img.shape[1]:
        img = cv2.transpose(img)
    
    return str(str(img.shape[0]) + "_"  + str(img.shape[1])) 