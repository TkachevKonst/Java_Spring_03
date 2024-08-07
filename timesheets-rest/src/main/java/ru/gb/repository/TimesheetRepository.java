package ru.gb.repository;

import org.springframework.stereotype.Repository;
import ru.gb.model.Timesheet;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class TimesheetRepository {

    private static Long count = 1L;
    private final List<Timesheet> timesheets = new ArrayList<>();

    public Optional<Timesheet> getById(Long id) {
        return timesheets.stream()
                .filter(timesheet -> Objects.equals(timesheet.getId(), id))
                .findFirst();
    }

    public List<Timesheet> getAll() {
        return List.copyOf(timesheets);
    }

    public Timesheet create (Timesheet timesheet){
        timesheet.setId(count++);
        timesheets.add(timesheet);
        return timesheet;
    }

    public void delete (Long id){
        timesheets.stream()
                .filter(timesheet -> Objects.equals(timesheet.getId(), id))
                .findFirst()
                .ifPresent(timesheets::remove);
    }
}



