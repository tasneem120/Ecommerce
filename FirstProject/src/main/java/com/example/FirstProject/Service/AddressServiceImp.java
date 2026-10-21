package com.example.FirstProject.Service;

import com.example.FirstProject.Repository.AddressRepo;
import com.example.FirstProject.Repository.UserRepository;
import com.example.FirstProject.exception.ResourceNotFoundException;
import com.example.FirstProject.model.Address;
import com.example.FirstProject.model.User;
import com.example.FirstProject.payload.AddressDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImp implements AddressService {
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    AddressRepo  addressRepo;
    @Autowired
    UserRepository userRepo;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address=modelMapper.map(addressDTO,Address.class);
        List<Address> addressList=user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);
        address.setUser(user);
        Address savedAddress = addressRepo.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {

        List<Address> addressList=addressRepo.findAll();
        return addressList.stream().map(
                 address -> modelMapper.map(address, AddressDTO.class)
         ).toList();
    }

    @Override
    public AddressDTO getAddresseById(Long addressId) {
        Address address=addressRepo.findById(addressId).orElseThrow(
                ()-> new ResourceNotFoundException("Address","addressId", addressId)
        );
        return modelMapper.map(address, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getUserAddresse(User user) {
List<Address> addressList=user.getAddresses();

        return addressList.stream().map(
                address -> modelMapper.map(address, AddressDTO.class)
        ).toList();
    }

    @Override
    public AddressDTO updateAddress (Long addressId, AddressDTO addressDTO) {
        Address addressFromDataBase=addressRepo.findById(addressId).orElseThrow(
                ()-> new ResourceNotFoundException("Address","addressId", addressId)
        );
        addressFromDataBase.setCity(addressDTO.getCity());
        addressFromDataBase.setCountry(addressDTO.getCountry());
        addressFromDataBase.setStreet(addressDTO.getStreet());
        addressFromDataBase.setBuildingName(addressDTO.getBuildingName());
        addressFromDataBase.setZip(addressDTO.getZip());
        addressFromDataBase.setState(addressDTO.getState());
 Address updatedAddress=addressRepo.save(addressFromDataBase);
 User user=addressFromDataBase.getUser();
user.getAddresses().removeIf(address ->
    address.getUser().getAddresses().equals(addressId));
user.getAddresses().add(updatedAddress);
 userRepo.save(user);
        return modelMapper.map(updatedAddress, AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDataBase=addressRepo.findById(addressId).orElseThrow(
                ()-> new ResourceNotFoundException("Address","addressId", addressId)
        );
        User user=addressFromDataBase.getUser();

        user.getAddresses().removeIf(address ->
                address.getAddressId().equals(addressId)
        );

        userRepo.save(user);
        addressRepo.delete(addressFromDataBase);

        return "Address deleted successfully with address id : " + addressId;
    }


}
