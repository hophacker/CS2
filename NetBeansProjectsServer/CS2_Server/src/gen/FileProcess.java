package gen;
import	java.io.*;
import 	java.util.*; 
import 	java.util.regex.*;
public class FileProcess {
	public static void insertMap(String word, int num, Map<String,Set<Integer>> map){
		if (word.length() == 0) return;
		for (int i = 0; i < 2; i++){
			if (i == 1) word = word.toLowerCase();
			if (!map.containsKey(word)){
				TreeSet hs = new TreeSet();
				hs.add(num);
				map.put(word, hs);
			}else{
				map.get(word).add(num);
			}
		}
	}
	public static void byKeywordAtFile(String fileName, int num, Map<String,Set<Integer>> map, String reg){
		Pattern pattern = Pattern.compile(reg);
		Matcher matcher;
		
		File file = new File(fileName);
		String[] words;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String strLine = null;
            while ((strLine = reader.readLine()) != null) {
            	matcher = pattern.matcher(strLine);
                words = pattern.split(strLine);
                while (matcher.find()) {
                	insertMap(matcher.group(), num, map);
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
	}
	
	
	public static Map byKeyWord(int numOfFile, String reg){//return map(keyword --> ids)
		Map<String,Set<Integer>> map = new TreeMap();
		for (int i = 0; i < numOfFile; i++){
			byKeywordAtFile(""+i, i, map, reg);
		}
		return map; 
	}
	public static void printMap(Map map){
		Iterator iter = map.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			System.out.println(entry.getKey() + " " + entry.getValue().toString());
		}
	}

	public static void main(String[] args){
		//",|\\.| |\\(|\\)|\"|\\/"
		Map map = FileProcess.byKeyWord(6, "\\w*");
		FileProcess.printMap(map);
//		fp.processByKeyWord();
//		fp.printMap();
	}
}
