import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class assignment implements Serializable {
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    private String subject;
    private String title;
    private String description;

    private Date date;

    public assignment(String subject, String title, String description, Date date ){
        this.subject = subject;
        this.title = title;
        this.description = description;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "assignment{" +
                "subject='" + subject + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", date=" + formatter.format(date) +
                '}';
    }
}
