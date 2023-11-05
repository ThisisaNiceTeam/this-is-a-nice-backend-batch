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
public class Chat {

    private int id;

    private String message;

    private Date createdAt;
}
