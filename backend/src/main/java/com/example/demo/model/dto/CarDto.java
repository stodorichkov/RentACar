package com.example.demo.model.dto;

import com.example.demo.model.enums.EngineEnum;
import com.example.demo.model.enums.TransmissionEnum;
import jakarta.validation.constraints.*;

import java.util.Objects;

public class CarDto {

 private Long id;

 private String imageUrl;

 private String model;

 private String make;

 private Integer capacity;

 private String registrationPlate;
 private EngineEnum engine;

 private TransmissionEnum transmissionEnum;

 private Double pricePerDay;

 private Boolean isRented;


 public CarDto(Long id, String imageUrl, String model,
               String make, Integer capacity, String registrationPlate, EngineEnum engine, TransmissionEnum transmissionEnum,
               Double pricePerDay, Boolean isRented) {
  this.id = id;
  this.imageUrl = imageUrl;
  this.model = model;
  this.make = make;
  this.capacity = capacity;
  this.registrationPlate = registrationPlate;
  this.engine = engine;
  this.transmissionEnum = transmissionEnum;
  this.pricePerDay = pricePerDay;
  this.isRented = isRented;
 }

 public CarDto() {
 }

 public Long getId() {
  return id;
 }

 public void setId(Long id) {
  this.id = id;
 }

 public String getImageUrl() {
  return imageUrl;
 }

 public void setImageUrl(String imageUrl) {
  this.imageUrl = imageUrl;
 }

 public String getModel() {
  return model;
 }

 public void setModel(String model) {
  this.model = model;
 }

 public String getMake() {
  return make;
 }

 public void setMake(String make) {
  this.make = make;
 }

 public Integer getCapacity() {
  return capacity;
 }

 public void setCapacity(Integer capacity) {
  this.capacity = capacity;
 }

 public EngineEnum getEngine() {
  return engine;
 }

 public void setEngine(EngineEnum engine) {
  this.engine = engine;
 }

 public TransmissionEnum getTransmissionEnum() {
  return transmissionEnum;
 }

 public void setTransmissionEnum(TransmissionEnum transmissionEnum) {
  this.transmissionEnum = transmissionEnum;
 }

 public Double getPricePerDay() {
  return pricePerDay;
 }

 public void setPricePerDay(Double pricePerDay) {
  this.pricePerDay = pricePerDay;
 }

 public Boolean getRented() {
  return isRented;
 }

 public void setRented(Boolean rented) {
  isRented = rented;
 }

 public String getRegistrationPlate() {
  return registrationPlate;
 }

 public void setRegistrationPlate(String registrationPlate) {
  this.registrationPlate = registrationPlate;
 }

 @Override
 public boolean equals(Object o) {
  if (this == o) return true;
  if (!(o instanceof CarDto carDto)) return false;
  return Objects.equals(getId(),
          carDto.getId()) && Objects.equals(getImageUrl(),
          carDto.getImageUrl()) && Objects.equals(getModel(),
          carDto.getModel()) && Objects.equals(getMake(),
          carDto.getMake()) && Objects.equals(getCapacity(),
          carDto.getCapacity()) && getEngine() == carDto.getEngine() &&
          getTransmissionEnum() == carDto.getTransmissionEnum() &&
          Objects.equals(getPricePerDay(), carDto.getPricePerDay());
 }

 @Override
 public int hashCode() {
  return Objects.hash(getId(), getImageUrl(), getModel(), getMake(),
          getCapacity(), getEngine(), getTransmissionEnum(), getPricePerDay());
 }

}
