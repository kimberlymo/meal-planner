package ch.funproject.mealplanner.service;

import ch.funproject.mealplanner.domain.Availability;
import ch.funproject.mealplanner.domain.RepeatingAvailability;
import ch.funproject.mealplanner.repository.AvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AvailabilityService {
    private AvailabilityRepository availabilityRepository;

    public List<Availability> findAll() {
        return availabilityRepository.findAll();
    }

    public Optional<Availability> findById(UUID id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return availabilityRepository.findById(id);
    }

    public List<RepeatingAvailability> findByRepeating() {
        return List.of();
    }

    public Availability save(Availability availability) {
        if (availability == null) {
            throw new IllegalArgumentException("Availability cannot be null");
        }
        return availabilityRepository.save(availability);
    }

    public void deleteById(UUID id) {
        availabilityRepository.deleteById(id);
    }
}
