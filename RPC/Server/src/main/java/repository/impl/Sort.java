package repository.impl;


import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class Sort {

    private boolean asc = true;
    private List<Pair<String, Boolean>> params= new ArrayList<Pair<String, Boolean>>();

    public Sort (String ...s)
    {
        for (String i: s)
        {
            params.add(new Pair<String, Boolean>(i, asc));
        }
    }

    public Sort (Boolean asc, String ...s)
    {
        this.asc=asc;
        for (String i: s)
        {
            params.add(new Pair<String, Boolean>(i, asc));
        }
    }

    public boolean getAsc()
    {
        return asc;
    }

    public List<Pair<String, Boolean>> getParams()
    {
        return params;
    }

    public Sort and(Sort s)
    {
        params.addAll(s.getParams());
        return this;
    }
}
