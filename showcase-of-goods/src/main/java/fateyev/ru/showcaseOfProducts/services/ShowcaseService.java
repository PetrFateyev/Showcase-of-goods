package fateyev.ru.showcaseOfProducts.services;

import fateyev.ru.showcaseOfProducts.models.Showcase;
import fateyev.ru.showcaseOfProducts.repositories.ShowcaseRepository;
import fateyev.ru.showcaseOfProducts.utils.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class ShowcaseService {

    private final ShowcaseRepository showcaseRepository;

    @Autowired
    public ShowcaseService(ShowcaseRepository showcaseRepository) {
        this.showcaseRepository = showcaseRepository;
    }

    @Transactional
    public void save(Showcase showcase) {
        showcaseRepository.save(showcase);
    }

    public Page<Showcase> findAll(Specification<Showcase> specification, Pageable pageable){
        return showcaseRepository.findAll(specification, pageable);
    }
    @Transactional
    public void update(UUID id, Showcase updatedShowcase) {
        if (!showcaseRepository.existsById(id)){
            throw new NotFoundException("Showcase with this id was not found");
        }
        updatedShowcase.setShowcaseId(id);
        showcaseRepository.save(updatedShowcase);
    }

    @Transactional
    public void delete(UUID id) {
        showcaseRepository.deleteById(id);
    }
}
