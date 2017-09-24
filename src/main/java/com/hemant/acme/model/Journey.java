package com.hemant.acme.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hemant.acme.model.geojson.LineString;
import com.hemant.acme.model.geojson.Point;


@Document(collection = "truck_journeys")
public class Journey {

	@Id
	private String id;
	private String startPointName;
	private String endPointName;
	private Date startTime = new Date();
	private Date estimatedEndTime = new Date();
	private LineString location;

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

	public LineString getLocation() {
		return location;
	}

	public void setLocation(LineString location) {
		if(null == location) {
			throw new IllegalArgumentException("Non empty object expected");
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
	
	
	/**
	 * https://stackoverflow.com/questions/8065532/how-to-randomly-pick-an-element-from-an-array
	 * @param array
	 * @return
	 */
	@JsonIgnore
	public Point getRandomPointInJourney() {
		List<double[]> points = this.location.getCoordinates();
	    int rnd = new Random().nextInt(points.size());
	    double[] coordinates = points.get(rnd);
	    Point point = new Point();
	    point.setCoordinates(coordinates);
	    return point;
	}

	public static void main(String[] args) throws Exception {
		Journey jour = new Journey();
		jour.setId("111");
		
		
		
		List<double[]> points = new ArrayList<>();
		points.add(new double[] {10d, 20d});
		points.add(new double[] {30d, 50d});
		points.add(new double[] {50d, 80d});
		points.add(new double[] {1000d, 80d});
		points.add(new double[] {5000d, 80d});
		
		LineString str = new LineString();
		str.setCoordinates(points);
		
		jour.setLocation(str);
		
		ObjectMapper mapper = new ObjectMapper();
		
		System.out.println(mapper.writeValueAsString(jour));
	}
}
