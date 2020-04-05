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
 * Entity that corrisponds to the user's information. It contains the id, email, threashold over which want to be notified,
 * a boolean variable to see if an user has been notified or not. For each field is specified both the SQL  
 * and the business constraints.
 * 
 * @author Andrea Sturiale
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

    public User(String email, float threashold) {
        super();
        this.email = email;
        this.threashold = threashold;
    }

    public User(String email, float threashold, boolean notified) {
        this.notified=notified;
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
        return "User [email=" + email + ", id=" + id + ", notified=" + notified + ", threashold=" + threashold  +"]";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        User other = (User) obj;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id != other.id)
            return false;
        if (notified != other.notified)
            return false;
        if (Float.floatToIntBits(threashold) != Float.floatToIntBits(other.threashold))
            return false;
        return true;
    }

}