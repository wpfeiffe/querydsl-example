package com.wspfeiffer.apttestgradle.resource;

import com.querydsl.core.types.Predicate;
import com.wspfeiffer.apttestgradle.entity.Thing;
import com.wspfeiffer.apttestgradle.repository.ThingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.data.web.SortDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/things")
public class ThingResource {

    final private ThingRepository thingRepository;

    public ThingResource(final ThingRepository thingRepository) {
        this.thingRepository = thingRepository;
    }

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Thing>> getAllThings() {
        return ResponseEntity.ok((ArrayList<Thing>) thingRepository.findAll());
    }

    @GetMapping(value = "/pageable", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Thing>> getPageThings(@SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable p) {
        return ResponseEntity.ok((Page<Thing>) thingRepository.findAll(p));
    }

    @GetMapping(value = "/pagefilter", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<Thing>> getPageFilterThings(
            @QuerydslPredicate(root = Thing.class, bindings = ThingRepository.class) Predicate predicate,
            @SortDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok((Page<Thing>) thingRepository.findAll(predicate, pageable));
    }

    @PostMapping
    public ResponseEntity<Thing> create(@RequestBody Thing newThing) {
        final Thing updated = thingRepository.save(newThing);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Thing> getById(@PathVariable Long id) {
        return thingRepository.findById(id)
                               .map(ResponseEntity::ok)
                               .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Thing> updateThing(@RequestBody Thing newThing, @PathVariable Long id) {

        return thingRepository.findById(id).map(thing -> {
            thing.setThingDesc(newThing.getThingDesc());
            thing.setThingName(newThing.getThingName());
            return ResponseEntity.ok(thingRepository.save(thing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Thing> deleteThing(@PathVariable Long id) {
        return thingRepository.findById(id)
                               .map(thing -> {
                                   thingRepository.deleteById(id);
                                   return ResponseEntity.ok(thing);
                               })
                               .orElse(ResponseEntity.notFound().build());
    }
}
