
package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Menu extends JFrame implements ActionListener{
    
    // buttons
    JButton startgame;
    JButton showleaderboard;
    
    // game instance
    Game game;
    
    public Menu(Game game){
        this.game = game;
        frameSetup();
    }
    
    private void frameSetup(){
        // visual
        getContentPane().setBackground(GameGUI.BACKGROUND);
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Welcome to UNO!");
        setLocationRelativeTo(null);
    }
    
    public void showMenu(){
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(GameGUI.BACKGROUND);
        add(panel, BorderLayout.CENTER);
        
        startgame = new JButton("Play UNO!");
        startgame.addActionListener(this);
        
        showleaderboard = new JButton("View Leaderboard");
        showleaderboard.addActionListener(this);
        
        panel.add(startgame);
        panel.add(showleaderboard);
        
        setVisible(true);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == startgame){
            game.setchoice(1);
            dispose();
        }
        if(e.getSource() == showleaderboard){
            game.setchoice(2);
            dispose();
        }
    }
}
