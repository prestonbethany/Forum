package view.actions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import org.apache.struts2.ServletActionContext;

import database.DAO.DAOFactory;
import database.models.Thread;
import view.models.Post;

public class ThreadAction extends ActionSupport {
    private long id;
    private String title;
    private ArrayList<Post> posts;
    private boolean archived;

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

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    //Controller methods

    private void addDbPostToView(database.models.Post dbPost) {
        Post viewPost = new Post();
        viewPost.id = dbPost.getId();
        viewPost.content = dbPost.getText();
        viewPost.imagePath = dbPost.getImagePath();
        viewPost.creationDateTime = dbPost.getDateAndTimeCreated();
        this.posts.add(viewPost);
    }

    private void createPost(Thread thread) {
        database.models.Post post = new database.models.Post();
        post.setThreadID(thread);
        post.setText(ServletActionContext.getRequest().getParameter("message"));
        post.setDateAndTimeCreated(new Date());
        post.setImagePath(null);
        post.setId(DAOFactory.getPostDao().save(post));
        if (post.getId() != -1){
            addDbPostToView(post);
        }
    }

    @Override
    public String execute() {
        id = Long.parseLong(ServletActionContext.getRequest().getParameter("threadid"));
        List<database.models.Post> posts = DAOFactory.getPostDao().findAllByThreadId(id);
        title = posts.get(0).getThreadID().getTitle();
        archived = posts.get(0).getThreadID().isArchivedFlag();
        this.posts = new ArrayList<Post>();
        for (database.models.Post dbPost : posts) {
            addDbPostToView(dbPost);
        } 
        return SUCCESS;
    }

    public String newPost() {
        execute();
        Thread currentThread = DAOFactory.getThreadDao().findById(id);
        createPost(currentThread);
        return SUCCESS;
    }

    public String newThread() {
        Thread newThread = new Thread();
        this.title = ServletActionContext.getRequest().getParameter("title");
        newThread.setTitle(title);
        this.id = DAOFactory.getThreadDao().save(newThread);
        this.posts = new ArrayList<Post>();
        createPost(newThread);
        return SUCCESS;
    }

    public String input() {
        return INPUT;
    }
}