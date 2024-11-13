import java.util.Scanner;
class Main {

    //rules
    public static void getRules (){
        System.out.println("No Pair (Payout: 0) - The lowest hand, containing five separate cards that do not match up to create any of the hands below\nOne Pair (Payout: 1) - Two cards of the same value, for example two queens\nTwo Pairs (Payout: 2) - Two pairs, for example two queens and 2 5’s\nThree of a Kind (Payout: 3) - Three cards of the same value, for example three queens\nStraight (Payout: 4) - Five cards with consecutive values, not necessarily of the same suit, such as 4,5,6,7, and 8. The ace can either precede a 2 or follow a king\nFlush (Payout: 5) - Five cards, not necessarily in order, of the same suit\nFull House (Payout: 6) - Three of a kind and a pair, for example three queens and two 5’s\nFour of a Kind (Payout: 25) - Four cards of the same value, such as four queens\nStraight Flush (Payout: 50) - A straight and a flush: Five cards with consecutive values of the same suit\nRoyal Flush (Payout: 250) - The best possible hand in poker. A 10, jack, queen, king and ace, all of the same suit");
    }

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //user input for rules
        System.out.println("Would you like to read the rules?");
        String rulesPrompt = scan.nextLine();
        if (rulesPrompt.equalsIgnoreCase("yes"))
            getRules();

        //loop
        String again = "yes";
        while (Card.balance > 0 && again.equalsIgnoreCase("yes")){
            int count = 0;

            //make the deck
            Card[] cards = new Card[52];

            int index = 0;
            for (int s = 0; s < 4; s++){
                for (int r = 1; r < 14; r++){
                    cards[index] = new Card(r, s);
                    index++;
                }
            }

            //betting
            System.out.println("\nYour current balance is $" + Card.balance +  ". How much would you like to bet?");
            String betInput = scan.nextLine();
            Scanner betScan = new Scanner(betInput);
            int bet = betScan.nextInt();
            while (bet > Card.balance){
                System.out.println("You don't have that much money! Please input a valid bet: ");
                String betInput1 = scan.nextLine();
                Scanner betScan1 = new Scanner(betInput1);
                bet = betScan1.nextInt();
                betScan1.close();
            }

            //shuffle and print hand
            Card.shuffle(cards, 52);
      /*for (int i = 0; i < 52; i++){
        System.out.println(cards[i]);
      }*/
            Card.printHand(cards);

            //user input time
            System.out.println("\nWould you like to keep your hand?");
            String keep = scan.nextLine();

            if (keep.equalsIgnoreCase("no")){
                System.out.println("\nWhich cards would you like to discard? Please input their position in your hand (ex. 1 3)");
                String discard = scan.nextLine();
                Scanner cardDiscard = new Scanner(discard);

                while (cardDiscard.hasNext()){
                    if (cardDiscard.hasNextInt()){
                        int loc = cardDiscard.nextInt();
                        count++;
                        Card.replace(cards, loc, count);
                    }
                }
                cardDiscard.close();
                Card.printHand(cards);
            }
            Card.score(cards, bet);
            if (Card.balance > 0){
                System.out.println("\nWould you like to play again?");
                again = scan.nextLine();
            }
            else{
                System.out.println("Looks like you're out of money. Better luck next time!");
            }
            betScan.close();
        }
    }
}