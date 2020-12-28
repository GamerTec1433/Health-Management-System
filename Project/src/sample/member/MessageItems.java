package sample.member;

import java.sql.Date;

public class MessageItems {
    private String message;
    private Date date;

    public MessageItems()
    {
        message = "";
        date = Date.valueOf("0000-00-00");
    }

    public MessageItems(String message, Date date) {
        this.message = message;
        this.date = date;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
