package com.maxim.dto;

import lombok.Value;

@Value(staticConstructor = "of")
public class User {

   int id;
   String name;
   String password;

}
