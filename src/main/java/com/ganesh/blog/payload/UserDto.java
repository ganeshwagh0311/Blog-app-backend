package com.ganesh.blog.payload;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ganesh.blog.entities.Role;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;


@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private Long id;
    @NotEmpty
    @Size(min=4,message="username must be min of 4 characters")
    private String name;
    @Email(message = "Email address is not valid !! " )
    private String email;
    @NotEmpty
    @Size(min=3,max=10,message = "Password must be min of 3 chars and max of 10 chars !!")

    private String password;
    @NotEmpty
    private String about;
    @ManyToMany(cascade = CascadeType.MERGE)
    private Set<RoleDto> roles=new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
@JsonIgnore
    public String getPassword() {
        return this.password;
    }
@JsonProperty
public void setPassword(String password) {
    this.password = password;
}

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }



    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", about='" + about + '\'' +
                '}';
    }
}
