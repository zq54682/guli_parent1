import java.util.*;

public class Test04 {

    public static void main(String[] args) {

        Set<String> set = new TreeSet<>();
        set.add("1");
        set.add("20303030");
        set.add("3");
        set.add("4");
        set.add("5");
        set.add("65640");
        set.add("7");
        set.add("8");
        set.add("9");
        set.add("1");

        for (String str:set) {
            System.out.println(str);
        }

        Map<String,String> map = new HashMap<>();
        map.put("1", "一");
        map.put("2", "二");
        map.put("3", "三");
        map.put("4", "四");
        map.put("5", "五");

        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }

    }

}
