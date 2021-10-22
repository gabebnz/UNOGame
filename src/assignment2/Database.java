
package assignment2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;

public class Database extends JFrame implements ActionListener{
    
    public static ArrayList<Score> scores = new ArrayList<>();
    private boolean viewing = true;
    
    public static Connection connection;
    public String url = "jdbc:derby:leaderboardDB;create=true";
    
    public Database(){
        
        try{
            connection = DriverManager.getConnection(url);
            System.out.println("Database created");
            
            String sql = "CREATE TABLE SCORESDB (name VARCHAR(255), score INTEGER)";
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
            
            
        }catch(SQLException e){
            System.out.println("here");
            System.out.println(e);
        }
        
        
        getdbcontent();
        frameSetup();
    }
    
        private void cleardb(){
        try{
            Statement stmt = connection.createStatement();
            String query = "DELETE FROM scoresDB";
            int deletedRows=stmt.executeUpdate(query);
            if(deletedRows>0){
                 System.out.println("Deleted All Rows In The Table Successfully...");
            }
            else{
                System.out.println("Table already empty."); 
            }
            }catch(SQLException s){
                System.out.println("Deleted All Rows In  Table Error. ");		
                s.printStackTrace();
            }
    }
    
    public static void makeEntry(Score score){
        try{
            Statement stmt = connection.createStatement();
            String sql = "Insert into scoresDB (name, score) values ('" +score.name+ "', " +score.score+ ")";
            int rows =stmt.executeUpdate(sql);
            if(rows>0){
                 System.out.println("good");
            }

            }catch(SQLException s){
                System.out.println("Deleted All Rows In  Table Error. ");		
                s.printStackTrace();
            }
    }
    
    public void updateLeaderboard(){
        cleardb();
        // add all array people back into db
        for(Score score: scores){
            makeEntry(score);
        }
        revalidate();
    }
    
    private void getdbcontent(){
        try{
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM scoresDB");
            
            while(rs.next()){ 
                System.out.println("Adding user to internal array...");
                scores.add(new Score(rs.getString("name"), rs.getInt("score")));
            }
        }catch(SQLException e){
            System.out.println("No users in DB?");
            System.out.println(e);
        }
        
    }
    
    private void frameSetup(){
        // visual
        getContentPane().setBackground(GameGUI.BACKGROUND);
        setSize(500, 500);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Leaderboard!");
        setLocationRelativeTo(null);
    }
    
    public void showLeaderboard(){
        
        updateLeaderboard();
        
        viewing = true;
        
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(GameGUI.BACKGROUND);
        
        for(Score score: scores){
            JLabel label = new JLabel("- " + score.name + " | " + score.score);
            label.setFont(new Font("Arial", Font.BOLD, 15));
            label.setForeground(GameGUI.FOREGROUND);
            label.setAlignmentX(Component.CENTER_ALIGNMENT);
            main.add(label);
        }
        
        JButton exit = new JButton("Back");
        exit.addActionListener(this);
        exit.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.add(exit);
        
        add(main);
        setVisible(true);
        
        while(viewing) Thread.onSpinWait();
        
        setVisible(false);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        viewing = false;
    }
    
    
    
}
