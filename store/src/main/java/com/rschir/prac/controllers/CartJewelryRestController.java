package com.rschir.prac.controllers;

import com.rschir.prac.model.CartJewelry;
import com.rschir.prac.model.Stats;
import com.rschir.prac.services.CartJewelryService;
import com.rschir.prac.services.CartJewelryService;
import com.rschir.prac.util.exceptions.ErrorResponse;
import com.rschir.prac.util.exceptions.ForbiddenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("cart_jewelry")
public class CartJewelryRestController {


        private final CartJewelryService cartJewelryService;

        @Autowired
        public CartJewelryRestController(CartJewelryService cartJewelryService) {
            this.cartJewelryService = cartJewelryService;
        }

        @GetMapping("/all")
        public List<CartJewelry> getCartJewelry() {
            System.out.println("get cart");
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());
            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            System.out.println("get cart");
            return cartJewelryService.findAllByClientId(clientId);
        }

        @GetMapping("/stats")
        public Stats getCartJewelryQuantity() {
            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            return cartJewelryService.statsByClientId(clientId);
        }


        @PostMapping("/{jewelryId}/{quantity}")
        public CartJewelry addCartJewelry(@PathVariable long jewelryId, @PathVariable long quantity) {
            System.out.println(SecurityContextHolder.getContext().getAuthentication().getDetails());

            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            System.out.println(clientId);
            return cartJewelryService.addCartJewelry(jewelryId, quantity, clientId);
        }

        @DeleteMapping
        public List<CartJewelry> deleteAllCartJewelry() {
            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            return cartJewelryService.deleteAllByClientId(clientId);
        }

        @DeleteMapping("/{productId}")
        public void deleteCartJewelry(@PathVariable long productId) {
            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            cartJewelryService.deleteByClientIdAndJewelryId(clientId, productId);
        }


        @PatchMapping("/{productId}/(quantity)")
        public CartJewelry updateOneCartJewelry(@PathVariable long productId, @PathVariable long quantity) {
            Long clientId = (Long)SecurityContextHolder.getContext().getAuthentication().getDetails();
            return cartJewelryService.updateOneCartJewelry(productId, quantity, clientId);
        }

        @DeleteMapping("/make")
        public void makeOrder() {
            Long clientId = (Long) SecurityContextHolder.getContext().getAuthentication().getDetails();
            cartJewelryService.makeOrder(clientId);
        }

        @ExceptionHandler({ForbiddenException.class, AccessDeniedException.class})
        private ResponseEntity<ErrorResponse> handleForbiddenException() {
            ErrorResponse response = new ErrorResponse(
                    "You don't have permissions",
                    System.currentTimeMillis()
            );
            return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
        }
    
}
