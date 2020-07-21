package domain;

import java.io.Serializable;

public class Client extends Entity<Long> implements Serializable {
    private String serialNumber;
    private String name;
    private int age;


    public Client() {
    }

    public Client(String name, int age, String serial) {
        this.name = name;
        this.age = age;
        this.serialNumber=serial;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String new_name) {
        this.name = new_name;
    }

    public String getSerial() {
        return this.serialNumber;
    }

    public void setSerial(String new_serial) {
        this.serialNumber = new_serial;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int new_age) {
        this.age = new_age;
    }

    @Override
    public boolean equals(Object o)
    {
        if(this==o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }

        Client cli = (Client) o;

        if (!this.name.equals(cli.name)) return false;
        if(!this.serialNumber.equals(cli.serialNumber)) return false;
        return (this.age==cli.age);
    }

    @Override
    public String toString()
    {
        return "Client{ "+"serial: "+serialNumber +", name: "+
                name + ", age: "+ age +"} "+ super.toString();
    }

    @Override
    public int hashCode()
    {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + age;
        return result;
    }
}
