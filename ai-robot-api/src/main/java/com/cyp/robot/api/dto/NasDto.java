package com.cyp.robot.api.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class NasDto {
    private String name;
    private String dir;
    private String parentName;
    private String createDate;
    private String updateDate;
    private String type;
    private String size;
    private String updateBy;
}
