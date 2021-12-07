package ru.gx.fin.common.dris.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gx.fin.common.dris.memdata.PlacesMemoryRepository;
import ru.gx.fin.common.dris.memdata.ProvidersMemoryRepository;
import ru.gx.fin.common.dris.out.Place;
import ru.gx.fin.common.dris.out.Provider;

import static lombok.AccessLevel.PROTECTED;

@Tag(name = "data", description = "The data API")
@RestController
@RequestMapping("data")
public class RestDataController {
    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private PlacesMemoryRepository placesMemoryRepository;

    @Getter(PROTECTED)
    @Setter(value = PROTECTED, onMethod_ = @Autowired)
    private ProvidersMemoryRepository providersMemoryRepository;

    @Operation(description = "Get all places")
    @ApiResponses(
            @ApiResponse (
                    responseCode = "200",
                    description = "Found the places",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Place.class))
                            )
                    }
            )
    )
    @GetMapping("get-places")
    public Iterable<Place> getPlaces() {
        return this.placesMemoryRepository.getAll();
    }

    @Operation(description = "Get all providers")
    @ApiResponses(
            @ApiResponse (
                    responseCode = "200",
                    description = "Found the providers",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = Provider.class))
                            )
                    }
            )
    )
    @GetMapping("get-providers")
    public Iterable<Provider> getProviders() {
        return this.providersMemoryRepository.getAll();
    }
}
