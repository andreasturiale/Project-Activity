package it.distributedsystems.projectactivity.temperatureservice.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * User
 */
@Entity
public class User implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @NotNull(message = "The email can't be null")
    @Email(message = "The email '${validatedValue}' should be valid")
    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    @Min(value = 0, message = "The threashold must be at least {value}")
    @Max(value = 50, message = "The threashold must be at most {value}")
    private float threashold;

    @Column(nullable = false)
    private boolean notified;

    public User() {
        this.notified=false;
    }

    public User(int id, String email, float threashold, boolean notified) {
        this.id = id;
        this.email = email;
        this.threashold = threashold;
        this.notified = notified;
    }

    public User(int id, String email, float threashold) {
        super();
        this.id = id;
        this.email = email;
        this.threashold = threashold;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public float getThreashold() {
        return threashold;
    }

    public void setThreashold(float threashold) {
        this.threashold = threashold;
    }

    public boolean isNotified() {
        return notified;
    }

    public void setNotified(boolean notified) {
        this.notified = notified;
    }

    @Override
    public String toString() {
        return "User [email=" + email + ", id=" + id + ", notified=" + notified + ", threashold=" + threashold + "]";
    }

}