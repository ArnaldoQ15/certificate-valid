package br.com.certificatevalid.service;

import br.com.certificatevalid.dto.AddressResponseDto;
import br.com.certificatevalid.dto.UserAddressInDto;
import br.com.certificatevalid.dto.UserAddressUpdateDto;
import br.com.certificatevalid.exception.BadRequestException;
import br.com.certificatevalid.model.UserAddress;
import br.com.certificatevalid.repository.UserAddressRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static br.com.certificatevalid.util.Constants.ADDRESS_NOT_FOUND;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Service
public class UserAddressService extends BaseService {

    @Autowired
    private UserAddressRepository repository;
    @Autowired
    private ModelMapper modelMapper;


    public List<UserAddress> convertAddressInDtoListToEntity(List<UserAddressInDto> addresses) {
        List<UserAddress> finalList = new ArrayList<>();
        addresses.forEach(userAddressInDto -> finalList.add(getAddressInformation(userAddressInDto)));
        return finalList;
    }

    private UserAddress getAddressInformation(UserAddressInDto userAddressInDto) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<AddressResponseDto> response = restTemplate.getForEntity(
                String.format("https://viacep.com.br/ws/%s/json/",
                        userAddressInDto.getZipCode()), AddressResponseDto.class);

        if (isNull(response.getBody()) || !response.getStatusCode().equals(HttpStatus.OK)) {
            throw new BadRequestException(ADDRESS_NOT_FOUND);
        }

        UserAddress userAddress = modelMapper.map(response.getBody(), UserAddress.class);
        userAddress.setZipCode(userAddressInDto.getZipCode());
        userAddress.setComplement(isNull(userAddressInDto.getComplement()) ? null : userAddressInDto.getComplement());
        userAddress.setNumber(isNull(userAddressInDto.getNumber()) ? null : userAddressInDto.getNumber());

        return userAddress;
    }

    public List<UserAddress> updateUserAddress(List<UserAddressUpdateDto> addresses) {
        List<UserAddress> finalList = new ArrayList<>();
        AtomicReference<UserAddress> addressEntityNew = new AtomicReference<>(new UserAddress());

        addresses.forEach(userUpdateDto -> {
            UserAddress addressFind = findAddress(userUpdateDto.getAddressId());
            if (nonNull(userUpdateDto.getZipCode())) {
                addressEntityNew.set(getAddressInformation(modelMapper.map(userUpdateDto, UserAddressInDto.class)));
            }

            addressEntityNew.get().setZipCode(isNull(userUpdateDto.getZipCode()) ? addressFind.getZipCode() : userUpdateDto.getZipCode());
            addressEntityNew.get().setNumber(isNull(userUpdateDto.getNumber()) ? addressFind.getNumber() : userUpdateDto.getNumber());
            addressEntityNew.get().setComplement(isNull(userUpdateDto.getComplement()) ? addressFind.getComplement() : userUpdateDto.getComplement());
            addressEntityNew.get().setAddressId(isNull(addressEntityNew.get().getAddressId()) ? addressFind.getAddressId() : addressEntityNew.get().getAddressId());

            finalList.add(addressEntityNew.get());
        });
        return finalList;
    }

    public ResponseEntity<Void> delete(Long addressId) {
        UserAddress address = findAddress(addressId);
        repository.delete(address);
        return ResponseEntity.ok().build();
    }

}