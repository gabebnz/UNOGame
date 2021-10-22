package assignment2;

public class Game {
    public int choice;
    public static Database db = new Database();

    public static void main(String[] args) {
        Game game = new Game();
        game.mainMenu();
    }
    
    @SuppressWarnings("empty-statement")
    private void mainMenu(){
        while(choice !=3){
            Menu menu = new Menu(this);
            menu.showMenu();

            while(choice == 0) Thread.onSpinWait(); // waits for value returned from menu

            if(choice ==1 ){
                System.out.println("Starting game");
                startGUIGame();
            }
            if(choice == 2){
                System.out.println("Leaderboard");
                openLeaderboard();
            }
            
            choice = 0;
        }
    }
    
    public void setchoice(int num){
        choice = num;
    }
    
    private void openLeaderboard(){
        db.showLeaderboard();
    }

    private void startGUIGame(){
        GameGUI gui = new GameGUI();
        gui.build();
        gui.createPlayers();
        
        gui.startUno(); // start the game
        gui.endGame(); // hides the game panel when game is finished
    }

}
