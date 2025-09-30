from graphics import *
import math

colors = ["red", "black", "blue", "green", "purple", "white", "yellow", "cyan", "magenta"]

def GenerateCircle(windowWidth: int, windowHeight: int, radius: int, outlineColor: str, fillColor: str):
    circ = Circle(Point(windowWidth/2, windowHeight/2), radius)
    circ.setFill(fillColor)
    circ.setOutline(outlineColor)
    return circ

def GenerateText(windowWidth: int, windowHeight: int, value: str):
    text_x = 0.01 * windowWidth
    text_y = 0.01 * windowHeight
    text = Text(Point(text_x + 65.0, text_y + 20.0), value)
    return text

def input_to_int(value):
    try:
        output = int(value)
        return output
    except ValueError:
        print("Please enter a valid number")
        return False

def get_color(color: str):
    color = color.lower().strip()
    if color in colors:
        return color
    else:
        print("Enter a valid color:")
        print(str(colors))
        return False
    
def main():
    ## GET WINDOW SIZE
    
    ### GET WIDTH
    while True:
        width = input("Please enter the width of the window you want to make: ")
        if input_to_int(width) != False:
            gw_width = input_to_int(width)
            break
    
    ### GET HEIGHT
    while True:
        height = input("Please enter the height of the window you want to make: ")
        if input_to_int(height) != False:
            gw_height = input_to_int(height)
            break
    
    ## GET CIRCLE INFORMATION
    
    ### GET RADIUS
    while True:
        radius = input("Please enter the radius of the circle: ")
        if input_to_int(radius) != False:
            radius = input_to_int(radius)
            break
    
    ## CALCULATE CIRCLE INFO
    circumference = 2*math.pi*radius
    area = math.pi*math.pow(radius,2)
    
    ### GET OUTLINE COLOR
    while True:
        outlineColor = input("Please enter the color you want the outline of the circle to be: ")
        if get_color(outlineColor) != False:
            outlineColor = get_color(outlineColor)
            break
        
    ### GET FILL COLOR
    while True:
        fillColor = input("Please enter the color you want the circle to be filled with: ")
        if get_color(fillColor) != False:
            fillColor = get_color(fillColor)
            break
    
    
    ## GENERATE WINDOW
    win = GraphWin("Circle Graphics", gw_width, gw_height)
    
    ## DRAW CIRCLE
    circ = GenerateCircle(gw_width, gw_height, radius, outlineColor, fillColor)
    circ.draw(win)
    
    text = GenerateText(gw_width, gw_height, "Name: Test\n" + f"Circumference: {circumference:.2f}\n" + f"Area: {area:.2f}")
    text.draw(win)
    text.setSize(10)
    
    ## PRINT INFO
    print("Circle Circumference: " + str(circumference))
    print("Circle Area: " + str(area))
    
    win.getMouse() # Pause to view the window
    win.close()

main()
    
    
    
    