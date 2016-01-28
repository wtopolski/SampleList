package wtopolski.android.samplelist.model;

/**
 * Created by wtopolski on 13.11.15.
 */
public class Element {
    private long id;
    private String title;
    private String desc;
    private long priority;

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getPriority() {
        return priority;
    }

    public void setPriority(long priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Element{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", priority='" + priority + '\'' +
                '}';
    }
}
