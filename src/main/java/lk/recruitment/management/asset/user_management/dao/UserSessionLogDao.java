package lk.recruitment.management.asset.user_management.dao;


import lk.recruitment.management.asset.user_management.entity.Enum.UserSessionLogStatus;
import lk.recruitment.management.asset.user_management.entity.User;
import lk.recruitment.management.asset.user_management.entity.UserSessionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionLogDao extends JpaRepository<UserSessionLog, Integer > {
    UserSessionLog findByUserAndUserSessionLogStatus(User user, UserSessionLogStatus userSessionLogStatus);
}
