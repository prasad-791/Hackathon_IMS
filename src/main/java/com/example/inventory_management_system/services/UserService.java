package com.example.inventory_management_system.services;

import com.example.inventory_management_system.config.JwtService;
import com.example.inventory_management_system.entity.User;
import com.example.inventory_management_system.repository.IUserRepository;
import com.example.inventory_management_system.util.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class UserService implements IUserService{
    private static final Logger logger = LogManager.getLogger(UserService.class);

    @Autowired
    private IUserRepository repository;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Override
    public ResponseEntity<?> addUser(User user) {
        try{
            user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            this.repository.save(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> loginUser(Map<String, ?> body) {
        try{
            if(!body.containsKey("username") || !body.containsKey("password")){
                return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
            }
            Optional<User> user = this.repository.findByUsername(body.get("username").toString());
            if(user.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            body.get("username").toString(),
                            body.get("password").toString()
                    )
            );
            Map<String,Object> resultMap = new HashMap<>();
            resultMap.put("token",jwtService.generateToken(user.get()));
            resultMap.put("data",user.get());
            return new ResponseEntity<>(resultMap,HttpStatus.OK);
        }catch (BadCredentialsException e){
            logger.error(Constants.exceptionOccurred+e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<?> getAllUsers() {
        try{
            List<User> userList = this.repository.findAll();
            return new ResponseEntity<>(userList,HttpStatus.OK);
        }catch (Exception e){
            logger.error(Constants.exceptionOccurred+e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
