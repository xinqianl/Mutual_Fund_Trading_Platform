package databean;

import org.genericdao.PrimaryKey;

@PrimaryKey("id")
public class FundBean {

	private int id;
	private String name;
	
	private String ticker;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

}
