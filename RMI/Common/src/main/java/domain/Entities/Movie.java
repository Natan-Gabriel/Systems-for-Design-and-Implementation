package domain.Entities;

import java.io.Serializable;

public class Movie extends Entity<Long> implements Serializable {
    private String serialNumber;
    private String name;
    private int year;
    private int rating; //out of 100

    public Movie() {
    }

    public Movie(String name, int year, int rating, String serial) {
        this.name = name;
        this.year = year;
        this.rating = rating;
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

    public int getYear() {
        return this.year;
    }

    public void setYear(int new_year) {
        this.year = new_year;
    }

    public int getRating() {
        return this.rating;
    }

    public void setRating(int new_rating) {
        this.rating = new_rating;
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

        Movie mov = (Movie) o;

        if (!this.name.equals(mov.name)) return false;
        if (this.year!=mov.year) return false;
        if(!this.serialNumber.equals(mov.serialNumber)) return false;
        return (this.rating==mov.rating);
    }

    @Override
    public String toString()
    {
        return "Movie{ "+"serial: "+serialNumber +", name: "+
                name + ", year: "+ year + ", rating: "+ rating + "} "+ super.toString();
    }

    @Override
    public int hashCode()
    {
        int result = serialNumber.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + rating;
        return result;
    }
}
