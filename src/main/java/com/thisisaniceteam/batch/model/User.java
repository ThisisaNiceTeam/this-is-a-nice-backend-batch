package com.thisisaniceteam.batch.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class User {

    private int id;

    private String account;

    private String password;

    private Date createdAt;

    private Date updatedAt;
}
