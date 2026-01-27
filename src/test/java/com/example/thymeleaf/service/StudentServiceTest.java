package com.example.thymeleaf.service;

import com.example.thymeleaf.entity.Address;
import com.example.thymeleaf.entity.Student;
import com.example.thymeleaf.repository.AddressRepository;
import com.example.thymeleaf.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private StudentService studentService;

    private Student sampleStudent;
    private Address sampleAddress;

    @BeforeEach
    void setUp() {
        sampleAddress = new Address();
        sampleAddress.setId("addr-1");
        sampleAddress.setCity("Warszawa");

        sampleStudent = new Student();
        sampleStudent.setId("id-123");
        sampleStudent.setName("Jan Kowalski");
        sampleStudent.setAddress(sampleAddress);
    }

    @Test
    void shouldFindStudentById() {
        // Given
        when(studentRepository.findById("id-123")).thenReturn(Optional.of(sampleStudent));

        // When
        Student result = studentService.findById("id-123");

        // Then
        assertNotNull(result);
        assertEquals("Jan Kowalski", result.getName());
        verify(studentRepository, times(1)).findById("id-123");
    }

    @Test
    void shouldThrowExceptionWhenNotFound() {
        // Given
        when(studentRepository.findById("non-existent")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NoSuchElementException.class, () -> studentService.findById("non-existent"));
    }

    @Test
    void shouldSaveStudent() {
        // When
        Student savedStudent = studentService.save(sampleStudent);

        // Then
        assertNotNull(savedStudent);
        verify(studentRepository).save(sampleStudent);
        verify(addressRepository).save(sampleAddress);
    }

    @Test
    void shouldUpdateStudent() {
        // Given
        Student existingStudent = new Student();
        existingStudent.setId("id-123");
        existingStudent.setName("Stary Name");
        existingStudent.setAddress(new Address());

        Student updatedData = new Student();
        updatedData.setName("Nowy Name");
        updatedData.setAddress(new Address());
        updatedData.getAddress().setCity("Kraków");

        when(studentRepository.findById("id-123")).thenReturn(Optional.of(existingStudent));
        when(studentRepository.save(any(Student.class))).thenAnswer(i -> i.getArguments()[0]);

        // When
        Student result = studentService.update("id-123", updatedData);

        // Then
        assertEquals("Nowy Name", result.getName());
        assertEquals("Kraków", result.getAddress().getCity());
        verify(studentRepository).save(existingStudent);
    }

    @Test
    void shouldDeleteStudent() {
        // Given
        when(studentRepository.findById("id-123")).thenReturn(Optional.of(sampleStudent));

        // When
        studentService.deleteById("id-123");

        // Then
        verify(studentRepository).delete(sampleStudent);
    }
}