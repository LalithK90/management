package lk.recruitment_management.asset.district.dao;


import lk.recruitment_management.asset.common_asset.model.Enum.Province;
import lk.recruitment_management.asset.district.entity.District;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DistrictDao extends JpaRepository<District, Integer> {
//select * from district where province = ?1
    List<District> findByProvince(Province province);

}
