package com.example.FirstProject.Controller;

import com.example.FirstProject.Service.AddressService;
import com.example.FirstProject.model.User;
import com.example.FirstProject.payload.AddressDTO;
import com.example.FirstProject.util.AuthUtile;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class AddressController {
    @Autowired
    AuthUtile authUtile;
    @Autowired
    private AddressService addressService;
    @PostMapping("/addresses")
    public ResponseEntity<AddressDTO>  createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        User user=authUtile.loggedInUser();
        AddressDTO savedAddressDTO=addressService.createAddress(addressDTO,user);
        return new ResponseEntity<AddressDTO>(savedAddressDTO,HttpStatus.CREATED);
    }

    @GetMapping("/addresses")
    public ResponseEntity< List<AddressDTO> >  getAddresses( ) {
        List<AddressDTO> addressList=addressService.getAddresses();

        return new ResponseEntity<>(addressList,HttpStatus.OK);
    }
    @GetMapping("/addresses/{addressId}")
    public ResponseEntity< AddressDTO > getAddressById(@PathVariable Long addressId ) {
AddressDTO addressDTO=addressService.getAddresseById(addressId);

        return new ResponseEntity<>(addressDTO,HttpStatus.OK);
    }
    @GetMapping("/users/addresses")
    public ResponseEntity<List< AddressDTO> > getUserAddress(  ) {
User user=authUtile.loggedInUser();
        List<AddressDTO> addressList=addressService.getUserAddresse(user);
        return new ResponseEntity<>(addressList,HttpStatus.OK);
    }

    @PutMapping("/addresses/{addressId}")
    public ResponseEntity< AddressDTO > updateAddress (@PathVariable Long addressId,@RequestBody AddressDTO addressDTO ) {
        AddressDTO addressDTOs=addressService.updateAddress (addressId,addressDTO);

        return new ResponseEntity<>(addressDTOs,HttpStatus.OK);
    }
    @DeleteMapping("/addresses/{addressId}")
    public ResponseEntity< String > deleteAddress (@PathVariable Long addressId) {
        String status=addressService.deleteAddress(addressId);

        return new ResponseEntity<>(status,HttpStatus.OK);
    }

}
