import I2C_LCD_driver
from time import *

mylcd = I2C_LCD_driver.lcd()
mylcd.backlight(0)
mylcd.backlight(1)
mylcd.lcd_display_string("Hello World!")
while(True):
    sleep(1)
    mylcd.lcd_clear()
    mylcd.backlight(0)
    sleep(1)
    mylcd.lcd_display_string("Hello World!")
    mylcd.backlight(1)
