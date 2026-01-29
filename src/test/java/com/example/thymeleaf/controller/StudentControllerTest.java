package com.example.thymeleaf.controller;

import com.example.thymeleaf.dto.CreateStudentDTO;
import com.example.thymeleaf.dto.StudentResponseDTO;
import com.example.thymeleaf.dto.UpdateStudentDTO;
import com.example.thymeleaf.dto.mapper.StudentMapper;
import com.example.thymeleaf.entity.Address;
import com.example.thymeleaf.entity.Student;
import com.example.thymeleaf.repository.StudentRepository;
import com.example.thymeleaf.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentController sut;

    @Test
    void shouldShowStudents() {
        //given
        StudentResponseDTO dto = new StudentResponseDTO();
        List<StudentResponseDTO> mockDtos = List.of(dto);
        Student student = Mockito.mock(Student.class);

        when(studentRepository.findAll()).thenReturn(List.of(student));

        try (MockedStatic<StudentMapper> mockedMapper = mockStatic(StudentMapper.class)) {
            mockedMapper.when(() -> StudentMapper.toDTO(List.of(student))).thenReturn(mockDtos);

            //when
            ModelAndView output = sut.showStudents();

            //then
            assertThat(output).isNotNull();
            assertThat(output.getViewName()).isEqualTo("students");
            assertThat(output.getModel().get("students")).isEqualTo(mockDtos);
        }
    }

    @Test
    void shouldShowCreateForm() {
        //when
        ModelAndView output = sut.showCreateForm();

        //then
        assertThat(output).isNotNull();
        assertThat(output.getViewName()).isEqualTo("new-student");
        assertThat(output.getModel().get("student").getClass()).isEqualTo(CreateStudentDTO.class);
    }

    @Test
    void shouldCreate() {
        //given
        CreateStudentDTO studentDTO = Mockito.mock(CreateStudentDTO.class);
        BindingResult result = Mockito.mock(BindingResult.class);
        RedirectAttributes attributes = Mockito.mock(RedirectAttributes.class);
        Student student = Mockito.mock(Student.class);

        try (MockedStatic<StudentMapper> mockedMapper = mockStatic(StudentMapper.class)) {
            mockedMapper.when(() -> StudentMapper.toEntity(studentDTO)).thenReturn(student);

            //when
            String output = sut.create(studentDTO, result, attributes);

            //then
            assertThat(output).isEqualTo("redirect:/students");
        }
    }

    @Test
    void shouldCreateErr() {
        //given
        CreateStudentDTO studentDTO = Mockito.mock(CreateStudentDTO.class);
        BindingResult result = Mockito.mock(BindingResult.class);
        RedirectAttributes attributes = Mockito.mock(RedirectAttributes.class);

        when(result.hasErrors()).thenReturn(true);

        //when
        String output = sut.create(studentDTO, result, attributes);

        //then
        assertThat(output).isEqualTo("new-student");
    }


    @Test
    void shouldShowUpdateForm() {
        //given
        StudentResponseDTO studentDTO = Mockito.mock(StudentResponseDTO.class);
        Student student = Mockito.mock(Student.class);
        String ID_STUDENT = "idStudent";

        when(studentService.findById(ID_STUDENT)).thenReturn(student);

        try (MockedStatic<StudentMapper> mockedMapper = mockStatic(StudentMapper.class)) {
            mockedMapper.when(() -> StudentMapper.toDTO(student)).thenReturn(studentDTO);

            //when
            ModelAndView output = sut.showUpdateForm(ID_STUDENT);

            //then
            assertThat(output).isNotNull();
            assertThat(output.getViewName()).isEqualTo("edit-student");
            assertThat(output.getModel().get("student")).isEqualTo(studentDTO);
        }
    }

    @Test
    void shouldUpdate() {
        //given
        String ID_STUDENT = "idStudent";
        BindingResult result = Mockito.mock(BindingResult.class);
        RedirectAttributes attributes = Mockito.mock(RedirectAttributes.class);
        UpdateStudentDTO studentDTO = Mockito.mock(UpdateStudentDTO.class);
        Student student = Mockito.mock(Student.class);

        when(studentService.update(ID_STUDENT, student)).thenReturn(student);
        try (MockedStatic<StudentMapper> mockedMapper = mockStatic(StudentMapper.class)) {
            mockedMapper.when(() -> StudentMapper.toEntity(studentDTO)).thenReturn(student);


            //when
            String output = sut.update(ID_STUDENT,studentDTO, result, attributes);

            //then
            assertThat(output).isEqualTo("redirect:/students");
        }
    }

    @Test
    void shouldDelete() {
        //given
        String ID_STUDENT = "idStudent";
        RedirectAttributes attributes = Mockito.mock(RedirectAttributes.class);


        //when
        String output = sut.delete(ID_STUDENT, attributes);

        //then
        verify(studentService).deleteById(ID_STUDENT);
        assertThat(output).isEqualTo("redirect:/students");

    }

    @Test
    void shouldUpdateErr() {
        //given
        String ID_STUDENT = "idStudent";
        BindingResult result = Mockito.mock(BindingResult.class);
        RedirectAttributes attributes = Mockito.mock(RedirectAttributes.class);
        UpdateStudentDTO studentDTO = Mockito.mock(UpdateStudentDTO.class);
        when(result.hasErrors()).thenReturn(true);

        //when
        String output = sut.update(ID_STUDENT,studentDTO, result, attributes);

        //then
        assertThat(output).isEqualTo("edit-student");
    }


    private Student createStudent() {
        Student student = new Student();
        Address address = new Address();
        address.setId("address id");
        address.setZipCode("zipCode");
        address.setStreet("street");
        address.setNumber("number");
        address.setComplement("complement");
        address.setCity("city");
        address.setState("state");
        student.setId("student id");
        student.setName("name");
        student.setEmail("email");
        student.setBirthday(LocalDate.of(2000, 1, 1));
        student.setCreatedAt(LocalDateTime.of(LocalDate.of(2000, 1, 1), LocalTime.of(1, 1)));
        student.setAddress(address);

        return student;
    }

}