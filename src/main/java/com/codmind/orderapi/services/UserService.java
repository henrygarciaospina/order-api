package com.codmind.orderapi.services;

import com.codmind.orderapi.converters.UserConverter;
import com.codmind.orderapi.dtos.LoginRequestDTO;
import com.codmind.orderapi.dtos.LoginResponseDTO;
import com.codmind.orderapi.entity.User;
import com.codmind.orderapi.exceptions.GeneralServiceException;
import com.codmind.orderapi.exceptions.NoDataFoundException;
import com.codmind.orderapi.exceptions.ValidateServiceException;
import com.codmind.orderapi.repository.UserRepository;
import com.codmind.orderapi.validators.UserValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        try{
            UserValidator.signup(user);

            User existUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if(existUser != null) throw new ValidateServiceException("El nombre de usuario " + existUser.getUsername() + " ya existe!!");

            return userRepository.save(user);
        } catch (NoDataFoundException | ValidateServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
    public LoginResponseDTO login(LoginRequestDTO request){
        try{

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ValidateServiceException("Usuario y/o password incorrecto(s)"));

            if(!user.getPassword().equals(request.getPassword())) throw new ValidateServiceException("Password incorrecto");

            return new LoginResponseDTO(userConverter.fromEntity(user), "TOKEN");
        } catch (NoDataFoundException | ValidateServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
