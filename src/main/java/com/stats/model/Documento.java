/**
 * Demo realizado por:
 * Claudia Salazar Gonzales
 * Juan Dirceu Navarro Arias
 * Luis Fernando Numa Navarro Arias
 * 
 * Diplomado Software Libre versión Sucre
 * noviembre 2016
 */


package com.stats.model;

import com.mongodb.BasicDBObject;

public class Documento {
	private String id;
	private String titulo;
	private String autor;
	private String carrera;
	private String fechaPublicacion;
	private String disponibilidad;
	private String localizacion;
	
	public Documento() {}
	
	public Documento(String _id, String _titulo, String _autor, String _carrera, String _fechaPublicacion,
			String _disponibilidad, String _localizacion) {
		this.id = _id;
		this.titulo = _titulo; 
		this.autor = _autor; 
		this.carrera = _carrera; 
		this.fechaPublicacion = _fechaPublicacion; 
		this.disponibilidad = _disponibilidad; 
		this.localizacion = _localizacion;
	}
	
	public Documento(BasicDBObject dBObjectDocumento) {
		this.id = dBObjectDocumento.getString("_id");
		this.titulo = dBObjectDocumento.getString("titulo");
		this.autor = dBObjectDocumento.getString("autor");
		this.carrera = dBObjectDocumento.getString("carrera");
		this.fechaPublicacion = dBObjectDocumento.getString("fechaPublicacion");
		this.disponibilidad = dBObjectDocumento.getString("disponibilidad");
		this.localizacion = dBObjectDocumento.getString("localizacion");
	}
	
	public BasicDBObject toDBObjectDocumento() {
		BasicDBObject dBObjecDocumento = new BasicDBObject();

		dBObjecDocumento.append("titulo", this.getTitulo());
		dBObjecDocumento.append("autor", this.getAutor());
		dBObjecDocumento.append("carrera", this.getCarrera());
		dBObjecDocumento.append("fechaPublicacion", this.getFechaPublicacion());
		dBObjecDocumento.append("disponibilidad", this.getDisponibilidad());
		dBObjecDocumento.append("localizacion", this.getLocalizacion());
		
		return dBObjecDocumento;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getAutor() {
		return autor;
	}
	public void setAutor(String autor) {
		this.autor = autor;
	}
	public String getCarrera() {
		return carrera;
	}
	public void setCarrera(String carrera) {
		this.carrera = carrera;
	}
	public String getFechaPublicacion() {
		return fechaPublicacion;
	}
	public void setFechaPublicacion(String fechaPublicacion) {
		this.fechaPublicacion = fechaPublicacion;
	}
	public String getDisponibilidad() {
		return disponibilidad;
	}
	public void setDisponibilidad(String disponibilidad) {
		this.disponibilidad = disponibilidad;
	}
	public String getLocalizacion() {
		return localizacion;
	}
	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

}