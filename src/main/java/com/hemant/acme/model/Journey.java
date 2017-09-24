package com.hemant.acme.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.hemant.acme.model.geojson.GeoType;
import com.hemant.acme.model.geojson.Location;

@Document(collection = "truck_journeys")
public class Journey {

	@Id
	private String id;
	private String startPointName;
	private String endPointName;
	private Date startTime = new Date();
	private Date estimatedEndTime = new Date();
	private Location location;

	@Override
	public String toString() {
		return "Journey [id=" + id + ", startPointName=" + startPointName + ", endPointName=" + endPointName + "]";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getStartPointName() {
		return startPointName;
	}

	public void setStartPointName(String startPointName) {
		this.startPointName = startPointName;
	}

	public String getEndPointName() {
		return endPointName;
	}

	public void setEndPointName(String endPointName) {
		this.endPointName = endPointName;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEstimatedEndTime() {
		return estimatedEndTime;
	}

	public void setEstimatedEndTime(Date estimatedEndTime) {
		this.estimatedEndTime = estimatedEndTime;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		if(null == location) {
			throw new IllegalArgumentException("Non empty object expected");
		}
		if(location.getType() != GeoType.LineString) {
			throw new IllegalArgumentException("Only LineString geojson is accepted for Journey");
		}
		this.location = location;
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
		Journey other = (Journey) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
