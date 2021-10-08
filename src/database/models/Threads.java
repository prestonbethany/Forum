package database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "threads")
public class Threads implements Serializable{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "Title")
    private String title;
    @Column(name = "TimeToLive")
    private short timeToLive;
    @Column(name = "MaxTimeToLive")
    private short maxTimeToLive;
    @Column(name = "ArchivedFlag")
    private boolean archivedFlag;

    //Getters and Setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public short getTimeToLive() {
        return timeToLive;
    }
    public void setTimeToLive(short timeToLive) {
        this.timeToLive = timeToLive;
    }
    public short getMaxTimeToLive() {
        return maxTimeToLive;
    }
    public void setMaxTimeToLive(short maxTimeToLive) {
        this.maxTimeToLive = maxTimeToLive;
    }
    public boolean isArchivedFlag() {
        return archivedFlag;
    }
    public void setArchivedFlag(boolean archivedFlag) {
        this.archivedFlag = archivedFlag;
    }
    
}
