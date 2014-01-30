package database;

public class SimpleBean {
	public String message = "no message";
	public String getMessage(){
		return message;
	}
	public void setMessage(String message){
		this.message = "The message" + message;
	}
}	
