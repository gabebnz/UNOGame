package assignment2;

import javax.swing.*;
import java.util.ArrayList;

public abstract class Player {
    protected final ArrayList<Card> hand;
    protected JPanel panel;

    public Player(){
        hand = new ArrayList<>();
        pickUp(7); // starting amount
    }

    public JPanel getPanel(){
        return panel;
    }

    abstract void showCards();

    public void pickUp(int count){
        if(count < 1){
            System.out.println("Invalid number of cards to pickup");
            return;
        }

        for(int i = 0; i < count; i++){
            hand.add(Deck.getCard()); // add how ever many cards to card array
        }
    }

    public void gamePickUp(int count){
        // this method thread sleeps so that on pickup there is a pause
        // this is for simulation of time of a bot to pickup the cards
        if(count < 1){
            System.out.println("Invalid number of cards to pickup");
            return;
        }

        if(this instanceof User){
            GameGUI.announcement.setText("You picked up " + count + " cards!");
        }
        else{
            GameGUI.announcement.setText("Bot (ID: "+ this.hashCode() + ") picked up " + count + " cards!");
        }

        for(int i = 0; i < count; i++){
            try{
                Thread.sleep(500);
            }catch (InterruptedException ignored){

            }

            hand.add(Deck.getCard()); // add how ever many cards to card array
            this.showCards();
        }
    }

    public boolean canBePlayed(Card lastCard){
        for(Card card:hand){
            if(cardCheck(card, lastCard)){
                return true;
            }
        }
        return false;
    }

    public boolean checkUno(){
        if(hand.size() == 1){
            if(this instanceof User){
                GameGUI.announcement.setText("You have called UNO!");
            }
            else{
                GameGUI.announcement.setText("Bot (ID: "+ this.hashCode() + ") has called UNO!");
            }
            try{
                Thread.sleep(1500);
            }catch (InterruptedException ignored){

            }
        }
        else if(hand.size() == 0){
            if(this instanceof User){
                GameGUI.announcement.setText("<html>UNO!<br/>You have won the game!</html>");
                String name = JOptionPane.showInputDialog("Enter your name!");
                
                for(Score score: Database.scores){
                    if(score.name.equals(name)){
                        System.out.println("name matches, updating score");
                        score.score++;
                        return true;
                    }
                }
                
                Score ok = new Score(name, 1);
                Database.scores.add(ok); // new user

            }
            else{
                GameGUI.announcement.setText("<html>UNO!<br/>Bot (ID: " +
                        this.hashCode()
                        +") has won the game!</html>");
            }

            try{
                Thread.sleep(3000);
            }catch (InterruptedException ignored){

            }
            return true;
        }

        return false;
    }

    public boolean cardCheck(Card userChoice, Card lastCard){ // check if the card can actually be played.

        if(userChoice.type.equals("wild") || lastCard.type.equals("wild") || userChoice.type.equals("PU4") || lastCard.type.equals("PU4")){ // Player can always play these types of cards.
            return true;
        }
        else if(userChoice.type.equals("skip") && (lastCard.type.equals("skip") || lastCard.colour.equals(userChoice.colour))){
            return true;
        }
        else if(userChoice.type.equals("PU2") && (lastCard.type.equals("PU2") || lastCard.colour.equals(userChoice.colour))){
            return true;
        }
        else if(userChoice.type.equals("reverse") && (lastCard.type.equals("reverse") || lastCard.colour.equals(userChoice.colour))){
            return true;
        }
        else{
            return (userChoice.type.equals("normal") || lastCard.type.equals("normal")) && (lastCard.colour.equals(userChoice.colour) || lastCard.number == userChoice.number);
        }
    }

    static String getColorChoice(int choice) {
        String color = null;
        if(choice == 0){
            color = "red";
        }
        else if(choice == 1){
            color = "blue";
        }
        else if(choice == 2){
            color = "green";
        }
        else if(choice == 3){
            color = "yellow";
        }


        if (color == null){
            System.out.println("Error choosing color of wildcard");
            return null;
        }
        else{
            return color;
        }
    }

    protected void announceCard(){
        if(this instanceof User){
            GameGUI.announcement.setText("You placed a card!");
        }
        else{
            GameGUI.announcement.setText("Bot (ID: "+ this.hashCode() + ") placed a card.");
        }

        try{
            Thread.sleep(1000);
        }catch (InterruptedException ignored){

        }
    }

    public abstract String chooseColor();

    public abstract Card activeTurn(Card latestCard);


}
