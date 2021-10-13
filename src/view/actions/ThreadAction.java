package view.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import database.DAO.DAOFactory;
import database.models.Posts;
import database.models.Threads;
import view.models.Post;

public class ThreadAction extends ActionSupport {
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

    private void addDbPostToView(Posts dbPost) {
        Post viewPost = new Post();
        viewPost.id = dbPost.getId();
        viewPost.content = dbPost.getText();
        viewPost.imagePath = dbPost.getImagePath();
        viewPost.creationDateTime = dbPost.getDateAndTimeCreated();
        this.posts.add(viewPost);
    }

    private void createPost(Threads thread) {
        Posts post = new Posts();
        post.setThreadsID(thread);
        post.setText(ServletActionContext.getRequest().getParameter("message"));
        post.setDateAndTimeCreated(new Date());
        post.setImagePath(null);
        post.setId(DAOFactory.getPostsDao().save(post));
        addDbPostToView(post);
    }

    @Override
    public String execute() {
        id = Long.parseLong(ServletActionContext.getRequest().getParameter("threadid"));
        List<Posts> posts = DAOFactory.getPostsDao().findAllByThreadId(id);
        title = posts.get(0).getThreadsID().getTitle();
        this.posts = new ArrayList<Post>();
        for (Posts dbPost : posts) {
            addDbPostToView(dbPost);
        } 
        return SUCCESS;
    }

    public String newPost() {
        execute();
        Threads currentThread = DAOFactory.getThreadsDao().findById(id);
        createPost(currentThread);
        return SUCCESS;
    }

    public String newThread() {
        Threads newThread = new Threads();
        this.title = ServletActionContext.getRequest().getParameter("title");
        newThread.setTitle(title);
        this.id = DAOFactory.getThreadsDao().save(newThread);
        this.posts = new ArrayList<Post>();
        createPost(newThread);
        return SUCCESS;
    }

    public String input() {
        return INPUT;
    }
}