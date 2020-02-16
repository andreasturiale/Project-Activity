package it.distributedsystems.projectactivity.temperatureservice.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * User
 */
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Column(unique = true)
    private String email;
    private float threashold;
    private boolean notified;

    public User() {
    }

    public User(int id, String email, float threashold, boolean notified) {
        this.id = id;
        this.email = email;
        this.threashold = threashold;
        this.notified = notified;
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