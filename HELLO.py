import I2C_LCD_driver
from time import *

mylcd = I2C_LCD_driver.lcd()
mylcd.backlight(0)
mylcd.backlight(1)
mylcd.lcd_display_string("Hello World!", 1,1)
while(True):
    sleep(1)
    mylcd.backlight(0)
    sleep(1)
    mylcd.backlight(1)
