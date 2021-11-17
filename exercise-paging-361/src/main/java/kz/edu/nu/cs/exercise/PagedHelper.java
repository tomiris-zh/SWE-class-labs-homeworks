package kz.edu.nu.cs.exercise;

import java.util.List;

public class PagedHelper {
    private List<String> list;
    private int next;
    private int prev;
    
    public List<String> getList() {
        return list;
    }
    public void setList(List<String> list) {
        this.list = list;
    }
    public int getNext() {
        return next;
    }
    public void setNext(int next) {
        this.next = next;
    }
    public int getPrev() {
        return prev;
    }
    public void setPrev(int prev) {
        this.prev = prev;
    }
    
}
