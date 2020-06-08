package lk.recruitment.management.asset.subject.dao;


import lk.recruitment.management.asset.subject.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectDao extends JpaRepository<Subject, Integer> {

/*//select * from district where province = ?1
    List<Subject> findByProvince(Province province);*/

}
