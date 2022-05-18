public class Test08 {

    private static Test08 test08 = new Test08("张三",25);

    private String name;
    private Integer age;

    public Test08(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public static Test08 getTest08(){
        return test08;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Test08{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
