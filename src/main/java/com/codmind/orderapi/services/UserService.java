package com.codmind.orderapi.services;

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
    private UserRepository userRepository;

    public User createUser(User user){
        try{

            UserValidator.signup(user);

            User existUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if(existUser != null) throw new ValidateServiceException("El nombre del usuario " + existUser.getUsername() + " ya existe!!");

            return userRepository.save(user);
        } catch (NoDataFoundException | ValidateServiceException e){
            log.info(e.getMessage(), e);
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }
}
