package io.xiaper.dwr.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.directwebremoting.annotations.DataTransferObject;
import org.directwebremoting.annotations.RemoteProperty;

import java.util.Date;

@Data
@AllArgsConstructor
@DataTransferObject
public class User {

    @RemoteProperty
    private Integer id;

    @RemoteProperty
    private String name;

    @RemoteProperty
    private Integer age;

    @RemoteProperty
    private String address;

    @RemoteProperty
    private Date createAt;

}
