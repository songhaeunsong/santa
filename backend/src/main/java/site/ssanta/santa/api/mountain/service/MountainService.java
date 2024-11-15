package site.ssanta.santa.api.mountain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import site.ssanta.santa.api.mountain.repository.MountainRepository;

@Service
@RequiredArgsConstructor
public class MountainService {

    private final MountainRepository mountainRepository;
}
