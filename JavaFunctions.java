package Game;
import java.util.Random;
public class JavaFunctions{
    
    //Random int generator.
    public static int GetRandomInt(int min, int max){
        Random rand = new Random();
            int RandNum = min;
        if(min < max){
            RandNum = rand.ints(min, max + 1).findFirst().getAsInt();
        }
        
        return RandNum;
    }
}