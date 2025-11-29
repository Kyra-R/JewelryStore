package com.rschir.prac.config;

import com.rschir.prac.model.Jewelry;
import com.rschir.prac.model.ProductMaterial;
import com.rschir.prac.model.ProductType;
import org.springframework.data.jpa.domain.Specification;

public class JewelrySpecification {

    public static Specification<Jewelry> hasType(ProductType type) {
        return (root, query, cb) ->
                type == null ? null : cb.equal(root.get("jewelryType"), type);
    }

    public static Specification<Jewelry> hasMaterial(ProductMaterial material) {
        return (root, query, cb) ->
                material == null ? null : cb.equal(root.get("jewelryMaterial"), material);
    }

    public static Specification<Jewelry> priceBetween(Long min, Long max) {
        return (root, query, cb) -> {
            if (min != null && max != null)
                return cb.between(root.get("cost"), min, max);
            if (min != null)
                return cb.greaterThanOrEqualTo(root.get("cost"), min);
            if (max != null)
                return cb.lessThanOrEqualTo(root.get("cost"), max);
            return null;
        };
    }
}
