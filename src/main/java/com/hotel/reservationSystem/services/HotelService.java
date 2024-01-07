package com.hotel.reservationSystem.services;

import com.hotel.reservationSystem.models.Hotel;
import com.hotel.reservationSystem.repositories.HotelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class HotelService {
    private final HotelRepository hotelRepository;

    @Autowired
    public HotelService(HotelRepository hotelRepository) {
        this.hotelRepository = hotelRepository;
    }

    @Transactional
    public Hotel save(Hotel hotel) {
        return hotelRepository.save(hotel);
    }

    @Transactional
    public void update(Integer id, Hotel hotel) {
        hotel.setId(id);
        hotelRepository.save(hotel);
    }

    @Transactional
    public void delete(Integer id) {
        hotelRepository.deleteById(id);
    }

    public Hotel find(Integer id) {
        return hotelRepository.findById(id).orElse(null);
    }

    public List<Hotel> findAll() {
        return hotelRepository.findAll();
    }
}
