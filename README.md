# Camerax_ImageCapture_Chaquopy_OpenCV_ImageResolution

CameraX and Chaquopy show image resolution of Image Capture.
Application made in Android Studio CameraX.
Capture image, send it to Chaquopy(Python) like a string,and Python with OpenCV return image resolution.

//App is very very slow.(improved)


UPDATE: 

I made it much quicker, by few modification:

- Passing image like bytearray to python (instead of string and base64 conversion) - on Malcom Smith suggestion
- Bitmap compress set to JPEG (instead of PNG)
- Python started on Create

Special thanks to people from who I learned a lot:


MALCOM SMITH -Creator of Chaquopy 

M.Van Luke - CameraX code     https://medium.com/swlh/introduction-to-androids-camerax-with-java-ca384c522c5

ProgrammingFever - Chaquopy   https://www.youtube.com/playlist?list=PLeOtHc_su2eXZuiqCH4pBgV6vamBbP88K

Murtaza's Workshop - Opencv Python  https://www.youtube.com/c/MurtazasWorkshopRoboticsandAI

