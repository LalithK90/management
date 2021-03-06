package lk.recruitment_management.asset.police_station.Service;


import lk.recruitment_management.asset.ag_office.entity.AgOffice;
import lk.recruitment_management.asset.police_station.dao.PoliceStationDao;
import lk.recruitment_management.asset.police_station.entity.PoliceStation;
import lk.recruitment_management.util.interfaces.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// spring transactional annotation need to tell spring to this method work through the project
@CacheConfig(cacheNames = "policeStation")
public class PoliceStationService implements AbstractService<PoliceStation, Integer> {
    private final PoliceStationDao policeStationDao;

    @Autowired
    public PoliceStationService(PoliceStationDao policeStationDao) {

        this.policeStationDao = policeStationDao;
    }

    @Cacheable
    public List<PoliceStation> findAll() {
        return policeStationDao.findAll();
    }

    @Cacheable
    public PoliceStation findById(Integer id) {
        return policeStationDao.getOne(id);
    }

    @Caching(evict = {@CacheEvict(value = "policeStation", allEntries = true)},
            put = {@CachePut(value = "policeStation", key = "#policeStation.id")})
    @Transactional
    public PoliceStation persist(PoliceStation policeStation) {
        return policeStationDao.save(policeStation);
    }

    @CacheEvict(allEntries = true)
    public boolean delete(Integer id) {
        policeStationDao.deleteById(id);
        return false;
    }

    @Cacheable
    public List<PoliceStation> search(PoliceStation policeStation) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<PoliceStation> policeStationExample = Example.of(policeStation, matcher);
        return policeStationDao.findAll(policeStationExample);
    }

    public boolean isPoliceStationPresent(PoliceStation policeStation) {
        return policeStationDao.equals(policeStation);
    }

    public List<PoliceStation> findByAgOffice(AgOffice agOffice) {
        return policeStationDao.findByAgOffice(agOffice);
    }
}
