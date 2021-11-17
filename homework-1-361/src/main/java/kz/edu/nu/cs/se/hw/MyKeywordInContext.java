package kz.edu.nu.cs.se.hw;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MyKeywordInContext implements KeywordInContext {

    String name;
    String path;

    private MyIndexable x, y;
    Set<MyIndexable> indexables = new TreeSet<>((x, y) -> {
        int insensitive = String.CASE_INSENSITIVE_ORDER.compare(x.getEntry(), y.getEntry());
        boolean num = (x.getLineNumber() == y.getLineNumber());
        if (insensitive != 0) {
            return insensitive;
        } else if (num) {
            return x.getEntry().compareTo(y.getEntry());
        }
        return 1;
    });

    public MyKeywordInContext(String name, String path) {
        this.name = name;
        this.path = path;
        // TODO Auto-generated constructor stub
    }

    @Override
    public int find(String word) {
        for(MyIndexable f : indexables) {
            if(f.getEntry().toLowerCase().equals(word.toLowerCase())) {
                return f.getLineNumber();
            }
        }
        return -1;
        // TODO Auto-generated method stub
    }

    @Override
    public Indexable get(int i) {
        for(MyIndexable g : indexables) {
            if(g.getLineNumber() == i) {
                return g;
            }
        }
        return null;
        // TODO Auto-generated method stub
    }

    @Override
    public void txt2html() {
        try {
            FileReader fileRead = new FileReader(path);
            BufferedReader bufferedRead = new BufferedReader(fileRead);

            File html = new File(name + ".html");
            FileWriter fileWriter = new FileWriter(html);
            BufferedWriter bWriter = new BufferedWriter(fileWriter);

            bWriter.write("<!DOCTYPE html>" + "\n");
            bWriter.write("<html><head><meta charset=\"UTF-8\"></head><body>" + "\n");
            bWriter.write("<div>" + "\n");

            int line = 1;
            String textString;

            while ((textString = bufferedRead.readLine()) != null) {
                bWriter.write(textString + "<span id=\"line_" + line + "\">&nbsp&nbsp[" + line + "]</span><br>" + "\n");
                line ++;
            }

            bWriter.write("</div></body></html>");

            bufferedRead.close();
            bWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void indexLines() {
        try {
            FileReader fileRead = new FileReader(path);
            BufferedReader bufferedRead = new BufferedReader(fileRead);

            FileReader stopPoints = new FileReader("dayTime.txt");
            BufferedReader stopReader = new BufferedReader(stopPoints);

            Set<String> stopWords = new HashSet<String>();
            String word;
            while ((word = stopReader.readLine()) != null) {
                stopWords.add(word.toLowerCase());
            }
            stopReader.close();
            String textString;
            String[] words = new String[50];
            int line = 1;

            while ((textString = bufferedRead.readLine()) != null) {
                words = textString.replaceAll("[^a-zA-Z0-9 ]", "").split("\\s+");
                for( String s: words ) {
                    if(s.length()==0 || stopWords.contains(s.toLowerCase())) {
                        continue;
                    }
                    indexables.add(new MyIndexable(s, line));
                }
                line ++;
            }
            bufferedRead.close();
            writeIndexToFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
    }

    @Override
    public void writeIndexToFile() {
        try {
            FileReader fileRead = new FileReader(path);
            BufferedReader bufferedRead = new BufferedReader(fileRead);

            ArrayList<String> lines = new ArrayList<>();
            String textString;

            while ((textString = bufferedRead.readLine()) != null) {
                lines.add(textString);
            }
            bufferedRead.close();

            File html = new File("kwic-" + name + ".html");
            FileWriter fileWriter = new FileWriter(html);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write("<!DOCTYPE html>" + "\n");
            bufferedWriter.write("<html><head><meta charset=\"UTF-8\"></head><body><div style=\"text-align:center;line-height:1.6\">" + "\n");

            String line;
            for(MyIndexable w : indexables) {
                line = lines.get(w.getLineNumber() - 1);
                if(line.length() == 0) {
                    continue;
                }
                bufferedWriter.write(line.replaceAll("\\b"+w.getEntry()+"\\b", "<a href=\"" + name + ".html#line_" + w.getLineNumber() + "\">" + w.getEntry().toUpperCase() + "</a>") + "<br>\n");
            }
            bufferedWriter.write("</div></body></html>");
            bufferedWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // TODO Auto-generated method stub
    }

}
