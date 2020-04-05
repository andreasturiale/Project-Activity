package it.distributedsystems.projectactivity.temperatureservice.model;

/**
 * This class represent a custom message produced by temperature sensor. 
 * It contains the unit and the value of the measurement plus an eventual
 * description like for example the symbolic position of the sensor.
 * 
 * @author Andrea Sturiale
 */
public class TemperatureSensorMessage {

    private String unit;
    private float value;
    private String description;
    
    public TemperatureSensorMessage(String unit, float value, String description) {
        this.unit = unit;
        this.value = value;
        this.description = description;
    }

    public TemperatureSensorMessage(){}


    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
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
        return "Temperature Sensor Message with payload: [description=" + description +  ", unit=" + unit + ", value=" + value + "]";
    }

}