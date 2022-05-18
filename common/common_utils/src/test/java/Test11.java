import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Test11 implements Runnable{

    int number = 10000;

    public static void main(String[] args) {

        Test11 test11 = new Test11();

        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            fixedThreadPool.execute(test11);
        }
        fixedThreadPool.shutdown();
        List<String> str = new ArrayList<>();

    }

    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            synchronized (this){
                number--;
                System.out.println(Thread.currentThread().getName() + " :  " + number);
            }
        }
    }

    @Test
    public void demo1(){
        System.out.println(Math.ceil(-11.3));
        System.out.println(Math.floor(-11.3));
        System.out.println(Math.round(-11.5));
        System.out.println(Math.round(-11.6));
        System.out.println(Math.round(11.5));
        System.out.println(Math.round(11.6));
        StringBuilder sb = new StringBuilder();
        StringBuffer sf = new StringBuffer();
    }

    @Test
    public void demo2(){
        String str1 = new String(new char[]{'a','b','c'});
        String str2 = new String(new char[]{'a','b','c'});
        str1.hashCode();
        new Object().hashCode();
        System.out.println(str1==str2);
        System.out.println(str1.equals(str2));

    }


}
