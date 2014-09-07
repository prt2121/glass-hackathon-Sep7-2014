
package com.intellibins.recyclethis.model;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;

public class BinData {

    @Expose
    private Meta meta;
    @Expose
    private List<List<String>> data = new ArrayList<List<String>>();

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public List<List<String>> getData() {
        return data;
    }

    public void setData(List<List<String>> data) {
        this.data = data;
    }

}
