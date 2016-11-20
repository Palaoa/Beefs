package com.example.dell.dbtest.Models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/11/5.
 */

public class StoryModel implements Serializable
{
    public String story_id;
    public String user_id;
    public String title;
    public String content;
    public String photo;
    public char state;
    // No OnShow End (N O E)
    public char mshow;
    // No Yes (N Y)
    public Date editTime;

}
