package core.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BaseEntity<ID extends Serializable> implements Serializable {
    @Id
    @GeneratedValue
    private ID id;

    /*public ID getId(){
        return id;
    }

    public void setId(ID new_id){
        this.id=new_id;
    }

    @Override
    public String toString()
    {
        return "Entity{ "+"ID="+id+" }";
    }*/

}
