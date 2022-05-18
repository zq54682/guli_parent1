import lombok.ToString;

import java.util.Objects;

public class Test05 {
    String id;
    String name;

    public Test05() {
    }
    public Test05(String id, String name) {
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
        return "Test05{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Test05)) return false;
        Test05 test05 = (Test05) o;
        return Objects.equals(getId(), test05.getId()) &&
                Objects.equals(getName(), test05.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }


}
