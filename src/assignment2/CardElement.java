package assignment2;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class CardElement extends JPanel {

    private Card card;
    private boolean botCards = false;
    private boolean variation;
    private int degrees;

    private int WIDTH = 110;
    private int HEIGHT = 155;
    private int x = WIDTH/2 - 100/2;
    private int y = HEIGHT/2 - 150/2;

    // Constructor for regular card (usually player)
    public CardElement(Card card){
        this.card = card;
    }

    // Prints a blank card for bots (so you dont see their card)
    public CardElement(boolean botcard){
        this.botCards = botcard;
    }

    // prints blank cards for left and right bots
    public CardElement(boolean botcard, boolean leftright){
        this.botCards = botcard;
        this.variation = true;

        if(leftright){
            degrees = 90;
        }
        else{
            degrees = -90;
        }
    }

    // Constructor for top card placement (has left and right variations)
    // to simulate hand placement
    public CardElement(Card card, boolean variation){
        this.card = card;
        this.variation = variation;

        WIDTH = 150;
        HEIGHT = 180;
        x = WIDTH/2 - 100/2;
        y = HEIGHT/2 - 150/2;

        Random rand = new Random();
        degrees = rand.nextInt(7)-rand.nextInt(7); // chose random rotation amount for card placement
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        setOpaque(false);
        setBackground(new Color(0,0,0,x)); // Transparent JPanel

        if(variation){
            drawRandRect(g);
        }
        else if(botCards){
            drawBotCard(g);
            drawBlankText(g);
            return;
        }
        else{
            drawRect(g);
        }

        drawText(g);
    }

    private void drawBotCard(Graphics g){
        // Bot card
        g.setColor(GameGUI.BACKGROUND_LIGHT);
        g.fillRoundRect(x,y, 100,150, 15, 15);

        // Border Color
        g.setColor(GameGUI.FOREGROUND);

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x,y,100,150,15,15);
    }

    private void drawRandRect(Graphics g){
        Graphics2D g2 = (Graphics2D)g;
        g2.rotate(Math.toRadians(degrees));

        g2.setColor(GameGUI.BACKGROUND_LIGHT);
        g2.fillRoundRect(x,y, 100,150, 15, 15);

        // Border Color
        if(card.colour.equals("r")){
            g2.setColor(GameGUI.RED);
        }
        else if(card.colour.equals("g")){
            g2.setColor(GameGUI.GREEN);
        }
        else if(card.colour.equals("y")){
            g2.setColor(GameGUI.YELLOW);
        }
        else if(card.colour.equals("b")){
            g2.setColor(GameGUI.BLUE);
        }

        if(card.type.equals("wild") || card.type.equals("PU4")){
            g.setColor(GameGUI.FOREGROUND);
        }

        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x,y,100,150,15,15);

    }

    private void drawRect(Graphics g){
        // Main card
        g.setColor(GameGUI.BACKGROUND_LIGHT);
        g.fillRoundRect(x,y, 100,150, 15, 15);

        // Border Color
        if(card.colour.equals("r")){
            g.setColor(GameGUI.RED);
        }
        else if(card.colour.equals("g")){
            g.setColor(GameGUI.GREEN);
        }
        else if(card.colour.equals("y")){
            g.setColor(GameGUI.YELLOW);
        }
        else if(card.colour.equals("b")){
            g.setColor(GameGUI.BLUE);
        }

        if(card.type.equals("wild") || card.type.equals("PU4")){
            g.setColor(GameGUI.FOREGROUND);
        }

        Graphics2D g2 = (Graphics2D)g;
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x,y,100,150,15,15);

    }

    private void drawBlankText(Graphics g){
        //Text
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics fm = g2d.getFontMetrics();
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        int x = (getWidth() - fm.stringWidth("[ UNO ]")) / 2;

        g2d.setColor(GameGUI.FOREGROUND);

        g2d.drawString("[ UNO ]", x, y);
    }

    private void drawText(Graphics g){
        //Text
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setFont(new Font("Arial", Font.BOLD, 15));
        FontMetrics fm = g2d.getFontMetrics();
        int y = ((getHeight() - fm.getHeight()) / 2) + fm.getAscent();
        int x = (getWidth() - fm.stringWidth(card.toString())) / 2;

        g2d.setColor(GameGUI.FOREGROUND);

        g2d.drawString(card.toString(), x, y);
    }

    public Card getCard(){
        return card;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

}

