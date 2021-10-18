package view.actions;

import java.util.ArrayList;
import java.util.List;

import com.opensymphony.xwork2.ActionSupport;

import database.DAO.DAOFactory;
import database.models.Post;
import view.models.Thread;

public class MainAction extends ActionSupport {
    private List<Thread> threadList;
    private boolean archivedFlag;

    public boolean isArchivedFlag() {
        return archivedFlag;
    }

    public void setArchivedFlag(boolean archivedFlag) {
        this.archivedFlag = archivedFlag;
    }

    public List<Thread> getThreadList() {
        return threadList;
    }

    public void setThreadList(List<Thread> threadList) {
        this.threadList = threadList;
    }

    private void checkArchive(boolean isArchived) {
        this.threadList = new ArrayList<Thread>();
        List<database.models.Thread> dbThreadList = DAOFactory.getThreadDao().findAll(isArchived);
        for (database.models.Thread dbThread : dbThreadList) {
            Thread viewThread = new Thread();
            viewThread.id = dbThread.getId();
            viewThread.title = dbThread.getTitle();
            List<Post> dbPostList = DAOFactory.getPostDao().findAllByThreadId(viewThread.id);
            viewThread.dateTimeCreated = dbPostList.get(0).getDateAndTimeCreated();
            viewThread.imagePath = dbPostList.get(0).getImagePath();
            threadList.add(viewThread);
        }
    }

    @Override
    public String execute() {
        archivedFlag = false;
        checkArchive(false);
        return SUCCESS;
    }

    public String archivedExecute() {
        archivedFlag = true;
        checkArchive(true);
        return SUCCESS;
    }
}
