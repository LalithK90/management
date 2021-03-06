package lk.recruitment_management.asset.police_station.controller;


import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lk.recruitment_management.asset.ag_office.controller.AgOfficeController;
import lk.recruitment_management.asset.ag_office.entity.AgOffice;
import lk.recruitment_management.asset.ag_office.service.AgOfficeService;
import lk.recruitment_management.asset.common_asset.model.Enum.Province;
import lk.recruitment_management.asset.district.controller.DistrictController;
import lk.recruitment_management.asset.district.service.DistrictService;
import lk.recruitment_management.asset.police_station.entity.PoliceStation;
import lk.recruitment_management.asset.police_station.Service.PoliceStationService;
import lk.recruitment_management.util.interfaces.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/policeStation")
public class PoliceStationController implements AbstractController<PoliceStation, Integer> {

    private final PoliceStationService policeStationService;
    private final AgOfficeService agOfficeService;
    private  final DistrictService districtService;

    @Autowired
    public PoliceStationController(PoliceStationService policeStationService, AgOfficeService agOfficeService, DistrictService districtService) {
        this.policeStationService = policeStationService;
        this.agOfficeService = agOfficeService;
        this.districtService = districtService;
    }

    private String commonThing(Model model, Boolean booleanValue, PoliceStation policeStationObject) {

        model.addAttribute("addStatus", booleanValue);
        model.addAttribute("policeStation", policeStationObject);
        model.addAttribute("provinces", Province.values());
        model.addAttribute("districtURL",
                MvcUriComponentsBuilder
                        .fromMethodName(DistrictController.class, "getDistrictByProvince", "")
                        .toUriString());
        model.addAttribute("agOfficeURL",
                MvcUriComponentsBuilder
                        .fromMethodName(AgOfficeController.class, "getAgOfficeByDistrict", "")
                        .toUriString());
        return "policeStation/addPoliceStation";
    }

    @GetMapping
    public String findAll(Model model) {
        model.addAttribute("policeStations", policeStationService.findAll());
        return "policeStation/policeStation";
    }

    @GetMapping("/add")
    public String form(Model model) {
        return commonThing(model, false, new PoliceStation());
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Integer id, Model model) {
        model.addAttribute("policeStationDetail", policeStationService.findById(id));
        return "policeStation/policeStation-detail";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        model.addAttribute("districts", districtService.findAll());
        model.addAttribute("agOffices", agOfficeService.findAll());
        return commonThing(model, true, policeStationService.findById(id));
    }

    @PostMapping(value = {"/save", "/update"})
    public String persist(@Valid @ModelAttribute PoliceStation policeStation, BindingResult bindingResult,
                          RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            return commonThing(model, false, policeStation);
        }
        redirectAttributes.addFlashAttribute("policeStationDetail", policeStationService.persist(policeStation));
        return "redirect:/policeStation";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id, Model model) {
        policeStationService.delete(id);
        return "redirect:/policeStation";
    }

    @GetMapping(value = "/getPoliceStation/{id}")
    @ResponseBody
    public MappingJacksonValue getPoliceStationByAgOffice(@PathVariable int id) {
        AgOffice agOffice = new AgOffice();
        agOffice.setId(id);

        //MappingJacksonValue
        List<PoliceStation> policeStations = policeStationService.findByAgOffice(agOffice);


        //Create new mapping jackson value and set it to which was need to filter
        MappingJacksonValue mappingJacksonValue = new MappingJacksonValue(policeStations);

        //simpleBeanPropertyFilter :-  need to give any id to addFilter method and created filter which was mentioned
        // what parameter's necessary to provide
        SimpleBeanPropertyFilter simpleBeanPropertyFilter = SimpleBeanPropertyFilter
                .filterOutAllExcept("id", "name");
        //filters :-  set front end required value to before filter

        FilterProvider filters = new SimpleFilterProvider()
                .addFilter("PoliceStation", simpleBeanPropertyFilter);
        //Employee :- need to annotate relevant class with JsonFilter  {@JsonFilter("Employee") }
        mappingJacksonValue.setFilters(filters);

        return mappingJacksonValue;
    }
}
