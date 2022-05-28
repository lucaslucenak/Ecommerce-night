package com.almeida.Ecommercenoite.models;

import com.almeida.Ecommercenoite.enums.UserTypeEnum;
import lombok.*;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "tb_usuarios")
public class UsuarioModel {

    public UsuarioModel(UserTypeEnum userType, String username, String password) {
        this.userType = userType;
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "userType", nullable = false)
    private UserTypeEnum userType;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
}
