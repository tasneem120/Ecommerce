package com.example.FirstProject.Service;

import com.example.FirstProject.model.User;
import com.example.FirstProject.payload.AddressDTO;

import java.util.List;

public interface AddressService {
    AddressDTO createAddress(AddressDTO addressDTO, User user);

    List<AddressDTO> getAddresses();

    AddressDTO getAddresseById(Long addressId);



    List<AddressDTO> getUserAddresse(User user);

    AddressDTO updateAddress (Long addressId, AddressDTO addressDTO);

    String deleteAddress(Long addressId);
}
