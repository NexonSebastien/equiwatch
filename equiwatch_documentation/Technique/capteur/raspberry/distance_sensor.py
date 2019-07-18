#!/usr/bin/python3
import RPi.GPIO as GPIO
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

try:
	GPIO.setmode(GPIO.BOARD)

	PIN_TRIGGER = 7
	PIN_ECHO = 11

	GPIO.setup(PIN_TRIGGER, GPIO.OUT)
	GPIO.setup(PIN_ECHO, GPIO.IN)

	GPIO.output(PIN_TRIGGER, GPIO.LOW)

	#print ("Waiting for se,sor to settle")
        #Waiting for sentor to settle
	time.sleep(2)
        #Calculating distance
	#print ("Calculating distance") 

	GPIO.output(PIN_TRIGGER, GPIO.HIGH)

	time.sleep(0.0001)

	GPIO.output(PIN_TRIGGER, GPIO.LOW)

	while GPIO.input(PIN_ECHO)==0:
		pulse_start_time = time.time()
	while GPIO.input(PIN_ECHO)==1:
		pulse_end_time = time.time()

	pulse_duration = pulse_end_time - pulse_start_time

	distance = round(pulse_duration * 17150, 2)

        #Envoie a firebase
	#Use the application default credentials
	cred = credentials.Certificate('equiwatch.json')
	firebase_admin.initialize_app(cred)
	db = firestore.client()
	
	doc_ref = db.collection('capteurs').document('testRasberry')
	doc_ref.set({
                'id' : "testRasberry",
                'label' : "testRasberry",
                'type' : "Hydraulique",
                'distance' : distance,
                })
	print ("Distance:",distance,"cm")

finally:
	GPIO.cleanup()
