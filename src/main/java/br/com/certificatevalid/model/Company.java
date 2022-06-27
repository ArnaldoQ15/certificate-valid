package br.com.certificatevalid.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long companyId;

    @Column
    private String companyVerificationCode;

//    @OneToMany(mappedBy = "userId", cascade = CascadeType.ALL)
//    private List<User> users = new ArrayList<>();

}
