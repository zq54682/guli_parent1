public class Test07 {

    String id;
    String name;

    public Test07() {
    }
    public Test07(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Test07{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

}
