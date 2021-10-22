package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class User extends Player implements MouseListener {

    private boolean activeTurn = false;
    private volatile Card chosenCard;

    public User(JPanel panel){
        this.panel = panel;
        //panel.setLayout();
    }

    @Override
    public String chooseColor() {

        Object[] options = {"Red", "Blue", "Green", "Yellow"};

        int choice = JOptionPane.showOptionDialog(panel, "What colour would you like your wildcard to be?",
                "Wildcard color choice",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        return getColorChoice(choice);
    }

    @Override
    public Card activeTurn(Card latestCard) {
        activeTurn = true;
        panel.setBackground(GameGUI.ACTIVE);
        GameGUI.announcement.setText("It's your turn!");

        showCards();

        do{
            // Check if any card actually can be played.
            if(!canBePlayed(latestCard)){ // if no cards can be played
                GameGUI.announcement.setText("You have no playable cards, picking up!");
                gamePickUp(1);

                showCards();
                activeTurn = false;
                panel.setBackground(GameGUI.BACKGROUND);
                return null;
            }

            while (chosenCard == null) Thread.onSpinWait(); // WAIT until user has clicked on a card


            if(cardCheck(chosenCard, latestCard)){
                break;
            }

            try{
                panel.setBackground(GameGUI.RED);
                Thread.sleep(100);
            }catch (InterruptedException ignored){

            }
            panel.setBackground(GameGUI.BACKGROUND);
            chosenCard = null; //reset card choice
        }while(true);


        activeTurn = false;
        Card removed = hand.remove(hand.indexOf(chosenCard));
        chosenCard = null; //reset card choice
        showCards(); // update player card view
        announceCard();
        panel.setBackground(GameGUI.BACKGROUND);
        return removed;
    }

    @Override
    public void showCards(){
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets = new Insets(10, -2, 10, -2); // for padding between cards

        panel.removeAll(); // removes all drawn elements to have a clean area to draw on.
        for(Card card: hand){
            CardElement elem = new CardElement(card);
            elem.addMouseListener(this);
            panel.add(elem, gbc);
            panel.repaint();
            panel.revalidate();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        CardElement cardClicked = (CardElement) e.getSource();
        if(activeTurn && chosenCard == null){
            chosenCard = cardClicked.getCard();
        }
        else{
            System.out.println("WAIT YOUR TURN...");
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
