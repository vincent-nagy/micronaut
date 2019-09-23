package be.fenego.store.dtos;

import java.util.List;

import be.fenego.store.entities.StoreHours;


public class EnrichedStore extends Store {
	private List<StoreHours> openingHours;

	public EnrichedStore() {
	}

	public EnrichedStore(Store store) {
		this.type = store.type;
		this.name = store.name;
		this.address = store.address;
		this.address2 = store.address2;
		this.address3 = store.address3;
		this.postalCode = store.postalCode;
		this.city = store.city;
		this.country = store.country;
		this.countryCode = store.countryCode;
		this.email = store.email;
		this.fax = store.fax;
		this.phoneBusiness = store.phoneBusiness;
		this.phoneBusinessDirect = store.phoneBusinessDirect;
		this.firstName = store.firstName;
		this.lastName = store.lastName;
		this.firstLastName = store.firstLastName;
		this.uuid = store.uuid;
	}

	public List<StoreHours> getOpeningHours() {
		return openingHours;
	}

	public void setOpeningHours(List<StoreHours> openingHours) {
		this.openingHours = openingHours;
	}

}
