package core.domain;

import lombok.*;

import java.io.Serializable;
import javax.persistence.Entity;
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Client extends BaseEntity<Long> implements Serializable {
    @NonNull
    private String serialNumber;
    //@Pattern(regexp = "^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$")
    @NonNull
    private String name;
    private int age;

    /*
    public Client() {
        //super.getId();
    }

    public Client(String name, int age, String serial) {
        //super();
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

    public String getSerialNumber() {
        return this.serialNumber;
    }

    public void setSerialNumber(String new_serial) {
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
    }*/
}
