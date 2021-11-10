package database.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "role")
public class Role implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Name")
    private String name;
    @Column(name = "LoginToApp")
    private boolean loginToApp;
    @Column(name = "DeleteThread")
    private boolean deleteThread;
    @Column(name = "DeletePost")
    private boolean deletePost;
    @Column(name = "UpdateTimeToLive")
    private boolean updateTimeToLive;
    @Column(name = "CreateModerator")
    private boolean createModerator;

    //Getters and Setters

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isLoginToApp() {
        return loginToApp;
    }
    public void setLoginToApp(boolean loginToApp) {
        this.loginToApp = loginToApp;
    }
    public boolean isDeleteThread() {
        return deleteThread;
    }
    public void setDeleteThread(boolean deleteThread) {
        this.deleteThread = deleteThread;
    }
    public boolean isDeletePost() {
        return deletePost;
    }
    public void setDeletePost(boolean deletePost) {
        this.deletePost = deletePost;
    }
    public boolean isUpdateTimeToLive() {
        return updateTimeToLive;
    }
    public void setUpdateTimeToLive(boolean updateTimeToLive) {
        this.updateTimeToLive = updateTimeToLive;
    }
    public boolean isCreateModerator() {
        return createModerator;
    }
    public void setCreateModerator(boolean createModerator) {
        this.createModerator = createModerator;
    }
}
