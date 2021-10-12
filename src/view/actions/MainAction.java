package view.actions;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import database.DAO.DAOFactory;
import database.models.Posts;
import database.models.Threads;
import view.models.Thread;

public class MainAction extends ActionSupport {
    private List<Thread> threadList;

    public List<Thread> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList) {
        this.threadList = threadList;
    }

    @Override
    public String execute() {
        this.threadList = new ArrayList<Thread>();
        List<Threads> dbThreadList = DAOFactory.getThreadsDao().findAll();
        for (Threads dbThread : dbThreadList) {
            Thread viewThread = new Thread();
            viewThread.id = dbThread.getId();
            viewThread.title = dbThread.getTitle();
            List<Posts> dbPostList = DAOFactory.getPostsDao().findAllByThreadId(viewThread.id);
            viewThread.dateTimeCreated = dbPostList.get(0).getDateAndTimeCreated();
            viewThread.imagePath = dbPostList.get(0).getImagePath();
            threadList.add(viewThread);
        }
        return SUCCESS;
    }
}
