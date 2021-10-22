package assignment2;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GameGUI extends JFrame {
    // Player Variables
    private ArrayList<Player> players;
    private int currPlayerIndex = 0; // always start with user as first player.
    private boolean playing = true;
    private String direction = "right"; // right (default) or left

    // GUI COLOUR Variables
    public static Color BACKGROUND = new Color(43, 43, 43);
    public static Color ACTIVE = new Color(78, 78, 78, 255);
    public static Color BACKGROUND_LIGHT = new Color(69, 69, 69);
    public static Color FOREGROUND = new Color(247, 247, 247);
    public static Color RED = new Color(252, 88, 88);
    public static Color GREEN = new Color(114, 239, 102);
    public static Color BLUE = new Color(72, 132, 239);
    public static Color YELLOW = new Color(239, 233, 89);


    // Panel Variables
    private JPanel center;
    private JPanel player;
    private JPanel botTop;
    private JPanel botRight;
    private JPanel botLeft;

    //Global Jlabel for events
    public static JLabel announcement;

    public void endGame(){
        setVisible(false);
    }

    public void build(){
        frameSettings();
        createPanels();
    }

    private void createPanels(){
        // Settings for Center Panel
        center = new JPanel(new BorderLayout());
        center.setBackground(BACKGROUND);
        center.setPreferredSize(new Dimension(1000, 500));
        center.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, FOREGROUND));
        add(center, BorderLayout.CENTER);

        // Settings for center panel announcement label
        announcement = new JLabel("Welcome to UNO!",SwingConstants.CENTER);
        announcement.setHorizontalAlignment(JLabel.CENTER);
        announcement.setFont(new Font("Arial", Font.BOLD, 15));
        announcement.setForeground(FOREGROUND);
        announcement.setPreferredSize(new Dimension(1000, 50));
        center.add(announcement);

        // Settings for player Panel
        player = new JPanel();
        player.setBackground(BACKGROUND);
        player.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, FOREGROUND));
        player.setPreferredSize(new Dimension(1000, 200));
        add(player, BorderLayout.SOUTH);

        // Settings for botTop Panel
        botTop = new JPanel();
        botTop.setBackground(BACKGROUND);
        botTop.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, FOREGROUND));
        botTop.setPreferredSize(new Dimension(1000, 170));
        add(botTop, BorderLayout.NORTH);

        // Settings for botRight Panel
        botRight = new JPanel();
        botRight.setBackground(BACKGROUND);
        botRight.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, FOREGROUND));
        botRight.setPreferredSize(new Dimension(200, 1000));
        add(botRight, BorderLayout.EAST);

        // Settings for botLeft Panel
        botLeft = new JPanel();
        botLeft.setBackground(BACKGROUND);
        botLeft.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, FOREGROUND));
        botLeft.setPreferredSize(new Dimension(200, 1000));
        add(botLeft, BorderLayout.WEST);

    }

    private void frameSettings(){

        // visual
        getContentPane().setBackground(BACKGROUND);
        setSize(1300, 900);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("UNO");
        setLocationRelativeTo(null);

        // final
        setVisible(true);
    }

    public void createPlayers(){
        // Create the actual games deck
        Deck.createDeck();

        players = new ArrayList<>();

        // Create main user
        Player user = new User(player);
        players.add(user);

        // Create bots...

        Player bot1 = new Bot(botRight);players.add(bot1);
        Player bot2 = new Bot(botTop);players.add(bot2);
        Player bot3 = new Bot(botLeft);players.add(bot3);


        System.out.println("Players Created.");
    }


    public void startUno(){
        Card topCard = Deck.getCard();
        updateCenter(topCard);

        while (playing){

            Card currentCard = players.get(currPlayerIndex).activeTurn(topCard);

            if(currentCard == null){ // Picked up or error, skip turn
                currPlayerIndex = getNextPlayerIndex(currPlayerIndex);
                continue;
            }

            if(players.get(currPlayerIndex).checkUno()){ // check if player has or is near to uno
                playing = false; // end the game
//                if(players.get(currPlayerIndex) instanceof User){ // if the HUMAN USER wins
//                    leaderboard.submitScore(); // update user score file
//                }
                break;
            }

            Deck.reclaimAdd(topCard); // add current top card to reclaim pile so we dont run out of cards...
            topCard = currentCard;

            updateCenter(topCard);

            if (currentCard.type.equals("reverse")){
                reverseDirection();
            }
            else if (!currentCard.type.equals("normal")){
                cardAction(currentCard,currPlayerIndex, getNextPlayerIndex(currPlayerIndex));
            }

            updateCenter(topCard);
            currPlayerIndex = getNextPlayerIndex(currPlayerIndex);

        }
    }

    private void updateCenter(Card top){
        JPanel helper = new JPanel(new GridBagLayout());
        helper.setBackground(BACKGROUND);
        helper.add(new CardElement(top, true));
        helper.add(new CardElement( true));

        center.removeAll(); // removes all drawn elements to have a clean area to draw on.
        center.add(helper);
        center.add(announcement, BorderLayout.SOUTH);
        center.repaint();
        center.revalidate();
    }

    private void reverseDirection(){
        if (direction.equals("right")) {
            direction = "left";
        }
        else{
            direction = "right";
        }
        announcement.setText("Game Direction reversed!");
    }

    private void cardAction(Card card, int currIndex, int targetIndex){ // determines what the action cards do
        if(card.type.equals("wild")){
            players.get(currIndex).chooseColor();
        }
        else if (card.type.equals("skip")){
            whoWasSkipped(targetIndex);
            currPlayerIndex = targetIndex;
        }
        else if (card.type.equals("PU2")){
            players.get(targetIndex).gamePickUp(2);
        }
        else if (card.type.equals("PU4")){
            players.get(targetIndex).gamePickUp(4);
        }
        else{
            System.out.println("Something went wrong choosing a card action function.");
        }
    }

    private void whoWasSkipped(int skipped){
        JPanel panel = players.get(skipped).getPanel();



        // Flash their panel to indicate skipped
        try{
            panel.setBackground(GameGUI.YELLOW);

            if(players.get(skipped) instanceof User){
                announcement.setText("You were skipped!");
            }
            else{
                announcement.setText("Bot (ID: "+ players.get(skipped).hashCode() + ") was skipped!");
            }

            Thread.sleep(1500);
        }catch (InterruptedException ignored){

        }
        panel.setBackground(GameGUI.BACKGROUND);


    }

    private int getNextPlayerIndex(int current){
        int nextPlayerIndex = -1;

        if (direction.equals("right") && current == players.size()-1){
            nextPlayerIndex = 0;
        }
        else if (direction.equals("left") && current == 0){
            nextPlayerIndex = players.size()-1;
        }
        else if (direction.equals("right")){
            nextPlayerIndex = current+1;
        }
        else if (direction.equals("left")){
            nextPlayerIndex = current-1;
        }

        if (nextPlayerIndex == -1){
            System.out.println("Something went wrong getting the next player index.");
        }

        return nextPlayerIndex;
    }
}
