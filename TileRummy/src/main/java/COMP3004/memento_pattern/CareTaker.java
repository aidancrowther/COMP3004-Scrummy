package COMP3004.memento_pattern;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<Memento> mementoList = new ArrayList<Memento>();

    public void add(Memento state){
        mementoList.add(state);
    }

    public Memento get(int index){
        return mementoList.get(index);
    }

    public boolean isEmpty(){
        return mementoList.isEmpty();
    }

    public Memento getLastSave(){
        if(this.mementoList.isEmpty()){
            return null;
        }
        return this.mementoList.get(this.mementoList.size()-1);
    }
}
