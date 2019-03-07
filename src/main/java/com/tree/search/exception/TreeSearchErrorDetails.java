package com.tree.search.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class TreeSearchErrorDetails {
    private Date timestamp;
    private String message;
    private String details;
}
