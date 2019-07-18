import RPi.GPIO as GPIO
import time

#configuration générale
GPIO.setmode(GPIO.BOARD)

#configuration des entrées/sorties
GPIO.setup(7,GPIO.IN)

#test de la broche
while True:
    temp = GPIO.input(7)

    print(temp)

    time.sleep(1)
    
