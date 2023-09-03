package fateyev.ru.showcaseOfProducts.controllers;

import fateyev.ru.showcaseOfProducts.dto.ShowcaseDTO;
import fateyev.ru.showcaseOfProducts.models.Showcase;
import fateyev.ru.showcaseOfProducts.services.ShowcaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.kaczmarzyk.spring.data.jpa.domain.Between;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Tag(name="Showcase", description="The Showcase API")
@RestController
@RequestMapping("/api")
public class ShowcaseController {

    private final ShowcaseService showcaseService;
    private final ModelMapper modelMapper;

    @Autowired
    public ShowcaseController(ShowcaseService showcaseService, ModelMapper modelMapper) {
        this.showcaseService = showcaseService;
        this.modelMapper = modelMapper;
    }

    @Operation(
            summary = "Получение всех витрин",
            description = "Позволяет получить все витрины с фильтрацией по типу, адрессу," +
                    "за период по дате создания и за период по дате последней актуализации")
    @GetMapping("/showcases")
    public List<ShowcaseDTO> getAll(
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
            }) Specification<Showcase> showcaseSpecification){
        return showcaseService.findAll(showcaseSpecification).stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Operation(
            summary = "Добавление витрины",
            description = "Позволяет добавить витрину"
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/showcases")
    public ShowcaseDTO create(@RequestBody @Valid ShowcaseDTO showcaseDTO){
        Showcase showcase = convertToEntity(showcaseDTO);
        showcaseService.save(showcase);
        return convertToDTO(showcase);
    }

    @Operation(
            summary = "Изменение данных витрины",
            description = "Позволяет изменить данные витрины"
    )
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/showcases/{id}")
    public void update(@PathVariable("id") UUID id, @RequestBody @Valid ShowcaseDTO showcaseDTO) {
        Showcase showcase = convertToEntity(showcaseDTO);
        showcaseService.update(id, showcase);
    }

    @Operation(
            summary = "Удаление витрины",
            description = "Позволяет удалить витрину"
    )
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/showcases/{id}")
    public void delete(@PathVariable("id") UUID id) {
        showcaseService.delete(id);
    }

    private ShowcaseDTO convertToDTO(Showcase showcase) {
        return modelMapper.map(showcase, ShowcaseDTO.class);
    }

    private Showcase convertToEntity(ShowcaseDTO showcaseDTO) {
        return modelMapper.map(showcaseDTO, Showcase.class);
    }
}
