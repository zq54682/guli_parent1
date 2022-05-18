import org.junit.Test;

import java.util.ArrayList;
import java.util.Vector;

public class Test09 {

    @Test
    public void demo1(){
        Test08 test08 = Test08.getTest08();
        System.out.println(test08.toString());
        test08.setName("李四");

        System.out.println("-------------------------------------------------");

        Test08 test081 = Test08.getTest08();
        System.out.println(test081.toString());
        test08.equals(test08);
    }

    @Test
    public void demo2(){
        System.out.println(Math.round(-1.5));
        new ArrayList<String>();
        new Vector<String>();
    }


}
