package om;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
public class NimoPlayerFrame extends JFrame {
	// Declaration 
	private NimoTextField Nbrhuman;
	private NimoTextField Nbrplayer;
	private NimouLabel lblNbrPlayer;
	private NimouLabel lblNbrhuman;
	private NimoButton Np;
	private NimoButton Nh;
	private int NbrHuman;
	String name;
	ArrayList<Player> players ;
public NimoPlayerFrame (Game game) {
	setTitle("Nimo Player Frame");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setSize(300,350);
	setLocationRelativeTo(null);
	setLayout(null);
	getContentPane().setBackground(Color.red);
	// Creation

		lblNbrPlayer=new NimouLabel("Please enter the number of Player");
		lblNbrhuman=new NimouLabel("Please enter the number of Human");
	lblNbrPlayer.setBounds(20, 30, 260, 25);
	Nbrplayer=new NimoTextField();
	Nbrhuman= new NimoTextField();
	lblNbrhuman.setBounds(20, 160, 260, 25);
	
	
	
	Nbrplayer.setBounds(20, 60, 260, 40);
	Nbrhuman.setBounds(20,190,260,40);
	add(lblNbrPlayer);
	  ActionListener lancerJeuListener = e -> {
          String input = Nbrplayer.getText().trim();
          try {
              int numberOfPlayers = Integer.parseInt(input);
              if (numberOfPlayers > 1 && numberOfPlayers <= 4) {
            	  game.setnombredeplayer(numberOfPlayers);
                  // TODO: Implémentez la logique pour initialiser les joueurs
                  System.out.println("Nombre de joueurs saisi : " + numberOfPlayers);
              } else {
                  JOptionPane.showMessageDialog(this, "Please enter a number between 2 and 4");
              }
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(this, "Please enter a number between 2 and 4");
          }
      };
      ActionListener Humanpartie = e -> {
          String input = Nbrplayer.getText().trim();
          try {
              int numberOfhuman = Integer.parseInt(input);
              if (numberOfPlayers > 1 && numberOfPlayers <= 4) {
                  // TODO: Implémentez la logique pour initialiser les joueurs
            	  NbrHuman= numberOfhuman;
                  System.out.println("Nombre de joueurs saisi : " + numberOfPlayers);
              } else {
                  JOptionPane.showMessageDialog(this, "Please enter a number between 1and 4");
              }
          } catch (NumberFormatException ex) {
              JOptionPane.showMessageDialog(this, "Please enter a number between 1 and 4");
          }
      };
// depuis AI
	Np =new NimoButton("Ok",Color.BLACK,Color.white,20,lancerJeuListener);
	Nh =new NimoButton("Ok",Color.BLACK,Color.white,20,Humanpartie);
	
	Np.setBounds(90, 120, 100, 30);
	Nh.setBounds(90, 250, 100, 30);
	add(Nh);
	 add(Nbrplayer);
		add(lblNbrPlayer);
	add(lblNbrhuman);
	add(Nbrhuman);
	add(Np);
	setVisible(true);
	for (int i = 1; i <=NbrHuman ; i++) {
		HumanPlayer human_player = new HumanPlayer("Player "+i);
		players.add(human_player);
	}
	for (int j=	1; j<=Nbrplayer-NbrHuman; j++) {
		ComputerPlayer computerPlayer=new ComputerPlayer("Computerplayer"+j);
		players.add(computerPlayer);
	}
}
public ArrayList<Player> getArrayPlayer(){
	return players;
}
}



