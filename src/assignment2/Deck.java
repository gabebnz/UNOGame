package assignment2;


import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private static ArrayList<Card> mainDeck;
    private static final ArrayList<Card> reclaimDeck = new ArrayList<>();

    private Deck(){

    }

    public static void createDeck(){
        generateDeck();
        Collections.shuffle(mainDeck); // shuffle the deck after creation...
    }


    public static Card getCard(){
        if(mainDeck.size() <= 1){
            reclaimToMain();
        }

        return mainDeck.remove(0);
    }

    private static void reclaimToMain(){
        mainDeck = reclaimDeck;

        Collections.shuffle(mainDeck);
    }

    public static void reclaimAdd(Card card){
        reclaimDeck.add(card);
    }

    private static void generateDeck(){ // generate a new deck.
        mainDeck = new ArrayList<>();

        // We must generate 108 cards.
        String[] colours = {"r","g","b","y"};


        for (int i = 0; i < 4; i++){
            mainDeck.add(new Card(colours[i], 0));
            mainDeck.add(new Card(colours[i], "wild"));
            mainDeck.add(new Card(colours[i], "PU4"));

            for(int s = 0; s < 2; s++){
                for(int n=0;n < 9;n++) { // Generate 1-9 cards
                    mainDeck.add(new Card(colours[i], n));
                }
                mainDeck.add(new Card(colours[i], "PU2"));
                mainDeck.add(new Card(colours[i], "skip"));
                mainDeck.add(new Card(colours[i], "reverse"));
            }
        }

        if (!(mainDeck.size() == 108)){
            System.out.println("Something went wrong with card generation");
            System.exit(0); // Quit because there must be enough cards for the game to work properly
        }
    }
}
