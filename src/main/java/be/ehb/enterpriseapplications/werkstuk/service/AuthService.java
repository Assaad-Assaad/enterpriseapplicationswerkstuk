package be.ehb.enterpriseapplications.werkstuk.service;

import be.ehb.enterpriseapplications.werkstuk.dto.AuthResponse;
import be.ehb.enterpriseapplications.werkstuk.dto.LoginDto;
import be.ehb.enterpriseapplications.werkstuk.dto.RegisterDto;
import be.ehb.enterpriseapplications.werkstuk.exception.ResourceException;
import be.ehb.enterpriseapplications.werkstuk.model.Person;
import be.ehb.enterpriseapplications.werkstuk.model.Role;
import be.ehb.enterpriseapplications.werkstuk.repository.PersonRepository;
import be.ehb.enterpriseapplications.werkstuk.util.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthService(PersonRepository personRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.personRepository = personRepository;
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
        user.setRole(new Role(Role.RoleName.USER));
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
