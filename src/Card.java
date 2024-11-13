import java.util.Random;

public class Card{
    private int rank;
    private int suit;
    public static int balance = 100;

    public Card(int rank, int suit){
        this.rank = rank;
        this.suit = suit;
    }

    //make the cards readable
    public String toString(){
        String[] suit = {"Clubs", "Spades", "Hearts", "Diamonds"};
        String[] rank = {null, "Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        String x = rank[this.rank] + " of " + suit[this.suit];
        return x;
    }

    //print out player hand
    public static void printHand(Card[] cards){
        System.out.println("\nYour Hand:");
        for (int i = 0; i < 5; i++){
            if (i != 4)
                System.out.print(cards[i] + ", ");
            else
                System.out.println(cards[i]);
        }

    }

    //shuffle the deck
    public static void shuffle(Card[] cards, int n){
        Random rand = new Random();
        for (int i = 0; i < n; i++){
            int r = i + rand.nextInt(52 - i);
            Card temp = cards[r];
            cards[r] = cards[i];
            cards[i] = temp;
        }

    }

    //replace cards in hand
    public static void replace(Card[] cards, int loc, int num){
        cards[loc - 1] = cards[num + 5];
    }

    public static boolean isPair(Card[] cards){
        for (int i = 0; i < 5; i++){
            for (int j = i + 1; j < 5; j++){
                if (cards[j].rank == cards[i].rank){
                    i = 5;
                    j = 5;
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isTwoPair(Card[] cards){
        int count = 0;
        for (int i = 0; i < 5; i++){
            for (int j = i + 1; j < 5; j++){
                if (cards[j].rank == cards[i].rank){
                    count++;
                }
            }
        }
        if (count >= 2)
            return true;
        return false;
    }

    public static boolean isThreeKind(Card[] cards){
        for (int i = 0; i < 5; i++){ //0 1 2 3 4
            int j = i + 1; //1 2 3 4 0
            int k = j + 1; //2 3 4 0 1
            int l = k + 1; //3 4 0 1 2
            if (l >= 5)
                l = i - 2;
            if (k >= 5)
                k = i - 3;
            if (j >= 5)
                j = i - 4;
            if ((cards[i].rank == cards[j].rank && cards[i].rank == cards[k].rank) || (cards[i].rank == cards[j].rank && cards[i].rank == cards[l].rank)){
                return true;
            }
        }
        return false;
    }

    public static boolean isStraight(Card[] cards){
        int min = 0;
        for (int i = 0; i < 4; i++){
            for (int j = i + 1; j < 4; j++){
                if (cards[j].rank < cards[i].rank)
                    min = cards[j].rank;
            }
        }
        if (min == 0){
            int ace = 14;
            for (int i = 0; i < 5; i++){
                if (cards[i].rank == (ace - 1)){
                    i = 5;
                    for (int j = 0; j < 5; j++){
                        if (cards[j].rank == (ace - 2)){
                            j = 5;
                            for (int k = 0; k < 5; k++){
                                if (cards[k].rank == (ace - 3)){
                                    for (int l = 0; l < 5; l++){
                                        if (cards[l].rank == (ace - 4)){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        else{
            for (int i = 0; i < 5; i++){
                if (cards[i].rank == (min + 1)){
                    i = 5;
                    for (int j = 0; j < 5; j++){
                        if (cards[j].rank == (min + 2)){
                            j = 5;
                            for (int k = 0; k < 5; k++){
                                if (cards[k].rank == (min + 3)){
                                    for (int l = 0; l < 5; l++){
                                        if(cards[l].rank == (min + 4)){
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isFlush(Card[] cards){
        int count = 0;
        int suit = cards[0].suit;
        for (int i = 0; i < 5; i++){
            if (cards[i].suit == suit)
                count++;
        }
        if (count == 5){
            return true;
        }
        else
            return false;
    }

    public static boolean isFullHouse(Card[] cards){
        int count = 0;
        for (int i = 0; i < 5; i++){
            for (int j = i + 1; j < 5; j++){
                if (cards[j].rank == cards[i].rank){
                    count++;
                }
            }
        }
        if (count >= 4 && isThreeKind(cards)){
            return true;
        }
        else
            return false;
    }

    public static boolean isFourKind(Card[] cards){
        for (int i = 0; i < 5; i++){
            int j = i + 1;
            int k = j + 1;
            int l = k + 1;
            if (l >= 5){
                l = i - 2;
            }
            if (k >= 5){
                k = i - 3;
            }
            if (j >= 5){
                j = i - 4;
            }
            if (cards[i].rank == cards[j].rank && cards[i].rank == cards[k].rank && cards[i].rank == cards[l].rank){
                return true;
            }
        }
        return false;
    }

    public static boolean isStraightFlush(Card[] cards){
        if (isFlush(cards) && isStraight(cards)){
            return true;
        }
        else
            return false;
    }

    public static boolean isRoyalFlush(Card[] cards){
        if (isStraightFlush(cards)){
            for (int i = 0; i < 5; i++){
                if (cards[i].rank == 10){
                    for (int j = 0; j < 5; j++){
                        if (cards[j].rank == 1){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void score(Card[] cards, int bet){
        boolean found = false;
        if (isRoyalFlush(cards)){
            balance += (bet * 250) - bet;
            System.out.println("Wow! You have a royal flush!");
            found = true;
        }
        if (!found){
            if (isStraightFlush(cards)){
                balance += (bet * 50) - bet;
                System.out.println("You have a straight flush!");
                found = true;
            }
        }
        if (!found){
            if (isFourKind(cards)){
                balance += (bet * 25) - bet;
                System.out.println("You have four of a kind!");
                found = true;
            }
        }
        if (!found){
            if (isFullHouse(cards)){
                balance += (bet * 6) - bet;
                System.out.println("You have a full house!");
                found = true;
            }
        }
        if (!found){
            if (isFlush(cards)){
                balance += (bet * 5) - bet;
                System.out.println("You have a flush!");
                found = true;
            }
        }
        if (!found){
            for (int i = 0; i < 5; i++){
                if (isStraight(cards)){
                    i = 5;
                    balance += (bet * 4) - bet;
                    System.out.println("You have a straight!");
                    found = true;
                }
            }
        }
        if (!found){
            if (isThreeKind(cards)){
                balance += (bet * 3) - bet;
                System.out.println("You have three of a kind!");
                found = true;
            }
        }
        if (!found){
            if (isTwoPair(cards)){
                balance += (bet * 2) - bet;
                System.out.println("You have two pairs!");
                found = true;
            }
        }
        if (!found){
            if (isPair(cards)){
                balance += (bet * 1) - bet;
                System.out.println("You have a pair!");
                found = true;
            }
        }
        if (!found){
            balance -= bet;
            System.out.println("You don't have anything.");
        }
        System.out.println("\nYour current balance is: $" + balance);
    }
}