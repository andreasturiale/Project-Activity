package it.distributedsystems.projectactivity.temperatureservice.model;

/**
 * TemperatureSensorMessage
 * 
 * This class represent a custom message produced by temperature sensor 
 */
public class TemperatureSensorMessage {

    int lat;
    int lng;
    String unit;
    String type;
    float value;
    String description;
    
    public TemperatureSensorMessage(int lat, int lng, String unit, String type, float value, String description) {
        this.lat = lat;
        this.lng = lng;
        this.unit = unit;
        this.type = type;
        this.value = value;
        this.description = description;
    }

    public TemperatureSensorMessage(){}

    public int getLat() {
        return lat;
    }

    public void setLat(int lat) {
        this.lat = lat;
    }

    public int getLng() {
        return lng;
    }

    public void setLng(int lng) {
        this.lng = lng;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Temperature Sensor Message with payload: [description=" + description + ", lat=" + lat + ", lng=" + lng + ", type="
                + type + ", unit=" + unit + ", value=" + value + "]";
    }

}