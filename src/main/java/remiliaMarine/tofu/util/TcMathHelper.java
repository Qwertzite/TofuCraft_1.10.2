package remiliaMarine.tofu.util;

import java.util.Random;

public class TcMathHelper {

    /**
     * Returns the greatest integer less than or equal to the double argument
     */
    public static int floor(double value)
    {
        int i = (int)value;
        return value < (double)i ? i - 1 : i;
    }
    
    public static int getInt(Random random, int minimum, int maximum) {
        return minimum >= maximum ? minimum : random.nextInt(maximum - minimum + 1) + minimum;
    }
}
