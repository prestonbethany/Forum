package actions;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import database.DAO.DAOFactory;
import database.models.Posts;
import models.Post;

public class Thread extends ActionSupport {
    private long id;
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
        id = Long.parseLong(ServletActionContext.getRequest().getParameter("id"));
        List<Posts> posts = DAOFactory.getPostsDao().findAllByThreadId(id);
        System.out.println("id = " + id);
        System.out.println("posts variable = " + posts);
        title = posts.get(0).getThread().getTitle();
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
}