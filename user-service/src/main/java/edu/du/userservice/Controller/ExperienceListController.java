// File: src/main/java/edu/du/userservice/Controller/ExperienceListController.java
package edu.du.userservice.Controller;

import edu.du.userservice.dto.ExperienceDto;
import edu.du.userservice.dto.RemarkDto;
import edu.du.userservice.Service.ExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Experiences")
@RequiredArgsConstructor
public class ExperienceListController {

    private final ExperienceService experienceService;

    @GetMapping
    public Page<ExperienceDto> listExperiences(
            @RequestParam(value = "status",      required = false) String status,
            @RequestParam(value = "campaignId",  required = false) Long   campaignId,
            @RequestParam(value = "page",        defaultValue = "0")    int    page,
            @RequestParam(value = "size",        defaultValue = "10")   int    size
    ) {
        return experienceService.listExperiences(status, campaignId, page, size);
    }

    @PutMapping("/{id}/approve")
    public ExperienceDto approve(
            @PathVariable("id") Long   id,
            @RequestBody           RemarkDto body
    ) {
        return experienceService.approveExperience(id, body);
    }

    @PutMapping("/{id}/reject")
    public ExperienceDto reject(
            @PathVariable("id") Long   id,
            @RequestBody           RemarkDto body
    ) {
        return experienceService.rejectExperience(id, body);
    }
}