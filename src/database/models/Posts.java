package database.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "posts")
public class Posts implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "ThreadsID")
    private Threads threadsID;
    @Column(name = "Text")
    private String text;
    @Column(name = "ImagePath")
    private String imagePath;
    @Column(name = "DateAndTimeCreated")
    private Date dateAndTimeCreated;

    //Getters and Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Threads getThreadsID() {
        return threadsID;
    }
    public void setThreadsID(Threads threadsID) {
        this.threadsID = threadsID;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getImagePath() {
        return imagePath;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public Date getDateAndTimeCreated() {
        return dateAndTimeCreated;
    }
    public void setDateAndTimeCreated(Date dateAndTimeCreated) {
        this.dateAndTimeCreated = dateAndTimeCreated;
    }
}