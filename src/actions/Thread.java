package actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import database.DAO.DAOFactory;
import database.models.Posts;
import database.models.Threads;
import models.Post;

public class Thread extends ActionSupport {
    private long id = -1;
    private String title;
    private ArrayList<Post> posts;

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

    public ArrayList<Post> getPosts() {
        return posts;
    }

    public void setPosts(ArrayList<Post> posts) {
        this.posts = posts;
    }

    //Controller methods

    @Override
    public String execute() {
        if (id < 0) {
            id = Long.parseLong(ServletActionContext.getRequest().getParameter("id"));
        }
        List<Posts> posts = DAOFactory.getPostsDao().findAllByThreadId(id);
        title = posts.get(0).getThreadsID().getTitle();
        this.posts = new ArrayList<Post>();
        //TODO(Preston): Optimize this later
        for (Posts post : posts) {
            Post viewPost = new Post();
            viewPost.id = post.getId();
            viewPost.content = post.getText();
            viewPost.imagePath = post.getImagePath();
            viewPost.creationDateTime = post.getDateAndTimeCreated();
            this.posts.add(viewPost);
        } 
        return SUCCESS;
    }

    public String newPost() {
        Posts newPost = new Posts();
        id = Long.parseLong(ServletActionContext.getRequest().getParameter("threadid"));
        Threads currentThread = DAOFactory.getThreadsDao().findById(id);
        newPost.setThreadsID(currentThread);
        newPost.setText(ServletActionContext.getRequest().getParameter("message"));
        newPost.setDateAndTimeCreated(new Date());
        newPost.setImagePath(null);
        DAOFactory.getPostsDao().save(newPost);
        return execute();
    }

}