package com.example.thymeleaf.dto.mapper;

import com.example.thymeleaf.dto.CreateStudentDTO;
import com.example.thymeleaf.dto.StudentResponseDTO;
import com.example.thymeleaf.entity.Address;
import com.example.thymeleaf.entity.Student;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StudentMapperTest {
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String EMAIL = "mail@mail.com";
    private static final LocalDate BIRTHDAY = LocalDate.of(2000, 1, 1);
    private static final String ZIPCODE = "ZipCode";
    private static final String STREET = "Street";
    private static final String NUMBER = "Number";
    private static final String COMPLEMENT = "Complement";
    private static final String DISTRICT = "District";
    private static final String CITY = "City";
    private static final String STATE = "State";
    private static final LocalDateTime CREATED_AT = LocalDateTime.of(2000, 1, 1,1, 1, 1);



    @Test
    void shouldMapToEntity() {
        //given
        CreateStudentDTO studentDTO = Mockito.mock(CreateStudentDTO.class);
        when(studentDTO.getName()).thenReturn(NAME);
        when(studentDTO.getEmail()).thenReturn(EMAIL);
        when(studentDTO.getBirthday()).thenReturn(BIRTHDAY);
        when(studentDTO.getZipCode()).thenReturn(ZIPCODE);
        when(studentDTO.getStreet()).thenReturn(STREET);
        when(studentDTO.getNumber()).thenReturn(NUMBER);
        when(studentDTO.getComplement()).thenReturn(COMPLEMENT);
        when(studentDTO.getDistrict()).thenReturn(DISTRICT);
        when(studentDTO.getCity()).thenReturn(CITY);
        when(studentDTO.getState()).thenReturn(STATE);

        //when
        Student student = StudentMapper.toEntity(studentDTO);

        //then
        assertThat(student.getName()).isEqualTo(NAME);
        assertThat(student.getEmail()).isEqualTo(EMAIL);
        assertThat(student.getBirthday()).isEqualTo(BIRTHDAY);
        assertThat(student.getAddress().getZipCode()).isEqualTo(ZIPCODE);
        assertThat(student.getAddress().getStreet()).isEqualTo(STREET);
        assertThat(student.getAddress().getNumber()).isEqualTo(NUMBER);
        assertThat(student.getAddress().getComplement()).isEqualTo(COMPLEMENT);
        assertThat(student.getAddress().getDistrict()).isEqualTo(DISTRICT);
        assertThat(student.getAddress().getCity()).isEqualTo(CITY);
        assertThat(student.getAddress().getState()).isEqualTo(STATE);
    }

    @Test
    void shouldMapToDto(){
        //given
        Address address = Mockito.mock(Address.class);
        when(address.getZipCode()).thenReturn(ZIPCODE);
        when(address.getStreet()).thenReturn(STREET);
        when(address.getNumber()).thenReturn(NUMBER);
        when(address.getComplement()).thenReturn(COMPLEMENT);
        when(address.getDistrict()).thenReturn(DISTRICT);
        when(address.getCity()).thenReturn(CITY);
        when(address.getState()).thenReturn(STATE);

        Student student = Mockito.mock(Student.class);
        when(student.getId()).thenReturn(ID);
        when(student.getName()).thenReturn(NAME);
        when(student.getEmail()).thenReturn(EMAIL);
        when(student.getBirthday()).thenReturn(BIRTHDAY);
        when(student.getCreatedAt()).thenReturn(CREATED_AT);
        when(student.getAddress()).thenReturn(address);

        //when
        StudentResponseDTO studentResponseDTO = StudentMapper.toDTO(student);

        //then
        assertThat(studentResponseDTO.getId()).isEqualTo(ID);
        assertThat(studentResponseDTO.getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(studentResponseDTO.getName()).isEqualTo(NAME);
        assertThat(studentResponseDTO.getEmail()).isEqualTo(EMAIL);
        assertThat(studentResponseDTO.getBirthday()).isEqualTo(BIRTHDAY);
        assertThat(studentResponseDTO.getZipCode()).isEqualTo(ZIPCODE);
        assertThat(studentResponseDTO.getStreet()).isEqualTo(STREET);
        assertThat(studentResponseDTO.getNumber()).isEqualTo(NUMBER);
        assertThat(studentResponseDTO.getComplement()).isEqualTo(COMPLEMENT);
        assertThat(studentResponseDTO.getDistrict()).isEqualTo(DISTRICT);
        assertThat(studentResponseDTO.getCity()).isEqualTo(CITY);
        assertThat(studentResponseDTO.getState()).isEqualTo(STATE);

    }

    @Test
    void shouldMapToDtoList(){
        //given
        Address address = Mockito.mock(Address.class);
        when(address.getZipCode()).thenReturn(ZIPCODE);
        when(address.getStreet()).thenReturn(STREET);
        when(address.getNumber()).thenReturn(NUMBER);
        when(address.getComplement()).thenReturn(COMPLEMENT);
        when(address.getDistrict()).thenReturn(DISTRICT);
        when(address.getCity()).thenReturn(CITY);
        when(address.getState()).thenReturn(STATE);

        Student student = Mockito.mock(Student.class);
        when(student.getId()).thenReturn(ID);
        when(student.getName()).thenReturn(NAME);
        when(student.getEmail()).thenReturn(EMAIL);
        when(student.getBirthday()).thenReturn(BIRTHDAY);
        when(student.getCreatedAt()).thenReturn(CREATED_AT);
        when(student.getAddress()).thenReturn(address);

        //when
        List<StudentResponseDTO> studentResponseDTO = StudentMapper.toDTO(List.of(student));

        //then
        assertThat(studentResponseDTO.get(0).getId()).isEqualTo(ID);
        assertThat(studentResponseDTO.get(0).getCreatedAt()).isEqualTo(CREATED_AT);
        assertThat(studentResponseDTO.get(0).getName()).isEqualTo(NAME);
        assertThat(studentResponseDTO.get(0).getEmail()).isEqualTo(EMAIL);
        assertThat(studentResponseDTO.get(0).getBirthday()).isEqualTo(BIRTHDAY);
        assertThat(studentResponseDTO.get(0).getZipCode()).isEqualTo(ZIPCODE);
        assertThat(studentResponseDTO.get(0).getStreet()).isEqualTo(STREET);
        assertThat(studentResponseDTO.get(0).getNumber()).isEqualTo(NUMBER);
        assertThat(studentResponseDTO.get(0).getComplement()).isEqualTo(COMPLEMENT);
        assertThat(studentResponseDTO.get(0).getDistrict()).isEqualTo(DISTRICT);
        assertThat(studentResponseDTO.get(0).getCity()).isEqualTo(CITY);
        assertThat(studentResponseDTO.get(0).getState()).isEqualTo(STATE);

    }


}