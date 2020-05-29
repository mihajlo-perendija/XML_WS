package user.service.impl;

import org.dozer.DozerBeanMapper;
import org.dozer.loader.api.BeanMappingBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import user.dto.RoleDTO;
import user.dto.UserDTO;
import user.exceptions.*;
import user.model.Role;
import user.model.User;
import user.repository.RoleRepository;
import user.repository.UserRepository;
import user.service.UserService;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    DozerBeanMapper mapper;

    @Override
    public UserDTO convertToDTO(User user) throws ConversionFailedError {
        try {
            UserDTO dto = mapper.map(user, UserDTO.class);
            dto.setPassword(null);
            return dto;
        } catch (Exception e) {
            throw new ConversionFailedError("Internal server error");
        }
    }

    @Override
    public User convertToModel(UserDTO userDTO) throws ConversionFailedError {
        try {
            return mapper.map(userDTO, User.class);
        } catch (Exception e) {
            throw new ConversionFailedError("Invalid data");
        }
    }

    @Override
    public UserDTO add(UserDTO userDTO) throws DuplicateEntity, InvalidEmailOrPasswordError, ConversionFailedError {
        if (!isValidEmailAddress(userDTO.getEmail())){
            throw new InvalidEmailOrPasswordError("Email is invalid");
        }
        if (userDTO.getPassword() == null || userDTO.getPassword().equals("")){
            throw new InvalidEmailOrPasswordError("Password is invalid");
        }

        if (userDTO.getRoles() == null || userDTO.getRoles().isEmpty()){
            return userRegistration(userDTO);
        } else {
            return userCreationByAdmin(userDTO);
        }
    }

    private UserDTO userRegistration(UserDTO userDTO) throws DuplicateEntity, ConversionFailedError {
        User check = userRepository.findByEmail(userDTO.getEmail());
        if (check != null){
            throw new DuplicateEntity("User with this email already exists");
        }

        User user = convertToModel(userDTO);
        Set<Role> roles = new HashSet<Role>();
        roles.add(roleRepository.findByName("SIMPLE_USER"));
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);

        User registered = userRepository.save(user);
        return convertToDTO(registered);
    }

    private UserDTO userCreationByAdmin(UserDTO userDTO) throws DuplicateEntity, ConversionFailedError {
        User check = userRepository.findByEmail(userDTO.getEmail());
        if (check != null){
            throw new DuplicateEntity("User with this email already exists");
        }

        User user = convertToModel(userDTO);
        Set<Role> roles = new HashSet<Role>();
        for (RoleDTO roleDTO: userDTO.getRoles()){
            Optional<Role> role = roleRepository.findById(roleDTO.getId());
            role.ifPresent(roles::add);
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setEnabled(true);

        User registered = userRepository.save(user);
        return convertToDTO(registered);
    }

    @Override
    public UserDTO getOne(Long id) throws EntityNotFound, ConversionFailedError {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            return convertToDTO(user.get());
        } else {
            throw new EntityNotFound("User with this email already exists");
        }
    }

    @Override
    public List<UserDTO> getAll() throws ConversionFailedError {
        List<User> users = userRepository.findAll();
        List<UserDTO> dtos = new ArrayList<>();
        for (User user: users){
            dtos.add(convertToDTO(user));
        }
        return dtos;
    }

    @Override
    public UserDTO update(Long id, UserDTO userDTO) throws UnexpectedError {
        return null;
    }

    @Override
    public UserDTO delete(Long id) throws EntityNotFound {
        return null;
    }

    public static boolean isValidEmailAddress(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
}
