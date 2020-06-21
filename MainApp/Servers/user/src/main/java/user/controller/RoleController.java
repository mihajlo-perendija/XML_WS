package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import user.dto.RoleDTO;
import user.exceptions.ConversionFailedError;
import user.service.RoleService;

import java.util.List;

@RestController
@RequestMapping(value = "role")
@CrossOrigin(origins = "*")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('ACTIVATE_USER_PERMISSION') or hasAuthority('DEACTIVATE_USER_PERMISSION')")
    public ResponseEntity<List<RoleDTO>> getAll() throws ConversionFailedError {

        List<RoleDTO> roles = roleService.getAll();

        return new ResponseEntity<>(roles, HttpStatus.OK);
    }
}
