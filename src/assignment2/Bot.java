package assignment2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Bot extends Player{
    
    public Bot(JPanel panel){
        this.panel = panel;
        showCards();
    }

    @Override
    void showCards() {
        GridBagConstraints gbc=new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // for padding between cards

        panel.removeAll(); // removes all drawn elements to have a clean area to draw on.
        for(Card card: hand){
            CardElement elem = new CardElement(true);
            panel.add(elem);
            panel.repaint();
            panel.revalidate();
        }
    }

    @Override
    public String chooseColor() {
        Random rand = new Random();
        int choice = rand.nextInt(4);

        return getColorChoice(choice);
    }

    @Override
    public Card activeTurn(Card latestCard) {
        panel.setBackground(GameGUI.ACTIVE);
        Random rand = new Random();
        int choice;

        showCards();
        
        fakeWait();

        // make sure that the bots card can be played
        do {
            // Check if any card actually can be played.
            if (!canBePlayed(latestCard)) { // if no cards can be played
                gamePickUp(1);

                fakeWait();
                panel.setBackground(GameGUI.BACKGROUND);
                return null;
            }

            choice = rand.nextInt(hand.size()); // get bot choice of card
        } while (!cardCheck(hand.get(choice), latestCard));


        Card removed = hand.remove(choice);

        showCards();

        announceCard();

        panel.setBackground(GameGUI.BACKGROUND);
        return removed;
    }

    private void fakeWait(){
        // this method simulates a random time for the bot to place a card
        // otherwise it is instant
        Random rand = new Random();
        try{
            Thread.sleep(rand.nextInt(750)+1000);
        }catch (InterruptedException ignored){

        }
    }
}
