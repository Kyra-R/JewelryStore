package com.rschir.prac.services;

import com.rschir.prac.config.JewelrySpecification;
import com.rschir.prac.model.*;
import com.rschir.prac.repositories.JewelryRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JewelryService {

    public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
        if (c != null && string != null) {
            try {
                return Enum.valueOf(c, string.trim().toUpperCase());
            } catch (IllegalArgumentException ex) {
            }
        }
        return null;
    }

    public static ProductType typeFromString(String name) {
        return getEnumFromString(ProductType.class, name);
    }

    public static ProductMaterial materialFromString(String name) {
        return getEnumFromString(ProductMaterial.class, name);
    }
    private final JewelryRepository jewelryRepository;

    @Autowired
    public JewelryService( JewelryRepository jewelryRepository) {
        this.jewelryRepository = jewelryRepository;
    }

    public List<Jewelry> readAllByType(String type){
        return jewelryRepository.findAllByJewelryType(typeFromString(type));
    }

    public List<Jewelry> readAllByMaterial(String material){
        return jewelryRepository.findAllByJewelryMaterial(materialFromString(material));
    }

    public List<Jewelry> readAllByMaterialAndType(String type, String material){
        return jewelryRepository.findAllByJewelryTypeAndJewelryMaterial(typeFromString(type), materialFromString(material));
    }

    //NEW
    public List<Jewelry> readAllByParameters(ProductType type, ProductMaterial material, Long minPrice, Long maxPrice){
        return jewelryRepository.findAll(
                Specification.where(JewelrySpecification.hasType(type))
                        .and(JewelrySpecification.hasMaterial(material))
                        .and(JewelrySpecification.priceBetween(minPrice, maxPrice)));
    }


    @Transactional(readOnly = true)
    public Jewelry read(long id) {
        return jewelryRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Jewelry> readAll() {
        return jewelryRepository.findAll();
    }

    @Transactional(readOnly = true)
    public String collectTypesMaterials()
    {
        /*List<Jewelry> list = jewelryRepository.findAll();
        EnumMap<ProductType, EnumSet<ProductMaterial>> result = new EnumMap<>(ProductType.class);


        for(Jewelry jewelry: list)
        {
            if(jewelry.getCount() == 0){
                continue;
            } else
            if(result.containsKey(jewelry.getJewelryType())){
                result.get(jewelry.getJewelryType()).add(jewelry.getJewelryMaterial());
            } else {
                result.put(jewelry.getJewelryType(), EnumSet.of(jewelry.getJewelryMaterial()));
            }
        }*/

        EnumMap<ProductType, EnumSet<ProductMaterial>> result =
                jewelryRepository.findAll().stream()
                        .filter(j -> j.getCount() > 0)
                        .collect(Collectors.groupingBy(
                                Jewelry::getJewelryType,
                                () -> new EnumMap<>(ProductType.class),
                                Collectors.mapping(
                                        Jewelry::getJewelryMaterial,
                                        Collectors.toCollection(() -> EnumSet.noneOf(ProductMaterial.class))
                                )
                        ));

        StringBuilder str = new StringBuilder();
        for(var obj: result.entrySet())
        {
            System.out.println(obj.getKey() + " " + obj.getValue() + ";");
            str.append(obj.getKey() + " " + obj.getValue() + "; ");
        }
        return str.toString();
    }


    @Transactional
    public Jewelry create(Jewelry jewelry) {

        return jewelryRepository.save(jewelry);
    }

    @Transactional
    public Jewelry update(Jewelry updatedJewelry, long id) {
        Jewelry jewelry = jewelryRepository.findById(id).orElseThrow(NotFoundException::new);

        jewelry.setJewelryId(id);
        jewelry.setCost(updatedJewelry.getCost());
        jewelry.setName(updatedJewelry.getName());
        jewelry.setJewelryMaterial(updatedJewelry.getJewelryMaterial());
        jewelry.setJewelryType(updatedJewelry.getJewelryType());
        jewelry.setCount(updatedJewelry.getCount());



        return jewelryRepository.save(jewelry);
    }



    @Transactional
    public void delete(long id) {
        jewelryRepository.deleteById(id);
    }
}
