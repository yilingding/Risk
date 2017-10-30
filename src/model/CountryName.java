package model;

public class CountryName {
	private Map map=new Map();
public static String[] CountryName=new String[42];

public CountryName(){
	addCountry();
}

private void addCountry() {
	// TODO Auto-generated method stub
	for(int h=0;h<42;h++){
		for(int i=0;i<map.getAfrica().getNumberOfCountry();i++){
			CountryName[h]=map.getAfrica().getCountryInt(i).getName();
			h++;
		}
		for(int i=0;i<map.getAsia().getNumberOfCountry();i++){
			CountryName[h]=map.getAsia().getCountryInt(i).getName();
			h++;
		}
		for(int i=0;i<map.getAustralia().getNumberOfCountry();i++){
			CountryName[h]=map.getAustralia().getCountryInt(i).getName();
			h++;
		}
		for(int i=0;i< map.getEurope().getNumberOfCountry();i++){
			CountryName[h]=map.getEurope().getCountryInt(i).getName();
			h++;
		}
		for(int i=0;i<map.getNorthAmerica().getNumberOfCountry();i++){
			CountryName[h]=map.getNorthAmerica().getCountryInt(i).getName();
			h++;
		}
		for(int i=0;i<map.getSouthAmerica().getNumberOfCountry();i++){
			CountryName[h]=map.getSouthAmerica().getCountryInt(i).getName();
			h++;
		}
	}
	
}

public String[] returnCountryName(){
	return CountryName;
}
}