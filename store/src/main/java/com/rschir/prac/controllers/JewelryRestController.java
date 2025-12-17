package com.rschir.prac.controllers;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.model.ProductMaterial;
import com.rschir.prac.model.ProductType;
import com.rschir.prac.services.JewelryBlobService;
import com.rschir.prac.services.JewelryService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.ForbiddenException;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@RequestMapping("jewelry")
public class JewelryRestController {
    JewelryBlobService jewelryBlobService;
    JewelryService jewelryService;

    @Autowired
    public JewelryRestController(JewelryService jewelryService, JewelryBlobService jewelryBlobService) {
        this.jewelryService = jewelryService;
        this.jewelryBlobService = jewelryBlobService;
    }


    @GetMapping
    public List<Jewelry> getAllJewelry() {
        return jewelryService.readAll();
    }

    @GetMapping("/{id}")
    public Jewelry getJewelryById(@PathVariable long id) {
        return jewelryService.read(id);
    }

    @PostMapping(path = "/admin/uploadFile/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> handleFileUpload(@RequestParam("image") MultipartFile file, @PathVariable long id) throws Exception {
        jewelryBlobService.saveAttachment(file, id);
        return ResponseEntity.ok().body("file received successfully");
    }

    @GetMapping(path = "/getFile/{id}")
    public ResponseEntity<byte []> getFile(@PathVariable long id) {
        byte [] blob = jewelryBlobService.getAttachment(id);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(blob);
    }




    @PostMapping("/admin/add")
    public ResponseEntity<Jewelry> postJewelry(@RequestBody Jewelry newJewelry) {


        return new ResponseEntity<>(jewelryService.create(newJewelry), HttpStatus.CREATED);
    }

    @GetMapping("/admin/get")
    public List<Jewelry> getAllJewelryAdmin() {
        return jewelryService.readAll();
    }


    @PutMapping("/admin/{id}")
    public Jewelry patchJewelry(@PathVariable long id, @RequestBody Jewelry updatedJewelry) {

        return jewelryService.update(updatedJewelry, id);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteJewelry(@PathVariable long id) {

        jewelryService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/type/{type}")
    public List<Jewelry> getJewelryByType(@PathVariable String type) {

        return jewelryService.readAllByType(type);
    }

    @GetMapping("/material/{material}")
    public List<Jewelry> getJewelryByMaterial(@PathVariable String material) {

        return jewelryService.readAllByMaterial(material);
    }

    @GetMapping("/both/{type}/{material}")
    public List<Jewelry> getJewelryByMaterialAndType(@PathVariable String material, @PathVariable String type) {

        return jewelryService.readAllByMaterialAndType(type, material);
    }

    //NEW
    @GetMapping("/params")
    public List<Jewelry> getJewelryByParameters(
            @RequestParam(required = false) ProductType jewelryType,
            @RequestParam(required = false) ProductMaterial jewelryMaterial,
            @RequestParam(required = false) Long minPrice,
            @RequestParam(required = false) Long maxPrice
    ) {

        return jewelryService.readAllByParameters(jewelryType, jewelryMaterial, minPrice, maxPrice);
    }


    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorResponse> handleNotFoundException() {
        ErrorResponse response = new ErrorResponse(
                "Jewelry or picture with this id was not found",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, DataIntegrityViolationException.class})
    private ResponseEntity<ErrorResponse> handleBadParseException(Exception e) {
        System.out.println("we are here");
        ErrorResponse response = new ErrorResponse(
                e.getMessage(),
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
    private ResponseEntity<ErrorResponse> handleForbiddenException() {
        System.out.println("We are here");
        ErrorResponse response = new ErrorResponse(
                "You don't have permissions",
                System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }
}
