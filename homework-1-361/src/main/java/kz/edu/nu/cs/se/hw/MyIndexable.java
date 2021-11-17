package kz.edu.nu.cs.se.hw;

public class MyIndexable implements Indexable {
    private String entry;
    private int lineNumber;

    public MyIndexable(String word, int line) {
        entry = word;
        lineNumber = line;
    }

    @Override
    public String getEntry() {
        return entry;
    }

    @Override
    public int getLineNumber() {
        return lineNumber;
    }
}
