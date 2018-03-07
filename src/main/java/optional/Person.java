package optional;

public class Person {

    private String age;

    public Person(String age) {
        this.age = age;
    }

    public void setAge(String price) {
        this.age = price;
    }

    public String getAge() {
        return this.age;
    }

    @Override
    public String toString() {
        return "this person is " + getAge();
    }

}
