package com.CMS.Content.Management.System.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Post {

    private int id;
    private String title;
    private String content;
    private String imageURL;
}
