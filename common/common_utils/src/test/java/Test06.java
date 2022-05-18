import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.HashSet;
import java.util.Set;

public class Test06 {

    public static void main(String[] args) {

        Test05 t1 = new Test05("1", "张三");
        Test05 t2 = new Test05("1", "张三");
        System.out.println(t1.equals(t2));

        Test07 t3 = new Test07("1", "张三");
        Test07 t4 = new Test07("1", "张三");
        System.out.println(t3.equals(t4));

    }

    @Test
    public void aaa(){
        Set<Test05> set = new HashSet<>();
        Test05 t3 = new Test05("1", "张三");
        System.out.println(t3.hashCode());
        Test05 t4 = new Test05("1", "张三");
        System.out.println(t4.hashCode());
        set.add(t3);
        set.add(t4);
        for (Test05 t : set) {
            System.out.println("foreach：" + t.hashCode());
        }
    }


}
