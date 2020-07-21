package domain;

import java.io.Serializable;

public class Entity<ID> implements Serializable {
    private ID id;

    public ID getId(){
        return this.id;
    }

    public void setId(ID new_id){
        this.id=new_id;
    }

    @Override
    public String toString()
    {
        return "Entity{ "+"ID="+id+" }";
    }

}
