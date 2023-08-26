package fateyev.ru.showcaseOfProducts.controllers;

import fateyev.ru.showcaseOfProducts.models.Showcase;
import fateyev.ru.showcaseOfProducts.services.ShowcaseService;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ShowcaseController {

    private final ShowcaseService showcaseService;

    @Autowired
    public ShowcaseController(ShowcaseService showcaseService) {
        this.showcaseService = showcaseService;
    }

    @GetMapping("/showcases")
    public  Page<Showcase> getAll(
            @And({
                    @Spec(path = "type", spec = Equal.class),
                    @Spec(path = "address", spec = Equal.class),
                    @Spec(
                            path = "createdDate",
                            params = {"afterCreation", "beforeCreation"},
                            spec = Between.class
                    ),
                    @Spec(
                            path = "modifiedDate",
                            params = {"afterModified", "beforeModified"},
                            spec = Between.class
                    )
            }) Specification<Showcase> showcaseSpecification, Pageable pageable){
        return showcaseService.findAll(showcaseSpecification, pageable);
    }

    @PostMapping("/showcases")
    public ResponseEntity<HttpStatus> createList(@RequestBody @Valid Showcase showcase){
        showcaseService.save(showcase);
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> updateList(@PathVariable("id") UUID id, @RequestBody @Valid Showcase showcase) {
        showcaseService.update(id, showcase);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/showcases/{id}")
    public ResponseEntity<HttpStatus> deleteList(@PathVariable("id") UUID id) {
        showcaseService.delete(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
