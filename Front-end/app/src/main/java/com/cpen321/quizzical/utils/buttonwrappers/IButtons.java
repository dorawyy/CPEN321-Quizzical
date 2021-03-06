package com.cpen321.quizzical.utils.buttonwrappers;

public interface IButtons {
    /**
     * This interface unifies MathView clickable, image buttons, and text buttons
     * So that we can have all kinds of buttons in a single list and shuffle them around.
     */

    ButtonTypes getButtonType();

    IButtons getButton();

    void myButtonSetBackGroundColor(int color);

    void setButtonId(int id);
}
