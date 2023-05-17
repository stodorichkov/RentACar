package com.example.demo.model.dto;

import com.example.demo.model.enums.EngineEnum;

public class CarDto {
 private String model;

 private String make;

 private String capacity;

 private EngineEnum engine;

 public CarDto(String model, String make, String capacity, EngineEnum engine) {
  this.model = model;
  this.make = make;
  this.capacity = capacity;
  this.engine = engine;
 }

 public CarDto() {
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

 public String getCapacity() {
  return capacity;
 }

 public void setCapacity(String capacity) {
  this.capacity = capacity;
 }

 public EngineEnum getEngine() {
  return engine;
 }

 public void setEngine(EngineEnum engine) {
  this.engine = engine;
 }
}
