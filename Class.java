
// Made by Dallin and is a simple horse management game.
import java.util.Random;
import java.util.Scanner;

//Horse Class
class Horse {
    int ID;
    int Age;
    float Speed;
    Horse[] Parents;
    Horse[] Children;

    public Horse(int HorseID, float HorseSpeed, int HorseAge, Horse[] HorseParents, Horse[] HorseChildren) {
        this.Speed = HorseSpeed;
        this.Parents = HorseParents;
        this.Children = HorseChildren;
        this.Age = HorseAge;
        this.ID = HorseID;
    }

}

// Player Class for storing coins and Owned Horses
class Player {
    int Coins;
    Horse[] OwnedHorses;

    public Player(int PlayerStartCoins, Horse[] PlayerOwnedHorses) {
        this.Coins = PlayerStartCoins;
        this.OwnedHorses = PlayerOwnedHorses;
    }
}

public class Class {
    // Players in the game.
    public static Player[] Players;
    // Number of players in the game.
    public static int NumOfPlay = 1;

    // Current selected player.
    public static int P;

    // Scan var for taking input.
    public static Scanner scan;

    public static void main(String[] args) {
        // Initialize scan.
        scan = new Scanner(System.in);

        // Sets how many players.
        System.out.println("How many players?");
        NumOfPlay = scan.nextInt();
        scan.nextLine();

        Players = new Player[NumOfPlay];
        for (int i = 0; i < NumOfPlay; i++) {
            Players[i] = new Player(100 + i, new Horse[0]);
            System.out.println("Player " + (i + 1) + " has " + Players[i].Coins + " coins.");
        }

        // Sets and uses the Current player that is playing.
        while (true) {
            System.out.println("Player " + (P + 1) + " is now playing.");
            PlayerMenu(P);
            P++;

            if (P >= NumOfPlay) {
                P = 0;
            }
        }
    }

    // Main Menu for buying, selling, breeding and ending your turn.
    public static void PlayerMenu(int CurrentPlayer) {
        System.out.println("You have " + Players[CurrentPlayer].Coins + " coins and have "
                + Players[CurrentPlayer].OwnedHorses.length + " Horses.");

        System.out.println("1: Buy Horse");
        System.out.println("2: Look at a Horse");
        System.out.println("3: Breed Horses");
        System.out.println("4: Sell Horse");
        System.out.println("5: Next Player");

        int CurrentInput = scan.nextInt();
        scan.nextLine();

        if (CurrentInput == 1) {
            BuyHorse(CurrentPlayer);
            return;
        }
        if (CurrentInput == 2) {
            LookAtHorse(CurrentPlayer);
            return;
        }
        if (CurrentInput == 3) {
            BreedHorses(CurrentPlayer);
            return;
        }
        if (CurrentInput == 4) {
            SellHorse(CurrentPlayer);
            return;
        }
        // No function so it goes back to the while loop and moves to the next player.
        if (CurrentInput == 5) {
            return;
        }
        PlayerMenu(CurrentPlayer);
    }

    // Adds a horse to the invintory of the player.
    public static void BuyHorse(int CurrentPlayer) {
        // Intilize random var.
        Random rand = new Random();

        // Subtract coins from player.
        Players[CurrentPlayer].Coins -= 50;
        System.out.println("You have bought a horse " + Players[CurrentPlayer].Coins);

        // adds a horse to the array.
        Horse[] NewHorses = new Horse[Players[CurrentPlayer].OwnedHorses.length + 1];
        for (int i = 0; i < Players[CurrentPlayer].OwnedHorses.length; i++) {
            NewHorses[i] = Players[CurrentPlayer].OwnedHorses[i];
        }
        Players[CurrentPlayer].OwnedHorses = NewHorses;
        // Sets a new horse with random values to the players invintory.
        Players[CurrentPlayer].OwnedHorses[Players[CurrentPlayer].OwnedHorses.length - 1] = new Horse(
                (int) rand.nextInt(1000000),
                rand.nextInt(10),
                rand.nextInt(10), new Horse[0], new Horse[0]);

        // Reads horses stats.
        System.out.println("Your Horse has a speed of "
                + Players[CurrentPlayer].OwnedHorses[Players[CurrentPlayer].OwnedHorses.length - 1].Speed);
        PlayerMenu(CurrentPlayer);
    }

    public static void SellHorse(int CurrentPlayer) {
        // If is 0 or less will return to menu.
        if (Players[CurrentPlayer].OwnedHorses.length <= 0) {
            PlayerMenu(CurrentPlayer);
            return;
        }

        // Shows you your avaiable horses.
        System.out.println("Which Horse would you like to sell?");
        System.out.println("0: Exit");
        int Num = 0;
        for (Horse CurrentHorse : Players[CurrentPlayer].OwnedHorses) {
            if (CurrentHorse == null) {
                continue;
            }
            Num++;
            System.out.println(Num + ": " + CurrentHorse.ID);
        }

        // Inputed value.
        int NextInt = scan.nextInt();
        scan.nextLine();

        // Exits if inputed value is 0.
        if (NextInt == 0) {
            PlayerMenu(CurrentPlayer);
            return;
        }

        // Removes the horse dynamicaly from the array of owned horses.
        Horse[] NewOwnedHorses = new Horse[Players[CurrentPlayer].OwnedHorses.length - 1];
        for (int i = 0, i2 = 0; i < Players[CurrentPlayer].OwnedHorses.length; i++) {
            if (i == NextInt - 1) {
                continue;
            }
            NewOwnedHorses[i2] = Players[CurrentPlayer].OwnedHorses[i];
            i2++;
        }
        Players[CurrentPlayer].OwnedHorses = NewOwnedHorses;

        // Adds coins to the player.
        Players[CurrentPlayer].Coins += 35;

        PlayerMenu(CurrentPlayer);

    }

    public static void LookAtHorse(int CurrentPlayer) {

        System.out.println("Which Horse would you like to look at?");
        System.out.println("0: Exit");

        // Shows you your avaiable horses.
        int Num = 0;
        for (Horse CurrentHorse : Players[CurrentPlayer].OwnedHorses) {
            Num++;
            System.out.println(Num + ": " + CurrentHorse.ID);
        }
        // Goes to main menu if you have no horses.
        if (Num == 0) {
            System.out.println("No Horses to look at");
            PlayerMenu(CurrentPlayer);
            return;
        }

        // Player Input.
        int Input = scan.nextInt();
        scan.nextLine();

        if (Input == 0) {
            PlayerMenu(CurrentPlayer);
            return;
        }

        // Grabs and prints selected horses stats.
        Horse HorseHolder = Players[CurrentPlayer].OwnedHorses[(Input - 1)];
        System.out.println("ID: " + HorseHolder.ID + " Age: " + HorseHolder.Age + " Speed: " + HorseHolder.Speed);

        // Prints Selected horses parents.
        if (HorseHolder.Parents.length > 0) {
            System.out
                    .println("Parent 1 ID: " + HorseHolder.Parents[0].ID + " Age:" + HorseHolder.Parents[0].Age
                            + " Speed:"
                            + HorseHolder.Parents[0].Speed);
            System.out
                    .println("Parent 2 ID: " + HorseHolder.Parents[1].ID + " Age:" + HorseHolder.Parents[1].Age
                            + " Speed:"
                            + HorseHolder.Parents[1].Speed);

        } else {
            System.out.println("No Record of horses parents.");
        }

        // Prints Selected horses children.
        if (HorseHolder.Children.length > 0) {
            int H = 0;
            for (Horse HorseChild : HorseHolder.Children) {
                H++;
                System.out.println(H + " Child Horse ID: " + HorseChild.ID + " Horse Speed: " + HorseChild.Speed);
            }

        } else {
            System.out.println("No Record of horses Children.");
        }

        // Restarts from LookAtHorse so they can select another easyer.
        LookAtHorse(CurrentPlayer);
    }

    // Breeds two horses and adds it to the players invintory.
    public static void BreedHorses(int CurrentPlayer) {

        // Checks if you have 2 horses to breed.
        if (Players[CurrentPlayer].OwnedHorses.length < 2) {
            System.out.println("You do not have enough horses.");
            PlayerMenu(CurrentPlayer);
            return;
        }

        System.out.println("Which horses would you like to breed? You have " + Players[CurrentPlayer].OwnedHorses.length
                + " horses");

        // Input for selecting the horse you want to breed.
        int FirstInput = scan.nextInt();
        scan.nextLine();

        System.out.println("Horse " + FirstInput + " selected.");

        // Second input for selecting the 2nd horse you want to breed.
        int SecondInput = scan.nextInt();
        scan.nextLine();

        System.out.println("Horse " + SecondInput + " selected.");

        // Grabs the two selected horses.
        Horse Parent1 = Players[CurrentPlayer].OwnedHorses[FirstInput - 1];
        Horse Parent2 = Players[CurrentPlayer].OwnedHorses[SecondInput - 1];
        // I got redundent with the variables but that can be fixed fairly easy later.
        Horse[] HorseParents = new Horse[2];
        HorseParents[0] = Parent1;
        HorseParents[1] = Parent2;

        Random rand = new Random();

        System.out.println("You have Gained a horse.");

        // Adds a extra slot for adding the new hors to the array.
        Horse[] NewHorses = new Horse[Players[CurrentPlayer].OwnedHorses.length + 1];
        for (int i = 0; i < Players[CurrentPlayer].OwnedHorses.length; i++) {
            NewHorses[i] = Players[CurrentPlayer].OwnedHorses[i];
        }
        Players[CurrentPlayer].OwnedHorses = NewHorses;

        // Creates new horse with stat influence from parents.
        Horse NewHorse = new Horse(
                (int) rand.nextInt(1000000),
                rand.nextInt((int) ((HorseParents[0].Speed + HorseParents[1].Speed) * 0.7f)), 0,
                HorseParents, new Horse[0]);

        Players[CurrentPlayer].OwnedHorses[Players[CurrentPlayer].OwnedHorses.length - 1] = NewHorse;

        // Adds NewHorse to the first parent.
        Horse[] P1TempChildren = Parent1.Children;
        Parent1.Children = new Horse[Parent1.Children.length + 1];
        for (int i = 0; i < P1TempChildren.length; i++) {
            Parent1.Children[i] = P1TempChildren[i];
        }
        Parent1.Children[Parent1.Children.length - 1] = NewHorse;

        // Adds NewHorse to the second parent.
        Horse[] P2TempChildren = Parent2.Children;
        Parent2.Children = new Horse[Parent2.Children.length + 1];
        for (int i = 0; i < P2TempChildren.length; i++) {
            Parent2.Children[i] = P2TempChildren[i];
        }
        Parent2.Children[Parent1.Children.length - 1] = NewHorse;

        // Display horses stats.
        System.out.println("Your Horse has a speed of "
                + NewHorse.Speed + " and is " + NewHorse.Age + " years old.");
        System.out.println("Your Horses parents are " + NewHorse.Parents[0].ID + " and " + NewHorse.Parents[1].ID);
        PlayerMenu(CurrentPlayer);
    }
}