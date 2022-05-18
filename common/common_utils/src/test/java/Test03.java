import org.junit.Test;

public class Test03 implements Runnable{

    @Test
    public  void function1() {

        Thread thread = new Thread(new Test03());
        thread.start();
        for (int i = 0; i < 10000; i++) {
            System.out.println("主线----------：" + i);
        }

    }

    @Override
    public void run() {
        for (int i = 10000; i > 0; i--) {
            System.out.println("支线：" + i);
        }
    }

}
