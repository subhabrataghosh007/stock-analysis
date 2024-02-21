package org.mutualfund.beans;

import lombok.Data;

import java.util.List;

@Data
public class Root {

    private String status;
    private List<List<Object>> data;
}
