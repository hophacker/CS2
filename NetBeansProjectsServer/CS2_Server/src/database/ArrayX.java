package database;

import cc.T;
import java.io.*;

//public static void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
public class ArrayX {
	public int length;
	public int bytes;
	public byte[] array;
	
	public ArrayX(int _arrayLength, int _arrayBytes){
            length = _arrayLength;
            bytes = _arrayBytes;
            array = new byte[length * bytes];
	}
        public ArrayX(){
            
	}
	public ArrayX(String file){
		input(file);
	}
	public byte[] at(int i){
		byte[] entry = new byte[bytes];
		System.arraycopy(array, bytes*i, entry, 0, bytes);
		return entry;
	}
	public void set(int i, byte element[]){
		System.arraycopy(element, 0, array, i*bytes, bytes);
	}
	public byte[] get(int i){
		return get(i, 0, bytes);
	}
	public byte[] get(int i, int len){
		return get(i, 0, len);
	}
        
        public void set(int i, int pos, byte element[]){
		System.arraycopy(element, 0, array, i*bytes+pos, element.length);
	}
	public byte[] get(int i, int pos, int len){
		byte[] ele = new byte[len];
		System.arraycopy(array, i*bytes+pos, ele, 0, len);
		return ele;
	}
	public void output(String file){
		try {
			DataOutputStream os = new DataOutputStream(new FileOutputStream(file));
			os.writeBytes(length+"\n");
			os.writeBytes(bytes+"\n");
			os.write(array);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public void input(String file){
		try {
			DataInputStream  os = new DataInputStream(new FileInputStream(file));
			length = Integer.parseInt(os.readLine());
			bytes = Integer.parseInt(os.readLine());
			array = new byte[length*bytes];
			os.read(array);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}
