package org.launchcode.techjobs.persistent.controllers;

import jakarta.validation.Valid;
import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("")
public class HomeController {

    @Autowired
    private EmployerRepository employerRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private SkillRepository skillRepository;

    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("title", "My Jobs");
        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());

        List employers = (List<Employer>) employerRepository.findAll();
        model.addAttribute("employers", employers);

        List skills = (List<Skill>) skillRepository.findAll();
        model.addAttribute("skills", skills);
        return "add";
    }

    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                    Errors errors, Model model, @RequestParam int employerId,
                                    @RequestParam List<Integer> skills) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            List employers = (List<Employer>) employerRepository.findAll();
            model.addAttribute("employers", employers);

            return "add";
        }

        Optional<Employer> optEmployer = employerRepository.findById(employerId);
        if (optEmployer.isPresent()) {
            Employer employer = optEmployer.get();
            newJob.setEmployer(employer);
        }


        List<Skill> skillObjs = (List<Skill>) skillRepository.findAllById(skills);
        newJob.setSkills(skillObjs);

        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {
        Optional<Job> optJob = jobRepository.findById(jobId);
        if (optJob.isPresent()) {
            Job job = (Job) optJob.get();
            model.addAttribute("job", job);
            return "view";
        } else {
            return "redirect:/";
        }
    }
}
