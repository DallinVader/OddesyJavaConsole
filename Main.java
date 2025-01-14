package Game;

import java.util.Scanner;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    // Variables
    public static Scanner myscan;
    public static int DaysAtSea = 0;

    public static int CurrentCoins = 2837;

    public static int CurrentFood = 0;
    public static int FoodCost = 2;

    public static int Crew = 0;
    public static int CrewCost = 45;

    public static int Ships;
    public static int ShipCapacity = 16;
    public static int ShipCost = 1276;

    // Initilize Scanner and creates players citizen file.
    public static void main(String[] args) {
        myscan = new Scanner(System.in);

        File citizenFile = new File("Citizen.txt");
        try {
            if (citizenFile.createNewFile()) {
                System.out.println("File created " + citizenFile.getName());
            } else {
                System.out.println("File already created");
            }

            try (FileWriter writeFile = new FileWriter(citizenFile)) {
                System.out.println("Name");
                writeFile.write("Name: " + myscan.nextLine() + "\n");
                System.out.println("Age");
                writeFile.write("Age: " + myscan.nextInt() + "\n");
                System.out.println("Home Land (Ithica or Idaho suggested)");
                myscan.nextLine();
                writeFile.write("Home Land: " + myscan.nextLine() + "\n");
                System.out.println("Starting Game");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        MainMenu();
    }

    // Method for restarting the game.
    static void Start() {
        DaysAtSea = 0;
        CurrentFood = 0;
        FoodCost = 2;
        Crew = 0;
        CrewCost = 45;
        ShipCapacity = 16;
        ShipCost = 1276;
        CurrentCoins = 2837;

        MainMenu();
    }

    // Main menu of the game which I also use for small harbor event.
    public static void MainMenu() {
        System.out.println("You have " + CurrentCoins + " coins left.");
        System.out.println("1: Buy Food");
        System.out.println("2: Recruit men");
        System.out.println("3: Buy Ship");
        System.out.println("4: Set Sail");
        int scanNum = myscan.nextInt();
        if (scanNum == 1) {
            BuyFood();
        }
        if (scanNum == 2) {
            RecruitMen();
        }
        if (scanNum == 3) {
            BuyShip();
        }
        if (scanNum == 4) {
            SetSail();
        }
    }

    // Function that allows you to purchase food.
    public static void BuyFood() {
        System.out.println("You have " + CurrentFood + " food left How many pounds would you like to buy? It costs "
                + FoodCost + " per pound");
        int cost = myscan.nextInt();
        CurrentFood += cost;
        CurrentCoins -= (cost * FoodCost);
        MainMenu();
    }

    // Function that allows you to recruet a crew for your ships.
    public static void RecruitMen() {
        System.out.println("You have a crew size of " + Crew + " how many Recrutes would you like to hire? It costs "
                + CrewCost + " coins per crew member");
        int InputNum = myscan.nextInt();
        Crew += InputNum;
        CurrentCoins -= InputNum * CrewCost;
        MainMenu();
    }

    // Function lets you buy ships.
    public static void BuyShip() {
        System.out.println("You currently have " + Ships + " how many would you like to purchase each ship costs "
                + ShipCost + " each Ship has a capacity of " + ShipCapacity);
        int InputNum = myscan.nextInt();
        Ships += InputNum;
        CurrentCoins -= ShipCost * InputNum;
        MainMenu();
    }

    // Checks if you have the rescources to set sail through simple calculations.
    public static void SetSail() {
        if (Crew <= 0 || Ships <= 0 || CurrentCoins < 0 || Crew > (Ships * ShipCapacity)) {
            System.out.println("You need more Crew or Ships or you are in debt");
            MainMenu();
            return;
        } else {
            Sailing();
        }
    }

    // Sets sail calculating starvation, days at sea, plays events, and checks if
    // the game is over.
    public static void Sailing() {
        // Starvation Check.
        CurrentFood -= Crew;
        if (CurrentFood <= 0) {
            Crew--;
        }
        DaysAtSea++;
        // Game over check.
        if (Crew <= 0 || Ships <= 0) {
            System.out.println(Ships + " Ships left, " + CurrentFood + " Food left, " + Crew + " Crew left");
            System.out.println("GAME OVER");
            if (myscan.nextInt() == 1)
                Start();
            return;
        }

        System.out.println("Total days at sea " + DaysAtSea);

        // Random Event or keep sailing.
        if (JavaFunctions.GetRandomInt(0, 10) > 5) {
            EventPicker(JavaFunctions.GetRandomInt(0, 4));
        } else {
            Sailing();
        }
    }

    // Selects an event based on the inputed number.
    public static void EventPicker(int Event) {

        if (Event == 0) {
            CyclopesEvent();
        }

        if (Event == 1) {
            IslandHarborEvent();
        }

        if (Event == 2) {
            SirenEvent();
        }
        if (Event == 3) {
            StormEvent();
        }
        if (Event == 4) {
            EnemyShipsEvent();
        }
    }

    // Cyclopes event.
    static void CyclopesEvent() {
        System.out.println("You see a island in the distance.");
        System.out.println("1: Sail to the island");
        System.out.println("2: Sail around the island");
        if (myscan.nextInt() == 1) {
            if (JavaFunctions.GetRandomInt(0, 10) > 4) {
                int DeadCrew = JavaFunctions.GetRandomInt(0, Crew);
                System.out.println(DeadCrew + " Crew Have Pareshid to a cyclopse");
                if (JavaFunctions.GetRandomInt(0, 15) < Crew) {
                    int FoodFound = JavaFunctions.GetRandomInt(1, Crew) * 97;
                    System.out.println("You killed the cyclopes and found " + FoodFound + " Food");
                    CurrentFood += FoodFound;
                }
                Crew -= DeadCrew;
                System.out.println(Crew + " Crew left");
                int InputNum = myscan.nextInt();
                Sailing();
            } else {
                int MoneyGained = JavaFunctions.GetRandomInt(0, 403);
                System.out.println("You Found " + MoneyGained + " in coins hidden in a old smelly cave");
                CurrentCoins += MoneyGained;
                int InputNum = myscan.nextInt();
                Sailing();
            }
        } else {
            Sailing();
        }
    }

    // Island Harbor event which just retriigers MainMenu() Function.
    static void IslandHarborEvent() {
        if (JavaFunctions.GetRandomInt(0, 10) > 5) {
            System.out.println("You made it to a island with a small harbor");
            MainMenu();
        } else {
            Sailing();
        }
    }

    // Siren Event.
    static void SirenEvent() {
        if (JavaFunctions.GetRandomInt(0, 10) > 5) {
            int DeadCrew = JavaFunctions.GetRandomInt(0, Crew);
            System.out.println(DeadCrew + " Crew Have Pareshid to sirens");
            Crew -= DeadCrew;
            System.out.println(Crew + " Crew left");
            int InputNum = myscan.nextInt();
        }
        Sailing();
    }

    // Storm Event.
    static void StormEvent() {
        if (JavaFunctions.GetRandomInt(0, 10) > 7) {
            int shipslost = JavaFunctions.GetRandomInt(0, Ships);
            System.out.println("You lost " + shipslost + " ships to a storm");
            Ships -= shipslost;
            int InputNum = myscan.nextInt();
        }
        Sailing();
    }

    // Enemy Ships Event.
    static void EnemyShipsEvent() {
        if (JavaFunctions.GetRandomInt(0, 10) > 7) {
            int EnemyShips = JavaFunctions.GetRandomInt(1, 6);
            System.out.println("You have sighted " + EnemyShips
                    + " enemy ships that have a heading twoards troy. What do you do?");
            System.out.println("1: Attack!");
            System.out.println("2: Leave them be");
            int InputNum = myscan.nextInt();
            if (InputNum == 1) {
                if (JavaFunctions.GetRandomInt(0, EnemyShips) < Ships) {
                    int ClaimedShips = JavaFunctions.GetRandomInt(0, EnemyShips);
                    int ClaimedFood = ClaimedShips * JavaFunctions.GetRandomInt(50, 250);
                    Ships += ClaimedShips;
                    CurrentFood += ClaimedFood;
                    System.out.println("You are victorious and have claimed " + ClaimedShips + "Ships and "
                            + ClaimedFood + " Food");
                } else {
                    int DestroyedShips = JavaFunctions.GetRandomInt(0, Ships);
                    Ships -= DestroyedShips;
                    int CrewLost = JavaFunctions.GetRandomInt(0, Crew);
                    Crew -= CrewLost;
                    System.out.println("You have been defeated and have lost " + DestroyedShips
                            + " ships and have lost " + CrewLost + " crew");
                }
            }
            int InputNum2 = myscan.nextInt();
        }
        Sailing();
    }
}