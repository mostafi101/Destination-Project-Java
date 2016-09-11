
public class City {
	private String name;
	private String state;
	private String latitude;
	private String longitude;
	
	
	public City(String name,String state,String latitude,String longitude) {
		this.name = name;
		this.state = state;
		this.latitude = latitude;
		this.longitude = longitude;
		
	}

	public String getName() {
		return name;
	}

	public String getState() {
		return state;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	
	
	
}
