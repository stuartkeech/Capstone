import I2C_LCD_driver
from time import *

mylcd = I2C_LCD_driver.lcd()

while True:
    flag=True
    msg = ""
    while flag:
        S = open("mj2p.txt","r")
        msg = S.read()
        S.close()
        flag = msg==""
    
    if msg=="0":
        mylcd.lcd_display_string("Comms Lost")
    elif msg=="1":
        mylcd.lcd_display_string("Weight Limit")
    elif msg=="2":
        mylcd.lcd_display_string("ESTOP")
    elif msg=="3":
        mylcd.lcd_display_string("Ultra Failed")
    elif msg=="4":
        mylcd.lcd_display_string("Infra Failed")
    elif msg=="5":
        mylcd.lcd_display_string("Camera Failed")
    elif msg=="6":
        mylcd.lcd_display_string("EndE Failed")
    elif msg=="7":
        mylcd.lcd_display_string("Drive Failed")
    elif msg=="8":
        mylcd.lcd_display_string("Paused")
    elif msg=="9":
        mylcd.lcd_display_string("Fetching")
    elif msg=="10":
        mylcd.lcd_display_string("Obstacle")
    else:
        mylcd.lcd_display_string("Invalid msg code")
