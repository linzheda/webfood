package com.zd.bean;

import java.io.Serializable;

public class Resadmin implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	private Integer raid ;
	private String raname;
	private String rapwd;
	private String valcode; 
	
	
	
	
	
	public Integer getRaid() {
		return raid;
	}
	public void setRaid(Integer raid) {
		this.raid = raid;
	}
	public String getRaname() {
		return raname;
	}
	public void setRaname(String raname) {
		this.raname = raname;
	}
	public String getRapwd() {
		return rapwd;
	}
	public void setRapwd(String rapwd) {
		this.rapwd = rapwd;
	}
	public String getValcode() {
		return valcode;
	}
	public void setValcode(String valcode) {
		this.valcode = valcode;
	}
	
	@Override
	public String toString() {
		return "Resadmin [raid=" + raid + ", raname=" + raname + ", rapwd=" + rapwd + ", valcode=" + valcode + "]";
	}
	
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((raid == null) ? 0 : raid.hashCode());
		result = prime * result + ((raname == null) ? 0 : raname.hashCode());
		result = prime * result + ((rapwd == null) ? 0 : rapwd.hashCode());
		result = prime * result + ((valcode == null) ? 0 : valcode.hashCode());
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
		Resadmin other = (Resadmin) obj;
		if (raid == null) {
			if (other.raid != null)
				return false;
		} else if (!raid.equals(other.raid))
			return false;
		if (raname == null) {
			if (other.raname != null)
				return false;
		} else if (!raname.equals(other.raname))
			return false;
		if (rapwd == null) {
			if (other.rapwd != null)
				return false;
		} else if (!rapwd.equals(other.rapwd))
			return false;
		if (valcode == null) {
			if (other.valcode != null)
				return false;
		} else if (!valcode.equals(other.valcode))
			return false;
		return true;
	}
	
	public Resadmin(Integer raid, String raname, String rapwd, String valcode) {
		super();
		this.raid = raid;
		this.raname = raname;
		this.rapwd = rapwd;
		this.valcode = valcode;
	}
	public Resadmin() {
		super();
	}
	

}
