package com.rschir.prac.services;

import com.rschir.prac.model.*;
import com.rschir.prac.repositories.CartJewelryRepository;
import com.rschir.prac.repositories.JewelryRepository;
import com.rschir.prac.repositories.JewelryRepository;
import com.rschir.prac.util.exceptions.NotFoundException;
import com.rschir.prac.util.exceptions.JewelryAlreadyUsedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class CartJewelryService {
    private final CartJewelryRepository cartJewelryRepository;
    private final JewelryRepository jewelryRepository;

    @Autowired
    public CartJewelryService(CartJewelryRepository cartJewelryRepository, JewelryRepository jewelryRepository) {
        this.cartJewelryRepository = cartJewelryRepository;
        this.jewelryRepository = jewelryRepository;
    }
    @Transactional
    public List<CartJewelry> findAllByClientId(Long clientId) {
        System.out.println(clientId + "lol");
        //List<Jewelry> jewelry
        return cartJewelryRepository.findAllByClientId(clientId);
    }



    @Transactional
    public List<CartJewelry> deleteAllByClientId(long clientId) {
        cartJewelryRepository.deleteAllByClientId(clientId);
        return cartJewelryRepository.findAllByClientId(clientId);
    }

    @Transactional
    public CartJewelry updateOneCartJewelry(Long jewelryId, Long quantity, Long clientId) {
        //System.out.println(updatedCardItem.getJewelryId());

        CartJewelry toUpdate = cartJewelryRepository.findByClientIdAndJewelryId(clientId, jewelryId);
        toUpdate.setQuantity(Math.toIntExact(quantity));
        return cartJewelryRepository.save(toUpdate);
    }

    @Transactional
    public CartJewelry addCartJewelry(Long jewelryId, Long quantity, Long clientId) {

        Jewelry jewelry = jewelryRepository.findById(jewelryId).orElseThrow(NotFoundException::new);
        CartJewelry newJewelry = new CartJewelry();
        if(quantity <= jewelry.getCount()) {
            newJewelry.setQuantity(Math.toIntExact(quantity));
        } else {
            newJewelry.setQuantity(jewelry.getCount());
        }
        newJewelry.setJewelry(jewelry);
        newJewelry.setJewelryId(jewelry.getJewelryId());
        newJewelry.setClientId(clientId);
        return cartJewelryRepository.save(newJewelry);
    }


    @Transactional
    public void deleteByClientIdAndJewelryId(long clientId, long productId) {
        cartJewelryRepository.deleteById(new CartJewelryId(clientId, productId));
    }

    @Transactional
    public void makeOrder(long clientId) {
        List<CartJewelry> cartJewelryArr = cartJewelryRepository.findAllByClientId(clientId);
        if (cartJewelryArr.isEmpty()) {
            return;
        }
        for(CartJewelry cartJewelry : cartJewelryArr) {
            if (cartJewelry.getQuantity() > cartJewelry.getJewelry().getCount()) {
                throw new JewelryAlreadyUsedException();
            }
        }
        for(CartJewelry cartJewelry : cartJewelryArr) {
            Jewelry jewelry = jewelryRepository.findById(cartJewelry.getJewelryId()).orElseThrow(NotFoundException::new);
            jewelry.setCount(jewelry.getCount() - cartJewelry.getQuantity());
            jewelryRepository.save(jewelry);
            cartJewelryRepository.deleteById(new CartJewelryId(cartJewelry.getClientId(), cartJewelry.getJewelryId()));
        }
        //cartJewelryRepository.deleteAllByClientId(clientId);

    }

    public Stats statsByClientId(Long clientId) {
        int quantity = 0;
        double totalCost = 0;
        for(var cartJewelry : cartJewelryRepository.findAllByClientId(clientId)) {
            quantity += cartJewelry.getQuantity();
            totalCost += cartJewelry.getJewelry().getCost() * cartJewelry.getQuantity();
        }
        return new Stats(quantity, totalCost);
    }
}
