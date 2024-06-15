package com.boskman.elibrary.series;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/series")
@RequiredArgsConstructor
public class SeriesController {

    private final SeriesService seriesService;
    private final PagedResourcesAssembler<Series> pagedResourcesAssembler;

    @PostMapping
    public ResponseEntity<Series> createSeries(@RequestBody SeriesRequest series) {
        Series createdSeries = seriesService.createSeries(series);
        return ResponseEntity.ok(createdSeries);
    }

    @PutMapping("/{seriesId}")
    public ResponseEntity<Series> updateSeries(@PathVariable Long seriesId, @RequestBody SeriesRequest seriesRequest) {
        Series updatedSeries = seriesService.updateSeries(seriesId, seriesRequest);
        return ResponseEntity.ok(updatedSeries);
    }

    @GetMapping
    public ResponseEntity<PagedModel<?>> getAllSeries(Pageable pageable) {
        Page<Series> series = seriesService.getAllSeries(pageable);
        PagedModel<?> pagedModel = pagedResourcesAssembler.toModel(series, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeriesController.class).getAllSeries(pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/search")
    public ResponseEntity<PagedModel<?>> searchSeries(@RequestParam String title, Pageable pageable) {
        Page<Series> series = seriesService.searchSeriesByTitle(title, pageable);
        PagedModel<?> pagedModel = pagedResourcesAssembler.toModel(series, WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SeriesController.class).searchSeries(title, pageable)).withSelfRel());
        return ResponseEntity.ok(pagedModel);
    }

    @GetMapping("/{seriesId}")
    public ResponseEntity<SeriesDto> getSeriesWithBooks(@PathVariable Long seriesId) {
        SeriesDto series = seriesService.getSeriesWithBooks(seriesId);
        return ResponseEntity.ok(series);
    }
}
