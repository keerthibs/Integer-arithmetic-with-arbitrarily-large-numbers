import java.util.Collections;

/** Driver program for LP1, level 1
 *  @author rbk
 *  Edit and replace "XYZ" by the name of your class
 */

public class LP1Driver {
    public static void main(String[] args) {
	
    	
    	//Addition
    	HugeIntegers a = new HugeIntegers("10");
    	HugeIntegers b = new HugeIntegers("10");
    	HugeIntegers c = new HugeIntegers();  	
    	c = c.add(a, b);
    	System.out.println(c);
    	
    	
    	//Subtraction
    	HugeIntegers a1 = new HugeIntegers("-100");
    	HugeIntegers b1 = new HugeIntegers("10");
    	HugeIntegers c1 = new HugeIntegers();  	
    	c1 = c1.sub(a1, b1);
    	System.out.println(c1);
    	
    	//Product
    	HugeIntegers a2 = new HugeIntegers("1000");
    	HugeIntegers b2 = new HugeIntegers("1000");
    	HugeIntegers c2 = new HugeIntegers();
    	c2=c2.product(a2, b2);
    	System.out.println(c2);
   
    	
    	
    	//Power
    	HugeIntegers a3 = new HugeIntegers("7");
    	Long b3 = new Long(2);
    	HugeIntegers c3 = new HugeIntegers();
    	c3=c3.power(a3,b3);
    	System.out.println(c3);
    	
    	
    }
}
