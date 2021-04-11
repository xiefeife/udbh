package com.last.demo.entity.request;

import lombok.Data;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@Data
public class ExportBusienss {

    private  String name;

    private List<String> businessId;
}
