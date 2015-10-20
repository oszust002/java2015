package pl.edu.agh.criminals;

import com.google.common.base.Splitter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class Main {

	protected static List<String> GetSplittedLine(String toSplit, Splitter split){
		List<String> splitted=new ArrayList<String>(split.splitToList(toSplit));
		return splitted;
	}
	protected static int GetKeyIndex(List<String> splittedLine, String key) {
		int i = 0;
		for (Iterator<String> iter = splittedLine.iterator(); iter.hasNext(); ) {
			if (iter.next().contains(key))
				break;
			else
				i++;
		}
		return i;
	}

	public static void parse(){
		try{
			BufferedReader reader = new BufferedReader(new FileReader("tmp/crimes.csv"));
			String line = null;
			int index=0;
			int counter =0;
			List<String> splitted = null;
			Splitter mySplitter = Splitter.on(',');
			splitted = GetSplittedLine(reader.readLine(), mySplitter);
			index = GetKeyIndex(splitted, "Date");
			System.out.println(index);
			splitted.clear();
			while((line=reader.readLine()) != null){
				splitted = GetSplittedLine(line, mySplitter);
				if(splitted.get(index).contains("2015")) {
					counter++;
				}
				splitted.clear();
			}
			System.out.println(counter);
		}
		catch(IOException ex){
			System.out.println("File not found");
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		parse();
		long end = System.currentTimeMillis();
		System.out.println((end-start)/1000.0);
	}
}
