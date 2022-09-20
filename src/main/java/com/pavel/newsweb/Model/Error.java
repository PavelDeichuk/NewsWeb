package com.pavel.newsweb.Model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Error {
    private String error;

    private String error_description;
}
