package cc;
import java.util.*;
public class Main {
	public static void main(String[] args){
		Gen gen = new Gen(6);
		
		
		System.out.println(gen.sArray.array[0]);
		System.out.println(gen.sArray.array[1]);
		System.out.println(gen.sArray.array[gen.sArray.length-2]);
		System.out.println(gen.sArray.array[gen.sArray.length-1]);
		Set<Integer> idSet = gen.sArray.search("the");
		System.out.println(gen.sArray.length);
		
		gen.output("CS2");
		Gen gen1 = new Gen("CS2");
		gen1.output("CS2");
		System.out.println(gen1.sArray.F("the"));
		System.out.println(gen1.sArray.table.length);
		
		System.out.println(gen1.sArray.array[0]);
		System.out.println(gen1.sArray.array[1]);
		System.out.println(gen1.sArray.array[gen1.sArray.length-2]);
		System.out.println(gen1.sArray.array[gen1.sArray.length-1]);
		System.out.println(gen1.sArray.length);
		Set<Integer> idSet1 = gen1.sArray.search("the");
		System.out.println(idSet1.toString());
	}
}
