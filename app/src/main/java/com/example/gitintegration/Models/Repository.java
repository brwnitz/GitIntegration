package com.example.gitintegration.Models;

import java.io.Serializable;

public class Repository implements Serializable {
    private String name;
    private String html_url;

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
