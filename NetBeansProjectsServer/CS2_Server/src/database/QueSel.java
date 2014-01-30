package database;

public class QueSel {
	public String que = null;
	public String selA = null;
	public String selB = null;
	public String selC = null;
	public String selD = null;
	public char ans = '?';
	public int id = 0;
	public QueSel(int i, String q, String A, String B, String C, String D, char an){
		id = i;
		que = q;
		selA = A;
		selB = B;
		selC = C;
		selD = D;
		ans = an;
	}
}
