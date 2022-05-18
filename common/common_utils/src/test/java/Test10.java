import org.junit.Test;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.HashSet;

public class Test10 {

    public static void main(String[] args) {
        int[] nums = new int[]{100,12,35,24,5,61,77,8,19};
        int a = 0;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if (nums[i]>nums[j]){
                    a = nums[i];
                    nums[i] = nums[j];
                    nums[j] = a;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
        System.out.println(3|9);

        HashSet<String> set = new HashSet<>();
        set.add("aaa");

        new Thread();

    }

    public String str(String... strs){
        return Arrays.toString(strs);
    }

    @Test
    public void catString(){
        String str = str("111", "222", "333");
        System.out.println(str);
        String a = "1111";
        boolean eq = a.equals("11111");
        System.out.println(eq);
    }



}
