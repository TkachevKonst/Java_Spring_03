package ru.gb.page;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.gb.service.TimesheetPageService;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller
@RequestMapping("/timesheets")
public class TimesheetPageController {

    private final TimesheetPageService timesheetPageService;

    public TimesheetPageController(TimesheetPageService timesheetPageService) {
        this.timesheetPageService = timesheetPageService;
    }
    @GetMapping
    public String getAllTimesheets (Model model){
        List<TimesheetPageDto> timesheets = timesheetPageService.findAll();
        model.addAttribute("timesheets", timesheets);
        return "timesheets-page.html";
    }

    @GetMapping ("/{id}")
    public String getTimesheetsPage(@PathVariable Long id,Model model){
        Optional<TimesheetPageDto> timesheetOpt = timesheetPageService.findById(id);
        if (timesheetOpt.isEmpty()){
            throw new NoSuchElementException();
        }
        model.addAttribute("timesheet" , timesheetOpt.get());
        return "timesheet-page.html";
    }
}
