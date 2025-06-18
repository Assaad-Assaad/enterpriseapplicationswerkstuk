package be.ehb.enterpriseapplications.werkstuk.service;

import be.ehb.enterpriseapplications.werkstuk.dto.AuthResponse;
import be.ehb.enterpriseapplications.werkstuk.dto.LoginDto;
import be.ehb.enterpriseapplications.werkstuk.dto.RegisterDto;
import be.ehb.enterpriseapplications.werkstuk.exception.ResourceException;
import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.model.Role;
import be.ehb.enterpriseapplications.werkstuk.repository.PersonRepository;
import be.ehb.enterpriseapplications.werkstuk.repository.RoleRepository;
import be.ehb.enterpriseapplications.werkstuk.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(PersonRepository personRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.personRepository = personRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    public AuthResponse register(RegisterDto registerDto) {
        if (personRepository.findByEmail(registerDto.getEmail()).isPresent()) {
            throw new ResourceException("Person with this email already exists");
        }

        Person user = new Person(
                registerDto.getAuctionPersonNumber(),
                registerDto.getName(),
                registerDto.getEmail(),
                passwordEncoder.encode(registerDto.getPassword())


        );
        Role userRole = roleRepository.findByName(Role.RoleName.USER)
                .orElseThrow(() -> new ResourceException("Error: Role is not found."));
        user.setRole(userRole);
        personRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().getName().name());
        return new AuthResponse(token);
    }

    public String login(LoginDto loginDto) {
        Person user = personRepository.findByEmail(loginDto.getEmail())
                .orElseThrow(() -> new ResourceException("User not found"));
        if (!passwordEncoder.matches(loginDto.getPassword(), user.getPassword())) {
            throw new ResourceException("Invalid password");
        }

        return jwtUtil.generateToken(user.getEmail(), user.getRole().getName().name());
    }


}
