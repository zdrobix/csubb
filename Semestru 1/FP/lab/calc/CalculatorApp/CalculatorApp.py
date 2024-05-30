from kivy.app import App
from kivy.uix.boxlayout import BoxLayout
from kivy.uix.button import Button
from kivy.uix.textinput import TextInput
from kivy.core.window import Window


# Define the main layout class for the calculator
class CalculatorLayout(BoxLayout):
    def __init__(self, **kwargs):
        # Initialize the base class with vertical orientation
        super().__init__(orientation="vertical", **kwargs)

        # Create a text input for displaying calculations, set to read-only
        self.display = TextInput(multiline=False, readonly=True, halign="right", font_size=55)

        # Add the display widget to the layout
        self.add_widget(self.display)

        # Define the calculator buttons
        buttons = [
            ["7", "8", "9", "/"],
            ["4", "5", "6", "*"],
            ["1", "2", "3", "-"],
            [".", "0", "C", "+"]
        ]

        # Loop through the button grid to add them to the layout
        for row in buttons:
            h_layout = BoxLayout()
            for label in row:
                # Create a button for each symbol
                button = Button(text=label, pos_hint={"center_x": 0.5, "center_y": 0.5})
                # Bind the button press event to a method
                button.bind(on_press=self.on_button_press)
                h_layout.add_widget(button)
            self.add_widget(h_layout)

        # Create and add the equals button
        equals_button = Button(text="=", pos_hint={"center_x": 0.5, "center_y": 0.5})
        equals_button.bind(on_press=self.on_solution)
        self.add_widget(equals_button)

    # Event handler for button presses
    def on_button_press(self, instance):
        if instance.text == "C":
            # Clear the display if 'C' is pressed
            self.display.text = ""
        else:
            # Append the button's text to the display
            self.display.text += instance.text

    # Event handler for the equals button
    def on_solution(self, instance):
        # Evaluate the expression in the display
        if self.display.text:
            try:
                # Evaluate the expression and show the result
                self.display.text = str(eval(self.display.text))
            except Exception:
                # If an error occurs, show 'Error'
                self.display.text = "Error"


class CalculatorApp(App):
    def build(self):
        return CalculatorLayout()


if __name__ == '__main__':
    Window.size = (310, 580)

    CalculatorApp().run()