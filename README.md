# Traffic Simulator

## Note
This assignment called for window resizing to affect the game so the elements rearrange. However, this would change the overall
game mechanics. For instance, if the user expanded vertically, the loop road function would have to adapt but that would allow
the player to see more and more cars, possibly making it too easy. Therefore, I locked the window size as I feel like that
supports the game mechanics I implemented as of now.

## Instructions for Player
**Objective:**

Guide the traffic by clicking cars to avoid crashes. You gain points for every car that passes an intersection.

**Controls:**

Click a vehicles to select it and it will stop at the next intersection. Use shift while selected to make the vehicles speed up. To restart, hit enter or click restart.

**Widegets:**

The right panel houses the widgets that can change the properties of the vehicles and zoom and pan with the arrows and slider. You can also add vehicles and control the game. The left panel is your status panel which shows your score and which vehicles are in the simulation.

## Program Description
**Classes:** (please make sure all are present to properly compile)

    Game.java
    Controls.java
    Status.java
    Canvas.java
    Model.java
    Arrows.java
    Button.java
    Toggle.java
    Slider.java
    Map.java
    Vehicle.java
    Car.java
    Truck.java
    Table.java
    TableModel.java
    TListener.java

**Objective:**

    The objective of this game/simulation is to allow all the vehicles to pass the intersections without crashing.
    They are created at random speeds at random locations, and the vehicles are random as well. All these factors
    ultimately lead to a simulation where vehicles interact with each other depending on speed and location. Several
    widgets allow for modification of these vehicles and their values, leading to a more interactive simulation. 10
    points are awarded for every intersection a vehicle crosses. 100 points are taken for every vehicle that crashes.
    The game ends when the player has points below 0 (has crashed too many times without regaining points).

**Description:**

    The Canvas class houses the main game with elements drawn in on a game tick which runs every 35 milliseconds.
    Additionally, the Controls class allows for controls on the right and Status allows for status displays on the left.
    These are all included in the main Game JFrame, along with the Model class. Within the Model (and Controls/Status)
    class(es) are many objects that either display an image with some properties and member functions, or extend a Swing
    class from Java like a JButton. In the Model class, there are certain functions run every game tick that constantly
    recalculate positions based on the game mechanics and checking interactions between objects as they move through the
    simulation.

## Special Features
There is a lot of randomness incorporated into this simulation. Vehicles stay in their lane but start in random locations
and change directions randomly as well. Additionally, when cars go off the screen, they are created again in a new
location. This accounts for window resizing, zooming, and panning which alters what the "screen" is.

## How to Compile
Make sure all files listed above are present, there should be 35 image files as well

IN COMMAND PROMPT:

    javac Game.java
    java Game

## Inheritance Hierarchy
    Game -> JFrame
    Controls -> JPanel
    Status -> JPanel
    Canvas -> JPanel
    Button -> JButton
    Slider -> JSlider
    Table -> JTable
    TableModel -> AbstractTableModel
    Map -> ImageIcon
    Vehicle -> ImageIcon
    Car -> Vehicle
    Truck -> Vehicle
    Arrows -> JPanel
    Toggle -> JToggleButton
    TListener
    Model

## Aggregation Hierarchy
    Game
    --> Controls
        --> Start/Stop Button
        --> Restart Button
        --> Add Vehicle Button
        --> Random Colors Button
        --> Deselect Button
        --> Panning Arrows
            --> Images (arrow_image, arrow_image_2)
            --> ImageIcon (arrow_icon)
            --> Rectangles (up, down, left, right)
    --> Status
        --> Score Label
        --> Score "Alert" Label
        --> Instructions Label
        --> How To Play Toggle Button
        --> Scroll Pane
        --> Data Table
            --> Table Model
    --> Model
        --> Vehicle Array
    --> Canvas
        --> Model
        --> Map

## Collaboration Relationships
    Game
    Controls uses Canvas and Model
    Status uses Canvas and Model
    Canvas uses Model
    Model uses Game, Canvas, Controls, and Status
    Arrows uses Canvas
    Button uses Model, Canvas, and Controls
    Toggle uses Status and Canvas
    Slider uses Model, Canvas, and Controls
    Map
    Vehicle uses Model (Car and Truck inherit and use it too)
    Table
    TableModel
    TListener uses Model and Canvas


## Class Secrets
    Each class has its own draw functions that manage all the changes in window/zoom/panning
    Each vehicle is created with a random color within the class in a random direction, and they can either be trucks or cars.
    Additionally, all the x values, y values, increment values, and other properties are encapsulated in each class as this
    allows for simple recalling of data and an organized object oriented approach to this program.
    Each vehicle also has its own images that hold its border image which changes according to the direction
