package be.fenego.store.entities;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.MappedEntity;
import io.micronaut.data.annotation.MappedProperty;

@MappedEntity("openingtimes")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StoreHours implements Comparable<StoreHours> {
	@Id
	private long id;
	private String date;
	@MappedProperty("openingtime")	private String openingTime;
	@MappedProperty("closingtime")	private String closingTime;
	@MappedProperty("storeuuid")	private String storeUuid;

	public StoreHours() {
	}

	public StoreHours(String date, String openingTime, String closingTime) {
		this();
		this.date = date;
		this.openingTime = openingTime;
		this.closingTime = closingTime;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getOpeningTime() {
		return openingTime;
	}

	public void setOpeningTime(String openingTime) {
		this.openingTime = openingTime;
	}

	public String getClosingTime() {
		return closingTime;
	}

	public void setClosingTime(String closingTime) {
		this.closingTime = closingTime;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStoreUuid() {
		return storeUuid;
	}

	public void setStoreUuid(String storeUuid) {
		this.storeUuid = storeUuid;
	}

	@Override
	public int compareTo(StoreHours o) {
		return date.compareTo(o.getDate());
	}
}
