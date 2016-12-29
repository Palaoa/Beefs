package io.github.froger.instamaterial.models;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dell on 2016/12/28.
 */

public class ComtBusiModel implements Serializable
{
    public String comment_id;
    public String user_id;
    public String story_id;

    public Date edittime;
    public String content;
    public Bitmap avatar;
}
