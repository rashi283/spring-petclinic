
package org.springframework.samples.petclinic.web;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Hospital;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;


@Controller
@SessionAttributes(types = Hospital.class)
public class HospitalController {

    private final ClinicService clinicService;


    @Autowired
    public HospitalController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");
    }

    @RequestMapping(value = "/hospitals/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        Hospital hospital = new Hospital();
        model.put("hospital", hospital);
        return "hospital/createOrUpdateHospitalForm";
    }

    @RequestMapping(value = "/hospitals/new", method = RequestMethod.POST)
    public String processCreationForm(@Valid Hospital hospital, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "hospitals/createOrUpdateHospitalForm";
        } else {
            this.clinicService.saveHospital(hospital);
            status.setComplete();
            return "redirect:/hospitals/" + hospital.getId();
        }
    }

    @RequestMapping(value = "/hospitals/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
        model.put("hospital", new Hospital());
        return "hospitals/findHospitals";
    }

    @RequestMapping(value = "/hospitals", method = RequestMethod.GET)
    public String processFindForm(Hospital hospital, BindingResult result, Map<String, Object> model) {

        // allow parameterless GET request for /owners to return all records
        if (hospital.getName() == null) {
            hospital.setName(""); // empty string signifies broadest possible search
        }

        // find owners by last name
        Collection<Hospital> results = this.clinicService.findHospitalByName(hospital.getName());
        if (results.size() < 1) {
            // no owners found
            result.rejectValue("name", "notFound", "not found");
            return "hospitals/findHospitals";
        }
        if (results.size() > 1) {
            // multiple owners found
            model.put("selections", results);
            return "hospitals/hospitalsList";
        } else {
            // 1 owner found
            hospital = results.iterator().next();
            return "redirect:/hospitals/" + hospital.getId();
        }
    }

    @RequestMapping(value = "/hospitals/{hospitalId}/edit", method = RequestMethod.GET)
    public String initUpdateHospitalForm(@PathVariable("hospitalId") int hospitalId, Model model) {
        Hospital hospital = this.clinicService.findHospitalById(hospitalId);
        model.addAttribute(hospital);
        return "hospitals/createOrUpdateHospitalForm";
    }

    @RequestMapping(value = "/hospitals/{hospitalId}/edit", method = RequestMethod.PUT)
    public String processUpdateHospitalForm(@Valid Hospital hospital, BindingResult result, SessionStatus status) {
        if (result.hasErrors()) {
            return "hospitals/createOrUpdateHospitalForm";
        } else {
            this.clinicService.saveHospital(hospital);
            status.setComplete();
            return "redirect:/hospitals/{hospitalId}";
        }
    }

    /**
     * Custom handler for displaying an owner.
     *
     * @param ownerId the ID of the owner to display
     * @return a ModelMap with the model attributes for the view
     */
    @RequestMapping("/hospitals/{hospitalId}")
    public ModelAndView showHospital(@PathVariable("hospitalId") int hospitalId) {
        ModelAndView mav = new ModelAndView("hospitals/hospitalDetails");
        mav.addObject(this.clinicService.findHospitalById(hospitalId));
        return mav;
    }

}
