#!/usr/bin/python3
import os
import glob
import time
import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')

base_dir = '/sys/bus/w1/devices/'
device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

def read_temp_raw():
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()
    return lines

#CELSIUS CALCULATION
def read_temp_c():
    lines = read_temp_raw()
    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_temp_raw()
    equals_pos = lines[1].find('t=')
    if equals_pos != -1:
        temp_string = lines[1][equals_pos+2:]
        temp_c = int(temp_string) / 1000.0
        temp_c = str(round(temp_c, 1))
        return temp_c

var = read_temp_c()

#Envoie a firebase
#Use the application default credentials
cred = credentials.Certificate('equiwatch.json')
firebase_admin.initialize_app(cred)
db = firestore.client()

doc_ref = db.collection('users').document('nolan.gougeon@edu.itescia.fr').collection('capteurs').document('Capteur Thermique')
doc_ref.set({
    'id' : "Capteur Thermique'",
    'label' : "Capteur Thermique'",
    'type' : "Thermique",
    'donnee' : var,
    })
