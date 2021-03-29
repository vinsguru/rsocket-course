package com.vinsguru.springrsocket.client.serviceregistry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RSocketServerInstance {

    private String host;
    private int port;

}
