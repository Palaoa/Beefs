package io.github.froger.instamaterial.models;

import java.io.Serializable;

/**
 * Created by dell on 2016/12/14.
 */

public class StBusiModel implements Serializable
{
    public String story_id;
    public String user_id;
    public String nickname;
    public String title;
    public String content;
    public int like_num;
    public boolean isLike;
}
