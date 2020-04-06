package it.distributedsystems.projectactivity.temperatureservice.model;

import java.io.Serializable;

/**
 * Class that corrisponds to the user's information. It contains the id, email, threashold over which want to be notified,
 * a boolean variable to see if an user has been notified or not. For each field is specified both the SQL  
 * and the business constraints.
 * 
 * @author Andrea Sturiale
 */
public class User implements Serializable{

    private static final long serialVersionUID = 1L;


    private int id;
    private String email;
    private float threashold;
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