package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long userId;

    @Column
    private String userName;

    @Column
    private String password;

    @Column
    private String documentCpf;

    @Column
    private String email;

//    @ManyToOne
//    @JoinColumn(name = "companyId")
//    @ToString.Exclude
//    private Company company;

}
