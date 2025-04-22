package om;

public class Game {
	private Deck deck;
    ArrayList<Card> youcef = new ArrayList<>();
    private int nombredeplayer;
    private 	int Sens; //0 ou 1//
    private 	Card card;
    private int currentindex;
    ArrayList<Player> round = new ArrayList<>();
    Player current ;
    Scanner scanner = new Scanner(System.in);
    // une fonction qui cree les joueur//
    private Player player;
    // Constructeur pour initialiser sens a 1 //
    public Game (ArrayList<Player> g) {
       round = g;
        currentindex=0;
        Sens=1;
    }
    public  Game(int nbplayer){
        nombredeplayer = nbplayer;
    }

    // fonction pour distribue les carte au player //
    public void destributiondecarte (int Nombredeplayer) {
        deck. initialistion();
        current=round.getFirst();
        for(int j=0;j<Nombredeplayer;j++) {
            current.dealHand(7);

        }
    }

    // special effect skip //
    public void skipNextPlayer() {

        if (Sens==1) {
            if ( currentindex != nombredeplayer - 1){
                current = round.get(currentindex + 1);
            }else {
                current = round.getFirst() ;
            }
        }
        else {
            if(currentindex==0) {
                currentindex=nombredeplayer-1;}
        }
    }

    //reverse //
    public void reverseTurnOrder() {

        if (Sens==1) {

            Sens=0;
        }
        else {             Sens=1;
        }
    }
    //  function ajouter des cartes par en player  //
    public void drawCardsForNextPlayer(Player current,int nb_card_draw) {

        boolean b = deck.chekposible_draw(youcef,nb_card_draw);
        if (b=true ) {
            round.get(currentindex).hand = deck.Draw(youcef, nb_card_draw) ;
        }else {
            skipNextPlayer();
        }
    }
    // fonction pour le joueur puis changer la couleur //
    public void changeColor () {
        Card.Color col;
        do {
            System.out.println("entre color : blue, red, green, yellow");
            Scanner sc = new Scanner(System.in);
            col= Card.Color.valueOf(sc.next());
        }while (col == Card.Color.blue|col==Card.Color.red|col==Card.Color.green|col==Card.Color.yellow );  }

    public void setNouvellecard(Card nouvellecard) {
        card=nouvellecard;
    }
    public void nextPlayer () {
        if (Sens==1) {
            if ( currentindex != nombredeplayer - 1){
                current = round.get(currentindex + 1);
            }else {
                current = round.getFirst() ;
            }
        }
        else {
            if(currentindex==0) {
                currentindex=nombredeplayer-1;}
        }
    }
    public Card getCurrentCard () {
        return this.card;
    }
    public void setCurrentCard (Card card ){
        this.card=card;
    }
    public  int get_index(){
        return  currentindex ;
    }
public void setnombredeplayer (int o) {
	this.nombredeplayer=o;
}
}
