package core.domain;

import lombok.*;
import org.springframework.context.annotation.Bean;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
public class Rental extends BaseEntity<Long> {
    private String rentalSerialNumber;
    private long clientID;
    private long movieID;

    //@Getter
    private boolean returned;

    //public boolean getReturned() { return this.returned;}

    /*public Rental() {
    }

    public Rental(String rsn, long mid, long cid){
        rentalSerialNumber=rsn;
        movieID=mid;
        clientID=cid;
        returned=false;
    }

    public Rental(String rsn, long mid, long cid, boolean ret)
    {
        rentalSerialNumber=rsn;
        movieID=mid;
        clientID=cid;
        returned=ret;
    }

    public String getRentalSerialNumber() {
        return this.rentalSerialNumber;
    }

    public void setRentalSerialNumber(String new_rsn) {
        rentalSerialNumber=new_rsn;
    }

    public long getMovieID() { return this.movieID;}

    public void setMovieID(long movieID1) { this.movieID=movieID1;}

    public boolean getReturned() { return this.returned;}

    public void setReturned(boolean returned1) { this.returned=returned1;}
    @Bean
    public long getClientID() { return this.clientID;}

    public void setClientID(long clientID1) { this.clientID=clientID1;}


    public boolean isReturned() { return this.returned; }

    public void returnMovie() { this.returned=true; }

    @Override
    public boolean equals(Object o)
    {
        if(this==o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }

        Rental ren = (Rental) o;

        if (!this.rentalSerialNumber.equals(ren.rentalSerialNumber)) return false;
        if (this.movieID<0) return false;
        if (this.clientID<0) return false;
        return (this.returned==ren.returned);
    }

    @Override
    public String toString()
    {
        return "Rental{ "+"serial: "+rentalSerialNumber +", movie ID: " + movieID + ", clientID: " + clientID+ ", returned: " + returned + " } "+ super.toString();
    }

    @Override
    public int hashCode()
    {
        int result = rentalSerialNumber.hashCode();
        //result = 31 * result + movieSerialNumber.hashCode();
       // result = 31 * result + movieName.hashCode();
        //result = 31 * result + clientSerialNumber.hashCode();
       // result = 31 * result + clientName.hashCode();
        return result;
    }*/
}
