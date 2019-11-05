package com.jtjt.qtgl.bean.base;

import java.io.Serializable;

public class UploderBearn  implements Serializable {
    private String path;

    public UploderBearn(String path) {
        this.path = path;
    }

    public UploderBearn() {
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "UploderBearn{" +
                "path='" + path + '\'' +
                '}';
    }
}
