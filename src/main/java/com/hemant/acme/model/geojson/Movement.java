package com.hemant.acme.model.geojson;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "movement_history")
public class Movement {

	@Id
	private String id;
	private String truckId;
	private Location location;
	// when movement data was generated by GPS
	private Date createdOn;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTruckId() {
		return truckId;
	}

	public void setTruckId(String truckId) {
		this.truckId = truckId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		if(null == location) {
			throw new IllegalArgumentException("Non empty object expected");
		}
		if(location.getType() != GeoType.Point) {
			throw new IllegalArgumentException("Only LineString geojson is accepted for Journey");
		}
		this.location = location;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Movement other = (Movement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Movement [id=" + id + ", truckId=" + truckId + ", location=" + location + ", createdOn=" + createdOn
				+ "]";
	}

}
