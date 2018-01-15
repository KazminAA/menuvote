package com.simplevoting.menuvoting.utils;

import com.simplevoting.menuvoting.HasId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ResponseEntityWithLocation {
    public static <T extends HasId> ResponseEntity<T> getResponseCreatedWithId(T created, String url) {
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(url + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
