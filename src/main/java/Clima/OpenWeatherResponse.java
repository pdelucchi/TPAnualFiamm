package Clima;

import java.util.Collection;

public class OpenWeatherResponse {
	private Main main;
	private Collection<List> list;

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public Collection<List> getList() {
		return list;
	}

}
