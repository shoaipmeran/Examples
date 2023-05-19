package com.digitalbooks.book.service;

import org.springframework.stereotype.Service;

import com.digitalbooks.book.dto.UserDto;
import com.digitalbooks.book.entity.User;
import com.digitalbooks.book.repository.UserRepository;



@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    //private RestTemplate restTemplate;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    /*
    @Override
    public ResponseDto getUser(Long userId) {

        ResponseDto responseDto = new ResponseDto();
        User user = userRepository.findById(userId).get();
        UserDto userDto = mapToUser(user);

        ResponseEntity<BookDto> responseEntity = restTemplate
                .getForEntity("http://localhost:8080/api/v1/digitalbooks" + user.getId(),
                BookDto.class);

        BookDto bookDto = responseEntity.getBody();

        System.out.println(responseEntity.getStatusCode());

        responseDto.setUser(userDto);
        responseDto.setBook(bookDto);

        return responseDto;
    }
	*/
    private UserDto mapToUser(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        return userDto;
    }

	public UserServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
    
	
}
