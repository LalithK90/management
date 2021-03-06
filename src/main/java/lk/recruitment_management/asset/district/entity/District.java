package lk.recruitment_management.asset.district.entity;




import com.fasterxml.jackson.annotation.JsonFilter;
import lk.recruitment_management.asset.ag_office.entity.AgOffice;
import lk.recruitment_management.asset.common_asset.model.Enum.Province;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonFilter("District")
public class District {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(min = 2, max = 13, message = "Your name length should be 13")
    private String name;

    @Enumerated(EnumType.STRING)
    private Province province;

    @OneToMany(mappedBy = "district")
    private List<AgOffice> agOffices;




}
