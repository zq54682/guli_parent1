public class Test12 {

    private static volatile Test12 test12;

    private Test12(){
    }

    public static Test12 getTest12() {
        if (test12==null){
            synchronized (Test12.class){
                if (test12==null){
                    test12 = new Test12();
                }
            }
        }
        return test12;
    }
}
