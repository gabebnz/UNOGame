package assignment2;

import javax.swing.*;

public class Card extends JPanel {
    public final String type; // Can be either normal or action card (wild, skip, reverse, PU2, PU4)

    public final String colour; // red, blue, yellow, green, wild
    public final int number; // The number of the card

    public Card(String color, int number){ // constructor for normal cards
        this.type = "normal";
        this.number = number;
        this.colour = color;

    }

    public Card(String color, String action){ // constructor for action cards
        this.type = action;
        this.colour = color;
        this.number = -1;
    }

    @Override
    public String toString() {
        if(type.equals("wild")){
            return "[ wild ]";
        }
        else if(type.equals("skip")){
            String returning = "[ ";
            returning += "skip ]";
            return returning;
        }
        else if(type.equals("reverse")){
            String returning = "[ ";
            returning += "reverse ]";
            return returning;
        }
        else if(type.equals("PU2")){
            String returning = "[ ";
            returning += "+2 ]";
            return returning;
        }
        else if(type.equals("PU4")){
            return "[ +4 ]";
        }
        else if(type.equals("normal")){
            String returning = "[ ";
            returning += number + " ]";
            return returning;
        }

        return "Card Print Error (Card class)";
    }






}
