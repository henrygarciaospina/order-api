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
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
public class UserService {

    @Value("${jwt.password}")
    private String jwtSecret;
    @Autowired
    private UserConverter userConverter;
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user) {
        try {
            UserValidator.signup(user);

            User existUser = userRepository.findByUsername(user.getUsername())
                    .orElse(null);

            if (existUser != null)
                throw new ValidateServiceException("El nombre de usuario " + existUser.getUsername() + " ya existe!!");

            return userRepository.save(user);
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public LoginResponseDTO login(LoginRequestDTO request) {
        try {

            User user = userRepository.findByUsername(request.getUsername())
                    .orElseThrow(() -> new ValidateServiceException("Usuario y/o password incorrecto(s)"));

            if (!user.getPassword().equals(request.getPassword()))
                throw new ValidateServiceException("Password incorrecto");

            String token = createToken(user);

            return new LoginResponseDTO(userConverter.fromEntity(user), token);
        } catch (NoDataFoundException | ValidateServiceException e) {
            log.info(e.getMessage(), e);
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new GeneralServiceException(e.getMessage(), e);
        }
    }

    public String createToken(User user) {
        Date now = new Date();
        //(1000*60*60*8) el token expira a las 8 horas
        Date expiryDate = new Date(now.getTime() + (1000 * 60 * 60 * 8));

        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
            return true;
        }catch (UnsupportedJwtException e) {
            log.error("JWT in a particular format/configuration that does not match the format expected");
        }catch (MalformedJwtException e) {
            log.error("JWT was not correctly constructed and should be rejected");
        }catch (SignatureException e) {
            log.error("Signature or verifying an existing signature of a JWT failed");
        }catch (ExpiredJwtException e) {
            log.error("JWT was accepted after it expired and must be rejected");
        }
        return false;
    }
}
