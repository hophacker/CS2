package noUse;
import	java.io.*;
import 	java.util.*; 
import 	java.util.regex.*;

public class Keyword_ID {
	private TreeMap map;
	private int num;
	public Keyword_ID(){
		map = new TreeMap<String,Integer>();
		num = 0;
	}
	public int getID(String key){
		if (!map.containsKey(key)){
			map.put(key, num++);
		}
		return (Integer) map.get(key);
	}
	public void store(String file){
		try {  
            ObjectOutputStream os = new ObjectOutputStream( new FileOutputStream(file));  
            os.writeObject(map);  
            os.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
	public void load(String file){
		try {  
            ObjectInputStream is = new ObjectInputStream(new FileInputStream(file));  
            map = (TreeMap) is.readObject();// 从流中读取User的数据  
            is.close();  
        } catch (Exception e) {  
            e.printStackTrace();  
        }
	}
}
